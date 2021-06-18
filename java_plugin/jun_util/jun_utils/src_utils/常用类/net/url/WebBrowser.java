package book.net.url;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.filechooser.FileFilter;

/**
 * 实现一个简单的Web浏览器，支持HTML和HTM页面的显示。使用了JEditorPane组件
 **/
public class WebBrowser extends JFrame implements HyperlinkListener,
		PropertyChangeListener {
    
    /**下面是使用的Swing组件**/
	
	// 显示HTML的面板
    JEditorPane textPane; 
    // 最底下的状态栏
    JLabel messageLine; 
    // 网址URL输入栏
    JTextField urlField;
    // 文件选择器，打开本地文件时用
    JFileChooser fileChooser;
    
    // 后退和前进 按钮
    JButton backButton;
    JButton forwardButton;

    // 保存历史记录的列表
    java.util.List history = new ArrayList(); 
    // 当前页面的在历史记录列表中位置
    int currentHistoryPage = -1;  
    // 当历史记录超过MAX_HISTORY时，清除旧的历史
    public static final int MAX_HISTORY = 50;

    // 当前已经打开的浏览器窗口数
    static int numBrowserWindows = 0;
    // 标识当所有浏览器窗口都被关闭时，是否退出应用程序
    static boolean exitWhenLastWindowClosed = false;

    // 默认的主页
    String home = "http://www.google.com";

    /**
     * 构造函数
     */
    public WebBrowser() {
        super("WebBrowser"); 

        // 新建显示HTML的面板，并设置它不可编辑
        textPane = new JEditorPane(); 
        textPane.setEditable(false); 

        // 注册事件处理器，用于超连接事件。
        textPane.addHyperlinkListener(this);
        // 注册事件处理器，用于处理属性改变事件。当页面加载结束时，触发该事件
        textPane.addPropertyChangeListener(this);

        // 将HTML显示面板放入主窗口，居中显示
        this.getContentPane().add(new JScrollPane(textPane),
                                  BorderLayout.CENTER);

        // 创建状态栏标签，并放在主窗口底部
        messageLine = new JLabel(" ");
        this.getContentPane().add(messageLine, BorderLayout.SOUTH);

        // 初始化菜单和工具栏
        this.initMenu();
        this.initToolbar();
        
        // 将当前打开窗口数增加1
        WebBrowser.numBrowserWindows++;
        
        // 当关闭窗口时，调用close方法处理
        this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
    }
    
    /**
     * 初始化菜单栏
     */
    private void initMenu(){
    	
    	// 文件菜单，下面有四个菜单项：新建、打开、关闭窗口、退出
    	JMenu fileMenu = new JMenu("文件");
    	fileMenu.setMnemonic('F');
    	JMenuItem newMenuItem = new JMenuItem("新建");
    	newMenuItem.setMnemonic('N');
    	// 当“新建”时打开一个浏览器窗口
    	newMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	newBrowser();
            }
        });
    	
    	JMenuItem openMenuItem = new JMenuItem("打开");
    	openMenuItem.setMnemonic('O');
    	// 当“打开”是打开文件选择器，选择待打开的文件
    	openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	openLocalPage();
            }
        });
    	
    	JMenuItem closeMenuItem = new JMenuItem("关闭窗口");
    	closeMenuItem.setMnemonic('C');
    	// 当“关闭窗口”时关闭当前窗口
    	closeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	close();
            }
        });
    	
    	JMenuItem exitMenuItem = new JMenuItem("退出");
    	exitMenuItem.setMnemonic('E');
    	// 当“退出”时退出应用程序
    	exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	exit();
            }
        });
    	
    	fileMenu.add(newMenuItem);
    	fileMenu.add(openMenuItem);
    	fileMenu.add(closeMenuItem);
    	fileMenu.add(exitMenuItem);
    	
    	//帮助菜单，就一个菜单项：关于
    	JMenu helpMenu = new JMenu("帮助");
    	fileMenu.setMnemonic('H');
    	JMenuItem aboutMenuItem = new JMenuItem("关于");
    	aboutMenuItem.setMnemonic('A');
    	helpMenu.add(aboutMenuItem);
    	
    	JMenuBar menuBar = new JMenuBar();
    	menuBar.add(fileMenu);
    	menuBar.add(helpMenu);
    	
    	// 设置菜单栏到主窗口
    	this.setJMenuBar(menuBar);
    }
    
    /**
     * 初始化工具栏
     */
    private void initToolbar(){
    	// 后退按钮，退到前一页面。初始情况下该按钮不可用
        backButton = new JButton("后退");
        backButton.setEnabled(false);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	back();
            }
        });
        
        // 前进按钮，进到前一页面。初始情况下该按钮不可用
        forwardButton = new JButton("前进");
        forwardButton.setEnabled(false);
        forwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	forward();
            }
        });
        
        // 前进按钮，进到前一页面。初始情况下该按钮不可用
        JButton refreshButton = new JButton("刷新");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	reload();
            }
        });
        
        // 主页按钮，打开主页
        JButton homeButton = new JButton("主页");
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	home();
            }
        });
        
        JToolBar toolbar = new JToolBar();
        toolbar.add(backButton);
        toolbar.add(forwardButton);
        toolbar.add(refreshButton);
        toolbar.add(homeButton);

        // 输入网址的文本框
        urlField = new JTextField();
        // 当用户输入网址、按下回车键时，触发该事件
        urlField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    displayPage(urlField.getText());
                }
            });

        // 地址标签
        toolbar.add(new JLabel("         地址："));
        toolbar.add(urlField);

        // 将工具栏放在主窗口的北部
        this.getContentPane().add(toolbar, BorderLayout.NORTH);
    }

    /**
     * 设置浏览器是否在所有窗口都关闭时退出
     * @param b
     */
    public static void setExitWhenLastWindowClosed(boolean b) {
        exitWhenLastWindowClosed = b;
    }

    /**
	 * 设置主页
	 * @param home	新主页
	 */
	public void setHome(String home) {
		this.home = home;
	}
	/**
	 * 获取主页
	 */
    public String getHome() {
		return home;
	}

    /**
     * 访问网址URL
     */
    private boolean visit(URL url) {
        try {
            String href = url.toString();
            // 启动动画，当网页被加载完成时，触发propertyChanged事件，动画停止。
            startAnimation("加载 " + href + "...");
            
            // 设置显示HTML面板的page属性为待访问的URL，
            // 在这个方法里，将打开URL，将URL的流显示在textPane中。
            // 当完全打开后，该方法才结束。
            textPane.setPage(url); 
            
            // 页面打开后，将浏览器窗口的标题设为URL
            this.setTitle(href);  
            // 网址输入框的内容也设置为URL
            urlField.setText(href); 
            return true;
        } catch (IOException ex) { 
        	// 停止动画
            stopAnimation();
            // 状态栏中显示错误信息
            messageLine.setText("不能打开页面：" + ex.getMessage());
            return false;
        }
    }

    /**
     * 浏览器打开URL指定的页面，如果成功，将URL放入历史列表中
     */
    public void displayPage(URL url) {
    	// 尝试访问页面
        if (visit(url)) { 
        	// 如果成功，则将URL放入历史列表中。
            history.add(url); 
            int numentries = history.size();
            if (numentries > MAX_HISTORY+10) { 
                history = history.subList(numentries-MAX_HISTORY, numentries);
                numentries = MAX_HISTORY;
            }
            // 将当前页面下标置为numentries-1
            currentHistoryPage = numentries - 1;
            // 如果当前页面不是第一个页面，则可以后退，允许点击后退按钮。
            if (currentHistoryPage > 0){
            	backButton.setEnabled(true);
            }
        }
    }

    /**
     * 浏览器打开字符串指定的页面
     * @param href	网址
     */
    public void displayPage(String href) {
        try {
        	// 默认为HTTP协议
        	if (!href.startsWith("http://")){
        		href = "http://" + href;
        	}
            displayPage(new URL(href));
        }
        catch (MalformedURLException ex) {
            messageLine.setText("错误的网址: " + href);
        }
    }

    /**
     * 打开本地文件
     */
    public void openLocalPage() {
        // 使用“懒创建”模式，当需要时，才创建文件选择器。
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
            // 使用文件过滤器限制只能够HTML和HTM文件
            FileFilter filter = new FileFilter() {
                    public boolean accept(File f) {
                        String fn = f.getName();
                        if (fn.endsWith(".html") || fn.endsWith(".htm")){
                            return true;
                        }else {
                        	return false;
                        }
                    }
                    public String getDescription() { 
                    	return "HTML Files"; 
                    }
                };
            fileChooser.setFileFilter(filter);
            // 只允许选择HTML和HTM文件
            fileChooser.addChoosableFileFilter(filter);
        }

        // 打开文件选择器
        int result = fileChooser.showOpenDialog(this);
        // 如果确定打开文件，则在当前窗口中打开选择的文件
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile( );
            try {
				displayPage(selectedFile.toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
        }
    }
    /**
     * 后退，回到前一页
     */
    public void back() {
        if (currentHistoryPage > 0){
        	// 访问前一页
            visit((URL)history.get(--currentHistoryPage));
        }
        // 如果当前页面下标大于0，允许后退
        backButton.setEnabled((currentHistoryPage > 0));
        // 如果当前页面下标不是最后，允许前进
        forwardButton.setEnabled((currentHistoryPage < history.size()-1));
    }
    /**
     * 前进，回到后一页
     */
    public void forward() {
        if (currentHistoryPage < history.size( )-1){
            visit((URL)history.get(++currentHistoryPage));
        }
        // 如果当前页面下标大于0，允许后退
        backButton.setEnabled((currentHistoryPage > 0));
        // 如果当前页面下标不是最后，允许前进
        forwardButton.setEnabled((currentHistoryPage < history.size()-1));
    }
    /**
     * 重新加载当前页面
     */
    public void reload() {
        if (currentHistoryPage != -1) {
            // 先显示为空白页
            textPane.setDocument(new javax.swing.text.html.HTMLDocument());
            // 再访问当前页
            visit((URL)history.get(currentHistoryPage));
        }
    }
    /**
     * 显示主页 
     */
    public void home() {
    	displayPage(getHome()); 
    }
    /**
     * 打开新的浏览器窗口 
     */
    public void newBrowser() {
        WebBrowser b = new WebBrowser();
        // 新窗口大小和当前窗口一样大
        b.setSize(this.getWidth(), this.getHeight());
        b.setVisible(true);
    }
    /**
     * 关闭当前窗口，如果所有窗口都关闭，则根据exitWhenLastWindowClosed属性，
     * 判断是否退出应用程序
     */
    public void close() {
    	// 先隐藏当前窗口，销毁窗口中的组件。
        this.setVisible(false);
        this.dispose();
        // 将当前打开窗口数减1。
        // 如果所有窗口都已关闭，而且exitWhenLastWindowClosed为真，则退出
        // 这里采用了synchronized关键字，保证线程安全
        synchronized(WebBrowser.class) {    
            WebBrowser.numBrowserWindows--; 
            if ((numBrowserWindows==0) && exitWhenLastWindowClosed){
                System.exit(0);
            }
        }
    }
    /**
     * 退出应用程序
     */
    public void exit() {
    	// 弹出对话框，请求确认，如果确认退出，则退出应用程序
		if ((JOptionPane.showConfirmDialog(this, "你确定退出Web浏览器？", "退出",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)){
			System.exit(0);
		}
	}
    /**
     * 实现HyperlinkListener接口。处理超连接事件
     */
    public void hyperlinkUpdate(HyperlinkEvent e) {
    	// 获取事件类型
        HyperlinkEvent.EventType type = e.getEventType();
        // 如果是点击了一个超连接，则显示被点击的连接
        if (type == HyperlinkEvent.EventType.ACTIVATED) {
            displayPage(e.getURL());
        }
        // 如果是鼠标移动到超连接上，则在状态栏中显示超连接的网址
        else if (type == HyperlinkEvent.EventType.ENTERED) {
            messageLine.setText(e.getURL().toString());  
        }
        // 如果是鼠标离开了超连接，则状态栏显示为空
        else if (type == HyperlinkEvent.EventType.EXITED) { 
            messageLine.setText(" ");
        }
    }

    /**
     * 实现PropertyChangeListener接口。处理属性改变事件。
     * 显示HTML面板textPane的属性改变时，由该方法处理。
     * 当textPane调用完setPage方法时，page属性便改变了。
     */
    public void propertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName().equals("page")) {
        	// 页面加载完毕时，textPane的page属性发生改变，此时停止动画。
            stopAnimation();
        }
    }

    // 动画消息，显示在最底下状态栏标签上，用于反馈浏览器的状态
    String animationMessage;
    // 动画当前的帧的索引
    int animationFrame = 0;
    // 动画所用到的帧，是一些字符。
    String[] animationFrames = new String[] {
        "-", "\\", "|", "/", "-", "\\", "|", "/", 
        ",", ".", "o", "0", "O", "#", "*", "+"
    };

    /**
     * 新建一个Swing的定时器，每个125毫秒更新一次状态栏标签的文本
     */
    javax.swing.Timer animator =
        new javax.swing.Timer(125, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	animate(); 
                }
            });

    /**
     * 显示动画的当前帧到状态栏标签上，并将帧索引后移
     */
    private void animate() {
        String frame = animationFrames[animationFrame++];
        messageLine.setText(animationMessage + " " + frame);
        animationFrame = animationFrame % animationFrames.length;
    }

    /**
     * 启动动画
     */
    private void startAnimation(String msg) {
        animationMessage = msg;
        animationFrame = 0; 
        // 启动定时器
        animator.start();
    }

    /**
     * 停止动画
     */
    private void stopAnimation() {  
    	// 停止定时器
        animator.stop();
        messageLine.setText(" ");
    }
    
    public static void main(String[] args) throws IOException {
        // 设置浏览器，当所有浏览器窗口都被关闭时，退出应用程序
		WebBrowser.setExitWhenLastWindowClosed(true);
		// 创建一个浏览器窗口
		WebBrowser browser = new WebBrowser(); 
		// 设置浏览器窗口的默认大小
		browser.setSize(800, 600);
		// 显示窗口
        browser.setVisible(true); 

        // 打开主页
        browser.displayPage(browser.getHome());
    }
}