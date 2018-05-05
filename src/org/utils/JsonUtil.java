package org.utils;


import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by Ԭʤ�� on Intellij IDEA
 *
 * @date 2015/4/2
 * @email 745861642@qq.com
 * @decription
 */
public class JsonUtil {

    public static String toJson(Object o) {
        return JSON.toJSONString(o);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return JSON.parseObject(json, classOfT);
    }

    public static <T> List<T> toList(String json, Class<T> classOfT) {
        return JSON.parseArray(json, classOfT);
    }


    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
    }


}
