package com.ky26.threeday;

import java.util.Scanner;

public class work {
    public static void main(String[] args) {
    	Scanner scan=new Scanner(System.in);
//    	练习运算产生结果的输出形式。
//    	8		/**
//    			 * *****
//    			 * ****
//    			 * ***
//    			 * **
//    			 * *
//    			 */
//    			/**
//    			 * 55555
//    			 * 4444
//    			 * 333
//    			 * 22
//    			 * 1
//    			 */
   /*	
    	
    	System.out.print("请输入层数:");
    	int m=scan.nextInt();
    	for(int i=1;i<=m;i++){
    	   for(int j=i;j<=m;j++){
    		   System.out.print("*");
//    		   System.out.print(6-i);
    	   }
    	   System.out.println();
    	}
    	for(int k=1;k<=m;k++){
    		for(int n=m;n>=k;n--){
    			System.out.print(m+1-k);
    		}
    		System.out.println();
    	}
    */	

  //求s=a+aa+aaa+aaaa...的值，其中a是一个数字。最终输出结果为1234=1+11+111+1111这样的输出形式，定义两个变量其中一个代表a的数值大小，另一个代表累加的项数
//    	Scanner scan=new Scanner(System.in);
   
    	/*System.out.print("请输入a的值：");
    	int a =scan.nextInt();
    	System.out.print("请输入累加项数：");
    	int count =scan.nextInt();
    	int n=0;
    	int sum=0;
    	String string="";
    	for(int i=1;i<=count;i++){
    		n=n*10+a;
    		if(i<count){
    			string=string+n+"+";
    		}else if(i==count){
    			string=string+n;
    		}
    		sum=sum+n;
//    		System.out.println(n);
    	}
    	System.out.println(sum+"="+string);*/
  	
//10 一小球从100米高度自由落体，每次落地后跳回原来高度的一半，在落下，求它在第10次落地时，共经过了多少米？第10次反弹多高
    	
    	System.out.print("请输入次数：");
    	int n =scan.nextInt();
    	double h=100;
    	double sum=100;
    	for(int i=2;i<=n;i++){
    		h=h*0.5;
    		sum=sum+h*2;
    		System.out.println(sum);
    	}
    	System.out.println("第"+n+"次能反弹"+h+"米");
    	System.out.println("在第"+n+"次落地时，共经过了"+sum+"米");
    	
    	
//11将一个正整数分解质因数。例如输入90，打印结果为90=2*3*3*5；
      
        /*System.out.print("请输入一个正整数：");
    	int number =scan.nextInt();
    	int n=number;
    	int i,j,k;
    	String string="";
    	do{
    	  for(i=2;i<=number;i++){
    		if(number%i==0){
    			for (j = 2; j <= Math.sqrt(i); j++){   
                    if (i % j == 0)
                        break;  
                }   
                if (j > Math.sqrt(i))
                	if(number!=i){
                	   string=string+i+"*";
                	}else if(number==i){
                       string=string+i;
                    }
                    break;
    		}
    	  }
    	  number=number/i; 
    	}while(number!=1) ;
    	System.out.println(n+"="+string);*/
    
	}
    
}
