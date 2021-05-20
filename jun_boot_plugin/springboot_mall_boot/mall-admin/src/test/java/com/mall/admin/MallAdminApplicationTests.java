package com.mall.admin;


import com.jfinal.kit.HttpKit;
import com.mall.admin.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class MallAdminApplicationTests {

    @Autowired
    private AdminService adminService;

    @Test
    public void test() {
        System.out.println(adminService);
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Map params = new HashMap<>();
//                params.put("token",
//                        "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NDU0MDE5MTcsInN1YiI6IjEiLCJleHAiOjE1NDU0MDIwODl9.EnOp_s7vKxGlp8S3H-MOHB9uXydFY1lWFmWymmKdlmI");
                HttpKit.get("http://127.0.0.1:80/system/admin/lock");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpKit.get("http://127.0.0.1:80/system/admin/lock");
//                Map params = new HashMap<>();
//                params.put("token",
//                        "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NDU0MDE5NDQsInN1YiI6IjIiLCJleHAiOjE1NDU0MDIxMTd9.XeOVkl9TD6y5KpXqirHjGGkhfabWVH0kZwEVO5q13M0");
//                HttpKit.get("http://127.0.0.1:80/system/admin/test", null, params);
            }
        }).start();
    }


}
