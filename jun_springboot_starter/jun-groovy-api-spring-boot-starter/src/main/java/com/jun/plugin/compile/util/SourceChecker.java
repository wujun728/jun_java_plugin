package com.jun.plugin.compile.util;

import java.util.regex.Pattern;

public class SourceChecker {

	public static String s = "(java\\.io)|(java\\.util\\.concurrent)|(java\\.net|java\\.lang\\.reflect)|(HashTable)|(HashSet)|(SimpleMavenProject)|(StringBuffer)|(StringBuilder)|(\\[)(.+)(\\])";
	public static  Pattern pattern =  Pattern.compile(s);
	public static boolean isValidate(String source){
		boolean b = pattern.matcher(source).find();
		return !b;
	}

	public  static void main(String[] args){
		SourceChecker checker = new SourceChecker();
		String str = "int a =1 , aa = new int[];";
		System.out.println(checker.isValidate(str));
		System.out.println(System.getProperty("java.class.path"));
	}


}
