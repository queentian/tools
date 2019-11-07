package com.ykz.utils;

public class TestExcel {
    public static void main(String[] args) {
        try {
            ExcelReaderUtil excel = new ExcelReaderUtil();
            excel.readOneSheet("C:\\Users\\yangkz\\Desktop\\汕尾UAT商贷测试账号.xlsx", 1);
            System.out.println(excel.getDataList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
