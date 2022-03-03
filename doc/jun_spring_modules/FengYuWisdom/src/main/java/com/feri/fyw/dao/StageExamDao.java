package com.feri.fyw.dao;

import com.feri.fyw.entity.StageExam;
import com.feri.fyw.entity.WeekExam;

import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-18 09:58
 */
public interface StageExamDao {
    //新增
    int insert(StageExam exam);
    //批量新增
    int insertBatch(List<StageExam> list);
    //查询
    List<StageExam> selectAll();
}
