package com.zb.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.zb.entity.batch.Student;

@Component("csvItemProcessor")
public class CsvItemProcessor implements ItemProcessor<Student, Student> {

    public CsvItemProcessor() {
    }

    /**
     * 对取到的数据进行简单的处理。
     */
    @Override
    public Student process(Student student) throws Exception {
        student.setId(student.getId());
        student.setName(student.getName());
        /* 年龄加2 */
        student.setAge(student.getAge() + 2);
        /* 分数加10 */
        student.setScore(student.getScore() + 10);
        /* 将处理后的结果传递给writer */
        return student;
    }

}
