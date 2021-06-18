package com.ky26.SevDay;

public class HomeWork2 {
	public static void main(String[] args) {
		/*int sum=fa(11);
		System.out.println(sum);*/
		
		
		
		for(int i=101;i<=200;i++){
			boolean flag=true;
			for(int j=2;j<i;j++){
				if(i%j==0){
					flag=false;
					break;
				}
			}
			if(flag==true){
				System.out.print(i+",");
			}
		}
		System.out.println();
		
		for(int i=100;i<=999;i++){
			int a=i/100;
			int b=i/10%10;
			int c=i%10;
			
			if(a*a*a+b*b*b+c*c*c==i){
				System.out.println("水仙花数"+i);
			}
		}
		
		int m=40,n=60;
		int min=2;
		int mul=1;
		int max=m>n?m:n;
		for(int i=0;i<=max;i++){
			if(m%min==0&&n%min==0){
				m=m/min;
				n=n/min;
				mul*=min;
				
			}
			else{
				min++;
			}
			
		}
		System.out.println("最小公倍数"+mul+",");
		System.out.print("最大公约数"+mul*m*n);
		System.out.println();
		
		
		int x=90,minZ=2;
		System.out.print(x+"=");
		while(minZ<=x){
			if(x%minZ==0){
				System.out.print("*"+minZ);
				x=x/minZ;
			}
			else{
				minZ++;
			}
		}
		System.out.println();
		for(int i=1;i<=1000;i++){
			int sum=0;
			for(int j=1;j<i;j++){
				if(i%j==0){
					sum+=j;
					if(sum==i){
						System.out.print(i+",");
					}
				}
			}
		}
		System.out.println();
		int count=10;
		double height=100,sumHeight=100;
		for(int i=1;i<count;i++){
			height=height/2;
			sumHeight=sumHeight+height*2;
		}
		System.out.println(height);
		System.out.println(sumHeight);
		
		int countT=0;
		for(int i=1;i<5;i++){
			for(int j=1;j<5;j++){
				for(int k=1;k<5;k++){
					if(i!=j&&i!=k&&j!=k){
						System.out.print(i+""+j+""+k+",");
						countT++;
					}
				}
			}
		}
		System.out.print("一共"+countT);
		System.out.println();
		
		int year=2012,mouth=8,date=17,sum=0;
		int[] arrMouth={0,29,31,30,31,30,31,31,30,31,30,31};
		for(int i=0;i<mouth;i++){
			if(year%4==0&&year%100!=0){
				arrMouth[1]=29;
			}
			else{
				arrMouth[1]=28;
			}
			sum+=arrMouth[i];
		}
		System.out.println("是当年的第"+(sum+date)+"天");
		
		/*for(int i=1;i<=9;i++){
			for(int j=1;j<=i;j++){
				System.out.print(j+"*"+i+"="+j*i+" ");
			}
			System.out.println();
		}
		
		int totle=0,rest=1;
		for(int i=9;i>0;i--){
			totle=(rest+1)*2;
			rest=totle;
		}
		System.out.println(totle);
		
		for(int i=1;i<5;i++){
			for(int j=1;j<6-i;j++){
				System.out.print(" ");
			}
			for(int k=1;k<=i;k++){
				System.out.print("*");
			}
			System.out.println();
		}
		for(int i=1;i<4;i++){
			for(int j=0;j<=i;j++){
				System.out.print(" ");
			}
			for(int k=1;k<5-i;k++){
				System.out.print("*");
			}
			System.out.println();
		}
		
		for(int i=0;i<5;i++){
			for(int k=0;k<5-i;k++){
				System.out.print("*");
			}
			for(int j=0;j<=i*2;j++){
				System.out.print(" ");
			}
			System.out.println();
		}
		for(int i=0;i<4;i++){
			for(int k=0;k<i+2;k++){
				System.out.print("*");
			}
			for(int j=0;j<=6-2*i;j++){
				System.out.print(" ");
			}
			System.out.println();
		}*/
		
		
		
		/*for(int i=0;i<5;i++){
			for(int j=0;j<5-i;j++){
				System.out.print(" ");
			}
			for(int k=0;k<=i*2-1;k++){
				System.out.print("&");
			}
			System.out.println("*");
		}
		
		for(int i=0;i<=5;i++){
			for(int j=0;j<i;j++){
				System.out.print(" ");
			}
			for(int k=8;k>=i*2-1;k--){
				System.out.print("#");
			}
			System.out.println("*");
		}*/
		
		/*for(int k=1;k<7;k++){
			for(int i=1;i<7-k;i++){
				System.out.print("$");
			}
			System.out.print("*");
			for(int i=1;i<=(k-1)*2-1;i++){
				System.out.print("@");
			}
			if(k!=1){
				System.out.print("*");
			}
			System.out.println();
		}
		
		for(int k=5;k>0;k--){
			for(int i=0;i<6-k;i++){
				System.out.print("&");
			}
			System.out.print("*");
			for(int i=1;i<=(k-1)*2-1;i++){
				System.out.print("#");
			}
			if(k!=1){
				System.out.print("*");
			}
			System.out.println();
		}
		
		
		for(int i=0;i<5;i++){
			for(int j=0;j<5-i;j++){
				System.out.print(" ");
			}
			for(int k=0;k<i*2-1;k++){
				System.out.print("*");
			}
			System.out.println();
		}
		for(int i=0;i<5;i++){
			for(int k=0;k<i;k++){
				System.out.print(" ");
			}
			for(int j=0;j<(5-i)*2-1;j++){
				System.out.print("*");
			}
			System.out.println();
		}*/
		
		
		
		
		
	}
	static int fa(int n){
		if(n<=2){
			return n=1;
		}
		else{
			return fa(n-1)+fa(n-2);
		}
	}
	

	
	
	
}
