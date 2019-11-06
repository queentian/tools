package com.ykz.model;


public class ReadExcelModel {

    private String filePath;
    private String headSheetName;
    private String bodySheetName;
    private String readSheetName;

    public ReadExcelModel() {
    }

    public ReadExcelModel(String filePath, String headSheetName, String bodySheetName, String readSheetName) {
        this.filePath = filePath;
        this.headSheetName = headSheetName;
        this.bodySheetName = bodySheetName;
        this.readSheetName = readSheetName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getHeadSheetName() {
        return headSheetName;
    }

    public void setHeadSheetName(String headSheetName) {
        this.headSheetName = headSheetName;
    }

    public String getBodySheetName() {
        return bodySheetName;
    }

    public void setBodySheetName(String bodySheetName) {
        this.bodySheetName = bodySheetName;
    }

    public String getReadSheetName() {
        return readSheetName;
    }

    public void setReadSheetName(String readSheetName) {
        this.readSheetName = readSheetName;
    }
}
