package com.ykz.controller;

import com.ykz.model.ResultData;
import com.ykz.code.EmReturnCode;
import com.ykz.exception.BusinessException;
import com.ykz.service.FileOptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件
 * @author yangkz
 */
@RequestMapping("/fileOption")
@RestController
public class FileOptionController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(FileOptionController.class);

    @Autowired
    private FileOptionService fileOptionService;

    @PostMapping("/upload")
    public ResultData uploadFile(MultipartFile file) throws BusinessException {
        log.debug("----------start file upload----------");
        //判断文件是否为空
        if (file == null || file.isEmpty()) {
            throw new BusinessException(EmReturnCode.FAIL, "文件不能为空");
        }
        String filePath = fileOptionService.uploadFile(file);
        return filePath != null ?
                new ResultData(EmReturnCode.SUCCESS, "文件上传成功", filePath) :
                new ResultData(EmReturnCode.FAIL, "文件上传失败");
    }

}
