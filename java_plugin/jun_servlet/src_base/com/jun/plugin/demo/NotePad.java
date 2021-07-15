package com.jun.plugin.demo;
//  NotePad.java 
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

public class NotePad extends JFrame implements ActionListener {
	JTextArea textarea = new JTextArea();
	JMenuBar menubar = new JMenuBar();
	JMenu filemenu = new JMenu("File");
	JMenuItem newitem = new JMenuItem("New");
	JMenuItem openitem = new JMenuItem("Open");
	JMenuItem saveitem = new JMenuItem("Save");
	JMenuItem saveasitem = new JMenuItem("Save As");
	JMenuItem exititem = new JMenuItem("Exit");
	JMenu editmenu = new JMenu("Edit");
	JMenuItem selectitem = new JMenuItem("Select All");
	JMenuItem copyitem = new JMenuItem("Copy");
	JMenuItem cutitem = new JMenuItem("Cut");
	JMenuItem pasteitem = new JMenuItem("Paste");
	JMenu aboutmenu = new JMenu("About");
	JMenuItem info = new JMenuItem("About NotePad");
	String fileName = "NoName";  //����Ĭ�ϵ��ļ���
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Clipboard clipboard = toolkit.getSystemClipboard();  //��������
	private FileDialog openFileDialog = new FileDialog(this, "Open File",
			FileDialog.LOAD);
	private FileDialog saveAsFileDialog = new FileDialog(this, "Save File As",
			FileDialog.SAVE);

	public NotePad() {
		setTitle("NotePad");
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setBackground(Color.white);
		setSize(400, 300);
		filemenu.add(newitem);
		filemenu.add(openitem);
		filemenu.addSeparator();
		filemenu.add(saveitem);
		filemenu.add(saveasitem);
		filemenu.addSeparator();
		filemenu.add(exititem);
		editmenu.add(selectitem);
		editmenu.add(copyitem);
		editmenu.addSeparator();
		editmenu.add(cutitem);
		editmenu.add(pasteitem);
		menubar.add(filemenu);
		menubar.add(editmenu);
		menubar.add(aboutmenu);
		aboutmenu.add(info);
		setJMenuBar(menubar);
		getContentPane().add(textarea);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		newitem.addActionListener(this);
		openitem.addActionListener(this);
		saveitem.addActionListener(this);
		saveasitem.addActionListener(this);
		exititem.addActionListener(this);
		selectitem.addActionListener(this);
		copyitem.addActionListener(this);
		cutitem.addActionListener(this);
		pasteitem.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		Object eventSource = e.getSource();//�����¼�Դ�жϱ������Ĳ˵���
		if (eventSource == newitem) {
			textarea.setText("");
		} else if (eventSource == openitem) {
			openFileDialog.show();
			fileName = openFileDialog.getDirectory() + openFileDialog.getFile();
			if (fileName != null)
				readFile(fileName);
		} else if (eventSource == saveitem) {
			if (fileName != null)
				writeFile(fileName);
		} else if (eventSource == saveasitem) {
			saveAsFileDialog.show();
			fileName = saveAsFileDialog.getDirectory()
					+ saveAsFileDialog.getFile();
			if (fileName != null)
				writeFile(fileName);
		} else if (eventSource == selectitem) {
			textarea.selectAll();
		} else if (eventSource == cutitem) {
			String text = textarea.getSelectedText();
			StringSelection selection = new StringSelection(text);
			clipboard.setContents(selection, null);
			textarea.replaceRange("", textarea.getSelectionStart(), textarea
					.getSelectionEnd());
		} else if (eventSource == pasteitem) {
			Transferable contents = clipboard.getContents(this);
			if (contents == null)
				return;
			String text;
			text = "";
			try {
				text = (String) contents
						.getTransferData(DataFlavor.stringFlavor);
			} catch (Exception exception) {
			}
			textarea.replaceRange(text, textarea.getSelectionStart(), textarea
					.getSelectionEnd());
		} else if (eventSource == exititem) {
			System.exit(0);
		}
	}

	//  ���ļ�
	public void readFile(String fileName) {
		try {
			File file = new File(fileName);
			FileReader readIn = new FileReader(file);
			int size = (int) file.length();
			int charsRead = 0;
			char[] content = new char[size];
			while (readIn.ready())
				charsRead += readIn.read(content, charsRead, size - charsRead);
			readIn.close();
			textarea.setText(new String(content, 0, charsRead));
		} catch (IOException e) {
			System.out.println("Error Opening file");
		}
	}

	// д�ļ�
	public void writeFile(String fileName) {
		try {
			File file = new File(fileName);
			FileWriter writeOut = new FileWriter(file);
			writeOut.write(textarea.getText());
			writeOut.close();
		} catch (IOException e) {
			System.out.println("Error writing file");
		}
	}

	public static void main(String[] args) {
		Frame frame = new NotePad();
		frame.show();
	}
}