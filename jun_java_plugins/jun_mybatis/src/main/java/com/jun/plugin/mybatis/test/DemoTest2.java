package com.jun.plugin.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.jun.plugin.mybatis.pojo.Grade;
import com.jun.plugin.mybatis.pojo.IBlogMapper;
import com.jun.plugin.mybatis.pojo.blog;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author LB
 * @version 1.0
 * @date 2020/12/22 14:25
 */
public class DemoTest2 {

    public static void main(String[] args) {
        //查询单个
         //queryOne();

        //查询多个
        //queryAll();

        //增加
        //add();

        //删除
        //delete();

        //修改
        //update();

        //带转换器
        //selectBlogByIdWithConverter();

        //添加 转换器
        //addBlogWithConverter();

        //使用map作为输入参数
        //SelectBlogWithHashMap();

        //返回值是HashMap
        //ResultByMap();

        //使用foreach 迭代对象数组
        //foreach();

        //使用foreach 迭代对象数组
        //array();

        //使用foreach 迭代集合
        //listForeach();

        //使用foreach 迭代对象数组
        objectForeach();
    }

    private static void objectForeach() {
        String resource="mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            IBlogMapper blogMapper = sqlSession.getMapper(IBlogMapper.class);
            blog blog1 = new blog(); blog1.setTitle("1");
            blog blog2 = new blog(); blog2.setTitle("2");
            blog blog3 = new blog(); blog3.setTitle("2");
            blog[] blogs ={blog1,blog2,blog3};
            List<blog> blogArray = blogMapper.objectForeach(blogs);
            for (blog blog : blogArray) {
                System.out.println("这个blog是"+blog);
            }
            //JDBC 方式 需要手动commit
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //sqlSession.close();
        }
    }

    private static void listForeach() {
        String resource="mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            IBlogMapper blogMapper = sqlSession.getMapper(IBlogMapper.class);
            ArrayList<Integer> ids = new ArrayList<Integer>();
            ids.add(1); ids.add(2);
            List<blog> blogs = blogMapper.listForeach(ids);
            for (blog blog : blogs) {
                System.out.println("这个blog是"+blog);
            }
            //JDBC 方式 需要手动commit
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //sqlSession.close();
        }
    }

    //foreach 迭代集合
    private static void foreach() {
        String resource="mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            IBlogMapper blogMapper = sqlSession.getMapper(IBlogMapper.class);
            Grade grade = new Grade();
            ArrayList<Integer> ids = new ArrayList<Integer>();
            ids.add(1); ids.add(2);
            grade.setIds(ids);
            List<blog> blogs = blogMapper.foreach(grade);
            for (blog blog : blogs) {
                System.out.println("这个blog是"+blog);
            }
            //JDBC 方式 需要手动commit
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //sqlSession.close();
        }
    }

    private static void array() {
        String resource="mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            IBlogMapper blogMapper = sqlSession.getMapper(IBlogMapper.class);
            Grade grade = new Grade();
            int[] ids2 = new int[10];
            ids2[0] = 1; ids2[1] = 2;
            List<blog> blogs = blogMapper.arrayForeach(ids2);
            for (blog blog : blogs) {
                System.out.println("这个blog是"+blog);
            }
            //JDBC 方式 需要手动commit
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //sqlSession.close();
        }
    }

    //查询Blog结果为Map类型
    private static void ResultByMap() {
        String resource="mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            IBlogMapper blogMapper = sqlSession.getMapper(IBlogMapper.class);
            List<HashMap<String, Object>> hashMaps = blogMapper.ResultByMap();
            for (HashMap<String, Object> hashMap : hashMaps) {
                System.out.println(hashMap);
            }

            //JDBC 方式 需要手动commit
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //sqlSession.close();
        }
    }

    private static void SelectBlogWithHashMap() {
        String resource="mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            IBlogMapper blogMapper = sqlSession.getMapper(IBlogMapper.class);
            HashMap<String,Object> map = new HashMap();
            map.put("id",999);
            List<blog> list = blogMapper.selectBlogWithHashMap(map);
            System.out.println("一共拥有"+list.size()+"个");
            //JDBC 方式 需要手动commit
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            sqlSession.close();
        }
    }

    private static void addBlogWithConverter() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            IBlogMapper blogMapper = sqlSession.getMapper(IBlogMapper.class);
            blog blog =new blog("999","测试","sex测试","2020-12-22 15.52.22",1,true);
            int addCount = blogMapper.addBlogWithConverter(blog);
            System.out.println("添加了"+addCount+"个");
            //JDBC 方式 需要手动commit
            sqlSession.commit();
            System.out.println(blog);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            sqlSession.close();
        }
    }

    private static void selectBlogByIdWithConverter() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            IBlogMapper blogMapper = sqlSession.getMapper(IBlogMapper.class);
            blog blog = blogMapper.selectBlogByIdWithConverter(1);
            //JDBC 方式 需要手动commit
            sqlSession.commit();
            System.out.println(blog);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            sqlSession.close();
        }
    }

    private static void update() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            IBlogMapper blogMapper = sqlSession.getMapper(IBlogMapper.class);
            blog blog =new blog("999","测试","yznl2","2020-12-22 15.52.22",1,true);
            int count = blogMapper.updateBlog(blog);
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
            IBlogMapper blogMapper = sqlSession.getMapper(IBlogMapper.class);
            int id = 99;
            int count =  blogMapper.deleteBlogById(id);
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
            IBlogMapper blogMapper = sqlSession.getMapper(IBlogMapper.class);
           blog blog =new blog("222","1测试","yznl","2020-12-22 15.52.22",1,false);
            int count = blogMapper.addBlog(blog);
            //JDBC 方式 需要手动commit
            sqlSession.commit();
            System.out.println("增加了"+count+"个");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            sqlSession.close();
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
            IBlogMapper blogMapper = sqlSession.getMapper(IBlogMapper.class);
            //动态代理 动态的将Object转换为blog
            List<blog> blogs = blogMapper.queryAll();
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
            //动态代理 动态的将Object转换为blog
            IBlogMapper blogMapper = sqlSession.getMapper(IBlogMapper.class);
            blog blog = blogMapper.selectBlogById(1);
            System.out.println(blog);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            sqlSession.close();
        }
    }
}
