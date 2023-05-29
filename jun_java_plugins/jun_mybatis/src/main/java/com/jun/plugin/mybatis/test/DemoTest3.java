package com.jun.plugin.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.jun.plugin.mybatis.pojo.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author LB
 * @version 1.0
 * @date 2020/12/22 14:25
 */
public class DemoTest3 {

    public static void main(String[] args) {
        //一对多查询
        querystudentAndClass();
        //测试驼峰转换
        //testTf();
        //一对多查询 延迟加载
        querystudentAndClassLazy();
    }

    private static void querystudentAndClassLazy() {
        String resource="mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            studentClassMapper studentMapper = sqlSession.getMapper(studentClassMapper.class);
            studentClass studentClass = studentMapper.querystudentAndClassLazy(1);
            System.out.println(studentClass.getClassId()+"==="+studentClass.getClassName());
            //JDBC 方式 需要手动commit
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            sqlSession.close();
        }
    }

    private static void testTf() {
        String resource="mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            studentMapper studentMapper = sqlSession.getMapper(studentMapper.class);
            student student = studentMapper.testTf(2);
            System.out.println(student);
            //JDBC 方式 需要手动commit
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //sqlSession.close();
        }
    }

    private static void querystudentAndClass() {
        String resource="mybatis-config.xml";
        InputStream inputStream = null;
        SqlSession sqlSession =null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            studentMapper studentMapper = sqlSession.getMapper(studentMapper.class);
            studentClass studentClass = studentMapper.querystudentAndClass(1);
            System.out.println(studentClass);
            //JDBC 方式 需要手动commit
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //sqlSession.close();
        }
    }



}
