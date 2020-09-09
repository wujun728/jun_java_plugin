package com.fiwan.qrgenerator;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * 使用javacv 1.2 获取摄像头图像，并识别图像中的二维码，
 * 
 * @author frogchou
 * @version 1.0
 */
public class ScanCode {
	JDialog jf;
	private JLabel icolabel;
	OpenCVFrameGrabber grabber;
	boolean isDisplayable = false;
	boolean getcontent = false;
	private JPanel panel;
	private JPanel panel_2;
	Frame frame = null;
	String retStr=null;
	Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
	Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
	int screenWidth = screenSize.width; // 获取屏幕的宽
	int screenHeight = screenSize.height; // 获取屏幕的高
	private javax.swing.JTextArea jTextArea1;
	public ScanCode(JTextArea jTextArea1) throws Exception, InterruptedException {
		this.jTextArea1=jTextArea1;
		jf = new JDialog();
		jf.setTitle("扫描二维码");
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//jf.setModalityType(ModalityType.APPLICATION_MODAL);
		//jf.setModal(true); 
		jf.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				isDisplayable = true;
				System.out.println(isDisplayable);
			}
		});
		jf.setAlwaysOnTop(true);
		jf.setResizable(false);
		jf.setSize(430, 415);// (frame.imageWidth, frame.imageHeight);
		int windowWidth = jf.getWidth(); // 获得窗口宽
		int windowHeight = jf.getHeight(); // 获得窗口高
		jf.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示
		// jf.add(icolabel);
		JPanel contentPane = new JPanel();
		jf.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(null);
		JButton close = new JButton("关闭");
		close.setBounds(180, 353, 83, 23);
		panel.add(close);

		panel_2 = new JPanel();
		panel_2.setBounds(10, 10, 404, 333);
		panel.add(panel_2);
		icolabel = new JLabel();
		panel_2.add(icolabel);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.exit(0);
				isDisplayable = true;
				jf.dispose();
			}
		});
		grabber = new OpenCVFrameGrabber(0);
		grabber.start(); // 开始获取摄像头数据
		// Frame frame = grabber.grab();
		jf.setVisible(true);
		new Thread1().start(); 
	}


/*接受一个IplImage 对象，转换为BufferedImage
 * @param IplImage
 * @return BufferedImage
 * */
	public static BufferedImage iplToBufImgData(IplImage mat) {
		if (mat.height() > 0 && mat.width() > 0) {
			BufferedImage image = new BufferedImage(mat.width(), mat.height(), BufferedImage.TYPE_3BYTE_BGR);
			WritableRaster raster = image.getRaster();
			DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
			byte[] data = dataBuffer.getData();
			mat.getByteBuffer().get(data);
			return image;
		}
		return null;
	}

	/**
	 * 自定义一个Thread1，处理图像显示，防止界面被卡死
	 * */
	class Thread1 extends Thread {
		public void run() {
			int i=0;
			while (true) {
				if (!jf.isDisplayable() || getcontent) {// 窗口是否关闭,是否获取到内容，是则退出循环
					try {
						grabber.stop();// 停止抓取
					} catch (Exception e) {
						e.printStackTrace();
					}
					jf.dispose();
					break;
				}
				try {
					frame = grabber.grab();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				OpenCVFrameConverter.ToIplImage toIplImage = new OpenCVFrameConverter.ToIplImage();
				IplImage image = toIplImage.convert(frame);
				BufferedImage bufferedImage = iplToBufImgData(image);
				
				
				/* //测试IplImage转BufferedImage是否成功 
				 try { File file=new File("D://tpm111//"+i+++".jpg");
				 file.createNewFile();
				  ImageIO.write(bufferedImage, "jpg", file); } catch 
				 (IOException
				  e1) { e1.printStackTrace(); }*/
				

				icolabel.setIcon(new ImageIcon(bufferedImage)); //在窗口中显示图像
				retStr=ZxingHandler.getQrcodeFromPic(bufferedImage);
				if (retStr!=null && !"".equals(retStr)){
					getcontent = true;
					jTextArea1.setText(retStr);
					System.out.println(retStr);
					break;
				}
				try {
					Thread.sleep(10);// 10毫秒刷新一次图像
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				grabber.stop();
				grabber.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
			jf.dispose();
		}
	}
}


