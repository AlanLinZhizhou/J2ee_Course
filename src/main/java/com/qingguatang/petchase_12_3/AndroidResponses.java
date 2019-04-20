package com.qingguatang.petchase_12_3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.sql.*;

@Controller
public class AndroidResponses {
    //mysql驱动包名
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    //数据库连接地址
    private static final String URL = "jdbc:mysql://localhost:3306/petchase" + "?serverTimezone=GMT%2B8&useSSL=false";
    //用户名
    private static final String USER_NAME = "root";
//    private static final String USER_NAME = "lzz";   //本地测试的时候用这个用户名
    //密码
    private static final String PASSWORD = "123456";
    public static Statement stmt;


    //public  String test()
    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestParam(name = "uphone") String uphone, @RequestParam(name = "upass") String upass) {
        query_users(uphone, upass);
        return query_users(uphone, upass);
    }

    private String query_users(String uphone, String upass) {
        Connection connection = null;
//        String upass1= Md5.md5Decode16(upass);
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            stmt = connection.createStatement();
            String sql = "SELECT uname from users where uphone = '" + uphone+"' and upass ='"+upass+"'";
            stmt.executeQuery(sql);
            PreparedStatement prst = connection.prepareStatement(sql);
            ResultSet rs = prst.executeQuery();
            //while (rs.next()) {
            if(rs.next()){
              //  rs.getString("uname");
                return "OK";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NOTOK";
    }

    @RequestMapping("/Serverlet/hello")
    @ResponseBody
    public String helloServerlet(@RequestParam(name = "id", required = true) String id) {
        operate_essays(id);
        return operate_essays(id);
    }

    private static String operate_essays(String i) {
        Connection connection = null;
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            stmt = connection.createStatement();
            //mysql查询语句
            String sql = "SELECT econtent from essay where eid=" + i;
            stmt.executeQuery(sql);
            PreparedStatement prst = connection.prepareStatement(sql);
            //结果集
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                return rs.getString("econtent");
            }
            rs.close();
            prst.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "程序猿小哥哥偷懒了哟";
    }
}
