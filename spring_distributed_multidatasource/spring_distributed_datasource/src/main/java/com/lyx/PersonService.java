package com.lyx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    @Autowired
    private PersonDao personDao;

    @RequireDataSource(name = DataSourceLookupKey.REMOTE_DATASOURCE)
    @Transactional
    public void addperson() {
        System.out.println("-----addPerson begin-----");
        System.out.println("====>" + DbContextHolder.getDbType());
        People people = new People();
        people.setFirstName("adasdfasdfwe");
        people.setLastName("adadfeexcsdwadfsafd");
        this.personDao.addPeople(people);
        System.out.println("-----addPerson end-----");
        System.out.println("====>" + DbContextHolder.getDbType());
    }
}
