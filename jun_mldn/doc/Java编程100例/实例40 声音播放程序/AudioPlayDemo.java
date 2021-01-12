import java.applet.AudioClip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

// 声音播放程序

public class AudioPlayDemo	extends JFrame	implements ActionListener, ItemListener {
	boolean looping = false; //是否循环播放
	String[] choics = { "chimes.wav", "start.wav" }; //声音文件名数组
	URL file1 = getClass().getResource(choics[0]); //声音文件1
	URL file2 = getClass().getResource(choics[1]); //声音文件2
	AudioClip sound1 = java.applet.Applet.newAudioClip(file1); //声音剪辑对象1
	AudioClip sound2 = java.applet.Applet.newAudioClip(file2); //声音剪辑对象2
	AudioClip chosenClip = sound1; //选择的声音剪辑对象

	JComboBox jcbFiles = new JComboBox(choics); //文件选择组合框
	JButton playButton = new JButton("播放"); //播放按钮
	JButton loopButton = new JButton("循环播放"); //循环播放按钮
	JButton stopButton = new JButton("停止"); //停止播放按钮
	JLabel status = new JLabel("选择播放文件"); //状态栏标签
	JPanel controlPanel = new JPanel(); //控制面板用于包容按钮
	Container container = getContentPane(); //获得窗口内容窗格

	public AudioPlayDemo() { //构造器
		super("声音播放程序"); //调用父类构造器设置窗口标题栏

		jcbFiles.setSelectedIndex(0); //设置组合框选择项
		jcbFiles.addItemListener(this); //为播放按钮添加项目监听器
		//为播放按钮、循环播放按钮、停止播放按钮添加动作监听器
		playButton.addActionListener(this);
		loopButton.addActionListener(this);
		stopButton.addActionListener(this);
		stopButton.setEnabled(false); //设置停止播放按钮不可用
		//把播放按钮、循环播放按钮、停止播放按钮加入控制面板
		controlPanel.add(playButton);
		controlPanel.add(loopButton);
		controlPanel.add(stopButton);
		//把文件选择组合框、控制面板、状态栏标签加入到窗口内容窗格
		container.add(jcbFiles, BorderLayout.NORTH);
		container.add(controlPanel, BorderLayout.CENTER);
		container.add(status, BorderLayout.SOUTH);

		setSize(300, 130); //设置窗口大小
		setVisible(true); //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}
	//文件选择组合框事件处理
	public void itemStateChanged(ItemEvent e) {
		if (jcbFiles.getSelectedIndex() == 0) {
			chosenClip = sound1;
		} else {
			chosenClip = sound2;
		}
	}
	//按钮事件处理
	public void actionPerformed(ActionEvent event) {
		if (chosenClip == null) {
			status.setText("声音未载入");
			return; //如果AudioClip对象为空，则直接返回
		}
		Object source = event.getSource(); //获取用户洗涤激活的按钮
		//播放按钮事件处理
		if (source == playButton) {
			stopButton.setEnabled(true); //设置停止播放按钮可用
			loopButton.setEnabled(true); //设置循环播放按钮可用
			chosenClip.play(); //播放选择的声音剪辑对象一次
			status.setText("正在播放"); //设置状态栏信息
		}

		//循环播放按钮事件处理
		if (source == loopButton) {
			looping = true;
			chosenClip.loop(); //循环播放选择的声音剪辑对象
			loopButton.setEnabled(false); //设置循环播放按钮不可用
			stopButton.setEnabled(true); //设置停止播放按钮可用
			status.setText("正在循环播放"); //设置状态栏信息
		}
		//停止播放按钮事件处理
		if (source == stopButton) {
			if (looping) {
				looping = false;
				chosenClip.stop(); //停止循环播放选择的声音剪辑对象
				loopButton.setEnabled(true); //设置循环播放按钮可用
			} else {
				chosenClip.stop(); //停止播放选择的声音剪辑对象
			}
			stopButton.setEnabled(false); //设置循环播放按钮可用
			status.setText("停止播放"); //设置状态栏信息
		}
	}

	public static void main(String s[]) {
		new AudioPlayDemo(); //创建AudioPlayDemo对象
	}
}
