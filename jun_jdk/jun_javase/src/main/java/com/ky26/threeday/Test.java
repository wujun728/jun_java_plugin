package com.ky26.threeday;

import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		
		/*int n=-3;
		int c=0;
	    String s="";
		while(n!=0){
			c+=n&1;
			s=(n&1)+s;
			n=n>>>1;
		}
		System.out.print("该数的二进制是："+s);//输出一个数的二进制，正负都行
		System.out.println();
		System.out.println("1的个数是："+c);//输出1的个数
*/		
		
		/*System.out.println("test");
		System.out.println(5>>1);//5换成二进制，右移一位，高位用0补，得到的数字再转为十进制
		System.out.println(-5>>1);
		System.out.println(5>>>1);
		System.out.println(-5>>>1);*/
		
		
		/*int m=12;
		String s="";
		while(m!=0){
			s=(m&1)+s;
			m=m>>>1;
		}
		System.out.println(s);*/
		
		/*int a=3,b=5;
		a=a^b;
		b=a^b;
		System.out.println(b);*/
		/*System.out.println(a);
		b=a^b;
		System.out.println(b);
		a=a^b;
		System.out.println(a);
		System.out.println(b);*/
		
		/*double a=3,b=4,c=5;
		double s=(a+b+c)/2;
		System.out.println(Math.sqrt(s*(s-a)*(s-b)*(s-c)));
		
		for(int i=0;i<=10;i++){
			for(int j=0;j<=10;j++){
				System.out.println(i+"====="+j);
				if(j==5){
					System.out.println(i+"-----"+j);
					continue;
				}
			}
		}*/
		
		/*int x=0;
		for(System.out.println("a");x<3;System.out.println("b")){
			for(System.out.println("M");x<3;System.out.println("N")){
				for(System.out.println("K");x<3;System.out.println("H")){
					System.out.println("X");
					x++;
				}
			}
		}*/
		
	/*	int m=0;
		for(int i=0;i<=9;i++){
			for(int j=0;j<=i;j++){
				for(int k=0;k<=j;k++){
					System.out.println(k);
					m++;
				}
			}
		}
		System.out.println(m);*/
		
		
		
		/*int a=10;
		int b=100;
		int m=0;
		System.out.println(m=(a>b?a:b));*/
		
		/*int a=23,b=0;
		
		for(int i=0;i<=a+1;i++){
			a=a/2;
			b=a%2;
			System.out.println(a+","+b);
		}*/
		
		/*int num=23;
		int count=0;
		for(int i=0;i<=num;i++){
			num=num>>1;	
			if(num>>1!=0){
				count++;
			}
		}
		
		System.out.println(count);*/
		
		/*int n=-1;
		int count = 0;
        int com = 1;
        while(com <= n){
            if((n&com) != 0)
                count++;
            com = com<<1;
        }
        System.out.println(count);
        
        System.out.println((-5)%2);*/
        

		/*int a=-3;
		int count=0;
		while(a!=0){
			if((a&1)!=0){
				count++;
			}
			a=a>>>1;
		}
		System.out.println(count);
		
		System.out.println(a&1);*/
		
		
		/*int n=-1;
		int c=0;
		int c1=0;
		while(n != 0){
			if((n&1)==1){
				++c;
			}else{
				c1++;
			}
			n>>>=1;
		}
		System.out.println("1的个数为"+c+"----"+"0的个数为"+c1);*/
		
		/*int n=23;//10111
		int c=0;
		while(n != 0){
			if(n%2 !=0){
				c++;
			}
			n/=2;
		}
		System.out.println(c);//只能输入正数的二进制有几个1
*/        
        
        
		/*if(a%2==0){
			b++;
		}
		else{
			c++;
		}
		
		System.out.println(b);
		System.out.println(c);*/
		
/*		for(int i=2;i<=100;i++){
			boolean t=true;
			for(int j=2;j<i;j++){
				if(i%j==0){
					t=false;
					break;
				}
			}
			if(t==true){
				System.out.println(i);
			}
		}*/
		
		
		
		
		/*int n=1234567890;
		int nn=0;
		int t=0;
		while(n>0){
			t=n%10;
			if (t==0)
				nn=0;
			else
				nn=nn*10+t;
			n/=10;
		}
		
		System.out.println(nn);
		
		
		
		int te=0,newN=1,old=1;
		for(int i=0;i<10;i++){
			te=newN;
			newN=old+newN;
			old=te;
			
			System.out.print(" "+newN);
		}//输出1 1 2 3 5 8
*/		
		
		
		/*System.out.print("请输入值：");
    	int n =scan.nextInt();
    	if(n>9){
    		System.out.println("输入有误");
    		return;
    	}
    	System.out.print("请输入累加项数：");
    	int c =scan.nextInt();
		int sum=0;
		int d=n;
		for(int i=1;i<=c;i++){
			System.out.print(n+"+");
			sum=sum+n;
			n=n*10+d;
		}
		System.out.print("="+sum);
		System.out.println();*///累加
		
		
		System.out.print("请输入次数：");
    	int count =scan.nextInt();
		double height=100,sumHeight=100;
		for(int i=1;i<count;i++){
			height=height/2;
			sumHeight=sumHeight+height*2;
		}
		System.out.println(sumHeight);
		System.out.println("第"+count+"次"+"一共弹跳了"+sumHeight+","+"第"+count+"次"+"弹跳的高度是"+height);
	
		
		
		System.out.print("请输入一个正整数分解：");
    	int number =scan.nextInt();	
    	int min=2;
		if(number<=1){
			System.out.println("输入有误");
		}
		else if(number==2){
			System.out.print(number+"="+"1"+"*"+number);
		}
		else{
			System.out.print(number+"=");
			while(min<=number){
	    		if(number%min==0){
	    			System.out.print("*"+min);
	    			number=number/min;
	    		}
	    		else{
	    			min++;
	    		}
	    	}
		}//分解质因数
		
		
		
	}
}
