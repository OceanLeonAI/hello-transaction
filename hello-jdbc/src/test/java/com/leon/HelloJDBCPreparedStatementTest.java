package com.leon;

import com.mysql.cj.jdbc.Driver;
import org.junit.Test;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @PROJECT_NAME: hello-transaction
 * @CLASS_NAME: HelloJDBCTest
 * @AUTHOR: OceanLeonAI
 * @CREATED_DATE: 2021/8/19 23:30
 * @Version 1.0
 * @DESCRIPTION: JDBC PreparedStatement 基本操作
 * <p>
 * 1.注册驱动
 * 2.获取连接
 * 3.获取数据库操作对象
 * 4.执行 SQL 语句
 * 5.处理结果集
 * 6.释放对象
 * <p>
 * 表结构:
 * CREATE TABLE `user` (
 * `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
 * `name` varchar(32) DEFAULT NULL COMMENT '姓名',
 * `age` int(11) DEFAULT NULL COMMENT '年龄',
 * `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
 * `create_time` datetime DEFAULT NULL,
 * `update_time` datetime DEFAULT NULL,
 * `version` int(2) DEFAULT '1',
 * `status` int(11) DEFAULT '1',
 * `deleted` int(11) DEFAULT '0',
 * PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 **/
public class HelloJDBCPreparedStatementTest {

    /**
     * JDBC 新增
     */
    @Test
    public void insert() {

        // 数据库连接对象
        Connection connection = null;
        // 预编译对象
        PreparedStatement preparedStatement = null;

        try {
            // 1.注册驱动
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            // 2.获取连接
            String url = "jdbc:mysql://127.0.0.1:3306/leon";
            String user = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接对象: " + connection);


            // 3.拼接 sql
            StringBuffer sb = new StringBuffer();
            sb.append("INSERT INTO USER (NAME, age )")
                    .append(" ")
                    .append("VALUES")
                    .append(" ");
            int userLength = 100;
            for (int i = 0; i < userLength; i++) {
                sb.append("('user" + i + "', " + i + " )");
                if (i + 1 != userLength) {
                    sb.append(",");
                }
            }
            String sql = sb.toString();
            System.out.println("sql ---> " + sql);

            // 4预编译,获取数据库操作对象
            preparedStatement = connection.prepareStatement(sql);

            // 专门执行 DML 语句（insert update delete）,返回受影响行数
            int count = preparedStatement.executeUpdate();

            System.out.println(count >= 1 ? "新增成功 " + count + " 条" : "新增失败");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源 从小到大
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
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

    /**
     * JDBC 删除
     */
    @Test
    public void delete() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2.获取连接
            String url = "jdbc:mysql://127.0.0.1:3306/leon";
            String user = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接对象: " + connection);

            // 3.构造 sql
            String sql = "DELETE FROM USER WHERE id < ?;";

            // 4.预编译sql,获取数据库操作对象
            preparedStatement = connection.prepareStatement(sql);

            // 5.设置参数
            // 删除前 10 条数据
            preparedStatement.setInt(1, 10);

            // 专门执行 DML 语句（insert update delete）,返回受影响行数
            int count = preparedStatement.executeUpdate();

            System.out.println(count > 0 ? "删除成功 " + count + " " : "删除失败");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源 从小到大
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
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

    /**
     * JDBC 修改
     */
    @Test
    public void update() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 1.注册驱动
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            // 2.获取连接
            String url = "jdbc:mysql://127.0.0.1:3306/leon";
            String user = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接对象: " + connection);

            // 3.构造 sql
            String sql = "UPDATE USER SET NAME = 'leon-update' WHERE id < ?;";

            // 4.预编译，获取数据库操作对象
            preparedStatement = connection.prepareStatement(sql);

            // 5.设置参数
            Object[] args = {20}; // 修改前 20 条数据
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            // 专门执行 DML 语句（insert update delete）,返回受影响行数
            int count = preparedStatement.executeUpdate();

            System.out.println(count > 0 ? "修改成功 " + count + " 条数据" : "修改失败");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源 从小到大
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
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

    /**
     * JDBC 查询
     */
    @Test
    public void query() {

        // 加载配置文件
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc");
        String url = resourceBundle.getString("url");
        String user = resourceBundle.getString("user");
        String password = resourceBundle.getString("password");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // 1.注册驱动
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            // 2.获取连接

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接对象: " + connection);

            // 3.准备 sql
            // String sql = "select * from user;";
            String sql = "select id,name, name as username,age,email from user;";

            // 4.预编译SQL，获取数据库操作对象
            preparedStatement = connection.prepareStatement(sql);

            // 专门执行 DML 语句（insert update delete）,返回受影响行数
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData(); // 获取键名
            int columnCount = resultSetMetaData.getColumnCount(); // 获取行的数量

            while (resultSet.next()) {
                for (int i = 1; i < columnCount; i++) { // 注意下标从 1 开始
                    // String columnLabel

                    // 通过列下标获取
                    // String getString(int columnIndex) throws SQLException;
                    // System.out.println(resultSet.getString(1));

                    // 通过返回列名称查询,有 field as 'xxx' 则查询 xxx
                    // String getString(String columnLabel) throws SQLException;
                    // System.out.println("username ---> " + resultSet.getString("username"));

                    System.out.print(resultSetMetaData.getColumnName(i) + " --- " + resultSet.getObject(i) + "\t");
                }
                System.out.println(); // 换行
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源 从小到大
            if (null != resultSet) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
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

    /**
     * JDBC 查询
     */
    @Test
    public void helloJDBCQueryByPreparedStatement() {

        // 加载配置文件
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc");
        String url = resourceBundle.getString("url");
        String user = resourceBundle.getString("user");
        String password = resourceBundle.getString("password");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // 1.注册驱动
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            // 2.获取连接

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接对象: " + connection);


            // 3.准备 sql，?是占位符
            String sql = "select id,name,age,email from user where id < ?;";

            // 4.预编译,获取数据库操作对象
            preparedStatement = connection.prepareStatement(sql);

            // 5.给占位符产值 第一个 ? 下标是 1
            preparedStatement.setString(1, "10");

            // 专门执行 DML 语句（insert update delete）,返回受影响行数
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData(); // 获取键名
            int columnCount = resultSetMetaData.getColumnCount(); // 获取行的数量

            while (resultSet.next()) {
                for (int i = 1; i < columnCount; i++) { // 注意下标从 1 开始
                    // String columnLabel

                    // 通过列下标获取
                    // String getString(int columnIndex) throws SQLException;
                    // System.out.println(resultSet.getString(1));

                    // 通过返回列名称查询,有 field as 'xxx' 则查询 xxx
                    // String getString(String columnLabel) throws SQLException;
                    System.out.print(resultSetMetaData.getColumnName(i) + " --- " + resultSet.getObject(i) + "\t");
                }
                System.out.println(); // 换行
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源 从小到大

            if (null != resultSet) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
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
}
