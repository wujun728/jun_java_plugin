package com.jun.plugin.demo;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

public class JScrollPaneDemo implements ActionListener {
	private JScrollPane scrollPane;

	public JScrollPaneDemo() {
		JFrame f = new JFrame("JScrollPaneDemo");
		Container contentPane = f.getContentPane();
		JLabel label1 = new JLabel(new ImageIcon(".\\icons\\Hill.jpg"));
		JPanel panel1 = new JPanel();
		panel1.add(label1);
		scrollPane = new JScrollPane();
		//���ô�����ʾ�����ݴ���Ϊpanel1
		scrollPane.setViewportView(panel1);
		//����ˮƽ�봹ֱ��ͷ
		scrollPane.setColumnHeaderView(new JLabel("ˮƽ��ͷ"));
		scrollPane.setRowHeaderView(new JLabel("��ֱ��ͷ"));
		//����scrollPane�ı߿�������߿�
		scrollPane.setViewportBorder(BorderFactory
				.createBevelBorder(BevelBorder.LOWERED));
		//����scrollPane�ı߽�ͼ��
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, new JLabel(
				new ImageIcon(".\\icons\\Sunset.jpg")));
		scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new JLabel(
				new ImageIcon(".\\icons\\Sunset.jpg")));
		JPanel panel2 = new JPanel(new GridLayout(3, 1));
		JButton b = new JButton("��ʾˮƽ������");
		b.addActionListener(this);
		panel2.add(b);
		b = new JButton("��Ҫ��ʾˮƽ������");
		b.addActionListener(this);
		panel2.add(b);
		b = new JButton("��ʱ��ʾˮƽ������");
		b.addActionListener(this);
		panel2.add(b);
		contentPane.add(panel2, BorderLayout.WEST);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		f.setSize(new Dimension(350, 220));
		f.show();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("��ʾˮƽ������")) {
			scrollPane.setHorizontalScrollBarPolicy
			    (JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		}
		if (e.getActionCommand().equals("��Ҫ��ʾˮƽ������")) {
			scrollPane.setHorizontalScrollBarPolicy
			    (JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		if (e.getActionCommand().equals("��ʱ��ʾˮƽ������")) {
			scrollPane.setHorizontalScrollBarPolicy
			    (JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
		scrollPane.revalidate();//������ʾJScrollPane��״��
	}

	public static void main(String[] args) {
		new JScrollPaneDemo();
	}
}