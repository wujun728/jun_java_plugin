package com.jd.ssh.sshdemo.entity;

import java.util.List;
import java.util.Map;

import com.jd.ssh.sshdemo.annotation.Indexable;
import com.jd.ssh.sshdemo.annotation.Searched;
import com.jd.ssh.sshdemo.annotation.Searched.SearchedEnum;
import com.jd.ssh.sshdemo.search.Searchable;

@Indexable
public class Person implements Searchable{
	private String name;
	private String id;
	private String sex;
	private String des;
	private String card;
	
	@Searched(type=SearchedEnum.store)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Searched(type=SearchedEnum.store)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Searched(type=SearchedEnum.index)
	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	@Override
	public int compareTo(Searchable o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> storeFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> indexFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float boost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, String> extendStoreDatas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> extendIndexDatas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends Searchable> ListAfter(long id, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Searched(type=SearchedEnum.store)
	public String getId() {
		return id;
	}

	
}
