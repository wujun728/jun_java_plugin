package com.jun.plugin.demo;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JListDemo implements ListSelectionListener {
	JList list = null;
	JLabel label = null;
	String[] s = { "����", "�й�", "Ӣ��", "����", "�����", "����", "����" };

	public JListDemo() {
		JFrame f = new JFrame("JList");
		Container contentPane = f.getContentPane();
		contentPane.setLayout(new BorderLayout());
		label = new JLabel();
		list = new JList(s);
		list.setVisibleRowCount(5); // �趨�б?��Ŀɼ�����
		list.setBorder(BorderFactory.createTitledBorder("����ϲ�����ĸ�������أ�"));
		list.addListSelectionListener(this);
		contentPane.add(label, BorderLayout.NORTH);
		// ���б?����ӹ�����
		contentPane.add(new JScrollPane(list), BorderLayout.CENTER);
		f.pack();
		f.show();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void valueChanged(ListSelectionEvent e) {
		int tmp = 0;
		String stmp = "��Ŀǰѡȡ��";
		// ����JList�����ṩ��getSelectedIndices()�����ɵõ��û���ѡȡ��������Ŀ
		int[] index = list.getSelectedIndices();
		// indexֵ����Щindexֵ��һ��int array����.
		for (int i = 0; i < index.length; i++) {
			tmp = index[i];
			stmp = stmp + s[tmp] + "  ";
		}
		label.setText(stmp);
	}

	public static void main(String args[]) {
		new JListDemo();
	}
}