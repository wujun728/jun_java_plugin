package com.ky26.winterHomeWork.Exercise5;

public class PokerTest2 {
	public static void main(String[] args) {
		Poker p1=new Poker();
		p1.licensing(4);
	}
}
class Poker{
	String[] colorArr= {"红桃","黑桃","方块","梅花"};
	String[] valueArr= {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	String[] pokerArr=new String[52];
	Poker(){
		int k=0;
		for(int i=0;i<colorArr.length;i++) {
			for(int j=0;j<valueArr.length;j++) {
				pokerArr[k]=colorArr[i]+valueArr[j];
				k++;
			}
		}
	}
	public void shuffle(int n) {
		licensing(n);
	}
	
	public void licensing(int n) {
		for(int i=0;i<n;i++) {
			int count=52/n;
			System.out.println("第"+(i+1)+"个人的牌是:");
			do {
				int j=getRandomNum();
				if(pokerArr[j]==null) {
					continue;
				}
				System.out.print(pokerArr[j]+",");
				pokerArr[j]=null;
				if(count<0) {
					break;
				}
				count--;
			}while(count>0);
			System.out.println();
		}
	}
	
	public int getRandomNum() {
		return (int)(Math.random()*52);
	}
}
