package com.yisin.dbc.form;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.yisin.dbc.StartApp;

@SuppressWarnings("serial")
public class MainWindow extends JPanel {

    public static float version = 1.0f;
    public static JLayeredPane interFrame = null;
    public static Map<String, BasePanel> panelMap = new HashMap<String, BasePanel>();
    public static Integer layeredLevel = 1;
    public static int width = 900;
    public static int height = 600;

    public static MainWindow mainWindow = null;
    public static MainForm mainForm = null;

    public DatabaseSetting dbsetPanel;
    public TableDataPanel tableDataPanel;
    public ProcessPanel processPanel;
    public AboutForm af;
    
    public MainWindow() {

    }

    protected void initComponents() {
        this.setSize(width, height);
        this.setLayout(null);
        
        createControlPanel();
    }

    protected void createControlPanel() {
        interFrame = new JLayeredPane();

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, width, 30);
        JMenu menu = new JMenu("设置");
        JMenu aboutMenu = new JMenu("关于");
        JMenuItem databaseItem = new JMenuItem("数据库配置");
        databaseItem.setName("databaseSet");
        menu.add(databaseItem);
        
        JMenuItem loadDbItem = new JMenuItem("加载数据库");
        loadDbItem.setName("loadDbItem");
        menu.add(loadDbItem);
        
        JMenuItem aboutItem = new JMenuItem("关于");
        aboutItem.setName("aboutItem");
        aboutMenu.add(aboutItem);

        databaseItem.addActionListener(new ShowActionListener());
        loadDbItem.addActionListener(new ShowActionListener());
        aboutItem.addActionListener(new ShowActionListener());

        menuBar.add(menu);
        menuBar.add(aboutMenu);
        this.add(menuBar);

        interFrame.setLayout(null);
        this.add(interFrame);

        interFrame.setBounds(0, 30, width, height - 30);

        mainForm = new MainForm(interFrame.getWidth(), interFrame.getHeight());
        interFrame.add(mainForm, layeredLevel++);
        panelMap.put("mainPanel", mainForm);
        mainForm.setBounds(0, 0, interFrame.getWidth(), interFrame.getHeight());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
                    // 设置本属性将改变窗口边框样式定义
                    System.setProperty("sun.java2d.noddraw", "true");
                    BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
                    BeautyEyeLNFHelper.launchBeautyEyeLNF();
                    UIManager.put("RootPane.setupButtonVisible", false);
                } catch (Exception e) {
                }
                JFrame frame = new JFrame();
                frame.setTitle("[隐心]数据库比对工具 v" + version);
                frame.setResizable(false);
                mainWindow = new MainWindow();
                mainWindow.initComponents();
                frame.setSize(width, height);
                frame.add(mainWindow);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // frame.pack();
                
                Toolkit toolkit = Toolkit.getDefaultToolkit(); 
                Dimension scmSize = toolkit.getScreenSize(); 
                frame.setLocation(scmSize.width/2 - (width/2), scmSize.height/2 - (height/2)); 
                frame.setVisible(true);
                
                StartApp.getInstance().hide();
            }
        });
    }

    public void showTableDataPanel(String tableName, int state){
        tableDataPanel = (TableDataPanel) panelMap.get("tableDataPanel");
        if (tableDataPanel == null) {
            tableDataPanel = new TableDataPanel(width, height - 30, state);
            panelMap.put("tableDataPanel", tableDataPanel);
            interFrame.add(tableDataPanel, layeredLevel++);
            tableDataPanel.setLocation(0, 0);
        } else {
            interFrame.remove(tableDataPanel);
            interFrame.add(tableDataPanel, layeredLevel++);
        }
        tableDataPanel.viewTableData(tableName);
        tableDataPanel.showPanel();
    }
    
    public void showDatabaseSetPanel(){
        dbsetPanel = (DatabaseSetting) panelMap.get("dbsetPanel");
        if (dbsetPanel == null) {
            dbsetPanel = new DatabaseSetting(width, height - 30);
            panelMap.put("dbsetPanel", dbsetPanel);
            interFrame.add(dbsetPanel, layeredLevel++);
            dbsetPanel.setLocation(0, 0);
        } else {
            interFrame.remove(dbsetPanel);
            interFrame.add(dbsetPanel, layeredLevel++);
        }
        dbsetPanel.showPanel();
        // 记住按钮状态
        mainForm.bakDisabled();
    }
    
    public void showProcessPanel(){
        processPanel = (ProcessPanel) panelMap.get("processPanel");
        if (processPanel == null) {
            processPanel = new ProcessPanel(width, height - 30);
            panelMap.put("processPanel", processPanel);
            interFrame.add(processPanel, layeredLevel++);
            processPanel.setLocation(0, 0);
        } else {
            interFrame.remove(processPanel);
            interFrame.add(processPanel, layeredLevel++);
        }
        processPanel.showPanel();
    }
    
    public void hideProcessPanel(){
        processPanel = (ProcessPanel) panelMap.get("processPanel");
        if (processPanel != null) {
            processPanel.hidePanel();
        }
    }
    
    public void loadDbData(){
        if(mainForm != null){
            mainForm.initTreeThread(0);
        }
    }
    
    private class ShowActionListener implements ActionListener {
        private ShowActionListener() {
        }

        public void actionPerformed(ActionEvent actionEvent) {
            String name = actionEvent.getActionCommand();
            if (name.equals("数据库配置")) {
                showDatabaseSetPanel();
            } else if (name.equals("加载数据库")) {
                loadDbData();
            } else if(name.equals("关于")){
                if(af == null){
                    af = new AboutForm();
                    af.showForm();
                } else {
                    af.showForm();
                }
            }
        }
    }

}
