package com.zf.utils;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author zf
 * @date 2020/3/4 14:43
 * @description
 */
public class DruidUtils {

    private DruidUtils(){
    }

    private static DruidDataSource druidDataSource = new DruidDataSource();

    static {
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://111.229.20.180/lagou");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("Password");

    }

    public static DruidDataSource getInstance() {
        return druidDataSource;
    }

}
