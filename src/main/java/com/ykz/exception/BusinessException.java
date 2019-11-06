package com.ykz.exception;

import com.ykz.code.CommonError;

/**
 * @author yangkz
 */
public class BusinessException extends Exception implements CommonError {

    private CommonError commonError;
    private String exceptionMsg;

    public BusinessException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    public BusinessException(CommonError commonError, String exceptionMsg) {
        super();
        this.commonError = commonError;
        this.exceptionMsg = exceptionMsg;
    }

    @Override
    public String getCode() {
        return this.commonError.getCode();
    }

    @Override
    public String getMsg() {
        return exceptionMsg == null ? this.commonError.getMsg()
                :exceptionMsg;
    }
}
