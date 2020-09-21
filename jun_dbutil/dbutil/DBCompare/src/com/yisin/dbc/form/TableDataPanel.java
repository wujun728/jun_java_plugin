package com.yisin.dbc.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.yisin.dbc.db.DBHelper;
import com.yisin.dbc.util.SQLFormatter;

public class TableDataPanel extends BasePanel {

    private String sql = "";
    private String tableName = "";
    private int width = 900, height = 600, state = 1;

    public TableDataPanel(int width, int height, int state) {
        this.width = width;
        this.height = height;
        this.state = state;
        initCommpant();
    }

    public void viewTableData(String tableName) {
        if (tableName != null) {
            this.tableName = tableName;
            sql = "select * from " + tableName + " limit 0, 1000";
            // 执行SQL
            excuteSql();
        } else {
            sql = "";
        }
        sqlArea.setText(sql);
    }

    public JTextArea sqlArea;
    public JTextArea infoArea;
    public JTable dataTable;
    public DefaultTableModel model = null;
    public JButton excuteBtn;
    public JButton formatBtn;
    public JScrollPane tabJsp;
    public JScrollPane infoJsp;

    public void initCommpant() {
        this.setSize(new Dimension(width, height - 30));
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        JButton close = new JButton("X");
        close.setBounds(width - 45, 2, 30, 20);
        this.add(close);
        close.addActionListener(new ComActionListener(this));
        
        excuteBtn = new JButton("执行SQL");
        excuteBtn.setBounds(5, 5, 100, 20);
        this.add(excuteBtn);
        excuteBtn.addActionListener(new ComActionListener(this));
        
        formatBtn = new JButton("美化SQL");
        formatBtn.setBounds(5 + 110, 5, 100, 20);
        this.add(formatBtn);
        formatBtn.addActionListener(new ComActionListener(this));

        sqlArea = new JTextArea();
        JScrollPane sqlJsp = new JScrollPane(sqlArea);
        //sqlArea.setBackground(new Color(245, 245, 245));
        sqlJsp.setBounds(5, 35, width - 20, 180);
        this.add(sqlJsp);
        
        infoArea = new JTextArea();
        infoArea.setBackground(new Color(245, 245, 245));
        
        infoJsp = new JScrollPane(infoArea);
        infoJsp.setBounds(5, 220, width - 20, height - 260);
        this.add(infoJsp);
        infoJsp.setVisible(false);
        
        dataTable = new JTable(null);
        tabJsp = new JScrollPane(dataTable);
        tabJsp.setBounds(5, 220, width - 20, height - 260);
        dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.add(tabJsp);

    }

    public void renderTableData(Vector<Vector<Object>> data, Vector<Object> columns) {
        model = new DefaultTableModel(data, columns);
        dataTable.setModel(model);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        // setDefaultRenderer(Object.class, r);
    }

    public void excuteSql() {
        new Thread() {
            public void run() {
               try {
                    showInfoOrTable(false, "执行中....");
                    Map<String, Object> dataMap = DBHelper.loadTableData(sql, state);
                    if(dataMap != null){
                        Vector<Vector<Object>> data = (Vector<Vector<Object>>) dataMap.get("dataSet");
                        Vector<Object> columns = (Vector<Object>) dataMap.get("feildSet");
                        if(data != null && columns != null){
                            showInfoOrTable(true, "");
                            renderTableData(data, columns);
                        } else {
                            showInfoOrTable(false, "执行成功，无结果...");
                        }
                    } else {
                        showInfoOrTable(false, "执行结果有误");
                    }
                } catch (Exception e) {
                    StringBuffer sb = new StringBuffer();
                    StackTraceElement[] stes = e.getStackTrace();
                    for (int i = 0; i < stes.length; i++) {
                        sb.append("\n" + stes[i].toString());
                    }
                    showInfoOrTable(false, "执行异常：" + e.getMessage() + sb.toString());
                }
            }
        }.start();
    }
    
    public void showInfoOrTable(boolean flag, String msg){
        tabJsp.setVisible(flag);
        infoJsp.setVisible(!flag);
        infoArea.setText(msg);
    }

    public Object[][] ListMapToArray2(List<Map<String, Object>> list) {
        int size1 = list.size(), size2 = 0, index = 0;
        Object[][] data = new Object[size1][];
        Object[] col = null;
        Map<String, Object> map = null;
        for (int i = 0; i < size1; i++) {
            map = list.get(i);
            size2 = map.keySet().size();
            col = new Object[size2];
            index = 0;
            for (Map.Entry<String, Object> ent : map.entrySet()) {
                col[index] = ent.getValue();
                index++;
            }
            data[i] = col;
        }
        return data;
    }

    public void showPanel() {
        //UIManager.put("TabbedPane.tabAreaInsets", new InsetsUIResource(3, 20, 2, 20));
        this.setVisible(true);
    }
    
    private class ComActionListener implements ActionListener {
        private TableDataPanel form = null;

        public ComActionListener(TableDataPanel form) {
            this.form = form;
        }

        public void actionPerformed(ActionEvent e) {
            String com = e.getActionCommand();
            if(com.equals("X")){
                form.setVisible(false);
                MainWindow.mainForm.resetDisabled();
            } else if(com.equals("执行SQL")){
                String text = sqlArea.getText();
                if(text.indexOf(tableName) == -1){
                    
                } else if(text.indexOf("limit ") == -1){
                    text = text + " limit 0, 1000";
                }
                sql = text;
                excuteSql();
            } else if(com.equals("美化SQL")){
                String sql = sqlArea.getText();
                if(sql != null){
                    sql = new SQLFormatter().format(sql);
                    sqlArea.setText(sql);
                }
            }
        }
    }

}
