package com.ykz.service.impl;

import com.ykz.code.EmReturnCode;
import com.ykz.exception.BusinessException;
import com.ykz.service.ReadExcelService;
import com.ykz.utils.ExcelReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReadExcelServiceImpl implements ReadExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ReadExcelServiceImpl.class);

    @Override
    public String getAllData(String filePath) throws BusinessException {
        String errorMsg;
        ExcelReaderUtil er = new ExcelReaderUtil();
        try {
            er.process(filePath);
            er.getExcelMap();
        } catch (Exception e) {
            errorMsg = "读取 excel 表格失败";
            logger.error(errorMsg, e);
            throw new BusinessException(EmReturnCode.FAIL, errorMsg);
        }
        return null;
    }
}
