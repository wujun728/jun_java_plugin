package com.jin.calendar.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Room extends Model<Room> {
	public static final Room dao=new Room();
	
	public List<Room> getAllRoom(){
		return dao.find("select * from room");
	}
}
