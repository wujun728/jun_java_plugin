import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//颜色选择下拉框演示

public class IconComboBoxDemo extends JFrame{

	JLabel iconLabel=null;  //用来响应列表框选择的变化
	JComboBox iconComboBox=null;  //定制的选择下拉框

   	public IconComboBoxDemo(){

      //定义Object二维数组,用于初始化下拉框,参数依次为图标,显示文本,提示文本
   	  Object[][] obj={
   	  	{new ImageIcon("1.gif"),"旅游","提供旅游的最新信息"},
   	  	{new ImageIcon("2.gif"),"音乐","提供最新的音乐资讯，古典的、流行的..."},
   	  	{new ImageIcon("3.gif"),"聊天","与朋友聊天"},
   	  	{new ImageIcon("4.gif"),"影视","影视娱乐"},
   	  	{new ImageIcon("5.gif"),"家居","家居世界"},
   	  };

   	  //初始化下拉框
      iconComboBox = new JComboBox();
      iconComboBox.setMaximumRowCount(3);  //设置最大可视行数
      iconComboBox.setRenderer(new IconRenderer()); //设置单元绘制器
      for (int i=0;i<obj.length;i++){   //增加数组中的所有元素到下拉框中
        iconComboBox.addItem(obj[i]);
      }


      //初始化iconLabel信息
      iconLabel = new JLabel();

      //下拉框事件处理,用匿名类实现
      iconComboBox.addActionListener(new ActionListener(){
      	public void actionPerformed(ActionEvent evt){  //处理事件
      	  Object[] obj = (Object[])iconComboBox.getSelectedItem();  //得到选择的内容,此处为一维数组
      	  iconLabel.setIcon((Icon)obj[0]);  //设置iconLabel的图标
      	  iconLabel.setText(obj[1].toString());  //设置iconLabel的文本
       }
      });

      //增加组件到主窗体上
      this.getContentPane().setLayout(new BorderLayout());  //设置布局管理器
      this.getContentPane().add(iconComboBox,BorderLayout.NORTH); //在上方增加下拉框
      this.getContentPane().add(iconLabel,BorderLayout.CENTER);  //在中间增加iconLabel,用于响应选择的变化
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭窗口时退出程序
      this.setSize(350,260);  //设置窗口尺寸
      this.setVisible(true);  //显示窗口
   	}

   	public static void main(String[] args){
      new IconComboBoxDemo();
   	}
}