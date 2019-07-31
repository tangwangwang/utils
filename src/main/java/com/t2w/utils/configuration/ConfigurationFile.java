package com.t2w.utils.configuration;

import com.t2w.utils.common.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 13:52
 * @name com.t2w.utils.configuration.ConfigurationFile.java
 * @see 配置文件类
 */
public class ConfigurationFile {

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static Map<String, ConfigurationFile> configurations = new HashMap<String, ConfigurationFile>();

    private Properties properties;

    private Map<String, Object> yaml;

    private ConfigurationFile(String fileName) {
        init(fileName);
    }

    public static ConfigurationFile getInstance(String fileName) {
        if (!configurations.keySet().contains(fileName)) {
            synchronized (ConfigurationFile.class) {
                if (!configurations.keySet().contains(fileName)) {
                    ConfigurationFile instance = new ConfigurationFile(fileName);
                    configurations.put(fileName, instance);
                }
            }
        }
        return configurations.get(fileName);
    }

    private void init(String fileName) {
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName); // 方法2
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, DEFAULT_ENCODING);
            // 根据文件名的后缀名解析文件
            String suffix = StringUtils.getSuffix(fileName);
            if (suffix.equals("properties"))
                loadProperties(inputStreamReader);
            if (suffix.equals("yml"))
                loadYaml(inputStreamReader);
        } catch (IOException e) {
            System.out.println("properties文件路径有误！");
            e.printStackTrace();
        }
    }

    private void loadYaml(InputStreamReader inputStreamReader) {
        yaml = new Yaml().load(inputStreamReader);
    }

    private void loadProperties(Reader reader) {
        try {
            properties = new Properties();
            properties.load(reader); // 加载格式化后的流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Set<String> getKeys(String prefix) {
        Set<String> keys = properties.stringPropertyNames();
        Set<String> result = new HashSet<String>();
        for (String key : keys) {
            if (key.startsWith(prefix)) {
                result.add(key);
            }
        }
        return result;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if (yaml != null)
            return yaml;
        if (properties != null) {
            for (String key : getKeys("")) {
                map.put(key, getProperty(key));
            }
        }
        return map;
    }

}
