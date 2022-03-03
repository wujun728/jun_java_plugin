package com.feri.fyw.dao;

import com.feri.fyw.dto.StudentJobDto;
import com.feri.fyw.entity.StudentJob;
import com.feri.fyw.entity.WeekExam;

import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-18 09:58
 */
public interface StudentJobDao {
    //新增
    int insert(StudentJob job);
    //批量新增
    int insertBatch(List<StudentJob> list);
    //查询
    List<StudentJob> selectAll();

    List<StudentJobDto> selectAllTop();
}
