//package com.qingguatang.petchase_12_3;
//
//import java.sql.*;
//
//public class TestMySql {
//
//    //mysql驱动包名
//    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
//    //数据库连接地址
//    private static final String URL = "jdbc:mysql://localhost:3306/petchase"+"?serverTimezone=GMT%2B8&useSSL=false";
//    //用户名
//    private static final String USER_NAME = "lzz";
//    //密码
//    private static final String PASSWORD = "123456";
//    public static Statement stmt;
//    public static void main(String[] args) {
//        //operate_users();
//        operate_essays("1");
//    }
//    private static void operate_collections(){
//        Connection connection = null;
//        try {
//            Class.forName(DRIVER_NAME);
//            connection=DriverManager.getConnection(URL, USER_NAME, PASSWORD);
//            stmt = connection.createStatement();
//            //mysql查询语句
//            String sql ="";
//            sql= "";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    private static void operate_essays(String i){
//        Connection connection = null;
//        try {
//            //加载mysql的驱动类
//            Class.forName(DRIVER_NAME);
//            //获取数据库连接
//            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
//            stmt = connection.createStatement();
//            //mysql查询语句
//            String sql ="SELECT econtent from essay where eid="+i;
//
//            //sql = "INSERT INTO users (uid, uname, uphone,upass) VALUES('00','test1','13338612187','123456');";
//            stmt.executeQuery(sql);
//            PreparedStatement prst = connection.prepareStatement(sql);
//            //结果集
//            ResultSet rs = prst.executeQuery();
//
//            while (rs.next()) {
//                System.out.println( rs.getString("econtent"));
//          }
//              rs.close();
//            prst.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
//
//
//
