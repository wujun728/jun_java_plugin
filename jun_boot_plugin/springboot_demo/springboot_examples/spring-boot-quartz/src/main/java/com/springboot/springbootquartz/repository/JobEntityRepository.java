package com.springboot.springbootquartz.repository;

import com.springboot.springbootquartz.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Version: 1.0
 * @Desc:
 */
public interface JobEntityRepository extends JpaRepository<JobEntity, Long> {
    JobEntity getById(Integer id);
}
