package com.jun.plugin.demo;
//package sendandrecievedata;

import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class MulticastDemo {
	private boolean packFrame = false;

	//	 ����Ӧ�ó���
	public MulticastDemo() {
		MulticastFrame frame = new MulticastFrame();
		if (packFrame) {
			frame.pack();
		} else {
			frame.validate();
		}
		// �Ѵ�����������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		frame.setVisible(true);
	}
	//	 Main ����
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel
			    (UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new MulticastDemo();
	}
}

class MulticastFrame extends JFrame {
	private JPanel contentPane;
	private TextField textField1 = new TextField();
	private Button button1 = new Button();
	private Label label1 = new Label();
	private Label label2 = new Label();
	int port; // �����鲥ʹ�õĶ˿�
	MulticastSocket socket; // ���������鲥��ʹ�õ�MulticastSocket��
	InetAddress group; // ���������鲥��ʹ�õ��鲥���ַ
	DatagramPacket packet; // �������ͺͽ��������ʹ�õ�DatagramPacket��

	//���촰��
	public MulticastFrame() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �ؼ���ʼ��
	private void init() throws Exception {
		contentPane = (JPanel) this.getContentPane(); //��ƴ��岼��
		textField1.setBounds(new Rectangle(88, 127, 240, 32));
		contentPane.setLayout(null);
		this.setSize(new Dimension(400, 300));
		this.setTitle("�鲥���з��ͺͽ������");
		button1.setLabel("�������");
		button1.setBounds(new Rectangle(166, 186, 88, 29));
		button1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button1_actionPerformed(e);
			}
		});
		label1.setBounds(new Rectangle(88, 84, 240, 32));
		label2.setBounds(new Rectangle(88, 41, 220, 32));
		contentPane.add(textField1, null);
		contentPane.add(button1, null);
		contentPane.add(label1, null);
		contentPane.add(label2, null);
		createMulticastGroupAndJoin();
	}

	// �رմ���ʱ�˳�
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}

	public void createMulticastGroupAndJoin() // ����һ���鲥�鲢�������ĺ���
	{
		try {
			port = 5000; // �����鲥��ļ���˿�Ϊ5000
			group = InetAddress.getByName("239.0.0.0"); // �����鲥��ĵ�ַΪ239.0.0.0
			socket = new MulticastSocket(port); // ��ʼ��MulticastSocket�ಢ���˿ں���֮����
			socket.setTimeToLive(1); // �����鲥��ݱ��ķ��ͷ�ΧΪ��������
			socket.setSoTimeout(10000); // �����׽��ֵĽ�����ݱ����ʱ��
			socket.joinGroup(group); // ������鲥��
			label2.setText("�Ѽ����ַΪ239.0.0.0���鲥��");
		} catch (Exception e1) {
			System.out.println("Error: " + e1); // ��׽�쳣���
		}
	}

	public void sendData() // ���鲥�鷢����ݵĺ���
	{
		try {
			byte[] data = textField1.getText().getBytes(); // ���û�Ҫ���͵����ת��Ϊ�ֽ���ʽ��
			// �洢��������
			packet = new DatagramPacket(data, data.length, group, port); // ��ʼ��DatagramPacket
			socket.send(packet); // ͨ��MulticastSocketʵ��˿����鲥�鷢�����
		} catch (Exception e1) {
			System.out.println("Error: " + e1); // ��׽�쳣���
		}
	}

	public String recieveData() // ���鲥�������ݵĺ���
	{
		String message;
		try {
			packet.setData(new byte[512]); // �趨������ݵ�DatagramPacketʵ��������С
			packet.setLength(512); // �趨������ݵ�DatagramPacketʵ��ĳ���
			socket.receive(packet); // ͨ��MulticastSocketʵ��˿ڴ��鲥��������
			// �����ܵ����ת�����ַ���ʽ
			message = new String(packet.getData());
		} catch (Exception e1) {
			System.out.println("Error: " + e1); // ��׽�쳣���
			message = "Error: " + e1;
		}
		return message;
	}

	// ��ť1�¼�����
	void button1_actionPerformed(ActionEvent e) {
		sendData(); // �������鲥�鷢�����
		String message = recieveData(); // Ȼ����鲥����ܴ����
		label1.setText("Data recieved: '" + message + "'"); // ������ʾ���յ������
	}
}