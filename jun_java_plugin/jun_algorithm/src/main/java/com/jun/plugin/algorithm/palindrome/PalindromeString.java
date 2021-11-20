package com.jun.plugin.algorithm.palindrome;
import org.junit.Test;

/**
 *判断字符串是否回文
 */
public class PalindromeString {
public boolean isPalindrome(String str){
	if(str==null||str.length()<=1){
		return true;
	}
	for(int i=0,j=str.length()-1;i<j;i++,j--){
		if(str.charAt(i)!=str.charAt(j)){
			return false;
		}
	}
	return true;
}
@Test
public void test(){
	System.out.println(isPalindrome("abcdcba"));
	System.out.println(isPalindrome("abcdeba"));
	System.out.println(isPalindrome("abccba"));
	System.out.println(isPalindrome("abcd"));
	System.out.println(isPalindrome("秋江楚雁宿沙洲 雁宿沙洲浅水流 流水浅洲沙宿雁 洲沙宿雁楚江秋"));
}
}
