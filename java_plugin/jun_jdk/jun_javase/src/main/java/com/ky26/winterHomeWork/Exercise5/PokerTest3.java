package com.ky26.winterHomeWork.Exercise5;

public class PokerTest3 {
	public static void main(String[] args) {
		Poker3 p1=new Poker3();
		int[] arr={1,2,3,4,5,6,7,8,9,0};
		p1.getRandomNum(arr);
	}
}

class Poker3{
	String[] colorArr= {"红桃","黑桃","方块","梅花"};
	String[] valueArr= {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	String[] pokerArr=new String[52];
	Poker3(){
		int k=0;
		for(int i=0;i<colorArr.length;i++) {
			for(int j=0;j<valueArr.length;j++) {
				pokerArr[k]=colorArr[i]+valueArr[j];
				k++;
			}
		}
	}
	public void shuffle() {
		int count=52/2;
		int[] intdexArr=new int[count];
		for(int i=0;i<52/2;i++){
			int index=getRandomNum(intdexArr);
			intdexArr[i]=index;
			System.out.println("本次随机数"+index);
			/*do{
				pokerArr[i]=pokerArr[index];
				String temp=pokerArr[i];
				pokerArr[index]=temp;
				count--;
				if(count<0){
					break;
				}
				
			}while(count>0);*/
		}
	}//洗牌
	
	public void licensing(int n){
		int k=0;
		for(int i=0;i<n;i++){
			for(int j=0;j<52/n;j++){
				System.out.print(pokerArr[k]+",");
				k++;
			}
			System.out.println();
		}
	}//发牌
	public int getRandomNum(int[] arr) {
		int number=0;
		int temp=0;
		boolean flag=false;
		do{
			for(int i=0;i<arr.length;i++){
				number=(int)(Math.random()*52);
				if(number==arr[i]){
					flag=true;
					break;
				}
			}
			if(flag==false){
				temp=number;
				break;
			}
		}while(flag);
//		return number;
		return temp;
	}
}
