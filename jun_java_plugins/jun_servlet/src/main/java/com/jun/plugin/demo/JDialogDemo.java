package com.jun.plugin.demo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

//JDialogDemo.java
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class JDialogDemo implements ActionListener {
	JFrame f = null;
	
	public JDialogDemo() {
		f = new JFrame("JDialogDemo");
		Container contentPane = f.getContentPane();
		JPanel buttonPanel = new JPanel();
		JButton b = new JButton("������Ʒ");
		b.addActionListener(this);
		buttonPanel.add(b);
		b = new JButton("�뿪ϵͳ");
		b.addActionListener(this);
		buttonPanel.add(b);

		// ���ñ߿�
		buttonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.blue, 2), "������Ʒϵͳ",
				TitledBorder.CENTER, TitledBorder.TOP));
		contentPane.add(buttonPanel, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("������Ʒ")) {
			new LendingSystem(f);
		} else if (cmd.equals("�뿪ϵͳ")) {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		SwingUtil.setLookAndFeel();
		new JDialogDemo();
	}
}

class LendingSystem implements ActionListener {
	JTextField staffField, objectField, borrowDateField, returnDateField,
			reasonField;
	JDialog dialog;

	LendingSystem(JFrame f) {
		dialog = new JDialog(f, "������Ʒ", true);
		GridBagConstraints c;
		int gridx, gridy, gridwidth, gridheight, 
		    anchor, fill, ipadx, ipady;
		double weightx, weighty;
		Insets inset;

		GridBagLayout gridbag = new GridBagLayout();
		Container dialogPane = dialog.getContentPane();
		dialogPane.setLayout(gridbag);

		JLabel label = new JLabel("Ա����� : ");
		gridx = 0; //��0��
		gridy = 0; //��0��
		gridwidth = 1; //ռһ��λ���
		gridheight = 1; //ռһ��λ�߶�
		weightx = 0; //��������ʱ�������������0
		weighty = 0; //��������ʱ����߶��������0
		anchor = GridBagConstraints.CENTER; //�����������sizeʱ�����������������
		fill = GridBagConstraints.BOTH; //��������ʱ������ˮƽ�봹ֱ�ռ�
		inset = new Insets(0, 0, 0, 0); //�������
		ipadx = 0; //�����ˮƽ���
		ipady = 0; //����ڴ�ֱ�߶�
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(label, c);
		dialogPane.add(label);

		label = new JLabel("�������� : ");
		gridx = 3;
		gridy = 0;
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(label, c);
		dialogPane.add(label);

		label = new JLabel("��������: ");
		gridx = 0;
		gridy = 1;
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(label, c);
		dialogPane.add(label);

		label = new JLabel("�黹����: ");
		gridx = 3;
		gridy = 1;
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(label, c);
		dialogPane.add(label);

		label = new JLabel("����ԭ�� : ");
		gridx = 0;
		gridy = 2;
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(label, c);
		dialogPane.add(label);

		staffField = new JTextField();
		gridx = 1;
		gridy = 0;
		gridwidth = 2;
		gridheight = 1;
		weightx = 1;
		weighty = 0;
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(staffField, c);
		dialogPane.add(staffField);

		objectField = new JTextField();
		gridx = 4;
		gridy = 0;
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(objectField, c);
		dialogPane.add(objectField);

		borrowDateField = new JTextField();
		gridx = 1;
		gridy = 1;
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(borrowDateField, c);
		dialogPane.add(borrowDateField);

		returnDateField = new JTextField();
		gridx = 4;
		gridy = 1;
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(returnDateField, c);
		dialogPane.add(returnDateField);

		reasonField = new JTextField();
		gridx = 1;
		gridy = 2;
		gridwidth = 5;
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(reasonField, c);
		dialogPane.add(reasonField);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		JButton b = new JButton("ȷ��");
		panel.add(b);
		b = new JButton("ȡ��");
		b.addActionListener(this);
		panel.add(b);

		gridx = 0;
		gridy = 3;
		gridwidth = 6;
		weightx = 1;
		weighty = 1;
		c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
				weightx, weighty, anchor, fill, inset, ipadx, ipady);
		gridbag.setConstraints(panel, c);
		dialogPane.add(panel);

		dialog.setBounds(200, 150, 400, 130);
		dialog.show();
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("ȷ��")) {
			// ������������Ҫ����Ķ���
			// ���罫��ݱ��浽��ݿ��

		} else if (cmd.equals("ȡ��")) {
			dialog.dispose();
		}
	}

}

class SwingUtil {
	public static final void setLookAndFeel() {
		try {
			Font font = new Font("JFrame", Font.PLAIN, 12);
			Enumeration keys = UIManager.getLookAndFeelDefaults().keys();
            // ���������嶼����Ϊfont�����ж��������
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				if (UIManager.get(key) instanceof Font) {
					UIManager.put(key, font);
				}
			}
			// Windows���Ľ���
			UIManager.setLookAndFeel
			    ("com.sun.java.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}