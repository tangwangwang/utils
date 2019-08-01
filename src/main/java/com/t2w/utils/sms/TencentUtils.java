package com.t2w.utils.sms;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.t2w.utils.common.StringUtils;
import com.t2w.utils.exception.SmsException;
import com.t2w.utils.sms.domain.SmsResult;
import com.t2w.utils.sms.configuration.SmsTencentConfiguration;
import com.t2w.utils.sms.domain.SmsType;
import org.json.JSONException;

import java.io.IOException;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 14:35
 * @name com.t2w.utils.sms.TencentUtils.java
 * @see 腾讯云短信服务工具包，需要在程序中配置 SmsTencentConfiguration，可直接在容器中加入一个该类型的 bean 进行使用
 */
public class TencentUtils {

    /**
     * @param configuration 腾讯云配置类，SmsTencentConfiguration 中的 appid, appkey, phone 字段必须填写
     *                      appid       短信应用 SDK AppID
     *                      appkey      短信应用 SDK AppKey
     *                      phone       需要发送短信的号码
     * @param msg           需要发送的短信内容
     * @return com.t2w.utils.sms.domain.SmsResult 发送短信的结果集
     * @date 2019-07-29 16:49
     * @see describing      单个号码发送短信，按短信内容直接发送，短信内容需要符合第三方提供的模板内容
     */
    public static SmsResult sendMessage(SmsTencentConfiguration configuration, String msg) {
        SmsResult smsResult = new SmsResult();
        smsResult.setSmsType(SmsType.TENCENT).setTotal(1);
        try {
            String phone = configuration.getPhone();
            if (StringUtils.isEmpty(phone)) {
                throw new SmsException("发送短信的手机号码为空。");
            }
            SmsSingleSender ssender = new SmsSingleSender(configuration.getAppid(), configuration.getAppkey());
            SmsSingleSenderResult result = ssender.send(0, configuration.getNationCode(), phone, msg
                    , configuration.getExtend(), configuration.getExt());
            if (result.result == 0) {
                smsResult.setSuccess(1).getSid().add(result.sid);
                smsResult.getSuccessPhones().add(phone);
            } else {
                smsResult.setFail(1).getSid().add(result.sid);
                smsResult.getFailPhones().add(phone);
                smsResult.getErrorMsg().add(result.errMsg);
            }
        } catch (HTTPException | JSONException | IOException e) {
            // HTTP 响应码错误 | JSON 解析错误 | 网络 IO 错误
            e.printStackTrace();
        }
        return smsResult;
    }

    /**
     * @param configuration 腾讯云配置类，SmsTencentConfiguration 中的 appid, appkey, phone, templateId, params, smsSign 字段必须填写
     *                      appid       短信应用 SDK AppID
     *                      appkey      短信应用 SDK AppKey
     *                      phone       需要发送短信的号码
     *                      templateId  发送内容的模板id，需要提供自己的腾讯云短信模板
     *                      params      短信模板内的参数，具体参数个数按照模板提供
     *                      smsSign     短信签名
     * @return com.t2w.utils.sms.domain.SmsResult 发送短信的结果集
     * @date 2019-07-29 16:51
     * @see describing      单个号码发送短信，按短信的模板发送，模板id和签名由腾讯云提供
     */
    public static SmsResult sendMessage(SmsTencentConfiguration configuration) {
        SmsResult smsResult = new SmsResult();
        smsResult.setSmsType(SmsType.TENCENT).setTotal(1);
        try {
            String phone = configuration.getPhone();
            if (StringUtils.isEmpty(phone)) {
                throw new SmsException("发送短信的手机号码为空。");
            }
            SmsSingleSender ssender = new SmsSingleSender(configuration.getAppid(), configuration.getAppkey());
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsSingleSenderResult result = ssender.sendWithParam(configuration.getNationCode(), phone
                    , configuration.getTemplateId(), configuration.getParams(), configuration.getSmsSign()
                    , configuration.getExtend(), configuration.getExt());
            if (result.result == 0) {
                smsResult.setSuccess(1).getSid().add(result.sid);
                smsResult.getSuccessPhones().add(phone);
            } else {
                smsResult.setFail(1).getSid().add(result.sid);
                smsResult.getFailPhones().add(phone);
                smsResult.getErrorMsg().add(result.errMsg);
            }
            System.out.println(result);
        } catch (HTTPException | JSONException | IOException e) {
            // HTTP 响应码错误 | JSON 解析错误 | 网络 IO 错误
            e.printStackTrace();
        }
        return smsResult;
    }

    /**
     * @param configuration 腾讯云配置类，SmsTencentConfiguration 中的 appid, appkey, phone 字段必须填写
     *                      appid       短信应用 SDK AppID
     *                      appkey      短信应用 SDK AppKey
     *                      phones       需要发送短信的号码数组
     * @param msg           需要发送的短信内容
     * @return com.t2w.utils.sms.domain.SmsResult 发送短信的结果集
     * @date 2019-07-29 16:54
     * @see describing      多个号码发送短信，按短信内容直接发送，短信内容需要符合第三方提供的模板内容
     */
    public static SmsResult sendMessages(SmsTencentConfiguration configuration, String msg) {
        SmsResult smsResult = new SmsResult();
        try {
            String[] phones = configuration.getPhones();
            if (phones == null || phones.length == 0) {
                throw new SmsException("发送短信的手机号码为空。");
            }
            smsResult.setSmsType(SmsType.TENCENT).setTotal(phones.length);
            SmsMultiSender msender = new SmsMultiSender(configuration.getAppid(), configuration.getAppkey());
            SmsMultiSenderResult result = msender.send(0, configuration.getNationCode(), phones, msg
                    , configuration.getExtend(), configuration.getExt());
            int success = 0, fail = 0;
            for (SmsMultiSenderResult.Detail detail : result.details) {
                smsResult.getSid().add(detail.sid);
                if (detail.result == 0) {
                    success++;
                    smsResult.getSuccessPhones().add(detail.mobile);
                } else {
                    fail++;
                    smsResult.getFailPhones().add(detail.mobile);
                    smsResult.getErrorMsg().add(detail.errmsg);
                }
            }
            smsResult.setSuccess(success).setFail(fail);
        } catch (HTTPException | JSONException | IOException e) {
            // HTTP 响应码错误 | JSON 解析错误 | 网络 IO 错误
            e.printStackTrace();
        }
        return smsResult;
    }

    /**
     * @param configuration 腾讯云配置类，SmsTencentConfiguration 中的 appid, appkey, phone, templateId, params, smsSign 字段必须填写
     *                      appid       短信应用 SDK AppID
     *                      appkey      短信应用 SDK AppKey
     *                      phones       需要发送短信的号码数组
     *                      templateId  发送内容的模板id，需要提供自己的腾讯云短信模板
     *                      params      短信模板内的参数，具体参数个数按照模板提供
     *                      smsSign     短信签名
     * @return com.t2w.utils.sms.domain.SmsResult 发送短信的结果集
     * @date 2019-07-29 16:54
     * @see describing      多个号码发送短信，按短信的模板发送，模板id和签名由腾讯云提供
     */
    public static SmsResult sendMessages(SmsTencentConfiguration configuration) {
        SmsResult smsResult = new SmsResult();
        try {
            String[] phones = configuration.getPhones();
            if (phones == null || phones.length == 0) {
                throw new SmsException("发送短信的手机号码为空。");
            }
            smsResult.setSmsType(SmsType.TENCENT).setTotal(phones.length);
            SmsMultiSender msender = new SmsMultiSender(configuration.getAppid(), configuration.getAppkey());
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsMultiSenderResult result = msender.sendWithParam(configuration.getNationCode(), phones
                    , configuration.getTemplateId(), configuration.getParams(), configuration.getSmsSign()
                    , configuration.getExtend(), configuration.getExt());
            int success = 0, fail = 0;
            for (SmsMultiSenderResult.Detail detail : result.details) {
                smsResult.getSid().add(detail.sid);
                if (detail.result == 0) {
                    success++;
                    smsResult.getSuccessPhones().add(detail.mobile);
                } else {
                    fail++;
                    smsResult.getFailPhones().add(detail.mobile);
                    smsResult.getErrorMsg().add(detail.errmsg);
                }
            }
            smsResult.setSuccess(success).setFail(fail);
        } catch (HTTPException | JSONException | IOException e) {
            // HTTP 响应码错误 | JSON 解析错误 | 网络 IO 错误
            e.printStackTrace();
        }
        return smsResult;
    }

}
