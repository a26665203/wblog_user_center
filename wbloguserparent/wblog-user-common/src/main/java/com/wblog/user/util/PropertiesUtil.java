package com.wblog.user.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 自定义工具类，获取配置文件属性
 */
public class PropertiesUtil {
    public static String getProperty(String path,String key){
        String value = "";
        try {
            Properties properties = new Properties();
            InputStream inputStream = PropertiesUtil.class.getResourceAsStream(path);
            properties.load(inputStream);
            if(properties.containsKey(key)){
                value = properties.getProperty(key);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return value;
    }
    public static void main(String[] args){
        System.out.println(PropertiesUtil.getProperty("/redis-config.properties","redis.ip"));
    }
}
