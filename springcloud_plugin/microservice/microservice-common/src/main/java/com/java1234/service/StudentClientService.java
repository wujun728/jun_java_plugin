package com.java1234.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.java1234.entity.Student;

@FeignClient(value="MICROSERVICE-STUDENT",fallbackFactory=StudentClientFallbackFactory.class)
public interface StudentClientService {

	 /**
     * 根据id查询学生信息
     * @param id
     * @return
     */
    @GetMapping(value="/student/get/{id}")
    public Student get(@PathVariable("id") Integer id);
     
    /**
     * 查询学生信息
     * @return
     */
    @GetMapping(value="/student/list")
    public List<Student> list();
     
    /**
     * 添加或者修改学生信息
     * @param student
     * @return
     */
    @PostMapping(value="/student/save")
    public boolean save(Student student);
     
    /**
     * 根据id删除学生信息
     * @return
     */
    @GetMapping(value="/student/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id);
    
    /**
     * 获取信息
     * @return
     */
    @GetMapping(value="/student/getInfo")
    public Map<String,Object> getInfo();
	
}
