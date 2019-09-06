package com.t2w.utils.vaptcha;

import com.t2w.utils.common.HttpUtils;
import com.t2w.utils.common.MapUtils;
import com.t2w.utils.vaptcha.configuration.Vaptcha;
import com.t2w.utils.vaptcha.domain.VaptchaStatus;

import java.util.Map;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-09-04 15:22
 * @name com.t2w.utils.Vaptcha.vaptchaUtils.java
 * @see describing Vaptcha 手势验证码校验工具类<br/>
 * 网址 <a href="https://www.vaptcha.com/">https://www.vaptcha.com/</a>
 */
public class VaptchaUtils {

    /** 校验 token 的网址 */
    private static final String URL = "http://api.vaptcha.com/v2/validate";

    /**
     * @param vaptcha 校验码的参数对象(id, secretkey, token 不可为空)
     * @return com.t2w.utils.vaptcha.domain.VaptchaStatus 校验的结果状态
     * @date 2019-09-06 17:10
     * @see describing 校验前台手势验证码产生的 token
     */
    public static VaptchaStatus vaptcha(Vaptcha vaptcha) {
        try {
            Map<String, Object> params = MapUtils.toMap(vaptcha);
            String result = HttpUtils.sendPost(URL, params);
            if (result == null) {
                return VaptchaStatus.DELAY;
            }
            Map<String, Object> map = MapUtils.toMap(result);
            if (map != null) {
                Object score = map.get("score");
                Object success = map.get("success");
                Object msg = map.get("msg");
                if ("1".equals(success.toString())) {
                    Integer integer = new Integer(score.toString());
                    if (integer > 20) {
                        return VaptchaStatus.SUCCESS;
                    } else {
                        return VaptchaStatus.FAIL;
                    }
                } else {
                    String string = msg.toString();
                    switch (string) {
                        case "token-expired":
                            return VaptchaStatus.EXPIRED;
                        case "id-empty":
                        case "id-error":
                        case "scene-error":
                        case "bad-request":
                            return VaptchaStatus.EXCEPTION;
                        default:
                            return VaptchaStatus.FAIL;
                    }
                }
            }
        } catch (Exception ignored) { }
        return VaptchaStatus.EXCEPTION;
    }

}
