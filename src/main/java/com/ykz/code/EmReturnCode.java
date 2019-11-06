package com.ykz.code;

/**
 * 返回码
 * @author yangkz
 */
public enum EmReturnCode implements CommonError {

    SUCCESS("0000", "成功"),
    FAIL("0001", "失败"),
    UNKNOWN_ERROR("0002", "未知错误"),
    NOT_FOUND("0004", "未找到对应的记录"),
    FILE_EXISTE("0005","文件已经存在")
    ;
    private String code;
    private String message;

    EmReturnCode(String code, String msg) {
        this.code = code;
        this.message = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.message;
    }

}
