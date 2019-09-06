package com.t2w.utils.vaptcha.domain;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-09-04 15:28
 * @name com.t2w.utils.vaptcha.domain.VaptchaStatus.java
 * @see describing 验证码验证状态
 */
public enum VaptchaStatus {

    /** 成功状态 */
    SUCCESS,
    /** 失败状态 */
    FAIL,
    /** 网络延迟状态, 无响应 */
    DELAY,
    /** token 过期 */
    EXPIRED,
    /** 请求异常 */
    EXCEPTION;

}
