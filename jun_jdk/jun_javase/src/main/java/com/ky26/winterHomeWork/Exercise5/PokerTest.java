package com.ky26.winterHomeWork.Exercise5;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PokerTest {
	public static void main(String[] args) {
		Poker2 dbColorBall = new Poker2();
		dbColorBall.showResult(4);
	}
}
class Poker2{
	HashMap<String, String> map = new HashMap<String, String>();
	private static final String[] colors = {"红桃", "方块", "梅花", "黑桃"};
	private static final String[] values = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
	private String[] newpai;  private int len;
	public Poker2() {
		len = colors.length * values.length;
		newpai = new String[len];
		int k = 0;
		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < values.length; j++) {
				newpai[k] = colors[i] + values[j];
				k++;
			}
		}
	}
	
	private void getNums() {
		Random r = new Random();
		int i = r.nextInt(len);
		String s;
		if (i >= 0 && !map.containsKey(String.valueOf(i))) {
			s = String.valueOf(i);
			map.put(s, newpai[i]);
		} else {
			getNums();
		}
	}
	
	public void showResult(int p) {
		for (int i = 0; i < len; i++) {
			getNums();
		}
		int l = len/p; //每人发几张牌
		int j=1;//计人数
		int k=0;//计牌数
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if(k%l==0 && j<=p){
				System.out.println();
				System.out.print("第"+j+"个人的牌:");
				j++;
			} else if(len-k <= len%p){
				System.out.println();
				System.out.print("剩余的牌:");
			}
			k++;
			System.out.print(entry.getValue()+",");
		}  
	}
}
