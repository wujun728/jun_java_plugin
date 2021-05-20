package com.ky26.threeday;

import com.ky26.sixday.CommonMethod;

public class AlgorithmTest {
	public static void main(String[] args) {
		System.out.println("=======兔子问题—黄金数列========");
		System.out.println(sum(11));
		
		System.out.println("=======质数========");
		for(int i=2;i<=100;i++){
            boolean flag=true;
            for(int j=2;j<i;j++){
                if(i%j==0){
                    flag=false;
                    break;//跳出能被其他数整除的数字（不是质数）
                }
            }
            if(flag==true){
                System.out.print(" "+i);
            }
		}
		
		
		System.out.println();
		System.out.println("=======水仙花数========");	
		for(int i=100;i<1000;i++){
			int a=i/100;
			int b=i/10%10;
			int c=i%10;
			if(a*a*a+b*b*b+c*c*c==i){
				System.out.println(a+"*"+a+"*"+a+"+"+b+"*"+b+"*"+b+"+"+c+"*"+c+"*"+c+"="+i);
			}
		}
		
		System.out.println("=======三目运算嵌套========");
		int n=60;
		System.out.println(n>=90?"优秀":(n>=60?"及格":"不及格"));
		
		
		System.out.println("=======a+aa+aaa+...的值========");
		int dig=7,amou=5,sum=0,m=7;//m为固定的输入值，amou是需要求到的数字长度
		for(int i=1;i<=amou;i++){
			sum+=dig;
			dig=dig*10+m;
			System.out.println(sum);
		}
		
		
		System.out.println("=======关于递归——阶乘========");
		System.out.println(factorial(5));
		
		
		System.out.println("=======do-while========");
		int b=100;
		do{
			System.out.print(","+b);
			b--;
		}while(b>=60);//执行语句之后满足while中的条件则再次执行语句，不满足则跳出循环
		
		
		/*System.out.println("=======最大公约数和最小公倍数=======");
		int a=40,b=60;
		int max=a>b?a:b;
		int min=2;
		int mul=1;
		for(int i=1;i<=max;i++){
			if(a%min==0&&b%min==0){
				a=a/min;
				b=b/min;
				mul*=min;
			}
			else{
				min++;
			}
		}
		System.out.println("最大公约数："+mul);
		System.out.print("最小公倍数："+mul*a*b);*/
		
		
		/*System.out.println("=======最大公约数和最小公倍数-方法调用=======");
		System.out.println("最大公约数"+mul(40,60));
		System.out.println("最大公约数"+mul(40,10,60));
		
		System.out.println("=======排列组合输出数字======");
		int count=0;
		for(int i=1;i<5;i++){
			for(int j=1;j<5;j++){
				for(int k=1;k<5;k++){
					if(i!=j&&i!=k&&j!=k){
						System.out.print(i*100+j*10+k+",");
						count++;
					}
				}
			}
		}
		System.out.println();
		System.out.println("有"+count+"个");*/
		
		
		
		/*double m=1,n=2,sum=0;
		for(int i=0;i<20;i++){
			double j=m;
			double k=n;
			sum=sum+(n/m);
			n=k+j;
			m=k;
		}
		System.out.println(sum);//2/1，3/2，5/3，8/5，13/8，21/13...*/		
		
		/*long sum=0;
		for(int i=1;i<=20;i++){
			sum+=factorial(i);
		}
		System.out.println(sum);//1+2!+3!+...+20!*/
		
		
		/*System.out.println("======插入新值到排序后的数组中输出其对应下标=====");
		int[] arr={12,21,15,3,4,13,43};
		CommonMethod.bubbleSort(arr);
		CommonMethod.printArrays(arr);
		int n=12;
		int index=0;
		for(int i=0;i<arr.length-1;i++){
			if(n>=arr[i]&&n<arr[i+1]){
				index=i+1;
				break;
			}
			else if(n>=arr[arr.length-1]){
				index=arr.length;
				break;
			}
			else if(n<arr[0]){
				index=0;
				break;
			}
		}
		System.out.println("新插入数字是："+n+","+"插入数组位置："+index);
		
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
		
		*
		*/
		
		
	}
	
	
	
	static int sum(int n){
		if(n>2){
			return sum(n-1)+sum(n-2);
		}
		else{
			return 1;
		}
	}
	
	static long factorial(int n){
		if(n<=1){
			return 1;
		}
		else{
			return n*factorial(n-1);
		}
	}
	
	static int mul(int m,int n){
		int max=m>n?m:n;
		int min=2;
		int mul=1;
		for(int i=1;i<=max;i++){
			if(m%min==0&&n%min==0){
				m=m/min;
				n=n/min;
				mul*=min;
			}
			else{
				min++;
			}
		}
		return (mul*m*n);
	}
	
	static int mul(int m,int n,int k){
		int temp=m>n?m:n;
		int max=temp>k?temp:k;
		int min=2;
		int mul=1;
		for(int i=1;i<=max;i++){
			if(m%min==0&&n%min==0&&k%min==0){
				m=m/min;
				n=n/min;
				k=k/min;
				mul*=min;
			}
			else{
				min++;
			}
		}
		return (mul*m*n*k);
	}
	
	
	
	
}