package com.yuan.springbootjunit.service;

import com.yuan.springbootjunit.entity.Person;

public interface PersonService {

     Person getPerson(int id);

     void savePerson(Person person);

     void deletePerson(int id);

     void updatePerson(Person person);
}
