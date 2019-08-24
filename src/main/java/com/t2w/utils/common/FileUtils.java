package com.t2w.utils.common;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-31 19:55
 * @name com.t2w.utils.common.FileUtils.java
 * @see 文件工具类
 */
public class FileUtils {

    private static final String CHARSET = "UTF-8";

    public static void saveData(File file, String data) {
        // 保证创建一个新文件
        if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
            file.getParentFile().mkdirs();
        }
        if (file.exists()) { // 如果已存在,删除旧文件
            file.delete();
        }
        try {
            file.createNewFile();
            saveData(new OutputStreamWriter(new FileOutputStream(file), CHARSET), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveData(Writer writer, String data) {
        try {
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param request  HTTP请求
     * @param fileName 下载的文件名
     * @return java.lang.String 重新编译的的文件名
     * @see describing 对文件流输出下载的中文文件名进行编码，屏蔽各种浏览器版本的差异性
     * @date 2019-08-09 15:05
     */
    public static String encodeChineseDownloadFileName(HttpServletRequest request, String fileName) {
        String agent = request.getHeader("USER-AGENT");
        try {
            if (null != agent) {
                if (-1 != agent.indexOf("Firefox")) { // Firefox 内核
                    fileName = "=?UTF-8?B?" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
                } else if (-1 != agent.indexOf("Chrome")) {// Chrome 内核
                    fileName = new String(fileName.getBytes(), "ISO8859-1");
                } else { // IE内核（IE7+）
                    fileName = URLEncoder.encode(fileName, "UTF-8");
                    fileName = fileName.replace(" ", "%20");
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fileName;
    }

}
