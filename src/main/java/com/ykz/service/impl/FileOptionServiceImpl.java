package com.ykz.service.impl;

import com.ykz.code.EmReturnCode;
import com.ykz.exception.BusinessException;
import com.ykz.service.FileOptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 处理文件
 * @author yangkz
 */
@Service
public class FileOptionServiceImpl implements FileOptionService {

    private static final Logger log = LoggerFactory.getLogger(FileOptionServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file) throws BusinessException {

        String origFile = file.getOriginalFilename();
        log.info("开始上传文件:"+origFile);
        String filePath = null;
        try {
            filePath = ResourceUtils.getURL("upload").getPath().concat(File.separator).concat(origFile);
            File saveFile = new File(filePath);
            file.transferTo(saveFile);
            if (log.isDebugEnabled()){
                log.debug("文件上传成功："+ filePath);
            }
        } catch (IOException e) {
            String errorMsg = "上传文件：["+ filePath +"] 失败";
            log.error(errorMsg, e);
            throw new BusinessException(EmReturnCode.FAIL, errorMsg);
        }
        return filePath;
    }

}
