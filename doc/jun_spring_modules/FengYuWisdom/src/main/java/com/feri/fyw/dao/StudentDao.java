package com.feri.fyw.dao;

import com.feri.fyw.dto.GradePersonDto;
import com.feri.fyw.dto.StudentSexDto;
import com.feri.fyw.entity.Student;

import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 11:37
 */
public interface StudentDao {
    //新增
    int insert(Student student);
    //批量新增
    int insertBatch(List<Student> list);
    //查询
    List<Student> selectAll();

    List<StudentSexDto> selectTjSex();
    List<GradePersonDto> selectTjPersons();
}
