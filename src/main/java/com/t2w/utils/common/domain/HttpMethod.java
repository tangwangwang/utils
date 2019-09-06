package com.t2w.utils.common.domain;

import lombok.Getter;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-09-04 15:58
 * @name com.t2w.utils.common.domain.HttpMethod.java
 * @see describing HTTP 请求方法
 */
@Getter
public enum HttpMethod {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    PATCH("PATCH"),
    COPY("COPY"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    LINK("LINK"),
    UNLINK("UNLINK"),
    PURGE("PURGE"),
    LOCK("LOCK"),
    UNLOCK("UNLOCK"),
    PROPFIND("PROPFIND"),
    VIEW("VIEW");

    private String name;

    HttpMethod(String name) {
        this.name = name;
    }
}
