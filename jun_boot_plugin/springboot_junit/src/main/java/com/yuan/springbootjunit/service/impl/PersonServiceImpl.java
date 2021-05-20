/**
 * @Auther: yuanyuan
 * @Date: 2018/8/29 14:18
 * @Description:
 */
package com.yuan.springbootjunit.service.impl;

import com.yuan.springbootjunit.entity.Person;
import com.yuan.springbootjunit.service.PersonService;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {


    @Override
    public Person getPerson(int id) {
        Person person = new Person();
        person.setId(id);
        person.setName("zhangsan");
        return person;
    }

    @Override
    public void savePerson(Person person) {
        System.out.println("=========================="+person.getId());
        System.out.println("=========================="+person.getName());
    }

    @Override
    public void deletePerson(int id) {
        System.out.println("=========================="+id);
    }

    @Override
    public void updatePerson(Person person) {
        System.out.println("=========================="+person.getName());
        System.out.println("=========================="+person.getId());
    }
}
