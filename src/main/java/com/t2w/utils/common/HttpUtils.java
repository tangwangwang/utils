package com.t2w.utils.common;

import com.t2w.utils.common.domain.HttpMethod;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-09-04 15:53
 * @name com.t2w.utils.common.HttpUtils.java
 * @see describing 发送 Http 请求的工具类
 */
@Slf4j
public class HttpUtils {

    private static final String CHARTSET = "UTF-8";

    /**
     * @param url 请求的链接
     * @return java.lang.String 响应字符串
     * @date 2019-09-06 17:19
     * @see describing 发送 Http 的 POST 请求
     */
    public static String sendPost(String url) {
        return send(url, null, HttpMethod.POST);
    }

    /**
     * @param url    请求的链接
     * @param params 请求的参数
     * @return java.lang.String 响应字符串
     * @date 2019-09-06 17:19
     * @see describing 发送 Http 的 POST 请求
     */
    public static String sendPost(String url, Map params) {
        return send(url, params, HttpMethod.POST);
    }

    /**
     * @param url 请求的链接
     * @return java.lang.String 响应字符串
     * @date 2019-09-06 17:20
     * @see describing 发送 Http 的 GET 请求
     */
    public static String sendGet(String url) {
        return send(url, null, HttpMethod.GET);
    }

    /**
     * @param url    请求的链接
     * @param params 请求的参数
     * @return java.lang.String 响应字符串
     * @date 2019-09-06 17:17
     * @see describing 发送 Http 的 GET 请求
     */
    public static String sendGet(String url, Map params) {
        return send(url, params, HttpMethod.GET);
    }

    /**
     * @param url    请求的链接
     * @param params 请求的参数
     * @param method 请求的方式
     * @return java.lang.String 响应字符串
     * @date 2019-09-06 17:20
     * @see describing 发送 Http 请求
     */
    public static String send(String url, Map params, HttpMethod method) {
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        StringBuffer buffer = null;
        try {
            StringBuilder builder = new StringBuilder(url);
            if (params != null && params.size() > 0) {
                builder.append("?");
                Iterator it = params.keySet().iterator();
                while (it.hasNext()) {
                    Object key = it.next();
                    Object value = params.get(key);
                    builder.append(key).append("=").append(value);
                    if (it.hasNext()) {
                        builder.append("&");
                    }
                }
                url = builder.toString();
            }

            URL urlAndParam = new URL(url);
            //得到连接对象
            connection = (HttpURLConnection) urlAndParam.openConnection();
            //设置请求类型
            connection.setRequestMethod(method.getName());
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "PostmanRuntime/7.16.3");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            //设置请求需要返回的数据类型和字符集类型
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            // POST请求需要设置 Content-Length 长度, 可通过 setChunkedStreamingMode 实现
            if (HttpMethod.POST == method) {
                connection.setChunkedStreamingMode(url.length());
            }
            //允许写出
            connection.setDoOutput(true);
            //允许读入
            connection.setDoInput(true);
            //不使用缓存
            connection.setUseCaches(false);
            //得到响应码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //得到响应流
                InputStream inputStream = connection.getInputStream();
                //将响应流转换成字符串
                buffer = new StringBuffer();
                String line;
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, CHARTSET));
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }
                return buffer.toString();
            }
        } catch (Exception e) {
            log.error("访问链接{}发生异常!", url);
            e.printStackTrace();
        }
        return null;
    }

}