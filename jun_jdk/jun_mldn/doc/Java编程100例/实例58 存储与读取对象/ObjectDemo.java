import java.awt.*;
import java.io.*;
import javax.swing.*;

//存储与读取对象
public class ObjectDemo extends JFrame{	
	public ObjectDemo(){
		super("存储与读取对象");  //调用父类构造函数		
		Container container=getContentPane();  //得到容器
		Icon imageIcon=new ImageIcon("image.gif");  //实例化一个图标
		writeIcon(imageIcon);	//把图标写入到文件里(存储对象到文件)
		Icon readIcon=(Icon)readIcon();	//从文件中读取对象
		container.add(new JLabel(readIcon)); //增加对象到容器上
		
		setSize(300,200); //设置窗口尺寸
		setVisible(true);  //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}
	
	public void writeIcon(Object obj){  //写入对象到文件的方法
		try{
			FileOutputStream fos=new FileOutputStream("label.obj"); //得到文件输出流
			ObjectOutputStream oos=new ObjectOutputStream(fos); //得到对象输出流
			oos.writeObject(obj); //写入对象
			oos.close(); //关闭对象输出流
			fos.close(); //关闭文件输出流
			System.out.println("写入对象到文件"); //在命令行窗口输出提示信息
		}
		catch (Exception ex){
			ex.printStackTrace(); //在命令行窗口输出出错信息
		}
	}
	
	public Object readIcon(){ //从文件中读取对象
		Object obj=null; 
		try{
			FileInputStream fis=new FileInputStream("label.obj"); //得到文件输入流
			ObjectInputStream ois=new ObjectInputStream(fis); //得到对象输入流
			obj=ois.readObject(); //读取对象
			ois.close(); //关闭对象输入流
			fis.close(); //关闭文件输入流
			System.out.println("从文件中读取对象"); //输出提示信息
		}
		catch (Exception ex){
			ex.printStackTrace();
		}		
		return obj;
	}
	
	public static void main(String[] args){
		new ObjectDemo();	
	}
}