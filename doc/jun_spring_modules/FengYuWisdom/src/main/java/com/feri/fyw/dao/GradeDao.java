package com.feri.fyw.dao;

import com.feri.fyw.entity.Grade;
import com.feri.fyw.entity.Subject;

import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-17 10:53
 */
public interface GradeDao {
    int insert(Grade grade);
    List<Grade> selectAll();
}
