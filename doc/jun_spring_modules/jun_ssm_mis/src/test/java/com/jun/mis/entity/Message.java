package com.jun.mis.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 短消息实体类
 * @author Wujun
 * @createTime   2011-9-7 下午07:51:03
 */
public class Message implements Serializable{
	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 短消息内容
	 */
	private String content;
	/**
	 * 短消息内容，用于页面显示
	 */
	private String contentText;
	/**
	 * 发送时间
	 */
	private Date sendTime;
	/**
	 * 发件人
	 */
	private User sender;
	/**
	 * 收件人列表(支持群发消息)
	 */
	private Set<User> receivers;
	/**
	 * 是否已读
	 */
	private Boolean hasRead;
	/**
	 * 级别   0=普通   1=重要
	 */
	private Integer level;
	/**
	 * 状态   0=草稿(未发送)   1=已发送
	 */
	private Integer status;
	/**
	 * 从收件箱中删除标志
	 */
	private Boolean deleteFromInBox;
	/**
	 * 从发件箱中删除标志
	 */
	private Boolean deleteFromOutBox;
	/**
	 * 收件人名字(多个的话，用逗号隔开)
	 */
	private String receiverName;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Set<User> getReceivers() {
		return receivers;
	}

	public void setReceivers(Set<User> receivers) {
		this.receivers = receivers;
	}

	public Boolean getHasRead() {
		return hasRead;
	}

	public void setHasRead(Boolean hasRead) {
		this.hasRead = hasRead;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getDeleteFromInBox() {
		return deleteFromInBox;
	}

	public void setDeleteFromInBox(Boolean deleteFromInBox) {
		this.deleteFromInBox = deleteFromInBox;
	}

	public Boolean getDeleteFromOutBox() {
		return deleteFromOutBox;
	}

	public void setDeleteFromOutBox(Boolean deleteFromOutBox) {
		this.deleteFromOutBox = deleteFromOutBox;
	}

	public String getReceiverName() {
		receiverName = "";
		if(null != receivers && receivers.size() != 0){
			for(User user : receivers){
				receiverName += user.getName()+",";
			}
			receiverName = receiverName.substring(0,receiverName.length()-1);
		}
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Message other = (Message) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Message(){}
}
