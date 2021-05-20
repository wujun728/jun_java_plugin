package com.zhaodui.springboot.data.datacontroller;

import com.alibaba.druid.util.StringUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dbUtil {
    public static Connection getConnection(){
        Connection conn=null;
        try {
//            String url="jdbc:oracle:thin:@192.168.200.102:1521:ZZWMS";
//            String user="WMSTEST";
//            String password="wmstest";
            String url="jdbc:oracle:thin:@117.25.184.100:1527:ctcrd";
            String user="ctcrd";
            String password="ctcrd";
            Class.forName("oracle.jdbc.driver.OracleDriver");//加载数据驱动
            conn = DriverManager.getConnection(url, user, password);// 连接数据库
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("加载数据库驱动失败");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("连接数据库失败");
        }
        return conn;
    }
    public static List<baseoutientity> getbysql(String sql, String para, int i) {
        List<baseoutientity> reslit = new ArrayList<baseoutientity>();
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = dbUtil.getConnection();
            pstmt2 = conn.prepareStatement(sql);
            if(!StringUtils.isEmpty(para)){
                pstmt2.setString(1, para);
            }
            rs = pstmt2.executeQuery();
            while (rs.next()){
                baseoutientity t = new baseoutientity();
                try {
                    Class cl = Class.forName("com.zhaodui.springboot.data.datacontroller.baseoutientity");//反射得到类
                    Object obj = cl.newInstance();//新建一个实例
                    int ilen = i;
                    for(int k =1;k<=ilen;k++){
                        String methodstr = "setOutX"+k;
                        Method method = cl.getMethod(methodstr,String.class);
                        method.invoke(obj,rs.getString(k));
                    }
                    t = (baseoutientity) obj;
                    reslit.add(t);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return reslit;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (pstmt2 != null) {
                    pstmt2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}