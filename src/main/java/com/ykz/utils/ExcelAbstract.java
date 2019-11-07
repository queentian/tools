package com.ykz.utils;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
 *
 * @author elon
 * @version 2018年7月7日
 */
public abstract class ExcelAbstract extends DefaultHandler {
    private SharedStringsTable sst;
    private String lastContents;
    private boolean nextIsString;

    private int curRow = 0;
    private String curCellName = "";

    /**
     * 读取当前行的数据。key是单元格名称如A1,value是单元格中的值。如果单元格式空,则没有数据。
     */
    private Map<String, String> rowValueMap = new HashMap<>();

    /**
     * 处理单行数据的回调方法。
     *
     * @param curRow 当前行号
     * @param rowValueMap 当前行的值
     * @throws SQLException
     */
    public abstract void optRows(int curRow, Map<String, String> rowValueMap);

    /**
     * 读取Excel指定sheet页的数据。
     *
     * @param filePath 文件路径
     * @param sheetNum sheet页编号.从1开始。
     * @throws Exception
     */
    public void readOneSheet(String filePath, int sheetNum) throws Exception {
        OPCPackage pkg = OPCPackage.open(filePath);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = getSheetParser(sst);

        // 根据 rId# 或 rSheet# 查找sheet
        InputStream sheet2 = r.getSheet("rId" + sheetNum);
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
        pkg.close();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        // c => 单元格
        if (name.equals("c")) {

            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
            String cellType = attributes.getValue("t");
            if (cellType != null && cellType.equals("s")) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
        }

        // 置空
        lastContents = "";

        /**
         * 记录当前读取单元格的名称
         */
        String cellName = attributes.getValue("r");
        if (cellName != null && !cellName.isEmpty()) {
            curCellName = cellName;
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (nextIsString) {
            try {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
            } catch (Exception e) {

            }
        }

        // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
        // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
        if (name.equals("v")) {
            String value = lastContents.trim();
            value = value.equals("") ? " " : value;
            rowValueMap.put(curCellName, value);
        } else {
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if (name.equals("row")) {
                optRows(curRow, rowValueMap);
                rowValueMap.clear();
                curRow++;
            }
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        // 得到单元格内容的值
        lastContents += new String(ch, start, length);
    }

    /**
     * 获取单个sheet页的xml解析器。
     * @param sst
     * @return
     * @throws SAXException
     */
    private XMLReader getSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }
}