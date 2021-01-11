package com.jin.calendar.bo;

import java.util.Date;

public class RoomEvent extends Event {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String roomName;
	private String email;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public RoomEvent() {
		super();
	}
	
	public RoomEvent(int id, String title, Date start, Date end, String userName, String roomName, String email, boolean editable) {
		super();
		this.id=id;
		this.title=title;
		this.start=start;
		this.end=end;
		this.userName = userName;
		this.roomName = roomName;
		this.email = email;
		this.editable = editable;
	}
}
