package com.jun.plugin.codegenerator;


import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
* @Author: wujun
* @Date: 2021/11/23/14:21
* @Description:
*/
public class YamlUtil  {

   public static void main(String[] args) throws Exception {
       Yaml yaml = new Yaml();
       String path = "/Users/test/test.yaml";
       InputStream in = new FileInputStream(path);
       Map<String, Object> map = yaml.loadAs(in, Map.class);
       System.out.println(get(map, "spec|:template|:spec|-0_containers|:image"));
       modify(map, "spec|:template|:spec|-0_containers|:image","11111");
       System.out.println(get(map, "spec|:template|:spec|-0_containers|:image"));
       System.out.println(yaml.dump(map));
   }

   public static Map<String, Object> load(String path) {
       try {
           Yaml yaml = new Yaml();
           InputStream in = new FileInputStream(path);
           Map<String, Object> map = yaml.loadAs(in, Map.class);
           return map;
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
   }


   private static Object get(Map<String, Object> map, String key) {
       if (key.contains("|")) {
           String[] ks = key.split("[|]");
           String ck;
           Map<String, Object> v = (Map<String, Object>) map.get(ks[0]);
           for (int i = 1; i < ks.length; i++) {
               ck = ks[i];
               String type = ck.substring(0, 1);
               if (type.equals(":")) {
                   String k = ck.substring(1);
                   if (i == ks.length - 1) {
                       return v.get(k);
                   } else {
                       try {
                           v = (Map<String, Object>) v.get(k);
                       } catch (Exception e) {
                           System.out.println();
                       }
                   }
               } else if (type.equals("-")) {
                   String[] strings = ck.substring(1).split("_");
                   String index = strings[0];
                   String k = strings[1];
                   List<Map<String, Object>> list = (List<Map<String, Object>>) v.get(k);
                   Map<String, Object> val = list.get(Integer.parseInt(index));
                   if (i == ks.length - 1) {
                       return val.get(k);
                   } else {
                       v = list.get(Integer.parseInt(index));
                   }
               }
           }
       } else {
           return map.get(key);
       }
       return null;
   }

   private static void modify(Map<String, Object> map, String key, String value) {
       if (key.contains("|")) {
           String[] ks = key.split("[|]");
           String ck;
           Map<String, Object> v = (Map<String, Object>) map.get(ks[0]);
           for (int i = 1; i < ks.length; i++) {
               ck = ks[i];
               String type = ck.substring(0, 1);
               if (type.equals(":")) {
                   String k = ck.substring(1);
                   if (i == ks.length - 1) {
                       v.put(k, value);
                   } else {
                       v = (Map<String, Object>) v.get(k);
                   }
               } else if (type.equals("-")) {
                   String[] strings = ck.substring(1).split("_");
                   String index = strings[0];
                   String k = strings[1];
                   List<Map<String, Object>> list = (List<Map<String, Object>>) v.get(k);
                   Map<String, Object> val = list.get(Integer.parseInt(index));
                   if (i == ks.length - 1) {
                       val.put(k, value);
                   } else {
                       v = list.get(Integer.parseInt(index));
                   }
               }
           }
       } else {
           map.put(key, value);
       }
   }

}
