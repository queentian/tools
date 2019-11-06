package com.ykz.model;

import com.ykz.code.CommonError;

/**
 *返回消息实体类
 * @author yangkz
 */
public class ResultData {

    private String code;
    private String message;
    private Object data;

    public ResultData() {
    }

    public ResultData(CommonError commonError){
        this.code = commonError.getCode();
        this.message = commonError.getMsg();
    }

    public ResultData(CommonError commonError, Object data){
        this.code = commonError.getCode();
        this.message = commonError.getMsg();
        this.data = data;
    }

    public ResultData(CommonError commonError, String msg, Object data) {
        this.code = commonError.getCode();
        this.message = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
