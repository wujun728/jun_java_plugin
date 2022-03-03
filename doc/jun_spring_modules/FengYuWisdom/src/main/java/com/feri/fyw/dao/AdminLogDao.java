package com.feri.fyw.dao;

import com.feri.fyw.entity.AdminLog;

import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 09:46
 */
public interface AdminLogDao {
    int insert(AdminLog log);
    List<AdminLog> selectAll(int aid);
}
