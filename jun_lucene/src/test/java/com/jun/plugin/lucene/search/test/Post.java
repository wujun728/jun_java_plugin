package com.jun.plugin.lucene.search.test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jun.plugin.lucene.search.Searchable;

/**
 * 测试索引的对象
 * @author Winter Lau
 */
public class Post implements Searchable {

	private long id;
	private String title;
	private String body;
		
	public Post(){}
	public Post(long id, String t, String b){
		this.id = id;
		this.title = t;
		this.body = b;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public int compareTo(Searchable o) {
		return 0;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public List<String> storeFields() {
		return Arrays.asList("title");
	}

	@Override
	public List<String> indexFields() {
		return Arrays.asList("title","body");
	}

	@Override
	public Map<String, String> extendStoreDatas() {
		return null;
	}

	@Override
	public Map<String, String> extendIndexDatas() {
		return null;
	}

	@Override
	public List<? extends Searchable> ListAfter(long id, int count) {
		return null;
	}
	@Override
	public float boost() {
		return 1.0f;
	}
}
