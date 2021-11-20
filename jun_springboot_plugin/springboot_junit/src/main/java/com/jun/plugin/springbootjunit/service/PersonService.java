package com.jun.plugin.springbootjunit.service;

import com.jun.plugin.springbootjunit.entity.Person;

public interface PersonService {

     Person getPerson(int id);

     void savePerson(Person person);

     void deletePerson(int id);

     void updatePerson(Person person);
}
