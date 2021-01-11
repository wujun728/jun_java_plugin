package com.jin.calendar.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class UserMenu extends Model<UserMenu> {
	public static final UserMenu dao=new UserMenu();

	public List<UserMenu> getDurationOrder(int userId, long start, long end){
		String sqlStr="select x.*,y.name menu_name,y.price from user_menu x left join menu y on x.menuid=y.id where x.userid= ? and x.order_date>=from_unixtime(?) and x.order_date<from_unixtime(?) order by x.state";
		return UserMenu.dao.find(sqlStr,userId,start,end);
	}
	
	public UserMenu getSingleOrder(int userId, int state, String startDate){
		String sqlStr="select x.*,y.name menu_name,y.price from user_menu x left join menu y on x.menuid=y.id where x.userid= ? and x.state=? and UNIX_TIMESTAMP(x.order_date)=UNIX_TIMESTAMP(?)";
		return UserMenu.dao.findFirst(sqlStr,userId,state,startDate);
	}
	
	/**
	 * 获取当天点餐订单
	 * @return
	 */
	public List<UserMenu> getTodayOrder(){
		String sqlStr="select x.*,y.name menu_name,y.price,z.name user_name,z.tel,z.email from user_menu x "
				+" left join menu y on x.menuid=y.id "
				+" left join user z on x.userid=z.id "
				+" where to_days(order_date)=to_days(now()) "
				+" order by state asc, create_date asc";
		return UserMenu.dao.find(sqlStr);
	}
}
