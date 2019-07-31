package com.t2w.utils.exception;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 17:40
 * @name com.t2w.utils.exception.SmsException.java
 * @see 发送短信异常
 */
public class SmsException extends RuntimeException {

    public SmsException() {
    }

    public SmsException(String message) {
        super(message);
    }
}
