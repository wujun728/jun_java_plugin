import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class ChatClient extends Applet {
    
  	TextField tfName = new TextField(15); //姓名输入文本域
  	Button btConnect = new Button("连接"); //连接按钮
  	Button btDisconnect = new Button("断开连接");
  	TextArea tfChat = new TextArea(8,27); //显示聊天信息文本框
  	Button btSend = new Button("发送");
  	TextField tfMessage = new TextField(30);  //聊天输入
  	java.awt.List list1 = new java.awt.List(9); //显示在线用户信息
  
   	Socket socket=null;  //连接端口
  	PrintStream ps=null; //输出流
  	Listen listen=null;  //监听线程
  
  	public void init() {  //Applet初始化
	    tfChat.setEditable(false);	 //设置信息显示框为不可编辑   
	    Panel panel1 = new Panel();  //实例化面板     
  		Panel panel2 = new Panel();        
  		Panel panel3 = new Panel();
  		tfChat.setBackground(Color.white);  //设置组件背景颜色
  		panel1.setBackground(Color.orange);
  		panel2.setBackground(Color.pink);    
	    panel3.setBackground(Color.orange);	    
	    panel1.add(new Label("姓名："));  //增加组件到面板上
	    panel1.add(tfName);
	    panel1.add(btConnect);
	    panel1.add(btDisconnect);    
	    panel2.add(tfChat);
	    panel2.add(list1);    
	    panel3.add(new Label("聊天信息"));
	    panel3.add(tfMessage);
	    panel3.add(btSend);	    
	    setLayout(new BorderLayout()); //设置Applet布局管理器
	    add(panel1, BorderLayout.NORTH);  //增加面板到Applet上
	    add(panel2, BorderLayout.CENTER);
	    add(panel3,  BorderLayout.SOUTH);
  	}

  	public boolean action(Event evt,Object obj){    //事件处理
  		try{
			if(evt.target==btConnect){   //点击连接按钮		
			    if (socket==null){
				 	socket=new Socket(InetAddress.getLocalHost(),5656);     //实例化一个套接字			                 
				 	ps=new PrintStream(socket.getOutputStream());   //获取输出流
				 	StringBuffer info=new StringBuffer("INFO: ");       			                                                      
				 	String userinfo=tfName.getText()+":"+InetAddress.getLocalHost().toString();
				 	ps.println(info.append(userinfo));	//输出信息		
				 	ps.flush();
				 	listen=new Listen(this,tfName.getText(),socket);    //实例化监听线程
				 	listen.start();     //启动线程
				}   
			}
		   	else if(evt.target==btDisconnect){    //点击断开连接按钮
		         disconnect();  //调用断开连接方法
		    }
		   	else if(evt.target==btSend){   //点击发送按钮
		         if(socket!=null){
				     StringBuffer msg=new StringBuffer("MSG: ");     
					 String msgtxt=new String(tfMessage.getText());
					 ps.println(msg.append(tfMessage.getText()));   //发送信息  
					 ps.flush();
				 }
		   	}
		}
		catch (Exception ex){
			ex.printStackTrace();  //输出错误信息
		}
    	return true;
  	}   

  	public void disconnect() {   //断开连接方法
     	if(socket!=null){
			ps.println("QUIT");  //发送信息
			ps.flush();
	 	}
  	}

  	class Listen extends Thread{    //监听服务器传送的信息
 		String name=null;          //用户名
	 	BufferedReader reader ;    //输入流
 		PrintStream ps=null;     //输出流
 		Socket socket=null;      //本地套接字
 		ChatClient client=null;   //客户端ChatClient实例

 		public Listen(ChatClient p,String n,Socket s) {  
	 		client=p;
	 		name=n;
	 		socket=s;

	 		try{
			    reader = new BufferedReader(new InputStreamReader(s.getInputStream())); //获取输入流
		 		ps=new PrintStream(s.getOutputStream());  //获取输出流

			}
	 		catch(IOException ex){
	    		client.disconnect(); //出错则断开连接
	    		ex.printStackTrace(); //输出错误信息
	   		}
    	}  
  
 		public void run(){  
      		String msg=null;
	  		while(socket!=null){
	     		try{
	     			msg=reader.readLine();  //读取服务器端传来信息
	     		}                 
		 		catch(IOException ex){
	    			client.disconnect(); //出错则断开连接
	    			ex.printStackTrace(); //输出错误信息
		 		}
		 		if (msg==null) {    //从服务器传来的信息为空则断开此次连接
			   		client.listen=null;              
		   			client.socket=null;
		   			client.list1.removeAll();
		   			return;
		 		}
		 		StringTokenizer st=new StringTokenizer(msg,":");   //分解字符串
		 		String keyword=st.nextToken();         

		 		if(keyword.equals("newUser")) {    //新用户连接信息
		     		client.list1.removeAll();  //移除原有用户名
			 		while(st.hasMoreTokens()) {    //取得目前所有聊天室用户名
			     		String str=st.nextToken();
				 		client.list1.add(str);  //增加到用户列表中
			 		}
		 		}
		 		else if(keyword.equals("MSG")) {    //聊天信息	                                                 
	     			String usr=st.nextToken();
			 		client.tfChat.append(usr);  //增加聊天信息到信息显示框
			 		client.tfChat.append(st.nextToken("\0"));
			 		client.tfChat.append("\n");  
			 	}
		 		else if(keyword.equals("QUIT")) {   //断天连接信息  
		     		System.out.println("Quit");
		     		try{
		      			client.listen=null;
		      			client.socket.close();  //关闭端口
			  			client.socket=null;
             		}
             		catch(IOException e){}
			  		client.list1.removeAll();  //移除用户列表	 
			 		return;
		 		}
	  		}
 		}     
	} 
}     
