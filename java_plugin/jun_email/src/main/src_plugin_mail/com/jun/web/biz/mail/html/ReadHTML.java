package com.jun.web.biz.mail.html;


import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStreamReader;  
 
public class ReadHTML {  
 
   /** 
    * @param args 
    */  
   //public static void main(String[] args) {  
       // TODO Auto-generated method stub  
   public static String reMailString(){  
       //String info="";  
       StringBuffer buff=new StringBuffer();  
       InputStreamReader in=null;  
       BufferedReader br=null;  
       String path = System.getProperty("user.dir") + "/src_jun/com/jun/web/biz/mail/html/email2.html";  
       File file=new File(path);  
       try {  
           in=new InputStreamReader(new FileInputStream(file));  
           br=new BufferedReader(in);  
           String line=null;  
           while((line=br.readLine()) != null){  
               System.out.println(line);  
               buff.append(line).append("\n");  
           }  
             
             
       } catch (FileNotFoundException e) {  
           // TODO Auto-generated catch block  
           e.printStackTrace();  
       } catch (IOException e) {  
           // TODO Auto-generated catch block  
           e.printStackTrace();  
       }finally{  
           if(in!=null){  
               try {  
                   in.close();  
               } catch (IOException e) {  
                   // TODO Auto-generated catch block  
                   e.printStackTrace();  
               }  
           }  
           if(br!=null){  
               try {  
                   br.close();  
               } catch (IOException e) {  
                   // TODO Auto-generated catch block  
                   e.printStackTrace();  
               }  
           }  
       }  
         
       return buff.toString();  
   }  
 
}  