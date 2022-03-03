package com.feri.fyw.dao;

import com.feri.fyw.entity.Project;
import com.feri.fyw.entity.StudentProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-17 10:53
 */
public interface StudentProjectDao {
    int insert(StudentProject studentProject);
    List<StudentProject> selectAll();
    int update(StudentProject studentProject);
}
