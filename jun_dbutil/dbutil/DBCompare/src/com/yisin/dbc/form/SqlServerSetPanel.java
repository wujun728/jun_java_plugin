package com.yisin.dbc.form;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SqlServerSetPanel extends JPanel {
    
    public static String sections = "sqlserver_conn_info";

    public SqlServerSetPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        JLabel label = new JLabel("敬请期待");
        label.setBounds((width - 100) / 2, (height - 25) / 2, 100, 25);
        add(label);
    }
    
    public void setConfigValue(HashMap<String, String> cfg){
        /*hostText.setText(cfg.get("host"));
        portText.setText(cfg.get("port"));
        dbText.setText(cfg.get("db_name"));
        userNameText.setText(cfg.get("userName"));
        pwdText.setText(cfg.get("password"));*/
    }

}
