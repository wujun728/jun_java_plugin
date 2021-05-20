package jun_file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ReadTxtTest {
 
   public static String txt2String(File file){
       StringBuilder result = new StringBuilder();
       Set set = new HashSet<>();
       try{
           BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
           String s = null;
           while((s = br.readLine())!=null){//使用readLine方法，一次读一行
        	   
//        	   System.out.println(s);
        	   s = s.substring(0, s.lastIndexOf("\\"));
        	   
        	   
        	   set.add(s);
//        	   System.out.println(s);
               //result.append(System.lineSeparator()+s);
           }
           br.close();    
       }catch(Exception e){
           e.printStackTrace();
       }
       Iterator it = set.iterator();
       while (it.hasNext()) {
           System.out.println(it.next());
       }
       return result.toString();
   }
   
   public static void main(String[] args){
       File file = new File("D:\\Documents\\Desktop\\123.txt");
       System.out.println(txt2String(file));
   }
}
