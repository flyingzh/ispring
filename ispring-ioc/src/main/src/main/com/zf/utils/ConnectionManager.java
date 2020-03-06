package com.zf.utils;

import com.zf.annotation.Service;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author admin
 * @date 2020/3/4 14:44
 * @description
 */
@Service
public class ConnectionManager {

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>(); // 存储当前线程的连接

    /**
     *  获取当前线程的connection连接
     * @return
     * @throws SQLException
     */
    public Connection getCurrentThreadConn() throws SQLException {
        Connection connection = threadLocal.get();
        if(connection == null) {
            connection = DruidUtils.getInstance().getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }

}
