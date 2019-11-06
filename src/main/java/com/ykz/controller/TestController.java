package com.ykz.controller;

import com.ykz.model.ResultData;
import com.ykz.code.EmReturnCode;
import com.ykz.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller
 * @author yangkz
 */
@RestController
public class TestController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/hello")
    public ResultData testHello(){
        if (logger.isDebugEnabled()){
            logger.debug("hello");
        }
        return new ResultData(EmReturnCode.SUCCESS, "filePath");
    }

    @RequestMapping("/throw")
    public ResultData testThrow() throws BusinessException {
        throw new BusinessException(EmReturnCode.NOT_FOUND, "测试异常的抛出");
    }
}
