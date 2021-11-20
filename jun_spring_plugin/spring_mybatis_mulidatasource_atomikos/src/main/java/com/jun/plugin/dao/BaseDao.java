package com.jun.plugin.dao;

import com.jun.plugin.entity.Entity;

public class BaseDao extends SessionFactory{
    
    
    public void test1Update(Entity entity) {
	this.getTest1Session().update(entity.getClass().getSimpleName()+".update", entity);
    }
    
    public void test2Update(Entity entity) {
   	this.getTest2Session().update(entity.getClass().getSimpleName()+".update", entity);
       }
}


