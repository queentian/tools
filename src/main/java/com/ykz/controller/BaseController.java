package com.ykz.controller;

import com.ykz.code.EmReturnCode;
import com.ykz.model.ResultData;
import com.ykz.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yangkz
 */
@RestController
public class BaseController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultData handleException(HttpServletRequest request, Exception e){
        if (e instanceof BusinessException){
            BusinessException businessException = (BusinessException)e;
            return new ResultData(businessException);
        }else{
            return new ResultData(EmReturnCode.UNKNOWN_ERROR);
        }
    }
}
