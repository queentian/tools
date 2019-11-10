package com.ykz.model;

import lombok.Data;

import java.util.Date;

@Data
public class FileModel {

    private Integer id;
    private String filePath;
    private Date uploadTime;
}
