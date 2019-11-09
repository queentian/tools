package com.ykz.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * POI事件驱动读取Excel文件的抽象类。
 */
public abstract class ExcelAbstract extends DefaultHandler {
    // 共享字符串表
    private SharedStringsTable sst;

    // 上一次的内容
    private String lastContents;
    private boolean nextIsString;

    private int sheetIndex = -1;
    private String curCellName = "";
    private Map<String, String> rowValueMap = new HashMap<>();
    // 当前行
    private int curRow = 0;


    /**
     * 遍历工作簿中所有的电子表格
     * @param filename
     * @throws Exception
     */
    public void process(String filename) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);
        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
    }

    /**
     * 只遍历一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3
     * @param filename 文件路径
     * @param sheetId 第几个 sheet 页
     * @throws Exception
     */
    public void process(String filename, int sheetId) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);
        // 根据 rId# 或 rSheet# 查找sheet
        InputStream sheet2 = r.getSheet("rId" + sheetId);
        sheetIndex++;
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst)
            throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }

    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        // c => 单元格
        if (Objects.equals("c", name)) {
            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
            String cellType = attributes.getValue("t");
            if (Objects.equals("s", cellType)) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
        }
        // 置空
        lastContents = "";

        // 记录当前读取单元格的名称
        String cellName = attributes.getValue("r");
        if (cellName != null && !cellName.isEmpty()) {
            curCellName = cellName;
        }

    }

    public void endElement(String uri, String localName, String name)
            throws SAXException {

//      System.out.println("endElement: " + localName + ", " + name);

        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (nextIsString) {
            try {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx))
                        .toString();
            } catch (Exception e) {

            }
        }
        // t元素也包含字符串
        if (Objects.equals("v", name)) {
            String value = lastContents.trim();
            value = value.equals("") ? " " : value;
            rowValueMap.put(curCellName, value);
        } else {
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if (Objects.equals("row", name)) {
                getRows(sheetIndex + 1, curRow, rowValueMap);
                rowValueMap.clear();
                curRow++;
            }
        }
    }

    public void characters(char[] ch, int start, int length){
        // 得到单元格内容的值
        lastContents += new String(ch, start, length);
    }

    /**
     * 获取行数据回调
     * @param sheetIndex
     * @param curRow
     * @param rowValueMap
     */
    abstract void getRows(int sheetIndex, int curRow, Map<String, String> rowValueMap);
}