package com.hjq.demo.database;


import static com.hjq.demo.database.DbOpenHelper.getConnection;

import android.util.Log;

import com.hjq.demo.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private String tableName = null;
    private int addId = 0;
    public static String username = null;
    public static String password = null;
    public static int userid = 0;
    private List<User> list_user;

    public void Query(String tableName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection  conn =(Connection)getConnection();
                String sql = "select * from "+tableName;
                Statement st;
                try {
                    if (conn ==null){
                        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        System.out.println(""+conn);
                    }else {
                        st = (Statement) conn.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                        while (rs.next()) {
                            Log.i("data1", rs.getString(1));
                            Log.i("data2", rs.getString(2));
                            Log.i("data3", rs.getString(3));
                        }
                        st.close();
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //closeConnection();
    }
    public int RegisterUser(String phone,String password) throws InterruptedException {
        Thread thread =   new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) getConnection();
                String sql = "insert into user (name,diqu,phone,password,image) values(?,?,?,?,?)";
                PreparedStatement pst;
                try {
                    pst = (PreparedStatement) conn.prepareStatement(sql);
                    pst.setString(1,"name");
                    pst.setString(2,"广东省广州市天河区");
                    pst.setString(3,phone);
                    pst.setString(4,password);
                    pst.setString(5,"image");
                    addId = pst.executeUpdate();
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.i("failed",e.getMessage().toString());
                }
            }
        });
        thread.start();
        thread.join();
        return addId;
    }
    public int loginUser(String phone, String password) throws InterruptedException {
        final int[] loginStatus = {-1}; // 默认登录失败

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                try {
                    conn = (Connection) getConnection();
                    String sql = "SELECT password FROM user WHERE phone = ?";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, phone);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        String dbPassword = rs.getString("password");
                        // 验证密码是否正确
                        if (dbPassword.equals(password)) {
                            loginStatus[0] = 1; // 登录成功
                        } else {
                            loginStatus[0] = 0; // 密码错误
                        }
                    } else {
                        loginStatus[0] = -1; // 用户不存在
                    }

                    rs.close();
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    loginStatus[0] = -2; // 数据库异常
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        thread.start();
        thread.join();
        return loginStatus[0];
    }

    public int updatePassword(String phone, String newPassword) throws InterruptedException {
        final int[] updateStatus = {-1}; // 默认更新失败

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                try {
                    conn = (Connection) getConnection();

                    // 查询用户是否存在
                    String checkSql = "SELECT COUNT(*) FROM user WHERE phone = ?";
                    PreparedStatement checkPst = conn.prepareStatement(checkSql);
                    checkPst.setString(1, phone);
                    ResultSet rs = checkPst.executeQuery();

                    if (rs.next() && rs.getInt(1) > 0) {
                        // 用户存在，执行更新密码操作
                        String updateSql = "UPDATE user SET password = ? WHERE phone = ?";
                        PreparedStatement updatePst = conn.prepareStatement(updateSql);
                        updatePst.setString(1, newPassword);
                        updatePst.setString(2, phone);

                        int rowsAffected = updatePst.executeUpdate();
                        if (rowsAffected > 0) {
                            updateStatus[0] = 1; // 更新成功
                        } else {
                            updateStatus[0] = 0; // 更新失败
                        }

                        updatePst.close();
                    } else {
                        updateStatus[0] = 0; // 用户不存在
                    }

                    rs.close();
                    checkPst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    updateStatus[0] = -2; // 数据库异常
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        thread.start();
        thread.join();
        return updateStatus[0];
    }

    public int updateNickname(String phone, String newNickname) throws InterruptedException {
        final int[] updateStatus = {-1}; // 默认更新失败

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                try {
                    conn = (Connection) getConnection();

                    // 查询用户是否存在
                    String checkSql = "SELECT COUNT(*) FROM user WHERE phone = ?";
                    PreparedStatement checkPst = conn.prepareStatement(checkSql);
                    checkPst.setString(1, phone);
                    ResultSet rs = checkPst.executeQuery();

                    if (rs.next() && rs.getInt(1) > 0) {
                        // 用户存在，执行更新昵称操作
                        String updateSql = "UPDATE user SET name = ? WHERE phone = ?";
                        PreparedStatement updatePst = conn.prepareStatement(updateSql);
                        updatePst.setString(1, newNickname);
                        updatePst.setString(2, phone);

                        int rowsAffected = updatePst.executeUpdate();
                        if (rowsAffected > 0) {
                            updateStatus[0] = 1; // 更新成功
                        } else {
                            updateStatus[0] = 0; // 更新失败
                        }

                        updatePst.close();
                    } else {
                        updateStatus[0] = 0; // 用户不存在
                    }

                    rs.close();
                    checkPst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    updateStatus[0] = -2; // 数据库异常
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        thread.start();
        thread.join();
        return updateStatus[0];
    }

    public String getNicknameByPhone(String phone) throws InterruptedException {
        final String[] nickname = {null}; // 默认昵称为null

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                try {
                    conn = (Connection) getConnection();

                    // 查询用户的昵称
                    String sql = "SELECT name FROM user WHERE phone = ?";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, phone);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        nickname[0] = rs.getString("name"); // 获取昵称
                    } else {
                        nickname[0] = "用户不存在"; // 如果用户不存在，返回特定消息
                    }

                    rs.close();
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    nickname[0] = "查询失败"; // 数据库异常
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        thread.start();
        thread.join();
        return nickname[0];
    }

    public int getIdByPhone(String phone) throws InterruptedException {
        final int[] userId = {0}; // 默认用户ID为0

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                try {
                    conn = (Connection) getConnection();

                    // 查询用户的ID
                    String sql = "SELECT id FROM user WHERE phone = ?";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, phone);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        userId[0] = rs.getInt("id"); // 获取用户ID
                    } else {
                        userId[0] = 0; // 如果用户不存在，返回0
                    }

                    rs.close();
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    userId[0] = 0; // 数据库异常
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        thread.start();
        thread.join();
        return userId[0];
    }

    public String getRegionByPhone(String phone) throws InterruptedException {
        final String[] region = {null}; // 默认地区为null

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                try {
                    conn = (Connection) getConnection();

                    // 查询用户的地区
                    String sql = "SELECT diqu FROM user WHERE phone = ?";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, phone);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        region[0] = rs.getString("diqu"); // 获取地区信息
                    } else {
                        region[0] = "用户不存在"; // 如果用户不存在，返回特定消息
                    }

                    rs.close();
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    region[0] = "查询失败"; // 数据库异常
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        thread.start();
        thread.join();
        return region[0];
    }

    public int updateRegionByPhone(String phone, String newRegion) throws InterruptedException {
        final int[] updateStatus = {-1}; // 默认更新失败

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                try {
                    conn = (Connection) getConnection();

                    // 查询用户是否存在
                    String checkSql = "SELECT COUNT(*) FROM user WHERE phone = ?";
                    PreparedStatement checkPst = conn.prepareStatement(checkSql);
                    checkPst.setString(1, phone);
                    ResultSet rs = checkPst.executeQuery();

                    if (rs.next() && rs.getInt(1) > 0) {
                        // 用户存在，执行更新地区操作
                        String updateSql = "UPDATE user SET diqu = ? WHERE phone = ?";
                        PreparedStatement updatePst = conn.prepareStatement(updateSql);
                        updatePst.setString(1, newRegion);
                        updatePst.setString(2, phone);

                        int rowsAffected = updatePst.executeUpdate();
                        if (rowsAffected > 0) {
                            updateStatus[0] = 1; // 更新成功
                        } else {
                            updateStatus[0] = 0; // 更新失败
                        }

                        updatePst.close();
                    } else {
                        updateStatus[0] = 0; // 用户不存在
                    }

                    rs.close();
                    checkPst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    updateStatus[0] = -2; // 数据库异常
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        thread.start();
        thread.join();
        return updateStatus[0];
    }
    public String getAvatarByPhone(String phone) throws InterruptedException {
        final String[] avatarUrl = {null}; // 默认头像URL为null

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                try {
                    conn = (Connection) getConnection();

                    // 查询用户的头像URL
                    String sql = "SELECT image FROM user WHERE phone = ?";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, phone);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        avatarUrl[0] = rs.getString("image"); // 获取头像URL或路径
                    } else {
                        avatarUrl[0] = "用户不存在"; // 如果用户不存在，返回特定消息
                    }

                    rs.close();
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    avatarUrl[0] = "查询失败"; // 数据库异常
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        thread.start();
        thread.join();
        return avatarUrl[0];
    }
    public int updateAvatarByPhone(String phone, String newAvatarUrl) throws InterruptedException {
        final int[] updateStatus = {-1}; // 默认更新失败

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;

                try {
                    conn = (Connection) getConnection();

                    // 查询用户是否存在
                    String checkSql = "SELECT COUNT(*) FROM user WHERE phone = ?";
                    PreparedStatement checkPst = conn.prepareStatement(checkSql);
                    checkPst.setString(1, phone);
                    ResultSet rs = checkPst.executeQuery();

                    if (rs.next() && rs.getInt(1) > 0) {
                        // 用户存在，执行更新头像操作
                        String updateSql = "UPDATE user SET image = ? WHERE phone = ?";
                        PreparedStatement updatePst = conn.prepareStatement(updateSql);
                        updatePst.setString(1, newAvatarUrl);
                        updatePst.setString(2, phone);

                        int rowsAffected = updatePst.executeUpdate();
                        if (rowsAffected > 0) {
                            updateStatus[0] = 1; // 更新成功
                        }

                        updatePst.close();
                    } else {
                        updateStatus[0] = 0; // 用户不存在
                    }

                    rs.close();
                    checkPst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    updateStatus[0] = -2; // 数据库异常
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        thread.start();
        thread.join(); // 等待线程完成
        return updateStatus[0];
    }
    public int updatePhoneNumber(String oldPhone, String newPhone) {

        final int[] updateStatus = {-1}; // 默认更新失败
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                try {
                    conn = getConnection();
                    // 查询旧手机号是否存在
                    String checkSql = "SELECT COUNT(*) FROM user WHERE phone = ?";
                    PreparedStatement checkPst = conn.prepareStatement(checkSql);
                    checkPst.setString(1, oldPhone);
                    ResultSet rs = checkPst.executeQuery();

                    if (rs.next() && rs.getInt(1) > 0) {
                        // 旧手机号存在，执行更新手机号操作
                        String updateSql = "UPDATE user SET phone = ? WHERE phone = ?";
                        PreparedStatement updatePst = conn.prepareStatement(updateSql);
                        updatePst.setString(1, newPhone);
                        updatePst.setString(2, oldPhone);

                        int rowsAffected = updatePst.executeUpdate();
                        if (rowsAffected > 0) {
                            updateStatus[0] = 1; // 更新成功
                        }

                        updatePst.close();
                    } else {
                        updateStatus[0] = 0; // 旧手机号不存在
                    }

                    rs.close();
                    checkPst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    updateStatus[0] = -2; // 数据库异常
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }});
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return updateStatus[0];
    }



}

