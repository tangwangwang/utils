package com.t2w.utils.common;

import java.io.*;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-31 19:55
 * @name com.t2w.utils.common.FileUtils.java
 * @see 文件工具类
 */
public class FileUtils {

    public static void saveData(String path, String data) throws IOException {
        saveData(new File(path), data);
    }

    public static void saveData(File file, String data) throws IOException {
        // 保证创建一个新文件
        if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
            file.getParentFile().mkdirs();
        }
        if (file.exists()) { // 如果已存在,删除旧文件
            file.delete();
        }
        file.createNewFile();
        saveData(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"), data);
    }

    public static void saveData(Writer writer, String data) throws IOException {
        writer.write(data);
        writer.flush();
        writer.close();
    }

}
