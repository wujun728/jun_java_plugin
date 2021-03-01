import java.io.File;
import java.io.FileOutputStream;

//访问权限的控制
public class PolicyExample {
	public static void main(String[] args){
  	try{
    	//写一个文件到c:/hello.txt
	    byte[] info = "Hello,这是测试信息".getBytes(); //定义写到文件的信息
	    File testFile = new File("c:/hello.txt"); //定义文件,输出到c:/hello.txt
	    FileOutputStream fout = new FileOutputStream(testFile);
	    fout.write(info); //写信息到文件
	    fout.close(); //关闭输出流
	    System.out.println(testFile.getAbsolutePath() + " 写入成功");
	}catch (Exception ex){
	    ex.printStackTrace();
	}
	}
}
