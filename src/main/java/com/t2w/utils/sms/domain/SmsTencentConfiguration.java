package com.t2w.utils.sms.domain;

import com.t2w.utils.common.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 17:17
 * @name com.t2w.utils.sms.domain.SmsTencentConfiguration.java
 * @see 腾讯云短信配置信息类（配置使用短信业务的基础参数信息）
 */
@Data
@Accessors(chain = true)
public class SmsTencentConfiguration {

    /** 国家（或地区）码 */
    private String nationCode = "86";
    /** 短信应用 SDK AppID */
    private int appid;
    /** 短信应用 SDK AppKey */
    private String appkey;
    /** 需要群发短信的手机号码 */
    private String[] phones;
    /** 需要单个发送短信的手机号码 */
    private String phone;
    /** 短信模板 ID，需要在短信应用中申请 */
    private int templateId;
    /** 需要发送短信模板中的参数 */
    private String[] params;
    /** 短信`签名内容` */
    private String smsSign;
    /** 短信码号扩展号，格式为纯数字串，其他格式无效。默认没有开通 */
    private String extend = "";
    /** 用户的 session 内容，腾讯 server 回包中会原样返回，可选字段，不需要就填空 */
    private String ext = "";

}
