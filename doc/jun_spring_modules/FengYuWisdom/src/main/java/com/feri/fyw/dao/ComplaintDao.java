package com.feri.fyw.dao;

import com.feri.fyw.entity.Complaint;
import com.feri.fyw.entity.Subject;

import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-17 10:53
 */
public interface ComplaintDao {
    int insert(Complaint complaint);
    List<Complaint> selectAll(String msg);
    int update(Complaint subject);
}
