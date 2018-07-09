package com.jackie.crawler.doubanmovie.dbUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * START
 * 代码功能描述：数据库相关操作
 * @author 李耀鹏
 */

public class dbOperation {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    /**
     * 函数描述：一个链接数据库的方法
     * 参数描述：无参数
     * 返回值描述：返回Connection型的数据库链接
     */
    public Connection getConnection(){

        /*String url="jdbc:mysql://192.168.154.243:3306/movie";
         String userName="root";
        String password="lpl963";*/
       // String url = "jdbc:mysql://10.132.0.53:3306/movie";
       // String url = "jdbc:mysql://192.168.154.89:3306/movie";
        String url = "jdbc:mysql://localhost:3306/movie";
        String userName="root";
        String password="123456";


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("找不到驱动！");
            e.printStackTrace();
        }
        try {
            conn= DriverManager.getConnection(url, userName, password);
            if(conn!=null){
                System.out.println("connection successful");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println( "connection fail");
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 函数描述：拿数据库url表中已经存储下来的链接
     * 参数描述：无参数
     * 返回值描述：List<String>；返回所有待爬取的子链接
     */
    public List<String> QuerySql(){
        List<String> dbUrls = new ArrayList<String>();
        String sql="select url from url";
        try {
            conn=getConnection();//连接数据库
            ps=conn.prepareStatement(sql);// 2.创建Satement并设置参数
            rs=ps.executeQuery(sql);  // 3.ִ执行SQL语句
            // 4.处理结果集
            while(rs.next()){
                dbUrls.add(rs.getString(1));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //释放资源
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return dbUrls;
    }

}

/**
 * END
 * @author 李耀鹏
 */



