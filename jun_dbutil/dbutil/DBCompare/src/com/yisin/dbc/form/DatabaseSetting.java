package com.yisin.dbc.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.yisin.dbc.db.DBHelper;
import com.yisin.dbc.util.IniReader;

@SuppressWarnings("serial")
public class DatabaseSetting extends BasePanel {

    private int width = 900;
    private int height = 600;
    private JTextField connName1 = null;
    private JTextField connName2 = null;
    private MySqlSetPanel mySqlSetPanel = null;
    private OracleSetPanel oracleSetPanel = null;
    private SqlServerSetPanel sqlServerSetPanel = null;
    private JTabbedPane tabPane = null;
    private boolean comboInited = false;

    public DatabaseSetting(int width, int height) {
        this.width = width;
        this.height = height;
        initComponent();
    }

    public void showPanel() {
        this.setVisible(true);
        initConnNameList(null);
    }
    
    public String getConnectionName(int state){
        String result = "";
        if(state == 1){
            result = connName1.getText();
        } else {
            result = connName2.getText();
        }
        if(result == null || result.length() == 0){
            result = "数据库" + state;
        }
        return result;
    }
    
    public void setConnectionName(String name, int state){
        if(state == 1){
            connName1.setText(name);
        } else {
            connName2.setText(name);
        }
    }

    public void initComponent() {
        this.setSize(new Dimension(width, height - 30));
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        JButton close = new JButton("X");
        close.setBounds(width - 45, 2, 30, 20);
        this.add(close);
        close.addActionListener(new ComActionListener(this));

        JLabel connNameLabel1 = new JLabel("连接名称1：");
        connNameLabel1.setBounds(10, 6, 70, 25);

        connName1 = new JTextField();
        connName1.setBounds(80, 6, 300, 25);
        
        JLabel connNameLabel2 = new JLabel("连接名称2：");
        connNameLabel2.setBounds(390, 6, 70, 25);
        
        connName2 = new JTextField();
        connName2.setBounds(460, 6, 300, 25);
        // -------------

        tabPane = new JTabbedPane();

        mySqlSetPanel = new MySqlSetPanel(width, height - 35 - 40);
        oracleSetPanel = new OracleSetPanel(width, height - 35 - 40);
        sqlServerSetPanel = new SqlServerSetPanel(width, height - 35 - 40);

        tabPane.addTab("MySQL", null, mySqlSetPanel, "MySQL 数据库配置信息");
        tabPane.addTab("Oracle", null, oracleSetPanel, "Oracle 数据库配置信息");
        tabPane.addTab("SQLServer", null, sqlServerSetPanel, "SQLServer 数据库配置信息");

        tabPane.setBounds(10, 35, width, height - 35);

        tabPane.addChangeListener(new TabChangeListener(this));

        this.add(connNameLabel1);
        this.add(connNameLabel2);
        this.add(connName1);
        this.add(connName2);
        this.add(tabPane);
        
    }

    public void initConnNameList(String secname) {
        try {
            comboInited = false;
            IniReader reader = IniReader.getIniReader();
            HashMap<String, HashMap<String, String>> map = reader.getConfig(DBHelper.sections);
            if (map != null && map.entrySet().size() > 0) {
                HashMap<String, String> cfg = null;
                int state = 1;
                String name = "";
                for(Map.Entry<String, HashMap<String, String>> entry : map.entrySet()){
                    cfg = entry.getValue();
                    name = entry.getKey();
                    if(state == 1){
                        connName1.setText(name);
                    } else {
                        connName2.setText(name);
                    }
                    setConfigValue(cfg, state++);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setConfigValue(HashMap<String, String> cfg, int state) {
        int index = tabPane.getSelectedIndex();
        if (index == 0) { // mysql
            mySqlSetPanel.setConfigValue(cfg, state);
        } else if (index == 1) { // Oracle
            oracleSetPanel.setConfigValue(cfg);
        } else if (index == 2) { // SQLServer
            sqlServerSetPanel.setConfigValue(cfg);
        }
    }

    private class TabChangeListener implements ChangeListener {
        private DatabaseSetting dbset = null;

        public TabChangeListener(DatabaseSetting dbset) {
            this.dbset = dbset;
        }

        public void stateChanged(ChangeEvent e) {
            JTabbedPane tab = (JTabbedPane) e.getSource();
            int index = tab.getSelectedIndex();
            if (index == 0) { // mysql
                dbset.initConnNameList(null);
            } else if (index == 1) { // Oracle
                dbset.initConnNameList(null);
            } else if (index == 2) { // SQLServer
                dbset.initConnNameList(null);
            }
        }
    }

    private class ComActionListener implements ActionListener {
        private DatabaseSetting dbset = null;

        public ComActionListener(DatabaseSetting dbset) {
            this.dbset = dbset;
        }

        public void actionPerformed(ActionEvent e) {
            dbset.setVisible(false);
            MainWindow.mainForm.resetDisabled();
        }
    }
    
    private class ComboItemListener implements ItemListener {
        private DatabaseSetting dbset = null;

        public ComboItemListener(DatabaseSetting dbset) {
            this.dbset = dbset;
        }
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange() == ItemEvent.SELECTED && comboInited){
                String secname = e.getItem().toString();
                System.out.println(secname);
                int index = tabPane.getSelectedIndex();
                if (index == 0) { // mysql
                    dbset.initConnNameList(secname);
                } else if (index == 1) { // Oracle
                    dbset.initConnNameList(secname);
                } else if (index == 2) { // SQLServer
                    dbset.initConnNameList(secname);
                }
                
            }
        }
    }
    
}
