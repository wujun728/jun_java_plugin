package com.fiwan.qrgenerator;

import java.awt.Dialog.ModalityType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;




public class CreateQRcodePicByFile {
	static Settings setting=Settings.getInstance();  
	public static void create(String byFilePath) {
		ArrayList<String> list=new ArrayList<String>();
		File byFile=new File(byFilePath);
		InputStreamReader read;
		String folder=new File(byFilePath).getName().split("\\.")[0];
		try {
			read = new InputStreamReader(new FileInputStream(byFile));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	if(lineTxt.trim()!=null || "".equals(lineTxt.trim())){
                		list.add(lineTxt.trim());
                	}
                }
                read.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JProgressBar_1 frame=new JProgressBar_1(list,folder);  
	      frame.setTitle("生成图片进度");  
	      frame.setSize(200,160);
	      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
	      frame.setResizable(false);
	      frame.setVisible(true); 
	      //frame.pack();  
	}
	
	

	
	
	

	
	
	
}
