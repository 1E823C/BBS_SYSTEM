package zt.bbs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 数据库连接与关闭工具类。
 * 
 * 
 */
public class DatabaseUtil {
    private static String driver = ConfigManager.getProperty("driver");// 数据库驱动字符串    使用数据源连接池后以弃用
    private static String url = ConfigManager.getProperty("url");// 连接URL字符串      使用数据源连接池后以弃用
    private static String user = ConfigManager.getProperty("user"); // 数据库用户名      使用数据源连接池后以弃用
    private static String password = ConfigManager.getProperty("password"); // 用户密码      使用数据源连接池后以弃用

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接对象。
     * @throws SQLException 
     */
    public static Connection getConnection() throws SQLException {
        // 获取连接并捕获异常
        Connection conn = null;
        try {
        	
           // conn = DriverManager.getConnection(url, user, password);
            Context ctx = new InitialContext();     //获取实例化对象
            DataSource dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/bbs");    //从配置文件中读取数据源
            conn= dataSource.getConnection();         //获得数据库连接
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;// 返回连接对象
    }

    /**
     * 关闭数据库连接。
     * 
     * @param conn
     *            数据库连接
     * @param stmt
     *            Statement对象
     * @param rs
     *            结果集
     */
    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {
        // 若结果集对象不为空，则关闭
        try {
            if (rs != null && !rs.isClosed())
                rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 若Statement对象不为空，则关闭
        try {
            if (stmt != null && !stmt.isClosed())
                stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 若数据库连接对象不为空，则关闭
        try {
            if (conn != null && !conn.isClosed())
                conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
