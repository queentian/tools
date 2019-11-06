package com.ykz.controller;

import com.ykz.code.EmReturnCode;
import com.ykz.model.ReadExcelModel;
import com.ykz.model.ResultData;
import com.ykz.exception.BusinessException;
import com.ykz.service.FileOptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/createConfigs")
@RestController
public class CreateConfigFileController extends BaseController{

    private static final Logger log = LoggerFactory.getLogger(CreateConfigFileController.class);
    @Autowired
    FileOptionService fileOptionService;

    @PostMapping("/create")
    public ResultData createConfigs(@RequestBody ReadExcelModel readExcelModel) throws BusinessException {
        //判断传入对象是否为 null
        if (readExcelModel == null){
            throw new BusinessException(EmReturnCode.FILE_EXISTE, "传入参数为 null");
        }
        if (readExcelModel.getReadSheetName() == null){
            //根据 index 页读取所有的 sheet 页

        }else{
            //读取指定的 sheet 页

        }

        return new ResultData(EmReturnCode.SUCCESS);
    }


}
