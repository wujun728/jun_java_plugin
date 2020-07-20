package com.ic911.htools.service.example;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ic911.htools.entity.example.Blog;

@Service
@Transactional
public interface BlogService extends CrudRepository<Blog,Long>{
	
}
