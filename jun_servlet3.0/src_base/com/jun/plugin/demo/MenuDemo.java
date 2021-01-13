package com.jun.plugin.demo;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

public class MenuDemo extends JApplet {
	JTextField t = new JTextField(15);  //���������ı���
	ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			t.setText(((JMenuItem)e.getSource()).getText());
		}
	};
	JMenu[] menus = { new JMenu("�ļ�"),
			          new JMenu("�༭"),
					  new JMenu("����")};
	JMenuItem[] items = { 
		new JMenuItem("�½�"), new JMenuItem("����"),
		new JMenuItem("��������"), new JMenuItem("����"),
		new JMenuItem("����"),new JMenuItem("����"),
		new JMenuItem("�˳�"),new JMenuItem("����"),
		new JMenuItem("����") };
	JPopupMenu popup = new JPopupMenu(); // ����һ������ʽ�˵�
	
	public void init(){
		for(int i = 0; i < items.length; i++){
            //Ϊÿһ���˵������Ӽ�����
			items[i].addActionListener(al);  
			// ��ÿ���˵�����ӵ����ԵĲ˵���
			menus[i%3].add(items[i]);
		}
	    JMenuBar mb = new JMenuBar();
	    for(int i = 0; i < menus.length; i++){
	    	//��ÿ���˵���ӵ��˵���������
		    mb.add(menus[i]);
		    setJMenuBar(mb);
	    }
	    Container cp = getContentPane();
	    // �趨���ݴ���Ĳ��ֿ���ΪFlowLayout��ʽ
	    cp.setLayout(new FlowLayout());
	    cp.add(t);
	    // Ϊ����ʽ�˵���Ӳ˵���ͼ�����
	    JMenuItem m = new JMenuItem("��С��");
	    popup.add(m);
	    m.addActionListener(al);
	    popup.addSeparator(); // �ڲ˵���ĩβ��ӷָ���϶
	    m = new JMenuItem("���");
	    popup.add(m);
	    m.addActionListener(al);
	    PopupListener pl = new PopupListener();
	    addMouseListener(pl);  // �������˵��¼���ӵ�����¼�����ģʽ��
	}
	
	class PopupListener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			maybeShowPopup(e);
		}
		
		public void mouseReleased (MouseEvent e){
			maybeShowPopup(e);
		}
		
		private void maybeShowPopup(MouseEvent e){
			if(e.isPopupTrigger()){ 
				// �������Ҽ��ǵ���ʽ�˵����ڵ�ǰ���λ����ʾ�ò˵�
				popup.show(e.getComponent(),e.getX(),e.getY());
			}
		}
	}
	
	public static void main(String args[]) {
		JApplet applet = new MenuDemo();
		// ����һ��Frame����applet��ӵ�frame��
		JFrame frame = new JFrame("Menu Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(applet);
		applet.init();
		applet.start();
		frame.pack();
		frame.setVisible(true);
	}
}