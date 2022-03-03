package com.feri.fyw.dao;

import com.feri.fyw.entity.Admin;

import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-11 16:33
 */
public interface AdminDao {
    /**
     * 新增
     * @author Feri
     * @date 2021/06/11
     **/
    int insert(Admin admin);
    /**
     * 查询*/
    Admin selectByName(String name);
    /**
     * 查询全部*/
    List<Admin> selectAll();

}
