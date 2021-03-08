package com.java1234.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java1234.entity.Student;
import com.java1234.service.StudentService;

/**
 * 学生信息controller层
 * @author Wujun
 *
 */
@RestController
@RequestMapping("/student")
public class StudentController {

	@Resource
	private StudentService studentService;
	
	/**
	 * 添加学生信息
	 * @param student
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("/add")
	public String add(@Valid Student student,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return bindingResult.getFieldError().getDefaultMessage();
		}else{
			studentService.add(student);
			return "添加成功";
		}
	}
}
