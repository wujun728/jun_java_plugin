package com.jun.plugin.javase.threeday;

import com.jun.plugin.javase.sixday.CommonMethod;

public class AlgorithmTest {
	public static void main(String[] args) {
		System.out.println("=======�������⡪�ƽ�����========");
		System.out.println(sum(11));
		
		System.out.println("=======����========");
		for(int i=2;i<=100;i++){
            boolean flag=true;
            for(int j=2;j<i;j++){
                if(i%j==0){
                    flag=false;
                    break;//�����ܱ����������������֣�����������
                }
            }
            if(flag==true){
                System.out.print(" "+i);
            }
		}
		
		
		System.out.println();
		System.out.println("=======ˮ�ɻ���========");	
		for(int i=100;i<1000;i++){
			int a=i/100;
			int b=i/10%10;
			int c=i%10;
			if(a*a*a+b*b*b+c*c*c==i){
				System.out.println(a+"*"+a+"*"+a+"+"+b+"*"+b+"*"+b+"+"+c+"*"+c+"*"+c+"="+i);
			}
		}
		
		System.out.println("=======��Ŀ����Ƕ��========");
		int n=60;
		System.out.println(n>=90?"����":(n>=60?"����":"������"));
		
		
		System.out.println("=======a+aa+aaa+...��ֵ========");
		int dig=7,amou=5,sum=0,m=7;//mΪ�̶�������ֵ��amou����Ҫ�󵽵����ֳ���
		for(int i=1;i<=amou;i++){
			sum+=dig;
			dig=dig*10+m;
			System.out.println(sum);
		}
		
		
		System.out.println("=======���ڵݹ顪���׳�========");
		System.out.println(factorial(5));
		
		
		System.out.println("=======do-while========");
		int b=100;
		do{
			System.out.print(","+b);
			b--;
		}while(b>=60);//ִ�����֮������while�е��������ٴ�ִ����䣬������������ѭ��
		
		
		/*System.out.println("=======���Լ������С������=======");
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
		System.out.println("���Լ����"+mul);
		System.out.print("��С��������"+mul*a*b);*/
		
		
		/*System.out.println("=======���Լ������С������-��������=======");
		System.out.println("���Լ��"+mul(40,60));
		System.out.println("���Լ��"+mul(40,10,60));
		
		System.out.println("=======��������������======");
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
		System.out.println("��"+count+"��");*/
		
		
		
		/*double m=1,n=2,sum=0;
		for(int i=0;i<20;i++){
			double j=m;
			double k=n;
			sum=sum+(n/m);
			n=k+j;
			m=k;
		}
		System.out.println(sum);//2/1��3/2��5/3��8/5��13/8��21/13...*/		
		
		/*long sum=0;
		for(int i=1;i<=20;i++){
			sum+=factorial(i);
		}
		System.out.println(sum);//1+2!+3!+...+20!*/
		
		
		/*System.out.println("======������ֵ��������������������Ӧ�±�=====");
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
		System.out.println("�²��������ǣ�"+n+","+"��������λ�ã�"+index);
		
		System.out.print("������һ���������ֽ⣺");
    	int number =scan.nextInt();	
    	int min=2;
		if(number<=1){
			System.out.println("��������");
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
		}//�ֽ�������
		
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