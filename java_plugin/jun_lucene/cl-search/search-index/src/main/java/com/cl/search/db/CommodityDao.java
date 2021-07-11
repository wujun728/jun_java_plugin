package com.cl.search.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.cl.search.util.DbUtil;



public class CommodityDao {

	private DbUtil dbUtil = DbUtil.getInstance();
	private String commodityPoolName = "commodity";
	protected Connection conn;
	
	public HashMap<String,HashMap<String,String>> getCommodityList()
	{
		HashMap<String,HashMap<String,String>> commodityMap = new HashMap<String,HashMap<String,String>>();
		String strSql = "select * from c_commodity";
		String no = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String,String> commodity = null;
		try{
			conn = dbUtil.getConnection(commodityPoolName);
			ps = conn.prepareStatement(strSql);
			rs = ps.executeQuery();
			
			while(rs.next()){
				no = rs.getString("no");
				commodity = new HashMap<String,String>();
				commodity.put("no", rs.getString("no"));
				commodity.put("brand_id", Integer.toString(rs.getInt("brand_id")));
				commodity.put("name", rs.getString("name"));
				commodity.put("style_no", rs.getString("style_no"));
				commodity.put("sale_price", Integer.toString(rs.getInt("sale_price")));
				commodityMap.put(no, commodity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.closeConnection(rs, ps, conn);
		
		return commodityMap;
	}
	
	private void closeConnection(ResultSet rs,PreparedStatement ps,Connection conn)
	{
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			dbUtil.freeConnection(commodityPoolName, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
