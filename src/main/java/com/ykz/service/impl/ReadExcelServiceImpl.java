package com.ykz.service.impl;

import com.ykz.code.EmReturnCode;
import com.ykz.exception.BusinessException;
import com.ykz.service.ReadExcelService;
import com.ykz.utils.BodySheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReadExcelServiceImpl implements ReadExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ReadExcelServiceImpl.class);

    @Override
    public String getAllData(String filePath) throws BusinessException {
        String errorMsg;
        BodySheet er = new BodySheet();
        try {
            Map<String, String> sheetMap = er.process(filePath);
//            Map<String, List> dataMap = er.getExcelMap();
        } catch (Exception e) {
            errorMsg = "读取 excel 表格失败";
            logger.error(errorMsg, e);
            throw new BusinessException(EmReturnCode.FAIL, errorMsg);
        }
        return null;
    }
}
