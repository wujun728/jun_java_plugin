/**
 * @Auther: yuanyuan
 * @Date: 2018/8/29 14:14
 * @Description:
 */
package com.yuan.springbootjunit.controller;

import com.yuan.springbootjunit.entity.Person;
import com.yuan.springbootjunit.rest.RestResponse;
import com.yuan.springbootjunit.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;



    @GetMapping("/getPerson/{id:\\d+}")
    @ResponseBody
    public RestResponse<Person> getPerson(@PathVariable("id") int id) {
        Person person =  personService.getPerson(id);
        return new RestResponse<Person>(true, "查询成功", person);
    }


    @PostMapping("/savePerson")
    public RestResponse savePerson(@RequestBody Person person) {
        personService.savePerson(person);
        return new RestResponse<Person>(true, "保存成功", person);
    }


    @DeleteMapping("/deletePerson/{id}")
    public RestResponse deletePerson(@PathVariable("id") int id) {
        personService.deletePerson(id);
        return new RestResponse<String>(true, "删除成功", "");
    }


    @PutMapping("/updatePerson")
    public RestResponse updatePerson(@RequestBody Person person) {
        personService.updatePerson(person);
        return new RestResponse<Person>(true, "更新成功", person);
    }
}
