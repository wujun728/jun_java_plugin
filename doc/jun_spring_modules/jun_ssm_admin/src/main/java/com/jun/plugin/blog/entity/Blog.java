package com.jun.plugin.blog.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * ����ʵ��
 * @author Wujun
 *
 */
public class Blog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id; // ���
	private String title; // ���ͱ���
	private String summary; // ժҪ
	private Date releaseDate; // ��������
 	private Integer clickHit; // �鿴����
	private Integer replyHit; // �ظ�����
	private String content; // ��������
	private String contentNoTag; // �������� ����ҳ��ǩ Lucene�ִ���
	private BlogType blogType; // ��������
	
	private Integer blogCount; // �������� �ǲ���ʵ�����ԣ���Ҫ�� ���ݷ������ڹ鵵��ѯ����������
	private String releaseDateStr; // ���������ַ��� ֻȡ�����
	private String keyWord; // �ؼ��� �ո����
	
	private List<String> imagesList=new LinkedList<String>(); // ��������ڵ�ͼƬ ��Ҫ�����б�չʾ��ʾ����ͼ
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Integer getClickHit() {
		return clickHit;
	}
	public void setClickHit(Integer clickHit) {
		this.clickHit = clickHit;
	}
	public Integer getReplyHit() {
		return replyHit;
	}
	public void setReplyHit(Integer replyHit) {
		this.replyHit = replyHit;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentNoTag() {
		return contentNoTag;
	}
	public void setContentNoTag(String contentNoTag) {
		this.contentNoTag = contentNoTag;
	}
	public BlogType getBlogType() {
		return blogType;
	}
	public void setBlogType(BlogType blogType) {
		this.blogType = blogType;
	}
	public Integer getBlogCount() {
		return blogCount;
	}
	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}
	public String getReleaseDateStr() {
		return releaseDateStr;
	}
	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	public List<String> getImagesList() {
		return imagesList;
	}
	public void setImagesList(List<String> imagesList) {
		this.imagesList = imagesList;
	}
	
	
	
}
