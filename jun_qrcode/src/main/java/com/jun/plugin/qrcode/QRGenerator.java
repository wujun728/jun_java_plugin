package com.jun.plugin.qrcode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.swetake.util.Qrcode;

public class QRGenerator  extends JPanel {

	private static final long serialVersionUID = 1L;
//	private BufferedImage bi;
	
	public void setBufferedImage(BufferedImage bi){
		this.bi = bi;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(bi != null)
			g.drawImage(bi, 0, 0, bi.getWidth(), bi.getHeight(), this);
	}
	
	
	private static JFrame mainf;
	private static JPanel pannel;
	private static JLabel inputLabel;
	private static JTextArea input;
	private static ImagePannel output;
	private static JPanel bPanel;
	private static JPanel iPanel;
	private static JButton generat;
	private static GridBagLayout gbl;
	private static GridBagConstraints gbc;
	private static GridBagLayout fgbl;
	private static GridBagConstraints fgbc;
	private static GridBagLayout igbl;
	private static GridBagConstraints igbc;
	private static Graphics2D gs;
	private static BufferedImage bi;
	private static JMenuBar menuBar;
	private static JMenu file;
	private static JMenuItem saveAsItem;
	private static JMenuItem exitItem;
	private static JMenu edit;
	private static JMenuItem selectAllItem;
	private static JMenuItem copyItem;
	private static JMenuItem pasteItem;
	private static JMenuItem deleteItem;
	private static JMenu help;
	private static JMenuItem aboutItem;
	private static int size = 0;
	private static JLabel sizeLabel;
	private static JTextField inSize;

	public static void main(String[] args) {
		initUI();
	}

	public static void initUI(){
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		fgbl = new GridBagLayout();
		fgbc = new GridBagConstraints();
		igbl = new GridBagLayout();
		igbc = new GridBagConstraints();
		mainf = new JFrame("二维码生成器");
		pannel = new JPanel(gbl);
		input = new JTextArea();
		inputLabel = new JLabel("<html><font style=\"color:green;\">输入要编码的内容：</font></html>");
		iPanel = new JPanel();
		output = new ImagePannel();
		bPanel = new JPanel();
		generat = new JButton();
		menuBar = new JMenuBar();
		file = new JMenu("文件");
		saveAsItem = new JMenuItem("另存为...",1);
		exitItem = new JMenuItem("退出");
		edit = new JMenu("编辑");
		selectAllItem = new JMenuItem("全选",1);
		copyItem = new JMenuItem("复制",1);
		pasteItem = new JMenuItem("粘贴",1);
		deleteItem = new JMenuItem("删除",1);
		help = new JMenu("帮助");
		aboutItem = new JMenuItem("关于我们",1);
		sizeLabel = new JLabel("<html><font style=\"color:red;\">二维码大小：</font></html>");
		inSize = new JTextField();
		
		generat.setText("生成二维码 >>");
		generat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!"".equals(input.getText().trim()) && !(input.getText() == null)){
				bi = new BufferedImage(275, 275, BufferedImage.TYPE_INT_RGB);
				for(int i = 0 ; i < 275 ; i++)
					for(int j = 0 ; j < 275 ; j++)
						bi.setRGB(j, i, Color.WHITE.getRGB());
				gs = bi.createGraphics();
				gs.setColor(Color.BLACK);
				Qrcode qrcode = new Qrcode();
				qrcode.setQrcodeEncodeMode('B');//N A ...
				qrcode.setQrcodeErrorCorrect('M');//L M Q H
				qrcode.setQrcodeVersion(7);
				try {
					if((input.getText().trim().getBytes("UTF-8")).length > 0 && (input.getText().trim().getBytes("UTF-8")).length < 123){
					boolean[][] rest = qrcode.calQrcode(input.getText().trim().getBytes("UTF-8")); 
					for(int i = 0 ; i< rest.length ; i++){
						for(int j = 0 ; j < rest.length ; j++){
							if(rest[j][i])
								gs.fillRect(j * 6, i * 6, 6, 6);
						}
					}
					output.setBufferedImage(bi);
					output.repaint();
					File f = new File(System.getProperty("user.dir") + "\\二维码文件.png");
					try {
						ImageIO.write(bi, "png", f);
						JOptionPane.showMessageDialog(mainf, new String("恭喜您，2维码生成成功咯！目标文件为：" + System.getProperty("user.dir") + "\\二维码文件.png"), "结果提示", JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(mainf, new String("很遗憾，2维码生成失败了唉！"), "结果提示", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
					}else{
						JOptionPane.showMessageDialog(mainf, new String("编码内容长度不能超过123个字符哦，亲！"), "输入提示", JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else
				JOptionPane.showMessageDialog(mainf, new String("编码内容不能为空哦，亲！"), "输入提示", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		fgbc.gridx = 0;
		fgbc.gridy = 0;
		fgbc.weightx = 550;
		fgbc.weighty = 3;
		fgbc.gridwidth = 1;
		fgbc.gridheight = GridBagConstraints.RELATIVE;
		fgbc.fill = GridBagConstraints.BOTH;
		saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S , InputEvent.CTRL_DOWN_MASK));
		file.add(saveAsItem);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE , 0));
		file.add(exitItem);
		menuBar.add(file);
		selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A , InputEvent.CTRL_DOWN_MASK));
		edit.add(selectAllItem);
		copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C , InputEvent.CTRL_DOWN_MASK));
		edit.add(copyItem);
		pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V , InputEvent.CTRL_DOWN_MASK));
		edit.add(pasteItem);
		deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE , 0));
		edit.add(deleteItem);
		menuBar.add(edit);
		help.add(aboutItem);
		menuBar.add(help);
		mainf.setLayout(fgbl);
		fgbl.setConstraints(menuBar, fgbc);
		mainf.add(menuBar);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		input.setLineWrap(true);
		input.setToolTipText("在此输入内容 :)");
		JPopupMenu pm = new JPopupMenu();
		pm.add(new JMenuItem("粘贴"));
		pm.add(new JMenuItem("复制"));
		pm.add(new JMenuItem("删除"));
		input.setComponentPopupMenu(pm);
		igbc.gridx = 0;
		igbc.gridy = 0;
		igbc.weightx = 1;
		igbc.weighty = 0.5;
		igbc.gridwidth = GridBagConstraints.REMAINDER;
		igbc.gridheight = 1;
		igbc.fill = GridBagConstraints.HORIZONTAL;
		iPanel.setLayout(igbl);
		inputLabel.setLabelFor(input);
		igbl.setConstraints(inputLabel, igbc);
		iPanel.add(inputLabel);
		igbc.gridy = 1;
		igbc.weighty = 10;
		igbc.gridheight = GridBagConstraints.RELATIVE;
		igbc.fill = GridBagConstraints.BOTH;
		igbl.setConstraints(input, igbc);
		iPanel.add(input);
		igbc.gridy = 2;
		igbc.insets = new Insets(10, 0, 0, 0);
		igbc.gridheight = GridBagConstraints.REMAINDER;
		iPanel.add(sizeLabel);
		inSize.setColumns(6);
		inSize.setComponentPopupMenu(pm);
		iPanel.add(inSize);
		gbl.setConstraints(iPanel, gbc);
		pannel.add(iPanel);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;
		generat.setToolTipText("点击我生成二维码 :D");
		bPanel.add(generat);
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbl.setConstraints(bPanel, gbc);
		pannel.add(bPanel);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 265;//分配额外的水平空间
		gbc.weighty = 1;
		gbc.ipady = 100;
		output.setBackground(Color.darkGray);
		output.setToolTipText("二维码生成区域");
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(output, gbc);
		pannel.add(output);
		
		fgbc.gridx = 0;
		fgbc.gridy = 1;
		fgbc.weightx = 550;
		fgbc.weighty = 255;
		fgbc.gridwidth = 1;
		fgbc.gridheight = GridBagConstraints.REMAINDER;
		fgbc.fill = GridBagConstraints.BOTH;
		fgbl.setConstraints(pannel, fgbc);
		mainf.setResizable(false);
		mainf.setBounds(350, 300, 550, 330);
		mainf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainf.add(pannel);
		mainf.setVisible(true);
	}
	
}
