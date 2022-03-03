package com.feri.fyw.dao;

import com.feri.fyw.dto.DayExamDto;
import com.feri.fyw.entity.DayExam;

import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-18 09:58
 */
public interface DayExamDao {
    //新增
    int insert(DayExam exam);
    //批量新增
    int insertBatch(List<DayExam> list);
    //查询
    List<DayExamDto> selectAll();
}
