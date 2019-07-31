package com.t2w.utils.sms.domain;

import lombok.Getter;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 16:06
 * @name com.t2w.utils.sms.domain.SmsType.java
 * @see
 */
@Getter
public enum SmsType {
    TENCENT("腾讯云"),
    ALIBABA("阿里云");
    private String name;

    SmsType() {
    }

    private SmsType(String name) {
        this.name = name;
    }
}
