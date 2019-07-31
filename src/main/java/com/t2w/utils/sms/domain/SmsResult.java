package com.t2w.utils.sms.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 15:29
 * @name com.t2w.utils.sms.domain.SmsResult.java
 * @see 短信服务返回结果通用类
 */
@Data
@Accessors(chain = true)
public class SmsResult {

    /** 提供短信服务的第三方类型 */
    private SmsType smsType;

    /** 发送短信的标识id，由第三方提供 */
    private LinkedList<String> sid = new LinkedList<String>();

    /** 总数 */
    private int total = 0;

    /** 成功数 */
    private int success = 0;

    /** 失败数 */
    private int fail = 0;

    /** 发送短信成功的手机数组 */
    private LinkedHashSet<String> successPhones = new LinkedHashSet<String>();

    /** 发送短信失败的手机数组 */
    private LinkedHashSet<String> failPhones = new LinkedHashSet<String>();

    /** 发送短信失败的错误消息 */
    private LinkedList<String> errorMsg = new LinkedList<String>();

}
