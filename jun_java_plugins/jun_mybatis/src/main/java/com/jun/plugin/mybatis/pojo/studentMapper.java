package com.jun.plugin.mybatis.pojo;

/**
 * @author LB
 * @version 1.0
 * @date 2020/12/23 21:11
 */
public interface studentMapper {
    studentClass querystudentAndClass(int id);
    student testTf(int id);
}
