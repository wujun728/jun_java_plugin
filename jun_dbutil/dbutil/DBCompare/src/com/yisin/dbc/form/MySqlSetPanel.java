package com.yisin.dbc.form;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.yisin.dbc.db.DBHelper;
import com.yisin.dbc.util.CommonUtils;
import com.yisin.dbc.util.IniReader;

@SuppressWarnings("serial")
public class MySqlSetPanel extends BasePanel {
    private String url1 = "jdbc:mysql://{0}:{1}/{2}";
    private JTextField hostText1 = null;
    private JTextField userNameText1 = null;
    private JPasswordField pwdText1 = null;
    private JTextField portText1 = null;
    private JTextField dbText1 = null;
    private JComboBox<Object> codeCombo1 = null;
    
    private String url2 = "jdbc:mysql://{0}:{1}/{2}";
    private JTextField hostText2 = null;
    private JTextField userNameText2 = null;
    private JPasswordField pwdText2 = null;
    private JTextField portText2 = null;
    private JTextField dbText2 = null;
    private JComboBox<Object> codeCombo2 = null;
    
    public MySqlSetPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        initComponent();
    }
    
    public void initComponent(){
        int labelWidth = 150, 
            labelTextHeight = 25,
            textWidth = 200,
            jiange = 400;
        JLabel hostLabel1 = new JLabel("MySql主机地址");
        hostLabel1.setBounds(10, 10, labelWidth, labelTextHeight);
        
        JLabel hostLabel2 = new JLabel("MySql主机地址");
        hostLabel2.setBounds(10 + jiange, 10, labelWidth, labelTextHeight);
        
        hostText1 = new JTextField("127.0.0.1");
        hostText1.setBounds(labelWidth, 10, textWidth, labelTextHeight);
        
        hostText2 = new JTextField("127.0.0.1");
        hostText2.setBounds(labelWidth + jiange, 10, textWidth, labelTextHeight);
        
        //////////////////////
        JLabel userNameLabel1 = new JLabel("用户名");
        userNameLabel1.setBounds(10, 10 + ((labelTextHeight + 10) * 1), labelWidth, labelTextHeight);
        
        JLabel userNameLabel2 = new JLabel("用户名");
        userNameLabel2.setBounds(10 + jiange, 10 + ((labelTextHeight + 10) * 1), labelWidth, labelTextHeight);
        
        userNameText1 = new JTextField("root");
        userNameText1.setBounds(labelWidth, 10 + ((labelTextHeight + 10) * 1), textWidth, labelTextHeight);
        
        userNameText2 = new JTextField("root");
        userNameText2.setBounds(labelWidth + jiange, 10 + ((labelTextHeight + 10) * 1), textWidth, labelTextHeight);
        
        //////////////////////
        JLabel pwdLabel1 = new JLabel("密码");
        pwdLabel1.setBounds(10, 10 + ((labelTextHeight + 10) * 2), labelWidth, labelTextHeight);
        
        JLabel pwdLabel2 = new JLabel("密码");
        pwdLabel2.setBounds(10 + jiange, 10 + ((labelTextHeight + 10) * 2), labelWidth, labelTextHeight);
        
        pwdText1 = new JPasswordField();
        pwdText1.setBounds(labelWidth, 10 + ((labelTextHeight + 10) * 2), textWidth, labelTextHeight);
        
        pwdText2 = new JPasswordField();
        pwdText2.setBounds(labelWidth + jiange, 10 + ((labelTextHeight + 10) * 2), textWidth, labelTextHeight);
        
        //////////////////////
        JLabel portLabel1 = new JLabel("端口号：");
        portLabel1.setBounds(10, 10 + ((labelTextHeight + 10) * 3), labelWidth, labelTextHeight);
        JLabel portLabel2 = new JLabel("端口号：");
        portLabel2.setBounds(10 + jiange, 10 + ((labelTextHeight + 10) * 3), labelWidth, labelTextHeight);
        
        portText1 = new JTextField("3306");
        portText1.setBounds(labelWidth, 10 + ((labelTextHeight + 10) * 3), textWidth, labelTextHeight);
        portText2 = new JTextField("3306");
        portText2.setBounds(labelWidth + jiange, 10 + ((labelTextHeight + 10) * 3), textWidth, labelTextHeight);
        
        //////////////////////
        JLabel dbLabel1 = new JLabel("数据库名称");
        dbLabel1.setBounds(10, 10 + ((labelTextHeight + 10) * 4), labelWidth, labelTextHeight);
        JLabel dbLabel2 = new JLabel("数据库名称");
        dbLabel2.setBounds(10 + jiange, 10 + ((labelTextHeight + 10) * 4), labelWidth, labelTextHeight);
        
        dbText1 = new JTextField();
        dbText1.setBounds(labelWidth, 10 + ((labelTextHeight + 10) * 4), textWidth, labelTextHeight);
        dbText2 = new JTextField();
        dbText2.setBounds(labelWidth + jiange, 10 + ((labelTextHeight + 10) * 4), textWidth, labelTextHeight);
        
        //////////////////////
        JLabel codeLabel1 = new JLabel("使用编码");
        codeLabel1.setBounds(10, 10 + ((labelTextHeight + 10) * 5), labelWidth, labelTextHeight);
        JLabel codeLabel2 = new JLabel("使用编码");
        codeLabel2.setBounds(10 + jiange, 10 + ((labelTextHeight + 10) * 5), labelWidth, labelTextHeight);
        
        codeCombo1 = new JComboBox<Object>(new Object[]{"utf-8", "gbk"});
        codeCombo1.setBounds(labelWidth, 10 + ((labelTextHeight + 10) * 5), textWidth, labelTextHeight);
        codeCombo1.setSelectedIndex(0);
        
        codeCombo2 = new JComboBox<Object>(new Object[]{"utf-8", "gbk"});
        codeCombo2.setBounds(labelWidth + jiange, 10 + ((labelTextHeight + 10) * 5), textWidth, labelTextHeight);
        codeCombo2.setSelectedIndex(0);
        
        JButton saveBtn = new JButton("保存");
        saveBtn.setBounds(430, 10 + ((labelTextHeight + 10) * 9), 110, labelTextHeight);
        
        JButton testBtn1 = new JButton("测试连接1");
        testBtn1.setBounds(labelWidth, 10 + ((labelTextHeight + 10) * 7), 110, labelTextHeight);
        
        JButton testBtn2 = new JButton("测试连接2");
        testBtn2.setBounds(labelWidth + jiange, 10 + ((labelTextHeight + 10) * 7), 110, labelTextHeight);
        
        this.add(hostLabel1);
        this.add(hostLabel2);
        this.add(hostText1);
        this.add(hostText2);
        
        this.add(userNameLabel1);
        this.add(userNameLabel2);
        this.add(userNameText1);
        this.add(userNameText2);
        
        this.add(pwdLabel1);
        this.add(pwdLabel2);
        this.add(pwdText1);
        this.add(pwdText2);
        
        this.add(portLabel1);
        this.add(portLabel2);
        this.add(portText1);
        this.add(portText2);
        
        this.add(dbLabel1);
        this.add(dbLabel2);
        this.add(dbText1);
        this.add(dbText2);
        
        this.add(codeLabel1);
        this.add(codeLabel2);
        this.add(codeCombo1);
        this.add(codeCombo2);
        
        this.add(saveBtn);
        this.add(testBtn1);
        this.add(testBtn2);
        
        saveBtn.addActionListener(new ExcActionListener());
        testBtn1.addActionListener(new ExcActionListener());
        testBtn2.addActionListener(new ExcActionListener());
        
    }
    
    public boolean testConnection1(){
        boolean bool = false;
        String ip = hostText1.getText();
        String port = portText1.getText();
        String dnname = dbText1.getText();
        String username = userNameText1.getText();
        String password = new String(pwdText1.getPassword());
        try {
            url1 = CommonUtils.format(url1, ip, port, dnname);
            // 加载MySql的驱动类
            Class.forName("com.mysql.jdbc.Driver");
            // 连接MySql数据库，用户名和密码都是root
            Connection conn = DriverManager.getConnection(url1, username, password);
            if(conn != null){
                bool = true;
                conn.close();
            }
        } catch (Exception e) {
            bool = false;
            System.out.println(ip + ":" + port + ", " + dnname + ", " + username + ", " + password);
        }
        return bool;
    }
    
    public boolean testConnection2(){
        boolean bool = false;
        String ip = hostText2.getText();
        String port = portText2.getText();
        String dnname = dbText2.getText();
        String username = userNameText2.getText();
        String password = new String(pwdText2.getPassword());
        try {
            url2 = CommonUtils.format(url2, ip, port, dnname);
            // 加载MySql的驱动类
            Class.forName("com.mysql.jdbc.Driver");
            // 连接MySql数据库，用户名和密码都是root
            Connection conn = DriverManager.getConnection(url2, username, password);
            if(conn != null){
                bool = true;
                conn.close();
            }
        } catch (Exception e) {
            bool = false;
            System.out.println(ip + ":" + port + ", " + dnname + ", " + username + ", " + password);
        }
        return bool;
    }
    
    public void setConfigValue(HashMap<String, String> cfg, int state){
        if(state == 1){
            hostText1.setText(CommonUtils.excNullToString(cfg.get("host"), ""));
            portText1.setText(CommonUtils.excNullToString(cfg.get("port"), "3306"));
            dbText1.setText(CommonUtils.excNullToString(cfg.get("db_name"), ""));
            userNameText1.setText(CommonUtils.excNullToString(cfg.get("userName"), ""));
            pwdText1.setText(CommonUtils.excNullToString(cfg.get("password"), ""));
            codeCombo1.setSelectedItem(CommonUtils.excNullToString(cfg.get("encode"), "UTF-8"));
        } else {
            hostText2.setText(CommonUtils.excNullToString(cfg.get("host"), ""));
            portText2.setText(CommonUtils.excNullToString(cfg.get("port"), "3306"));
            dbText2.setText(CommonUtils.excNullToString(cfg.get("db_name"), ""));
            userNameText2.setText(CommonUtils.excNullToString(cfg.get("userName"), ""));
            pwdText2.setText(CommonUtils.excNullToString(cfg.get("password"), ""));
            codeCombo2.setSelectedItem(CommonUtils.excNullToString(cfg.get("encode"), "UTF-8"));
        }
    }
    
    private class ExcActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String name = e.getActionCommand();
            if(name.equals("测试连接1")){
                Toolkit.getDefaultToolkit().beep();
                if(testConnection1()){
                    JOptionPane.showMessageDialog(null, "恭喜，连接成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "连接失败，请检查", "提示", JOptionPane.ERROR_MESSAGE);
                }
            } else if(name.equals("测试连接2")){
                Toolkit.getDefaultToolkit().beep();
                if(testConnection2()){
                    JOptionPane.showMessageDialog(null, "恭喜，连接成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "连接失败，请检查", "提示", JOptionPane.ERROR_MESSAGE);
                }
            } else if(name.equals("保存")){
                Toolkit.getDefaultToolkit().beep();
                try {
                    String conn_name1 = MainWindow.mainWindow.dbsetPanel.getConnectionName(1);
                    String ip1 = hostText1.getText();
                    String port1 = portText1.getText();
                    String dnname1 = dbText1.getText();
                    String username1 = userNameText1.getText();
                    String password1 = new String(pwdText1.getPassword());
                    String encode1 = codeCombo1.getSelectedItem().toString();
                    
                    String conn_name2 = MainWindow.mainWindow.dbsetPanel.getConnectionName(2);
                    String ip2 = hostText2.getText();
                    String port2 = portText2.getText();
                    String dnname2 = dbText2.getText();
                    String username2 = userNameText2.getText();
                    String password2 = new String(pwdText2.getPassword());
                    String encode2 = codeCombo2.getSelectedItem().toString();
                    
                    IniReader reader = IniReader.getIniReader();
                    reader.putValue(DBHelper.sections, conn_name1, "conn_name", conn_name1)
                          .putValue(DBHelper.sections, conn_name1, "host", ip1)
                          .putValue(DBHelper.sections, conn_name1, "port", port1)
                          .putValue(DBHelper.sections, conn_name1, "userName", username1)
                          .putValue(DBHelper.sections, conn_name1, "password", password1)
                          .putValue(DBHelper.sections, conn_name1, "db_name", dnname1)
                          .putValue(DBHelper.sections, conn_name1, "encode", encode1)
                          
                          .putValue(DBHelper.sections, conn_name2, "conn_name", conn_name2)
                          .putValue(DBHelper.sections, conn_name2, "host", ip2)
                          .putValue(DBHelper.sections, conn_name2, "port", port2)
                          .putValue(DBHelper.sections, conn_name2, "userName", username2)
                          .putValue(DBHelper.sections, conn_name2, "password", password2)
                          .putValue(DBHelper.sections, conn_name2, "db_name", dnname2)
                          .putValue(DBHelper.sections, conn_name2, "encode", encode2)
                          
                          .save();
                    
                    JOptionPane.showMessageDialog(null, "保存成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "保存失败，请检查", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

}
