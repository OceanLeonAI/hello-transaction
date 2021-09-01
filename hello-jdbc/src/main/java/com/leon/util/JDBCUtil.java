package com.leon.util;

import java.sql.*;

/**
 * @PROJECT_NAME: hello-transaction
 * @CLASS_NAME: JDBCUtil
 * @AUTHOR: OceanLeonAI
 * @CREATED_DATE: 2021/8/23 16:57
 * @Version 1.0
 * @DESCRIPTION: jdbc 连接 工具
 **/
public class JDBCUtil {

    /**
     * 私有化构造方法，防止外部初始化工具类
     */
    public JDBCUtil() {
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("加载[com.mysql.cj.jdbc.Driver]驱动失败");
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/leon";
        String user = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("获取到驱动 ---> " + connection);
        return connection;
    }

    /**
     * 关闭连接
     *
     * @param connection
     * @param statement
     * @param resultSet
     */
    public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        // 关闭资源 从小到大

        if (null != resultSet) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (null != statement) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


}
