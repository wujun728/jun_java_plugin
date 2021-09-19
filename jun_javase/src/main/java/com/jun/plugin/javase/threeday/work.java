package com.jun.plugin.javase.threeday;

import java.util.Scanner;

public class work {
    public static void main(String[] args) {
    	Scanner scan=new Scanner(System.in);
//    	��ϰ�����������������ʽ��
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
    	
    	System.out.print("���������:");
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

  //��s=a+aa+aaa+aaaa...��ֵ������a��һ�����֡�����������Ϊ1234=1+11+111+1111�����������ʽ������������������һ������a����ֵ��С����һ�������ۼӵ�����
//    	Scanner scan=new Scanner(System.in);
   
    	/*System.out.print("������a��ֵ��");
    	int a =scan.nextInt();
    	System.out.print("�������ۼ�������");
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
  	
//10 һС���100�׸߶��������壬ÿ����غ�����ԭ���߶ȵ�һ�룬�����£������ڵ�10�����ʱ���������˶����ף���10�η������
    	
    	System.out.print("�����������");
    	int n =scan.nextInt();
    	double h=100;
    	double sum=100;
    	for(int i=2;i<=n;i++){
    		h=h*0.5;
    		sum=sum+h*2;
    		System.out.println(sum);
    	}
    	System.out.println("��"+n+"���ܷ���"+h+"��");
    	System.out.println("�ڵ�"+n+"�����ʱ����������"+sum+"��");
    	
    	
//11��һ���������ֽ�����������������90����ӡ���Ϊ90=2*3*3*5��
      
        /*System.out.print("������һ����������");
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
