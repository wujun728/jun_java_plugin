package com.jun.plugin.algorithm.array;
import org.junit.Test;

/**
 *和为s的连续正整数序列
 * 输入一个正整数s，打印出所有和为s的连续正整数序列（至少含有两个数字）。
 */
public class SeriesSum {
public void printResult(int start,int end){
	for(int k=start;k<=end;k++){
		System.out.print(k+" ");
	}
	System.out.println();
}
public void addSum(int s){
	int start=1,end=2;
	int sum=start+end;
	int half=(s+1)/2;
	while(start<half){
		if(sum==s){
			printResult(start, end);
			sum=sum-start;
			start++;
			end++;
			sum+=end;
		}else if(sum<s){
			end++;
			sum+=end;
		}else{
			sum=sum-start;
			start++;
		}
	}
}
@Test
public void test(){
	addSum(21);
	System.out.println();
	addSum(15);
}
}
