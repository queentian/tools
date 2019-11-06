package com.ykz.controller;

import com.ykz.model.ResultData;
import com.ykz.code.EmReturnCode;
import com.ykz.exception.BusinessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller
 * @author yangkz
 */
@RestController
public class TestController extends BaseController{

    @RequestMapping("/hello")
    public ResultData testHello(){

        return new ResultData(EmReturnCode.SUCCESS, "filePath");
    }

    @RequestMapping("/throw")
    public ResultData testThrow() throws BusinessException {
        throw new BusinessException(EmReturnCode.NOT_FOUND, "测试异常的抛出");
    }


}
