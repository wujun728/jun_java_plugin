package com.sam.demo.spring.boot.jpa.secondary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSecondaryRepository extends JpaRepository<SecondaryUser, Long>{

}
