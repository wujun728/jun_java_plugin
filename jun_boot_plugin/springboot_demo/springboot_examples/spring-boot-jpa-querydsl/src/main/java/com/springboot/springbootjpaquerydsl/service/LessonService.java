package com.springboot.springbootjpaquerydsl.service;

import com.springboot.springbootjpaquerydsl.model.LessonModel;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @Version: 1.0
 * @Desc:
 */
public interface LessonService {

    List<LessonModel> findLessonList(String name, Date startDate, String address, String userId) throws ParseException;

    List<LessonModel> findLessonDynaList(String name, Date startDate, String address, String userId) throws ParseException;

    List<LessonModel> findLessonSubqueryList(String name, String address);
}
