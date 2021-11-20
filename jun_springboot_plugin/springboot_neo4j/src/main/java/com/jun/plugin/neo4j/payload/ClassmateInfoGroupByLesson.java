package com.jun.plugin.neo4j.payload;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

import com.jun.plugin.neo4j.model.Student;

import java.util.List;

/**
 * <p>
 * 按照课程分组的同学关系
 * </p>
 *
 * @package: com.xkcoding.neo4j.payload
 * @description: 按照课程分组的同学关系
 * @author: yangkai.shen
 * @date: Created in 2018-12-24 19:18
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@QueryResult
public class ClassmateInfoGroupByLesson {
    /**
     * 课程名称
     */
    private String lessonName;

    /**
     * 学生信息
     */
    private List<Student> students;
}
