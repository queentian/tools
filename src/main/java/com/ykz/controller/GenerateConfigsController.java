package com.ykz.controller;

import com.ykz.code.EmReturnCode;
import com.ykz.model.ResultData;
import com.ykz.exception.BusinessException;
import com.ykz.service.FileOptionService;
import com.ykz.service.ReadExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/generateConfigs")
@RestController
public class GenerateConfigsController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(GenerateConfigsController.class);
    @Autowired
    FileOptionService fileOptionService;
    @Autowired
    ReadExcelService readExcelService;

    /**
     * 接收一个excel 文档，保存到本地并且读取excel中的所有内容返回给前端
     * @param file excel文件
     * @return 所有excel文档中的内容
     * @throws BusinessException 业务异常
     */
    @PostMapping("/getExcelData")
    public ResultData createConfigs(MultipartFile file) throws BusinessException {
        //判断文件是否为空
        if (file == null || file.isEmpty()) {
            logger.error("excel文件不能为空");
            throw new BusinessException(EmReturnCode.FAIL, "excel文件不能为空");
        }
        String filePath = fileOptionService.uploadFile(file);
        // TODO：返回类型根据等读取excel的方法实现了之后再确定，暂时返回String
        String data = readExcelService.getAllData(filePath);

        return new ResultData(EmReturnCode.SUCCESS, data);
    }
}
