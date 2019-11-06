package com.ykz.service;

import com.ykz.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yangkz
 */
public interface FileOptionService {

    /**
     * 文件上传
     * @param file 文件对象
     * @return 文件保存路径
     * @throws BusinessException 业务异常
     */
    String uploadFile(MultipartFile file) throws BusinessException;
}
