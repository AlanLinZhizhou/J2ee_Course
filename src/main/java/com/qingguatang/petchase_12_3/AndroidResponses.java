package com.qingguatang.petchase_12_3;

import com.qingguatang.petchase_12_3.tables.FindPetBean;
import com.qingguatang.petchase_12_3.tables.KepuBean;
//import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.util.ArrayList;
import java.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    @RequestMapping(value="/release")
    @ResponseBody
    public String release(@RequestParam(name = "image") String image,
                          @RequestParam(name = "contact") String contact,
                          @RequestParam(name = "releaser") String releaser,
                          @RequestParam(name = "re_cotent") String re_cotent) {
        try{
        contact=new String(contact.getBytes("ISO-8859-1"),"UTF-8");
        releaser=new String(releaser.getBytes("ISO-8859-1"),"UTF-8");
        re_cotent=new String(re_cotent.getBytes("ISO-8859-1"),"UTF-8");}catch(Exception e){
            e.printStackTrace();
        }

        Date date = new Date();
        String timeStamp = String.valueOf(date.getTime());
        byte[] bb = new byte[1024];
        int n = 0;
        savefile(timeStamp, image);

//        String temp = "http://192.168.137.1:8080/static/photos/" + timeStamp + ".jpg";//本机测试使用localhost会失败
//        String temp="java"+File.separator+"images"+File.separator+timeStamp+".jpg";
        String temp = "http://lzzpros.cn:80/static/photos/" + timeStamp + ".jpg";
        release_findpet(temp, releaser, contact, re_cotent);
        //http://lzzpros.cn/static/photos/1582458195990.jpg   <-真实路径

        //测试代码，待删除
//        File file=new File("/home/admin/java"+File.separator+"images"+File.separator+timeStamp+".txt");
//        try {
//            byte[] s_img = releaser.getBytes();
//            FileOutputStream out = new FileOutputStream(file);
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            out.write(s_img, 0, s_img.length - 1);
//            out.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        return "发布成功";
    }

    private void savefile(String timeStamp, String image) {
        try {

            byte[] s_img = Base64.getDecoder().decode(image);
            String seprator=File.separator;
//            File file = new File("static"+seprator+"photos"+seprator+ timeStamp + ".jpg");
//            System.out.println(this.getClass().getResource("/").getPath());
            File file=new File("/home/admin/java"+File.separator+"images"+File.separator+timeStamp+".jpg");
//            File file = new File(this.getClass().getResource("/").getPath()+seprator + timeStamp + ".jpg");
            //this.getClass().getResource("/").getPath()
//http://lzzpros.cn/static/video/a.png
            FileOutputStream out = new FileOutputStream(file);
            if (!file.exists()) {
                file.createNewFile();
            }
//            while ((n = read(bb)) != -1) {
            out.write(s_img, 0, s_img.length - 1);
//            }
            out.close();
//            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/releasekepu")
    @ResponseBody
    public String releasekepu(@RequestParam(name = "image") String image,
                              @RequestParam(name = "k_trust") String k_trust,
                              @RequestParam(name = "releaser") String releaser,
                              @RequestParam(name = "re_cotent") String re_cotent) {
        Date date = new Date();
        String timeStamp = String.valueOf(date.getTime());
        int n = 0;
//        Byte[] img= Base64.decode(image);
        savefile(timeStamp, image);

//        String temp = "http://192.168.137.1:8080/static/photos/" + timeStamp + ".jpg";//本机测试使用localhost会失败
        //lzzpros.cn
        String temp = "http://lzzpros.cn:80/static/photos/" + timeStamp + ".jpg";
        release_kepu(temp, releaser, k_trust, re_cotent);
        //http://lzzpros.cn/static/photos/1582458195990.jpg   <-真实路径


        return "发布成功";
    }

    private void release_findpet(String image, String releaser, String contact, String re_content) {
        Connection connection = null;
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            stmt = connection.createStatement();
            String sql = "INSERT into findpet (icon,releaser,re_content,re_contact) values ('"
                    + image + "','" + releaser + "','" + re_content + "','" + contact + "');";
            stmt.execute(sql);
            PreparedStatement prst = connection.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void release_kepu(String image, String releaser, String k_trust, String re_content) {
        Connection connection = null;
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            stmt = connection.createStatement();
            String sql = "INSERT into kepu (k_icon,k_releaser,k_content,k_trust) values ('"
                    + image + "','" + releaser + "','" + re_content + "','" + k_trust + "');";
//            stmt.executeQuery(sql);
            stmt.execute(sql);
            PreparedStatement prst = connection.prepareStatement(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/bind")
    @ResponseBody
    public String bind(@RequestParam(name = "uid") String uphone) {
        query_bind(uphone);
        return query_bind(uphone);
    }

    private String query_bind(String uphone) {
        Connection connection = null;
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            stmt = connection.createStatement();
            String sql = "SELECT sn from bind where uid = " + uphone;
            stmt.executeQuery(sql);
            PreparedStatement prst = connection.prepareStatement(sql);
            ResultSet rs = prst.executeQuery();

            if (rs.next()) {
                String sn = rs.getString("sn");
                if (sn.equals("")) return "未绑定";
                return sn;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NOTOK";
    }

    private String query_users(String uphone, String upass) {
        Connection connection = null;
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            stmt = connection.createStatement();
            String sql = "SELECT uname from users where uphone = '" + uphone + "' and upass ='" + upass + "'";
            stmt.executeQuery(sql);
            PreparedStatement prst = connection.prepareStatement(sql);
            ResultSet rs = prst.executeQuery();
            //while (rs.next()) {
            if (rs.next()) {
                //  rs.getString("uname");
                return "OK";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NOTOK";
    }

    @RequestMapping(value = "/Serverlet/hello", produces = "application/json;charset=utf-8")
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
                String temp = rs.getString("econtent");
                String temp2 = new String(temp.getBytes(), StandardCharsets.UTF_8);
                System.out.println(temp2.getBytes());
                System.out.println(temp2);
                return temp2;
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


    @RestController
            //替代@Controller+@ResponseBody这两个注解的作用。
    class FindBeanController {
        @RequestMapping("findpet")
        public List<FindPetBean> getFindBean() {
//            FindPetBean[] flist = new FindPetBean[20];
            List<FindPetBean> f=new ArrayList<>();
            Connection connection = null;
            try {
                Class.forName(DRIVER_NAME);
                connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                stmt = connection.createStatement();
                //mysql查询语句
                String sql = "SELECT * from findpet ;";
                stmt.executeQuery(sql);
                PreparedStatement prst = connection.prepareStatement(sql);
                //结果集
                ResultSet rs = prst.executeQuery();
//                int i = 0;
                while (rs.next()) {
                    f.add(new FindPetBean(rs.getString("icon"),rs.getString("releaser"),
                            rs.getString("re_content"),rs.getString("re_contact")));
//                    FindPetBean findBean = new FindPetBean();
//                    findBean.setIcon(rs.getString("icon"));
//                    findBean.setRe_contact(rs.getString("re_contact"));
//                    findBean.setRe_content(rs.getString("re_content"));
//                    findBean.setReleaser(rs.getString("releaser"));
//                    flist[i] = findBean;
//                    i++;
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
            return f;
        }
    }

    @RestController
            //替代@Controller+@ResponseBody这两个注解的作用。
    class KepuBeanController {
        @RequestMapping("findkepu")
        public List<KepuBean>  getKepuBean() {
            List<KepuBean> l=new ArrayList<>();
//            KepuBean[] klist = new KepuBean[20];

            Connection connection = null;
            try {
                Class.forName(DRIVER_NAME);
                connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                stmt = connection.createStatement();
                //mysql查询语句
                String sql = "SELECT * from kepu ;";
                stmt.executeQuery(sql);
                PreparedStatement prst = connection.prepareStatement(sql);
                //结果集
                ResultSet rs = prst.executeQuery();
                int i = 0;
                while (rs.next()) {
                    l.add(new KepuBean(rs.getString("k_icon"),rs.getString("k_releaser"),
                            rs.getString("k_content"),rs.getString("k_trust")));
//                    KepuBean k = new KepuBean();
//                    k.setK_icon(rs.getString("k_icon"));
//                    k.setK_trust(rs.getString("k_trust"));
//                    k.setK_content(rs.getString("k_content"));
//                    k.setK_releaser(rs.getString("k_releaser"));
//                    klist[i] = k;
//                    i++;
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
            return l;
        }

        @RequestMapping(value = "/advice")
        @ResponseBody
        public String advice(@RequestParam(name = "aid") String aid,
                             @RequestParam(name = "acontent") String acontent) {
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
//            date.getTime();
            Connection connection = null;
            try {
                Class.forName(DRIVER_NAME);
                connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                stmt = connection.createStatement();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String sql = "INSERT into advice (aid,acontent,atime) values (?,?,?);";
//                simpleDateFormat.format(date.getTime());
                PreparedStatement prst = connection.prepareStatement(sql);
                prst.setString(1, aid);
                prst.setString(2, acontent);
                prst.setDate(3, date);
                int a = 0;
                prst.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @RequestMapping(value = "/send_vaccine")
        @ResponseBody
        public String setVac(@RequestParam(name = "send_vac") String send_vac,
                             @RequestParam(name = "vid") String vid) {
            Connection connection = null;
            String temprs = "";
            try {
                Class.forName(DRIVER_NAME);
                connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                stmt = connection.createStatement();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String sql0 = "SELECT * from vaccine where vid = " + vid;
                PreparedStatement prst0 = connection.prepareStatement(sql0);
                prst0.executeQuery();
                //结果集
                ResultSet rs = prst0.executeQuery();


                int a = 0;
                if (!rs.next()) {

                    String sql = "INSERT into vaccine (vtime,vid,remarks) values (?,?,?);";
//                simpleDateFormat.format(date.getTime());
                    PreparedStatement prst = connection.prepareStatement(sql);
                    prst.setString(1, send_vac);
                    prst.setString(2, vid);
                    prst.setString(3, "暂无");
                    prst.execute();

                } else {
                    String sql = "UPDATE  vaccine set vtime = ? where vid =" + vid + ";";
                    PreparedStatement prst = connection.prepareStatement(sql);
                    prst.setString(1, send_vac);
//                    prst.setString(2, vid);
//                    prst.setString(3, "暂无");
                    prst.execute();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @RequestMapping(value = "/get_vaccine")
        @ResponseBody
        public String get_vac(@RequestParam(name = "vid") String vid) {
            String s_date="";
            try {
                Connection connection = null;
                Class.forName(DRIVER_NAME);
                connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                stmt = connection.createStatement();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                simpleDateFormat.format()
                String sql0 = "SELECT * from vaccine where vid = " + vid;
                PreparedStatement prst0 = connection.prepareStatement(sql0);
                prst0.executeQuery();
                //结果集
                ResultSet rs = prst0.executeQuery();
                int a = 0;
                if (rs.next()) {
                    s_date = rs.getString("vtime");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
                return s_date;
        }
    }
}
