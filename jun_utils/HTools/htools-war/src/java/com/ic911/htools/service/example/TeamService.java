package com.ic911.htools.service.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ic911.htools.entity.example.Team;

@Service
@Transactional
public interface TeamService extends JpaRepository<Team,Long>{

}
