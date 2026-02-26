package com.jun.plugin.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.jun.plugin.mybatis.pojo.blog;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author LB
 * @version 1.0
 * @date 2020/12/22 14:25
 */
public class DemoTest1 {
    public static void main(String[] args) {
        //查询单个blog
        // queryOne();

        //查询多个
         //  queryAll();
        //增加
        add();
        //删除
        //delete();
        //修改
        update();

    }

    private static void update() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            String statement = "com.jun.plugin.mybatis.pojo.blogMapper.updateBlog";
            blog blog =new blog("99","测试","yznl2","2020-12-22 15.52.22",1,true);
            int count = sqlSession.delete(statement,blog);
            //JDBC 方式 需要手动commit
            sqlSession.commit();
            System.out.println("修改"+count+"个");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            sqlSession.close();
        }
    }

    private static void delete() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            String statement = "com.jun.plugin.mybatis.pojo.blogMapper.deleteBlogById";
            int id = 99;
            int count = sqlSession.delete(statement,id);
            //JDBC 方式 需要手动commit
            sqlSession.commit();
            System.out.println("删除"+count+"个");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            sqlSession.close();
        }
    }

    private static void add() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            String statement = "com.jun.plugin.mybatis.pojo.blogMapper.addBlog";
           blog blog =new blog("99","测试","yznl","2020-12-22 15.52.22",1,true);
            int count = sqlSession.insert(statement,blog);
            //JDBC 方式 需要手动commit
            sqlSession.commit();
            System.out.println("增加了"+count+"个");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            sqlSession.close();
        }
    }

    private static void queryAll() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            String statement = "com.jun.plugin.mybatis.pojo.blogMapper.queryAll";
            //动态代理 动态的将Object转换为blog
            List<blog> blogs = sqlSession.selectList(statement);
            System.out.println(blogs.size());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            sqlSession.close();
        }
    }

    public static void queryOne(){
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            String statement = "com.jun.plugin.mybatis.pojo.blogMapper.selectBlogById";
            String statement2 = "com.jun.plugin.mybatis.pojo.blogMapper.time";
            //动态代理 动态的将Object转换为blog
            blog blog = sqlSession.selectOne(statement,1);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //String times = sqlSession.selectOne(statement2,2);
            System.out.println(blog);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            sqlSession.close();
        }
    }
}
