package com.ykz.code;

/**
 * 公共错误
 * @author yangkz
 */
public interface CommonError {
    /**
     * 获取返回码
     * @return
     */
    String getCode();

    /**
     * 获取返回信息
     * @return
     */
    String getMsg();
}
