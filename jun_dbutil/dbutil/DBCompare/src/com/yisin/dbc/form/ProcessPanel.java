package com.yisin.dbc.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.yisin.dbc.util.IniReader;
import com.yisin.dbc.util.Utililies;

@SuppressWarnings("serial")
public class ProcessPanel extends BasePanel {

    private int width = 900;
    private int height = 600;
    private boolean isrun = false;

    public ProcessPanel(int width, int height) {
        this.width = width;
        this.height = height;
        initComponent();
    }

    public void showPanel() {
        this.setVisible(true);
        close.setVisible(false);
        new Thread(new RunThread()).start();
    }
    
    public void hidePanel() {
        isrun = false;
        close.setVisible(false);
        this.setVisible(false);
    }
    
    private JButton close;
    public void initComponent() {
        this.setSize(new Dimension(width, height - 30));
        this.setLayout(null);
        this.setBackground(new Color(251, 251, 251));

        close = new JButton("X");
        close.setBounds(width - 45, 2, 30, 20);
        this.add(close);
        close.addActionListener(new ComActionListener(this));
        close.setVisible(false);
        
        ImageIcon image = new ImageIcon(Utililies.class.getResource("/43630.gif"));
        image.setImage(image.getImage().getScaledInstance(400, 300, Image.SCALE_DEFAULT));
        
        JLabel imgLabel = new JLabel(image);
        imgLabel.setBounds((width - 400)/2, 30, 400, 300);
        this.add(imgLabel);
        
        JLabel label = new JLabel("处理中...");
        label.setFont(new Font("微软雅黑", HEIGHT, 40));
        label.setBounds((width - 400)/2, (height - 300)/2 + 200, 400, 60);
        this.add(label);
    }

    private class ComActionListener implements ActionListener {
        private ProcessPanel dbset = null;

        public ComActionListener(ProcessPanel dbset) {
            this.dbset = dbset;
        }

        public void actionPerformed(ActionEvent e) {
            dbset.setVisible(false);
        }
    }
    
    class RunThread implements Runnable {
        public void run(){
            isrun = true;
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isrun){
                close.setVisible(true);
            }
        }
    }
    
}
