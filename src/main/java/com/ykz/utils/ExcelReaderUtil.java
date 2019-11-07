package com.ykz.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;

/**
 * Excel读取公共类。
 * @author elon
 * @version 2018年7月7日
 */
public class ExcelReaderUtil extends ExcelAbstract {

    /**
     * 提取列名称的正则表达式
     */
    private static final String DISTILL_COLUMN_REG = "^([A-Z]{1,})";

    /**
     * 读取excel的每一行记录。map的key是列号(A、B、C...), value是单元格的值。如果单元格是空，则没有值。
     */
    private List<Map<String, String>> dataList = new ArrayList<>();

    @Override
    public void optRows(int curRow, Map<String, String> rowValueMap) {

        Map<String, String> dataMap = new HashMap<>();
        rowValueMap.forEach((k,v)->dataMap.put(removeNum(k), v));
        dataList.add(dataMap);
    }

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

    public List<Map<String, String>> getDataList() {
        return dataList;
    }
}
