package com.t2w.utils.exception;

import java.security.GeneralSecurityException;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-08-09 12:21
 * @name com.t2w.utils.exception.EncryptionKeySizeException.java
 * @see describing
 */
public class EncryptionKeySizeException extends RuntimeException {

    public EncryptionKeySizeException() {
    }

    public EncryptionKeySizeException(String msg) {
        super(msg);
    }
}
