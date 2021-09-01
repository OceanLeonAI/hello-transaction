package com.leon;

import com.leon.util.JDBCUtil;
import com.mysql.cj.jdbc.Driver;
import org.junit.Test;

import java.sql.*;

/**
 * @PROJECT_NAME: hello-transaction
 * @CLASS_NAME: HelloJDBCTransaction
 * @AUTHOR: OceanLeonAI
 * @CREATED_DATE: 2021/9/1 10:22
 * @Version 1.0
 * @DESCRIPTION:
 **/
public class HelloJDBCTransaction {
    /**
     * JDBC 查询
     */
    @Test
    public void queryByJDBCUtil() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            // 1.获取连接
            connection = JDBCUtil.getConnection();
            // System.out.println("数据库连接对象: " + connection);

            // 2.准备 sql
            // String sql = "select * from user;";
            String sql = "select id,name, name as username,age,email from user where id < 10;";

            // 3.预编译SQL，获取数据库操作对象
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
            // 关闭连接
            JDBCUtil.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    /**
     * JDBC 新增
     */
    @Test
    public void insertWithoutTransaction() {

        Connection connection = null;
        Statement statement = null;

        try {
            // 1.注册驱动
            // Class.forName("com.mysql.cj.jdbc.Driver");
            com.mysql.cj.jdbc.Driver driver = new Driver();
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
            int count = 0;

            // ----------------- 执行第一条 sql -----------------
            String sql = "INSERT INTO USER (NAME, age )VALUES ('leon3', 0 );";

            // 专门执行 DML 语句（insert update delete）,返回受影响行数
            count += statement.executeUpdate(sql);

            // ----------------- 故意抛出异常 -----------------
            String str = null;
            str.toString();

            // ----------------- 执行第二条 sql -----------------
            sql = "INSERT INTO USER (NAME, age )VALUES ('leon4', 0 );";

            // 专门执行 DML 语句（insert update delete）,返回受影响行数
            count += statement.executeUpdate(sql);

            System.out.println(count == 2 ? "新增成功" : "新增失败");


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
     * JDBC 新增
     */
    @Test
    public void insertWithTransaction() {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // 获取连接
            connection = JDBCUtil.getConnection();
            System.out.println("数据库连接对象: " + connection);

            // 关闭自动提交事务
            connection.setAutoCommit(false);

            // 构造 sql
            String sql = "INSERT INTO USER (NAME, age )VALUES (?, ?);";

            // 预编译
            statement = connection.prepareStatement(sql);

            int count = 0;

            // ------------- 第一次执行 begin -------------
            // 设置参数
            statement.setString(1, "leon111");
            statement.setInt(2, 111);

            // 专门执行 DML 语句（insert update delete）,返回受影响行数
            count += statement.executeUpdate();
            // ------------- 第一次执行   end -------------

            // ------------- 第二次执行 begin -------------
            // 设置参数
            statement.setString(1, "leon222");
            statement.setInt(2, 222);

            // ------------- 故意制造异常
            String str = null;
            str.toString();

            // 专门执行 DML 语句（insert update delete）,返回受影响行数
            count += statement.executeUpdate();
            // ------------- 第二次执行   end -------------

            System.out.println(count == 2 ? "新增成功" : "新增失败");
            // 提交事务
            connection.commit();

        } catch (Exception e) {
            if (null != connection) {
                try {
                    connection.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } finally {
            JDBCUtil.closeConnection(connection, statement, null);
        }
    }
}
