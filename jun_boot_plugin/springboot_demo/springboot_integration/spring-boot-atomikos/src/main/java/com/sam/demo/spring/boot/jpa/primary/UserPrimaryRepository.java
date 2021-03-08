package com.sam.demo.spring.boot.jpa.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPrimaryRepository extends JpaRepository<PrimaryUser, Long>{

}
