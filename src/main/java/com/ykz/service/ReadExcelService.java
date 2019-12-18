package com.ykz.service;

import com.ykz.exception.BusinessException;

import java.util.List;


public interface ReadExcelService {

    /**
     * 根据文件路径获取到 excel 文件中的所有sheet页名称和位置
     * @param filePath 文件路径
     * @return excel 文件中的所有sheet页名称和位置
     */
    List<String> getSheetName(String filePath) throws BusinessException;
}
