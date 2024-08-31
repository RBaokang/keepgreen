package com.hjq.demo.database;

import android.content.Context;
import android.util.Log;

import com.hjq.demo.Tool.SP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbOpenHelper {
    private static String driver = "com.mysql.jdbc.Driver";// mysql 驱动
    private static String ip = "192.168.43.17";  // 安装了 mysql 的电脑的 ip 地址
    private static String dbName = "yizhucao";    // 要连接的数据库
    private static String user = "root";    // 用户名
    private static String password = "123456"; // 密码
    private static SP sp;
    private static Connection sConnection = null;
    private static Context context;
    public DbOpenHelper(Context c){
        this.context = c;
    }
    public DbOpenHelper(){
    }
    // 2.设置好IP/端口/数据库名/用户名/密码等必要的连接信息
    private static int port = 3306;
    /*private static String url = "jdbc:mysql://" + ip + ":" + port
            + "/" + dbName+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    // 构建连接mysql的字符串c
   public static void getIp(String ipp){
       ip = ipp;
   }
    /**
     * 连接数据库
     */
    public static Connection getConnection() {
      try {
          String url = "jdbc:mysql://" + ip + ":" + port
                  + "/" + dbName+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
            Class.forName(driver);
            sConnection = DriverManager.getConnection(url, user, password);;//获取连接
          Log.i("test","连接成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
          Log.i("failed",e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("failed",e.getMessage());
        }
        return sConnection;
    }

    /**
     * 关闭数据库
     */
    public static void closeConnection() {
        if (sConnection != null) {
            try {
                sConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
