package com.t2w.utils.common;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-31 11:21
 * @name com.t2w.utils.common.PathUtils.java
 * @see describing 获取项目在服务器中的真实路径的工具类
 * 这是在 web 项目中，获取项目实际路径的最佳方式，在 windows 和 linux 系统下均可正常使用
 */
@Slf4j
public class PathUtils {

    /** 获取WEB项目的ClassPath路径 */
    private static final String classPath;

    private static final String rootPath;

    static {
        String path = "";
        try {
            // 启动 Web 项目时使用该路径
            path = PathUtils.class.getClassLoader().getResource("/").getPath();
        } catch (Exception e) {
            // 未启动 Web 项目时使用该路径
            path = PathUtils.class.getResource("/").getFile();
        }
        classPath = path;
        rootPath = classPath.replace("WEB-INF/classes/", "").substring(0, classPath.indexOf("target/"));
    }

    //对项目的根路径进行解析，拿到项目路径

    /**
     * @return java.lang.String 真实路径
     * @date 2019-07-31 14:15
     * @see describing 获取工程路径
     */
    public static String getRootPath() {
        return getRootPath("");
    }

    /**
     * @param relativePath 相对路径
     * @return java.lang.String 真实路径
     * @date 2019-07-31 14:14
     * @see describing 获取相对于工程路径的路径
     */
    public static String getRootPath(String relativePath) {
        if (relativePath.length() > 0) {
            char begin = relativePath.charAt(0);
            char end = relativePath.charAt(relativePath.length() - 1);
            if (begin == '/' || begin == '\\') {
                relativePath = relativePath.substring(1);
            }
            if (relativePath.length() > 0 && end != '/' && end != '\\') {
                relativePath += '/';
            }
        }
        return handleOS(rootPath + relativePath);
    }

    /**
     * @return java.lang.String 真实路径
     * @date 2019-07-31 14:14
     * @see describing 获取类路径
     */
    public static String getClassPath() {
        return getClassPath("");
    }

    /**
     * @param relativePath 相对路径
     * @return java.lang.String 真实路径
     * @date 2019-07-31 14:13
     * @see describing 获取相对于类路径的路径
     */
    public static String getClassPath(String relativePath) {
        if (relativePath.length() > 0) {
            char begin = relativePath.charAt(0);
            char end = relativePath.charAt(relativePath.length() - 1);
            if (begin == '/' || begin == '\\') {
                relativePath = relativePath.substring(1);
            }
            if (relativePath.length() > 0 && end != '/' && end != '\\') {
                relativePath += '/';
            }
        }
        return handleOS(classPath + relativePath);
    }

    /**
     * @param path 需要处理的路径
     * @return java.lang.String 处理后的路径
     * @date 2019-07-31 14:13
     * @see describing 根据操作系统处理路径的分割符
     */
    public static String handleOS(String path) {
        switch (File.separator) {
            // Windows 下
            case "\\":
                log.info("windows");
                if (path.charAt(0) == '/') {
                    path = path.substring(1);
                }
                path = path.replace("/", "\\");
                break;
            // Linux 下
            case "/":
                log.info("linux");
                path = path.replace("\\", "/");
                break;
        }
        return path;
    }

}
