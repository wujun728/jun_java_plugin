package com.luo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luo.pojo.TempIndex;

@Repository
public class TempIndexDao extends BaseDao<TempIndex, String>{

	public void deleteAll() {
		List<TempIndex> tempIndexList=this.findAll();
		for (TempIndex tempIndex : tempIndexList) {
			this.del(tempIndex);
		}
	}

}
