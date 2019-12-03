package com.ykz.utils;

import com.ykz.bean.BlockBean;
import com.ykz.bean.FieldBean;
import com.ykz.bean.XmlBean;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 读取 excel 所有内容的工具类。
 */
public class HeadSheet extends ExcelAbstract {

    // 提取列名称的正则表达式
    private static final String DISTILL_COLUMN_REG = "^([A-Z]+)";
    private XmlBean xmlBean = new XmlBean();
    private BlockBean inBlock = new BlockBean();
    private BlockBean outBlock = new BlockBean();
    private StackBean stackBean = new StackBean();
    private boolean isOut = false;

    /**
     * 日期数字转换为字符串。
     *
     * @param dateNum excel中存储日期的数字
     * @return 格式化后的字符串形式
     */
    public static String dateNum2Str(String dateNum) {
        Date date = HSSFDateUtil.getJavaDate(Double.parseDouble(dateNum));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * 删除单元格名称中的数字，只保留列号。
     * @param cellName 单元格名称。如:A1
     * @return 列号。如：A
     */
    private String removeNum(String cellName) {
        Pattern pattern = Pattern.compile(DISTILL_COLUMN_REG);
        Matcher m = pattern.matcher(cellName);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }


    @Override
    public void getRows(int sheetIndex, int curRow, Map<String, String> rowValueMap) {
        Map<String, String> dataMap = new HashMap<>();
        rowValueMap.forEach((k,v)->dataMap.put(removeNum(k), v));
        // 如果是输如输出则直接结束
        if (Objects.equals(dataMap.get("A"), "输入")){
            return;
        }
        if (Objects.equals(dataMap.get("A"), "输出")){
            isOut = true;
            return;
        }
        BlockBean bb = isOut? outBlock: inBlock;
        GenerateConfigUtils.saveRow(dataMap, stackBean, bb);
    }



    public XmlBean getXmlBean(){
        this.xmlBean.setInList(inBlock);
        this.xmlBean.setOutList(outBlock);
        return this.xmlBean;

    }

    public static void main(String[] args) {
        try {
            HeadSheet excel = new HeadSheet();
            String file = "/Users/yangkz/IdeaProjects/tools/src/main/resources/upload/韶关公积金.xlsx";
            excel.process(file, 1);
            System.out.println(excel.getXmlBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
