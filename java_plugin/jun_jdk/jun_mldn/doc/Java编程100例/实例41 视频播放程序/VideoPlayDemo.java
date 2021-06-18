import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.media.*;

// 视频播放程序

public class VideoPlayDemo extends JFrame {

	private Player player; // 播放器对象
	private Component visualMedia; // 视频显示组件
	private Component mediaControl; // 视频播放控制组件
	private Container container; // 主容器
	private File mediaFile; //媒体文件
	private URL fileURL; //媒体文件URL地址

	public VideoPlayDemo() { // 构造函数
		super("视频播放程序"); //调用父类构造函数

		container = getContentPane(); //得到窗口容器
		JToolBar toobar = new JToolBar(); //实例化工具栏
		JButton openFile = new JButton("打开媒体文件"); //实例化按钮
		toobar.add(openFile); //增加按钮到工具栏
		JButton openURL = new JButton("打开网络地址");
		toobar.add(openURL);
		container.add(toobar, BorderLayout.NORTH); //设置工具栏

		openFile.addActionListener(new ActionListener() { //打开文件按钮事件处理
			public void actionPerformed(ActionEvent event) {
				JFileChooser fileChooser = new JFileChooser(); //实例化文件选择器
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//设置文件打开模式为仅打开文件				
				int result = fileChooser.showOpenDialog(VideoPlayDemo.this);//显示对话框				
				if (result == JFileChooser.APPROVE_OPTION) { //得到用户行为
					mediaFile = fileChooser.getSelectedFile(); //得到选择的文件
				}
				if (mediaFile != null) {
					try {
						fileURL = mediaFile.toURL(); //得到文件的URL地址
					} catch (MalformedURLException ex) {
						ex.printStackTrace(); //输出错误信息
						showMessage("打开错误"); //显示错误信息
					}
					startPlayer(fileURL.toString()); //开始播放打开的文件
				}
			}
		});

		openURL.addActionListener(new ActionListener() { //打开URL按钮事件处理
			public void actionPerformed(ActionEvent event) {
				String addressName =JOptionPane.showInputDialog(VideoPlayDemo.this, "输入URL地址");
				if (addressName != null)
					startPlayer(addressName); //开始播放打开的URL
			}
		});

		Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, Boolean.TRUE);

		setSize(300, 200); //设置窗口大小
		setVisible(true); //设置窗口为可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}
	//初始化播放器
	public void startPlayer(String mediaLocation) {
		if (player != null)
			//如果播放器非空则移去先前的播放器组件
			if (visualMedia != null)
				container.remove(visualMedia); //如果对象visualMedia非空则移去
		if (mediaControl != null) {
			container.remove(mediaControl); //如果对象mediaControl非空则移去
			player.close(); //关闭播放器
		}
		MediaLocator mediaLocator = new MediaLocator(mediaLocation); //媒体定位器
		if (mediaLocator == null) {
			showMessage("打开文件错误"); //显示错误信息
			return;
		}
		try {
			player = Manager.createPlayer(mediaLocator); //得到播放器实例
			player.addControllerListener(new PlayerEventHandler()); //增加播放控制器
			player.realize();
		} catch (Exception ex) {
			ex.printStackTrace();
			showMessage("打开错误"); //显示错误信息
		}

	}
	//取得媒体组件
	public void getMediaComponents() {
		visualMedia = player.getVisualComponent(); //取得视频显示组件

		//如果对象visualMedia非空则加入到窗口内容窗格
		if (visualMedia != null) {
			container.add(visualMedia, BorderLayout.CENTER);
			pack();
		}

		mediaControl = player.getControlPanelComponent(); //取得播放控制组件

		//如果对象visualMedia非空则加入到窗口内容窗格
		if (mediaControl != null)
			container.add(mediaControl, BorderLayout.SOUTH);

	}

	//播放器事件处理
	private class PlayerEventHandler extends ControllerAdapter {

		public void realizeComplete(RealizeCompleteEvent realizeDoneEvent) {
			player.prefetch(); //预取媒体数据
		}

		//完成预取媒体数据后，开始播放媒体
		public void prefetchComplete(PrefetchCompleteEvent prefetchDoneEvent) {
			getMediaComponents();
			validate();
			player.start(); //开始播放媒体
		}

		//如果媒体播放完毕，重新设置媒体时间并停止媒体播放器
		public void endOfMedia(EndOfMediaEvent mediaEndEvent) {
			player.setMediaTime(new Time(0)); //重新设置媒体时间
			player.stop(); // 停止媒体播放
		}
	}
	
	public void showMessage(String s) {
		JOptionPane.showMessageDialog(this, s);	//显示提示信息
	}

	public static void main(String args[]) {
		new VideoPlayDemo();
	}

}
