package com.jun.admin.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/***
 * 抽奖
 * @author Wujun
 *
 */
public class GoodLuck {

	/**
	 * key:奖品ID 
	 * value:中奖百分比
	 */
	private Map<String, String> map = new HashMap<String, String>();

	/**
	 * 设置参数
	 * @param map
	 */
	public void setParam(Map<String, String> map) {
		this.map = map;
	}
	/**
	 * 抽奖
	 * @return
	 */
	public String lottery() {
		Iterator<String> it = this.map.keySet().iterator();
		java.util.Random ran = new java.util.Random();
		List<String> list = new ArrayList<String>();
		while (it.hasNext()) {
			String key = it.next();
			String value = map.get(key);
			double val = 0;
			if("0.00".equals(value) || "0".equals(value)){
				value = "0";
			} else  {
				val = Double.parseDouble(value);
				if (val >= 50.00) {
					val *= 70;
				} else if (val >= 40 && val <50) {
					val *= 50;
				} else if (val >= 30 && val <40) {
					val *= 30;
				} else if (val >= 20 && val <30){
					val *= 10;
				} else if (val >= 10 && val <20){
					val *= 4;
				}else if (val >= 1 && val <10){
					val *= 2;
				}
			}
			for (double j = 0; j < val; j++) {
				list.add(key);
			}
		}
		// 打乱奖池数据
		int l = ran.nextInt(list.size());
		Collections.shuffle(list);
		return list.get(l);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "0.00");
		map.put("2", "5.00");
		map.put("3泉水", "13.00");
		map.put("5雨伞", "20.00");
		map.put("4打火机", "33.00");
		map.put("6指甲刀", "40.00");
		GoodLuck gl = new GoodLuck();
		gl.setParam(map);
		for (int i = 1; i<=2000;i++) {
			System.out.println(gl.lottery());
		}
		
	}

}
