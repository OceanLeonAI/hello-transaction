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
 * @DESCRIPTION: JDBC 基本操作
 * <p>
 * 1.注册驱动
 * 2.获取连接
 * 3.获取数据库操作对象
 * 4.执行 SQL 语句
 * 5.处理结果集
 * 6.释放对象
 **/
public class HelloJDBCTest {

    /**
     * JDBC 新增
     */
    @Test
    public void helloJDBCInsert() {

        Connection connection = null;
        Statement statement = null;

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

            // 3.获取数据库操作对象
            statement = connection.createStatement();

            // 4.执行 sql
            String sql = "INSERT INTO USER ( id, NAME, age )\n" +
                    "VALUES\n" +
                    "\t( 4, 'leon', 0 ),( 5, 'leon', 0 ),( 6, 'leon', 0 );";


            // 专门执行 DML 语句（insert update delete）,返回受影响行数
            int count = statement.executeUpdate(sql);

            System.out.println(count >= 1 ? "新增成功" : "新增失败");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源 从小到大
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

    /**
     * JDBC 删除
     */
    @Test
    public void helloJDBCDelete() {

        Connection connection = null;
        Statement statement = null;

        try {
            // 1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2.获取连接
            String url = "jdbc:mysql://127.0.0.1:3306/leon";
            String user = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接对象: " + connection);

            // 3.获取数据库操作对象
            statement = connection.createStatement();

            // 4.执行 sql
            String sql = "DELETE \n" +
                    "FROM\n" +
                    "USER \n" +
                    "WHERE\n" +
                    "\tid = 1;";


            // 专门执行 DML 语句（insert update delete）,返回受影响行数
            int count = statement.executeUpdate(sql);

            System.out.println(count == 1 ? "删除成功" : "删除失败");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源 从小到大
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

    /**
     * JDBC 修改
     */
    @Test
    public void helloJDBCUpdate() {

        Connection connection = null;
        Statement statement = null;

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

            // 3.获取数据库操作对象
            statement = connection.createStatement();

            // 4.执行 sql
            String sql = "UPDATE USER \n" +
                    "\tSET NAME = 'leon-update' \n" +
                    "WHERE\n" +
                    "\tid = 1;";


            // 专门执行 DML 语句（insert update delete）,返回受影响行数
            int count = statement.executeUpdate(sql);

            System.out.println(count == 1 ? "修改成功" : "修改失败");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源 从小到大
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

    /**
     * JDBC 查询
     */
    @Test
    public void helloJDBCQuery() {

        // 加载配置文件
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc");
        String url = resourceBundle.getString("url");
        String user = resourceBundle.getString("user");
        String password = resourceBundle.getString("password");

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1.注册驱动
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            // 2.获取连接

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接对象: " + connection);

            // 3.获取数据库操作对象
            statement = connection.createStatement();

            // 4.执行 sql
            String sql = "select * from user;";


            // 专门执行 DML 语句（insert update delete）,返回受影响行数
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData(); // 获取键名
            int columnCount = resultSetMetaData.getColumnCount(); // 获取行的数量

            while (resultSet.next()) {
                for (int i = 1; i < columnCount; i++) { // 注意下标从 1 开始
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
}
