package com.ykz.service.impl;

import com.ykz.code.EmReturnCode;
import com.ykz.exception.BusinessException;
import com.ykz.service.ReadExcelService;
import com.ykz.utils.BodySheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReadExcelServiceImpl implements ReadExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ReadExcelServiceImpl.class);

    @Autowired
    BodySheet bodySheet;

    @Override
    public List<String> getSheetName(String filePath) throws BusinessException {
        List<String> sheetList = null;
        try {
            sheetList = bodySheet.getSheetName(filePath);
        } catch (Exception e) {
            String errorMessage = "获取sheet页失败";
            if (logger.isErrorEnabled()){
                logger.error(errorMessage);
            }
            throw new BusinessException(EmReturnCode.FAIL, errorMessage);
        }
        return sheetList;
    }
}
