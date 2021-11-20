package com.jun.web.biz.base;

import java.util.List;

public interface DAO<M,Q extends M> {
	public void create(M m);
	public void update(M m);
	public void delete(M m);
	
	public M getByUuid(Q qm,int uuid);
	public List<M> getAll(Q qm,int fromNum,int toNum);
	public List<M> getByCondition(Q qm,int fromNum,int toNum);
	public int getCount(Q qm);
}
