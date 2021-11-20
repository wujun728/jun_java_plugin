package com.jun.plugin.demo;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

public class DirList {
  public static void main(String[] args) {
    File path = new File(".");
    String[] list;
    if(args.length == 0){
      list = path.list();
    }
    else{
      //���ｫ����Ĳ�����Ϊ��������
      list = path.list(new DirFilter(args[0]));
    }
    for(int i = 0; i < list.length; i++){
      System.out.println(list[i]);
    }
  }
}

class DirFilter implements FilenameFilter {
  private Pattern pattern;
  public DirFilter(String regex) {
    pattern = Pattern.compile(regex);
  }
  public boolean accept(File dir, String name) {
    //�ж���Ϊname���ļ��ǲ��Ƿ�Ϲ�������
    return pattern.matcher(
      new File(name).getName()).matches();
  }
}

