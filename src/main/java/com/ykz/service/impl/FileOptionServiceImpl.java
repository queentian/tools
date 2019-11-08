package com.ykz.service.impl;

import com.ykz.code.EmReturnCode;
import com.ykz.exception.BusinessException;
import com.ykz.service.FileOptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 处理文件
 * @author yangkz
 */
@Service
public class FileOptionServiceImpl implements FileOptionService {

    private static final Logger logger = LoggerFactory.getLogger(FileOptionServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file) throws BusinessException {
        String errorMessage = null;
        String origFile = file.getOriginalFilename();
        String fileExtension = origFile.substring(origFile.lastIndexOf("."));
        if (!".xlsx".equals(fileExtension.toLowerCase())) {
            errorMessage = "文件格式必须是 xlsx";
            logger.error(errorMessage);
            throw new BusinessException(EmReturnCode.FAIL, errorMessage);

        }
        if (logger.isDebugEnabled()){
            logger.debug("开始上传文件:".concat(origFile));
        }
        String filePath = null;
        try {
            filePath = ResourceUtils.getURL("upload").getPath().concat(File.separator).concat(origFile);
            File saveFile = new File(filePath);
            file.transferTo(saveFile);
            if (logger.isInfoEnabled()){
                logger.info("文件上传成功：".concat(filePath));
            }
        } catch (IOException e) {
            errorMessage = "上传文件：["+ filePath +"] 失败";
            logger.error(errorMessage);
            throw new BusinessException(EmReturnCode.FAIL, errorMessage);
        }
        if (filePath == null){
            errorMessage = "上传文件：["+ filePath +"] 失败";
            logger.error(errorMessage);
            throw new BusinessException(EmReturnCode.FAIL, errorMessage);
        }
        return filePath;
    }

}
