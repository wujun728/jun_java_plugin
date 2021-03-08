package com.springboot.springbootjpaquerydsl.repository;

import com.springboot.springbootjpaquerydsl.model.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 *
 * @Date: 2019/9/25
 * @Time: 23:07
 * @email: inwsy@hotmail.com
 * Description:
 */
public interface LessonRepository extends JpaRepository<LessonModel, String> {
}
