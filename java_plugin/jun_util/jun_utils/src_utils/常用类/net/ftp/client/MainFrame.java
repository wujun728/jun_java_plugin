package book.net.ftp.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import book.string.ChangeCharset;

import sun.net.ftp.FtpClient;

public class MainFrame extends JFrame {
	//连接服务器的按钮
	JButton connectButton = new JButton("连 接");
	// FTP服务器IP地址输入框
	JLabel IPLabel = new JLabel("IP：");
	JTextField serverIPTextField = new JTextField(16);
	// /用户名标签和输入框
	JLabel userNameLabel = new JLabel("用户名：");
	JTextField userNameTextField = new JTextField(16);
	// 密码标签和输入框
	JLabel passwordLabel = new JLabel("密码：");
	JTextField passwordTextField = new JTextField(16);
	// 当前目录标签和输入框
	JLabel currentDirLabel = new JLabel("当前目录：");
	JTextField currentDirTextField = new JTextField(30);
	// 转入上一级目录的按钮
	JButton upLevelButton = new JButton("上一级目录");
	// 上载和下载文件的按钮
	JButton downloadButton = new JButton("下载");
	JButton uploadButton = new JButton("上传");
	// 断开和退出的按钮
	JButton disconnectButton = new JButton("断开");
	JButton exitButton = new JButton("关闭");

	// 表格的数据，用于显示FTP服务器上的信息
	String[] columnname = { "文件名", "文件大小", "修改日期" };
	DefaultTableModel tableModel = new DefaultTableModel();
	JTable ftpFileInfosTable = new JTable();
	JScrollPane ftpFileScrollPane1 = new JScrollPane(ftpFileInfosTable);

	// 下载和上传的文件对话框
	FileDialog saveFileDialog = new FileDialog(this, "Download To ..",
			FileDialog.SAVE);
	FileDialog openFileDialog = new FileDialog(this, "Upload File ..",
			FileDialog.LOAD);

	FtpClient ftpClient = null;
	// IP地址、用户名、密码、FTP路径
	String ip = "";
	String username = "";
	String password = "";
	String path = "";

	// 当前FTP目录下的文件和目录信息
	Vector files = new Vector();
	Vector fileSizes = new Vector();
	Vector fileTypes = new Vector();
	Vector fileDates = new Vector();

	// 当前选择的表格中的行号和列号
	int row = 0;
	int column = 0;

	// 当前正在执行上下载任务的线程
	Vector performTaskThreads = new Vector();
	// 显示当前的任务列表
	java.awt.List taskList = new List();
	// 显示FTP客户端程序的控制台信息
	JTextArea consoleTextArea = new JTextArea();

	// 因为从FTP中取得编码是ISO-8859-1，为了支持中文，所以将其转换。
	// 这个类是在将字符串的章节中定义的，这里直接使用了。
	ChangeCharset changeCharset = new ChangeCharset();
	
	public MainFrame() {
		init();
		setLocation(100, 150);
		setTitle("FTP 客户端");
		pack();
		try {
			// 设置界面为系统默认外观
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void init(){
		// 初始化各个文本框
		serverIPTextField.setText("127.0.0.1");
		userNameTextField.setText("");
		passwordTextField.setText("");
		currentDirTextField.setText("");
		currentDirTextField.setEditable(false);

		// 将IP、用户名、密码、连接标签和输入框放在面板panel1中
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.add(IPLabel);
		panel1.add(serverIPTextField);
		panel1.add(userNameLabel);
		panel1.add(userNameTextField);
		panel1.add(passwordLabel);
		panel1.add(passwordTextField);
		panel1.add(connectButton);

		// 将当前目录、上一级目录的标签、输入框和按钮放在面板panel2中
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel2.add(currentDirLabel);
		panel2.add(currentDirTextField);
		panel2.add(upLevelButton);
		
		JPanel panelA = new JPanel();
		panelA.setLayout(new BorderLayout());
		panelA.add(panel1, BorderLayout.NORTH);
		panelA.add(panel2, BorderLayout.CENTER);

		ftpFileScrollPane1.setBorder(new TitledBorder(BorderFactory
				.createEtchedBorder(Color.white, new Color(134, 134, 134)),
				"FTP服务器上文件信息"));

		// 将下载、上传、断开、退出按钮放在panel3中
		JPanel panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		panel3.add(downloadButton);
		panel3.add(uploadButton);
		panel3.add(disconnectButton);
		panel3.add(exitButton);

		// 将任务列表放在有滚动条的面板中
		JScrollPane taskScrollPane = new JScrollPane(taskList);
		taskScrollPane.setBorder(new TitledBorder(BorderFactory
				.createEtchedBorder(Color.white, new Color(134, 134, 134)), "上下载队列"));
		
		// 将控制台信息的文本域放在滚动条面板中，并且设置为不可编辑
		consoleTextArea.setBackground(SystemColor.control);
		consoleTextArea.setEditable(false);
		consoleTextArea.setText("");
		consoleTextArea.setRows(5);
		JScrollPane consoleScrollPane = new JScrollPane(consoleTextArea);
		consoleScrollPane.setBorder(new TitledBorder(BorderFactory
				.createEtchedBorder(Color.white, new Color(134, 134, 134)), "上下载信息"));
		
		JPanel panelB = new JPanel();
		panelB.setLayout(new BorderLayout());
		panelB.add(panel3, BorderLayout.NORTH);
		panelB.add(taskScrollPane, BorderLayout.CENTER);
		panelB.add(consoleScrollPane, BorderLayout.SOUTH);
		
		// 将各个面板添加到主面板中
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelA, BorderLayout.NORTH);
		this.getContentPane().add(ftpFileScrollPane1, BorderLayout.CENTER);
		this.getContentPane().add(panelB, BorderLayout.SOUTH);
		
		// 为按钮添加事件处理器
		connectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				connectButton_actionPerformed(e);
			}
		});
		upLevelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				upLevelButton_actionPerformed(e);
			}
		});
		downloadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				downloadButton_actionPerformed(e);
			}
		});
		uploadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				uploadButton_actionPerformed(e);
			}
		});
		disconnectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				disconnectButton_actionPerformed(e);
			}
		});
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				exitButton_actionPerformed(e);
			}
		});
		ftpFileInfosTable.addMouseListener(new MainFrame_Table_mouseAdapter(
				this));

	}

	/**
	 * 处理窗口关闭事件
	 */
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			disconnection();
			System.exit(0);
		}
	}
	/**
	 * 处理连接按钮发生的事件
	 * @param e
	 */
	void connectButton_actionPerformed(ActionEvent e) {
		// 获得IP、用户名和密码
		ip = serverIPTextField.getText();
		username = userNameTextField.getText();
		if (username.equals("")){
			username = "anonymous";
		}
		password = passwordTextField.getText();
		if (password.equals("")){
			password = "anonymous";
		}
		if (ip.equals(""))
			return;

		try {
			// 连接服务器
			Connector conn = new Connector(this, ip, username, password);
			conn.start();
		} catch (Exception ee) {
		}
	}

	/**
	 * 转入上一级目录按钮事件处理
	 * @param e
	 */
	void upLevelButton_actionPerformed(ActionEvent e) {
		upDirectory();
	}

	/**
	 * 下载按钮事件处理
	 * @param e
	 */
	void downloadButton_actionPerformed(ActionEvent e) {
		try {
			// 获得被表格中被选择的文件名
			String str = (String) files.elementAt(row);
			// 如果被选择的是一个文件，则下载。
			String t = (String) fileTypes.elementAt(row);
			if (t.startsWith("-")){
				// 表明是个文件类型
				download(str, "");
			}
		} catch (Exception ee) {
			consoleTextArea.append("file download has problems!\n");
		}

	}
	/**
	 * 上传按钮事件处理
	 * @param e
	 */
	void uploadButton_actionPerformed(ActionEvent e) {
		upload();

	}
	/**
	 * 断开连接按钮事件处理
	 * @param e
	 */
	void disconnectButton_actionPerformed(ActionEvent e) {
		disconnection();
	}

	/**
	 * 退出FTP客户端按钮事件处理
	 * @param e
	 */
	void exitButton_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	/**
	 * 上传文件到FTP
	 */
	void upload() {
		try {
			// 显示上传文件的对话框
			openFileDialog.show();
			String directory = openFileDialog.getDirectory();
			if (directory == null || directory.equals("")){
				return;
			}
			// 获得被选择的文件名
			String file = openFileDialog.getFile();
			if (file == null || file.equals("")){
				return;
			}
			String filename = directory + file;
			// 新建一个上传文件的任务线程，并启动
			UploadFileThread up = new UploadFileThread(this, ip, username,
					password, path, file, filename);
			up.start();
			// 保存到任务线程组中
			performTaskThreads.addElement(up);

		} catch (Exception ee) {
			consoleTextArea.append("file upload has problems!\n ");
			return;
		}
	}

	/**
	 * 下载文件到本地
	 * @param filename
	 * @param directory
	 */
	void download(String filename, String directory) {
		try {
			// 获得存放文件的本地目录和文件名
			if (directory.equals("")) {
				saveFileDialog.setFile(filename);
				saveFileDialog.show();
				// 本地目录和文件名
				directory = saveFileDialog.getDirectory();
				String file = saveFileDialog.getFile();
				if (directory == null || directory.equals("")){
					return;
				}
				if (file == null || file.equals("")){
					file = filename;
				} else {
					// 先获得带下载的文件名的后缀
					int index = filename.lastIndexOf(".");
					String extn = filename.substring(index + 1);
					// 如果本地文件名和待下载文件名的后缀不一样，
					// 则将本地文件名后面再追加待下载文件名的后缀
					index = file.lastIndexOf(".");
					String extn1 = file.substring(index + 1);
					if (!extn.equals(extn1)){
						file = file + "." + extn;
					}
				}
				directory = directory + file;
			} else {
				directory += filename;
			}
			// 启动一个下载文件的线程，并启动
			DownloadFileThread down = new DownloadFileThread(this, ip,
					username, password, path, filename, directory);
			down.start();
			performTaskThreads.add(down);
		} catch (Exception ee) {
			consoleTextArea.append("file" + filename + "has problems!");
		}
	}
	/**
	 * 断开FTP客户端连接
	 */	
	public void disconnection() {
		try {
			// 清除所有的任务线程
			if (performTaskThreads.size() != 0) {
				if ((JOptionPane.showConfirmDialog(this, "还有任务没有执行完，确定退出？",
						"断开连接", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)) {
					return;
				}
				try {
					for (int i = 0; i < performTaskThreads.size(); i++) {
						Object thread = performTaskThreads.elementAt(i);
						if (thread instanceof DownloadFileThread){
							DownloadFileThread down = (DownloadFileThread)thread;
							down.toStop();
						} else if (thread instanceof UploadFileThread){
							UploadFileThread upload = (UploadFileThread)thread;
							upload.toStop();
						}

					}
				} catch (Exception ee) {
					System.out.println(ee.toString());
				}
				taskList.removeAll();
			}
			//清除保存的一些数据
			files.removeAllElements();
			fileTypes.removeAllElements();
			fileSizes.removeAllElements();
			fileDates.removeAllElements();

			serverIPTextField.setText("");
			userNameTextField.setText("");
			passwordTextField.setText("");
			currentDirTextField.setText("");
			consoleTextArea.append("server " + ip + " disconnected!\n");

			ip = "";
			username = "";
			password = "";
			path = "";
			
			// 清除表格
			String[][] disdata = null;
			String[] col = null;
			tableModel.setDataVector(disdata, col);
			ftpFileInfosTable.setModel(tableModel);

			// 关闭FTP客户端
			ftpClient.closeServer();
		} catch (Exception ee) {
			consoleTextArea.append("server " + ip
					+ " dicconect has problems!\n");
		}
	}

	// 设置表格数据
	void setTableData() {
		try {
			// 首先清除文件列表信息
			if (files.size() != 0) {
				files.removeAllElements();
				fileTypes.removeAllElements();
				fileSizes.removeAllElements();
				fileDates.removeAllElements();
			}
			String list = "";
			// 切换到当前目录
			ftpClient.cd("/");
			if (!path.equals("")){
				ftpClient.cd(path);
			}
			// List当前目录下的数据、包括目录和文件名
			InputStream is = ftpClient.list();
			int c;
			while ((c = is.read()) != -1) {
				String s = (new Character((char) c)).toString();
				list += s;
			}
			is.close();
			if (!list.equals("")) {
				// 解析List命令得到的数据，得到当前目录下的目录、文件名、大小、类型
				StringTokenizer st = new StringTokenizer(list, "\n");
				int count = st.countTokens();
				for (int i = 0; i < count; i++) {
					String s = st.nextToken();
					StringTokenizer sst = new StringTokenizer(s, " ");
					c = sst.countTokens();

					String datastr = "";
					String filestr = "";

					for (int j = 0; j < c; j++) {
						String ss = sst.nextToken();
						if (j == 0){
							fileTypes.addElement(
									changeCharset.changeCharset(
											ss,ChangeCharset.ISO_8859_1, ChangeCharset.GBK));
						} else if (j == 4) {
							System.out.println(ss);
							fileSizes.addElement(ss);
						} else if (j == 5) {
							datastr = ss;
						} else if (j == 6) {
							datastr += " " + ss;
						} else if (j == 7) {
							datastr += " " + ss;
							fileDates.addElement(
									changeCharset.changeCharset(
											datastr,ChangeCharset.ISO_8859_1, ChangeCharset.GBK));
						} else if (j == 8) {
							if (c == 9){
								files.addElement(
										changeCharset.changeCharset(
												ss,ChangeCharset.ISO_8859_1, ChangeCharset.GBK));
							} else {
								filestr = ss;
							}
						} else if (j > 8) {
							filestr += " " + ss;
							if (j == (c - 1)){
								files.addElement(
										changeCharset.changeCharset(
												filestr,ChangeCharset.ISO_8859_1, ChangeCharset.GBK));
							}
						}
					}
				}
				int cc = files.size();
				String[][] mydata = new String[cc][3];

				for (int i = 0; i < cc; i++) {
					mydata[i][0] = (String) files.elementAt(i);
					mydata[i][1] = (String) fileSizes.elementAt(i);
					mydata[i][2] = (String) fileDates.elementAt(i);
				}
				// 更新表格
				tableModel.setDataVector(mydata, columnname);
				ftpFileInfosTable.setModel(tableModel);
			}
		} catch (Exception ee) {
			consoleTextArea.append("Read directory has problem !\n");
			return;
		}
	}

	/**
	 * 转入上一级目录
	 */
	void upDirectory() {
		// 如果已经到了最顶级目录，则不处理。
		if (path.equals("/")){
			return;
		}
		try {
			// 将目录上以及处理。
			StringTokenizer st = new StringTokenizer(path, "/");
			int count = st.countTokens();

			String s = "";
			for (int i = 0; i < count - 1; i++){
				s += st.nextToken() + "/";
			}
			if (s.length() != 0){
				path = s.substring(0, s.length() - 1);
			} else {
				path = "";
			}
			currentDirTextField.setText(path);
			setTableData();
		} catch (Exception ee) {
			consoleTextArea.append("go to parent directory has problems!");
		}
	}

	/**
	 * 选择表格中的数据是的事件处理
	 * @param e
	 */
	void table_mousePressed(MouseEvent e) {
		// 只处理鼠标左键的事件
		if (SwingUtilities.isLeftMouseButton(e)) {
			// 获得鼠标的位置
			Point point = e.getPoint();
			// 得到选择的行和列的值
			row = ftpFileInfosTable.rowAtPoint(point);
			column = ftpFileInfosTable.columnAtPoint(point);

			try {
				// 判断鼠标点击的次数
				int count = e.getClickCount();
				// 如果选择的是第0列，则表示选择的是文件或者目录名
				if (column == 0) {
					// 获得该位置上的文件名
					String str = (String) files.elementAt(row);
					// 双击
					if (count == 2) {
						// 如果选择的文件名是"."，则为转入根目录命令
						if (str.equals(".")) {
							path = "/";
							setTableData();
							return;
						} else if (str.equals("..")) {
							// 如果选择的文件名是".."，则转入上以及目录
							upDirectory();
							return;
						}
						// 如果选择是目录，则进入该目录
						String s = (String) fileTypes.elementAt(row);
						if (s.startsWith("d")) {
							if (path.equals("")){
								path = "/" + str;
							} else {
								path += "/" + str;
							}
							currentDirTextField.setText(path);
							setTableData();
						} else if (s.startsWith("-")) {
							//　如果选择的是文件，则下载
							download(str, "");
						}
					}
				}
			} catch (Exception ee) {
				consoleTextArea.append("download or read file has problems!\n");
			}

		}
	}
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}
}

class MainFrame_Table_mouseAdapter extends java.awt.event.MouseAdapter {
	MainFrame adaptee;

	MainFrame_Table_mouseAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void mousePressed(MouseEvent e) {
		adaptee.table_mousePressed(e);
	}

}
