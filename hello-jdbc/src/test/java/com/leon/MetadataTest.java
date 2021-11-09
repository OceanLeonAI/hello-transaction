package com.leon;

import com.leon.entity.DataSource;
import com.leon.util.MD5Util;
import com.mysql.cj.jdbc.Driver;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
public class MetadataTest {

    /**
     * JDBC 新增
     */
    @Test
    public void insert() {

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
//            String sql = "INSERT INTO USER ( id, NAME, age )\n" +
//                    "VALUES\n" +
//                    "\t( 4, 'leon', 0 ),( 5, 'leon', 0 ),( 6, 'leon', 0 );";
            String sql = "INSERT INTO USER (NAME, age )\n" +
                    "VALUES\n" +
                    "\t('leon', 0 ),('leon', 0 ),('leon', 0 );";


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
    public void delete() {

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
    public void update() {

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
                    "\tid = 7;";


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
    public void helloJDBCQueryByPreparedStatement() {

        List<DataSource> datasourceList = getDatasourceList();

        // 加载配置文件
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc-metadata");
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
            String sql = "update blade_datasource set md5_code = ? where id = ?";

            // 4.预编译,获取数据库操作对象
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < datasourceList.size(); i++) {
                DataSource dataSource = datasourceList.get(i);
                preparedStatement.setString(1, dataSource.getMd5Code());
                preparedStatement.setLong(2, dataSource.getId());
                // 执行
                preparedStatement.executeUpdate();
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


    public static List<DataSource> getDatasourceList() {

        // 加载配置文件
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc-metadata");
        String url = resourceBundle.getString("url");
        String user = resourceBundle.getString("user");
        String password = resourceBundle.getString("password");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<DataSource> dataSourceList = null;

        try {
            // 1.注册驱动
            // Class.forName("com.mysql.cj.jdbc.Driver");
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            // 2.获取连接

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接对象: " + connection);


            // 3.准备 sql，?是占位符
            String sql = "select id,name,md5_code,host_name,port,database_name from blade_datasource;";

            // 4.预编译,获取数据库操作对象
            preparedStatement = connection.prepareStatement(sql);

            // 专门执行 DML 语句（insert update delete）,返回受影响行数
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData(); // 获取键名
            int columnCount = resultSetMetaData.getColumnCount(); // 获取行的数量

            dataSourceList = new ArrayList<>();

            // 遍历构建数据
            while (resultSet.next()) {
                // id
                long id = resultSet.getLong("id");

                // 数据源名称
                String name = resultSet.getString("name");

                // 主机ip
                String hostName = resultSet.getString("host_name");

                // 端口
                String port = resultSet.getString("port");

                // 数据库名称
                String databaseName = resultSet.getString("database_name");

                // 构造md5
                StringBuffer sb = new StringBuffer();

                sb.append(hostName) // ip
                        .append("/")
                        .append(port) // port
                        .append("/")
                        .append(databaseName); // databaseName

                String md5Hex = MD5Util.encodeMD5Hex(sb.toString());

                dataSourceList.add(DataSource.builder()
                        .id(id)
                        .name(name)
                        .hostName(hostName)
                        .port(port)
                        .databaseName(databaseName)
                        .md5Code(md5Hex)
                        .build());
            }

            for (DataSource dataSource : dataSourceList) {
                System.out.println(dataSource);
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

            return dataSourceList;
        }

    }
}
