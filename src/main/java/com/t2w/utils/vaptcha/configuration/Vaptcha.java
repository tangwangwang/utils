package com.t2w.utils.vaptcha.configuration;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-09-04 16:29
 * @name com.t2w.utils.vaptcha.domain.VaptchaCon.java
 * @see describing 验证码对象
 */
@Data
@Accessors(chain = true)
public class Vaptcha {

    /** 验证单元的VID(必填) */
    private String id;
    /** 验证单元的Key(必填) */
    private String secretkey;
    /** 验证单元场景，需与前端配置参数中的scene一致，e.g: '01' */
    private String scene;
    /** 客户端验证成功得到的token(必填) */
    private String token;
    /** 获取用户的remote address */
    private String ip;

}
