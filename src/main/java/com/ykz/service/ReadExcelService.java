package com.ykz.service;

import com.ykz.exception.BusinessException;
import com.ykz.model.ReadExcelModel;


public interface ReadExcelService {

    /**
     * 根据文件路径获取到 excel 文件中的所有内容
     * @param filePath 文件路径
     * @return excel 的所有内容
     */
    String getAllData(String filePath) throws BusinessException;
}
