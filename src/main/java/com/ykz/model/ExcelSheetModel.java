package com.ykz.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ExcelSheetModel {

    private Integer id;
    private String sheetIndex;
    private String sheetName;
    private Integer fileId;

}
