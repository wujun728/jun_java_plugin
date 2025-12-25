package com.jun.plugin.codegenerator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.File;
import java.util.List;
import java.util.Set;


public class Test666 {


    public static void main(String[] args) {
        List<String> filenames = FileUtil.readLines(new File("D:\\Documents\\Desktop\\mp4.txt"),"UTF-8");
        System.out.println(filenames.size());
        Set<String> sets = Sets.newHashSet();
        filenames.forEach(i->{sets.add(i);});
        System.out.println(sets.size());

        List<String> urls = FileUtil.readLines(new File("D:\\Documents\\Desktop\\new_add_urls2.txt"),"UTF-8");
        Set<String> sets2 = Sets.newHashSet();
        urls.forEach(i->{sets2.add(i);});
        System.out.println(sets2.size());

        List<String> urlsNew = Lists.newArrayList();
        List<String> urlsNew2 = Lists.newArrayList();
        for(String url : sets2){
            if(!sets.stream().anyMatch(i->{
                //return url.endsWith(i);
                return FileNameUtil.getName(url).equalsIgnoreCase(i);
               // return url.substring(url.lastIndexOf("/"), url.length()).equalsIgnoreCase(i);
            })){
                urlsNew.add(url);
            }else{
                urlsNew2.add(url);
            }
        }
        System.out.println(urlsNew2.size());
        System.err.println(urlsNew.size());
        FileUtil.writeUtf8Lines(urlsNew2,"D:\\Documents\\Desktop\\exists.txt");
        FileUtil.writeUtf8Lines(urlsNew,"D:\\Documents\\Desktop\\new.txt");

//        String result = HttpUtil.post("https://gitlab.billjc.com/oauth/token?grant_type=password", "{\n" +
//                "    \"username\": \"wujun82921\",\n" +
//                "    \"password\": \"Abcde123456\"\n" +
//                "}");
//        Console.log(result);
    }

}
