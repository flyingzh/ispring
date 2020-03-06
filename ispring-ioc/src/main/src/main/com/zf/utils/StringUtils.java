package com.zf.utils;

/**
 * @author admin
 * @date 2020/3/4 15:14
 * @description
 */
public class StringUtils {

    /**
     * 判断是否不为为空字符串
     * @param arg
     * @return
     */
    public static Boolean isNotBlank(String arg){
        if(arg == null || arg.trim().length() == 0){
            return false;
        }
        return true;
    }


    /**
     * 判断是否为空字符串
     * @param arg
     * @return
     */
    public static Boolean isBlank(String arg){
        if(arg == null || arg.trim().length() == 0){
            return true;
        }
        return false;
    }

    /**
     * 首字母转小写
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

}
