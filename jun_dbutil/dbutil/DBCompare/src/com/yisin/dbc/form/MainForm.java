package com.yisin.dbc.form;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.yisin.dbc.db.DBHelper;
import com.yisin.dbc.db.DbCache;
import com.yisin.dbc.entity.DbTable;
import com.yisin.dbc.entity.DbTableColumn;
import com.yisin.dbc.entity.MysqlDbColumn;
import com.yisin.dbc.main.CreateThread;
import com.yisin.dbc.util.CommonUtils;

@SuppressWarnings("serial")
public class MainForm extends BasePanel {

	private int width = 900;
	private int height = 600;
	private boolean configInited = false;
	private boolean loadding = false;

	private static JTree tree_1 = null;
	private static JTree tree_2 = null;
	private DefaultMutableTreeNode dbNode1;
	private DefaultMutableTreeNode dbNode2;
	private JScrollPane jScrollPanel_1;
	private JScrollPane jScrollPanel_2;
	public static Map<String, Vector<Object>> columnMap1 = new HashMap<String, Vector<Object>>();
	public static Map<String, Vector<Object>> columnMap2 = new HashMap<String, Vector<Object>>();

	public MainForm() {
		this.setBackground(Color.white);
		this.setLayout(null);
		
		DBHelper.initDbConfig();
		
		initTreeThread(1);
		initCompant();
	}

	public MainForm(int width, int height) {
		this.width = width;
		this.height = height;
		this.setBackground(Color.white);
		this.setLayout(null);
		
		DBHelper.initDbConfig();
		
		initTreeThread(1);
		initCompant();
	}

	public List<DbTable> getDbTable(int state) {
		List<DbTable> list = DBHelper.getDbTables(state);
		return list;
	}

	public List<?> getTableColumn(String table, int state) {
		return DBHelper.getTableColumns(table, state);
	}

	public Map<String, List<DbTableColumn>> getTableColumn(int state) {
		return DBHelper.getMutilTableColumns(state);
	}

	private JButton seekTableDataBtn1;
	private JButton seekTableDataBtn2;
	private JButton singleCreateBtn;
	private JButton mutilCreateBtn;
	
	private JCheckBox showBox;
	
	private JLabel nameLabel1 = new JLabel("数据库1");
	private JLabel nameLabel2 = new JLabel("数据库2");
	
	// 0禁用，1启用，  1查看表数据1, 2查看表数据2， 3对比全部表，4对比选择的表
	public int[] btnDisabledState = {0, 0, 0, 0};

	private String chooseTable = null;
	private int chooseDb = 1;
	
	JFileChooser jfc = new JFileChooser();// 文件选择器  
	public static List<DbTable> tableDatas1 = new ArrayList<DbTable>();
	public static List<DbTable> tableDatas2 = new ArrayList<DbTable>();
	
	public void bakDisabled(){
        btnDisabledState[0] = seekTableDataBtn1.isEnabled() ? 1 : 0;
        btnDisabledState[1] = seekTableDataBtn2.isEnabled() ? 1 : 0;
        btnDisabledState[2] = singleCreateBtn.isEnabled() ? 1 : 0;
        btnDisabledState[3] = mutilCreateBtn.isEnabled() ? 1 : 0;
        
        seekTableDataBtn1.setEnabled(false);
        seekTableDataBtn2.setEnabled(false);
        singleCreateBtn.setEnabled(false);
        mutilCreateBtn.setEnabled(false);
	}
	
	public void resetDisabled(){
	    seekTableDataBtn1.setEnabled(btnDisabledState[0] == 1);
	    seekTableDataBtn2.setEnabled(btnDisabledState[1] == 1);
	    singleCreateBtn.setEnabled(btnDisabledState[2] == 1);
	    mutilCreateBtn.setEnabled(btnDisabledState[3] == 1);
    }

	public void initCompant() {
		int left = 200, btnWidth = 95, labelTextHeight = 25, textWidth = 150;

		nameLabel1.setBounds(5, 5, textWidth, labelTextHeight);
		add(nameLabel1);
		
		nameLabel2.setBounds(535, 5, textWidth, labelTextHeight);
		add(nameLabel2);
		
		// 按钮层
		seekTableDataBtn1 = new JButton("查看表数据1");
		seekTableDataBtn1.setBounds(left, 5, textWidth, labelTextHeight);
		add(seekTableDataBtn1);
		seekTableDataBtn1.setEnabled(false);
		
		seekTableDataBtn2 = new JButton("查看表数据2");
        seekTableDataBtn2.setBounds(530 + left, 5, textWidth, labelTextHeight);
        add(seekTableDataBtn2);
        seekTableDataBtn2.setEnabled(false);

		mutilCreateBtn = new JButton("对比全部表");
		mutilCreateBtn.setBounds(366, 120, btnWidth + 60, labelTextHeight);
		add(mutilCreateBtn);

		singleCreateBtn = new JButton("对比选择的表");
		singleCreateBtn.setBounds(366, 180, btnWidth + 60, labelTextHeight);
		add(singleCreateBtn);
		singleCreateBtn.setEnabled(false);
		
		showBox = new JCheckBox("只显示不一致的结果");
		showBox.setBounds(366, 250, btnWidth + 60, labelTextHeight);
		add(showBox);
		showBox.setSelected(false);
		
		seekTableDataBtn1.addActionListener(new BtnActionListener());
		seekTableDataBtn2.addActionListener(new BtnActionListener());
		singleCreateBtn.addActionListener(new BtnActionListener());
		mutilCreateBtn.addActionListener(new BtnActionListener());

		configInited = false;
	}

	public void initTreeThread(int flag) {
	    if(loadding){
	        /*int count = dbNode.getChildCount();
	        if(count > 0){
	        }*/
	        return;
	    }
		new Thread() {
			public void run() {
			    loadding = true;
			    MainWindow.mainWindow.showProcessPanel();
				initTree(1);
				try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
				initTree(2);
				
				nameLabel1.setText(DBHelper.connName1);
				nameLabel2.setText(DBHelper.connName2);
				
				MainWindow.mainWindow.hideProcessPanel();
				loadding = false;
			}
		}.start();
	}

	public void initTree(int state) {

		// 创建没有父节点和子节点、但允许有子节点的树节点，并使用指定的用户对象对它进行初始化。
		// public DefaultMutableTreeNode(Object TreeObject)
	    int tableSize = -1;
	    DbTable table = null;
        DefaultMutableTreeNode node = null;
	    if(state == 1){
	        if (jScrollPanel_1 != null) {
	            jScrollPanel_1.removeAll();
	            this.remove(jScrollPanel_1);
	        }
	        dbNode1 = new DefaultMutableTreeNode(new User(DBHelper.getSchema(state)));
	        
	        tableDatas1 = getDbTable(state);
            if (tableDatas1 != null) {
                tableSize = tableDatas1.size();
            }
	        for (int i = 0; i < tableSize; i++) {
                table = tableDatas1.get(i);
                node = new DefaultMutableTreeNode(new User(table.getTableName()));
                dbNode1.add(node);
	        }
	    } else {
	        if (jScrollPanel_2 != null) {
                jScrollPanel_2.removeAll();
                this.remove(jScrollPanel_2);
            }
	        dbNode2 = new DefaultMutableTreeNode(new User(DBHelper.getSchema(state)));
	        
	        tableDatas2 = getDbTable(state);
            if (tableDatas2 != null) {
                tableSize = tableDatas2.size();
            }
            for (int i = 0; i < tableSize; i++) {
                table = tableDatas2.get(i);
                node = new DefaultMutableTreeNode(new User(table.getTableName()));
                dbNode2.add(node);
            }
	    }

		if(state == 1){
		    tree_1 = new JTree(dbNode1);
		    tree_1.setName("tree_1");
		    
		    jScrollPanel_1 = new JScrollPane();
	        jScrollPanel_1.getViewport().add(tree_1);
	        jScrollPanel_1.setBounds(5, 40, 350, height - 80);
	        this.add(jScrollPanel_1);
	        // 添加选择事件
	        tree_1.addTreeSelectionListener(new JTreeSelectionListener());

		} else {
		    tree_2 = new JTree(dbNode2);
		    tree_2.setName("tree_2");
		    
		    jScrollPanel_2 = new JScrollPane();
	        jScrollPanel_2.getViewport().add(tree_2);
	        jScrollPanel_2.setBounds(530, 40, 350, height - 80);
	        this.add(jScrollPanel_2);
	        // 添加选择事件
	        tree_2.addTreeSelectionListener(new JTreeSelectionListener());
		}
		
		if (tableSize > 0) {
			new Thread(new LoadColumnThread(state)).start();
		} else if (tableSize == -1) {
			new Thread(new AlertThread()).start();
		}
	}

	class JTreeSelectionListener implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e) {
		    JTree tree = (JTree) e.getSource();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			if (node == null)
				return;
			int state = tree.getName().endsWith("_1") ? 1: 2;
			int count = node.getChildCount();
			Object object = node.getUserObject();
			if (object != null) { // node.isLeaf() //node.isRoot()
				User User = (User) object;
				if (User != null) {
					String table = User.toString();
					int level = node.getLevel();
					if (level == 0) { // 数据库层
						chooseTable = null;
						chooseDb = 0;
						if(state == 1){
						    seekTableDataBtn1.setEnabled(false);
						} else {
						    seekTableDataBtn2.setEnabled(false);
						}
						
						singleCreateBtn.setEnabled(false);
						
						//setCompTooltip();
						if (count == 0) {
							initTreeThread(1);
						}
					} else if (level == 1) { // 表层
						int selnum = tree.getSelectionCount();
						chooseTable = table;
						chooseDb = state;
						if(state == 1){
                            seekTableDataBtn1.setEnabled(selnum == 1);
                        } else {
                            seekTableDataBtn2.setEnabled(selnum == 1);
                        }
						singleCreateBtn.setEnabled(true);
						//setCompTooltip();
						if (count == 0) {
							initTableFeild(node, table, state);
						}
						
						/*if(state == 1){
				            DefaultMutableTreeNode node2 = getTreeNode(dbNode2, table);
				            
				            tree_2.setSelectionPath(new TreePath(node2));
						} else {
						    DefaultMutableTreeNode node1 = getTreeNode(dbNode1, table);
						    
						    tree_1.setSelectionPath(new TreePath(node1));
						}*/
					} else if (level == 2) { // 字段层
						int selnum = tree.getSelectionCount();
						chooseTable = table;
						chooseDb = state;
						if(state == 1){
                            seekTableDataBtn1.setEnabled(selnum == 1);
                        } else {
                            seekTableDataBtn2.setEnabled(selnum == 1);
                        }
						singleCreateBtn.setEnabled(true);
						//DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
					}
				}
			}
		}
	}

	public void renderTableFeild(DefaultMutableTreeNode top, String table, List<?> columns, int state) {
		if (columns != null) {
			MysqlDbColumn colum = null;
			DefaultMutableTreeNode node1 = null;
			String title = "";
			Vector<Object> vector = new Vector<Object>();
			for (int i = 0, k = columns.size(); i < k; i++) {
				colum = (MysqlDbColumn) columns.get(i);
				title = colum.getColumnName() + " - " + colum.getColumnType();
				node1 = new DefaultMutableTreeNode(new User(title));
				top.add(node1);
				vector.add(colum.getColumnName());
			}
			if(state == 1){
			    if (!columnMap1.containsKey(table)) {
	                columnMap1.put(table, vector);
	            }
			} else {
			    if (!columnMap2.containsKey(table)) {
	                columnMap2.put(table, vector);
	            }
			}
		}
	}

	public void initTableFeild(DefaultMutableTreeNode top, String table, int state) {
		List<MysqlDbColumn> columns = (List<MysqlDbColumn>) getTableColumn(table, state);
		if (columns != null) {
			MysqlDbColumn colum = null;
			DefaultMutableTreeNode node1 = null;
			String title = "";
			Vector<Object> vector = new Vector<Object>();
			// String[] column = new String[columns.size()];
			for (int i = 0, k = columns.size(); i < k; i++) {
				colum = columns.get(i);
				title = colum.getColumnName() + " - " + colum.getColumnType();
				node1 = new DefaultMutableTreeNode(new User(title));
				top.add(node1);
				// column[i] = colum.getColumnName();
				vector.add(colum.getColumnName());
			}
			if(state == 1){
                if (!columnMap1.containsKey(table)) {
                    columnMap1.put(table, vector);
                }
            } else {
                if (!columnMap2.containsKey(table)) {
                    columnMap2.put(table, vector);
                }
            }
		}
	}

	class LoadColumnThread implements Runnable {
	    int state = 0;
        
	    public LoadColumnThread(int state){
            this.state = state;
        }
        
		public void run() {
		    Map<String, List<DbTableColumn>> tabelMap = getTableColumn(state);
			if (tabelMap != null) {
				List<?> comulnList = null;
				int size = 0;
				if(state == 1){
				    DbCache.mysqlDbColumnMap1 = tabelMap;
				    size = dbNode1.getChildCount();
				} else {
				    DbCache.mysqlDbColumnMap2 = tabelMap;
				    size = dbNode2.getChildCount();
				}
				DefaultMutableTreeNode node;
				TreePath tpath = null;
				User user = null;
				for (int i = 0; i < size; i++) {
				    if(state == 1){
	                    node = (DefaultMutableTreeNode) dbNode1.getChildAt(i);
	                } else {
	                    node = (DefaultMutableTreeNode) dbNode2.getChildAt(i);
	                }
					user = (User) node.getUserObject();
					if (user != null) {
						comulnList = tabelMap.get(user.toString());
						if (comulnList != null) {
							renderTableFeild(node, user.toString(), comulnList, state);
						}
					}
					tpath = new TreePath(node);
					if(state == 1){
					    tree_1.expandPath(tpath);
	                    tree_1.collapsePath(tpath);
					} else {
					    tree_2.expandPath(tpath);
	                    tree_2.collapsePath(tpath);
					}
				}
			}
			
			JScrollBar sBar = null;
			if(state == 1){
			    sBar = jScrollPanel_1.getVerticalScrollBar(); // 得到JScrollPane中的JScrollBar
            } else {
                sBar = jScrollPanel_2.getVerticalScrollBar(); // 得到JScrollPane中的JScrollBar
            }
			sBar.setValue(sBar.getMaximum()); // 设置JScrollBar的位置到最后
			sBar.setValue(sBar.getMinimum()); // 设置JScrollBar的位置到最前
		}
	}

	// 获取所有表
	public List<String> getAllTable(DefaultMutableTreeNode node) {
		List<String> tableList = new ArrayList<String>();
		Enumeration<DefaultMutableTreeNode> children = node.children();
		User user = null;
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode child = children.nextElement();
			if (!child.isLeaf()) { // 是否叶子节点
				if (child.getLevel() == 1) { // 表
					user = (User) child.getUserObject();
					if (user != null) {
						tableList.add(user.toString());
					}
				}
			}
		}
		return tableList;
	}

	// 获取所有选中的表
	public List<String> getSelectedTable(int state) {
		List<String> tableList = new ArrayList<String>();
		TreePath[] treePaths = null;
		if(state == 1){
		    treePaths = tree_1.getSelectionPaths();
		} else {
		    treePaths = tree_2.getSelectionPaths();
		}
		if (treePaths != null) {
			DefaultMutableTreeNode node = null;
			User user = null;
			for (int i = 0; i < treePaths.length; i++) {
				node = (DefaultMutableTreeNode) treePaths[i].getLastPathComponent();
				if (node != null) {
					if (node.getLevel() == 1) { // 表
						user = (User) node.getUserObject();
						if (user != null) {
							tableList.add(user.toString());
						}
					}
				}
			}
		}
		return tableList;
	}
	
	// 指定的节点
    public DefaultMutableTreeNode getTreeNode(DefaultMutableTreeNode node, String tableName, String FieldName, int state) {
        DefaultMutableTreeNode result = null;
        Enumeration<DefaultMutableTreeNode> children = node.children();
        Enumeration<DefaultMutableTreeNode> children2 = null;
        User user = null;
        String title = "";
        DefaultMutableTreeNode child1 = null, child2 = null;
        while (children.hasMoreElements()) {
            child1 = children.nextElement();
            if (!child1.isLeaf() && child1.getLevel() == 1) { // 是否叶子节点 // 表
                user = (User) child1.getUserObject();
                if (user != null) {
                    title = user.toString();
                    if(title.equalsIgnoreCase(tableName)){
                        children2 = child1.children();
                        while (children2.hasMoreElements()) {
                            child2 = children.nextElement();
                            if (child1.isLeaf() && child1.getLevel() == 2) { // 是否叶子节点
                                user = (User) child1.getUserObject();
                                if (user != null) {
                                    title = user.toString();
                                    title = title.substring(0, title.indexOf(" "));
                                    if(title.equalsIgnoreCase(FieldName)){
                                        result = node;
                                        return result;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    // 指定的节点
    public DefaultMutableTreeNode getTreeNode(DefaultMutableTreeNode node, String tableName) {
        DefaultMutableTreeNode result = null;
        Enumeration<DefaultMutableTreeNode> children = node.children();
        User user = null;
        String title = "";
        DefaultMutableTreeNode child1 = null;
        while (children.hasMoreElements()) {
            child1 = children.nextElement();
            if (!child1.isLeaf() && child1.getLevel() == 1) { // 是否叶子节点 // 表
                user = (User) child1.getUserObject();
                if (user != null) {
                    title = user.toString();
                    if(title.equalsIgnoreCase(tableName)){
                        result = child1;
                        return result;
                    }
                }
            }
        }
        return result;
    }
    
	class AlertThread implements Runnable {
		public void run() {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "未检测到有效配置，数据库表加载失败", "提示", JOptionPane.ERROR_MESSAGE);
		}
	}

	class InputKeyListener implements KeyListener {
		public void keyTyped(KeyEvent e) { // 按下

		}

		public void keyPressed(KeyEvent e) {
		}

		public void keyReleased(KeyEvent e) { // 放开
			JTextField textFeild = (JTextField) e.getComponent();
			String name = textFeild.getName();
			//changeTips(name, textFeild.getText());
		}
	}

	class BtnActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String comm = e.getActionCommand();
			System.out.println(comm);
			if (comm.equals("查看表数据1")) {
				MainWindow.mainWindow.showTableDataPanel(chooseTable, chooseDb);
			} else if (comm.equals("查看表数据2")) {
                MainWindow.mainWindow.showTableDataPanel(chooseTable, chooseDb);
            } else if (comm.equals("对比全部表")) {
			    startCompare(true);
			} else if (comm.equals("对比选择的表")) {
				System.out.println("in....");
				startCompare(false);
			}
		}
	}
	
	public void startCompare(boolean isall) {
        List<String> tables1 = null;
        List<String> tables2 = null;
        if (isall) {
            tables1 = getAllTable(dbNode1);
            tables2 = getAllTable(dbNode2);
        } else {
            tables1 = getSelectedTable(1);
            tables2 = getSelectedTable(2);
        }
        
        List<String[]> tableList = getSameTableList(tables1, tables2);
        
        Map<String, List<DbTableColumn[]>> sameMap = new HashMap<String, List<DbTableColumn[]>>();
        List<DbTableColumn[]> sameList = null;
        if(tableList != null && tableList.size() > 0){
            String key = null, tableName1 = null, tableName2 = null;
            List<DbTableColumn> columnList1 = null;
            List<DbTableColumn> columnList2 = null;
            MysqlDbColumn com1 = null, com2 = null;
            int isSame = 0;
            
            // 第1个库为准
            for(int i = 0; i < tableList.size(); i ++){
                tableName1 = tableList.get(i)[0];
                tableName2 = tableList.get(i)[1];
                key = tableName1;
                sameList = new ArrayList<DbTableColumn[]>();
                
                if(tableName1 == null){
                    key = tableName2;
                    columnList2 = DbCache.mysqlDbColumnMap2.get(tableName2);
                    for(int k = 0; k < columnList2.size(); k ++){
                        com2 = (MysqlDbColumn) columnList2.get(k);
                        sameList.add(new DbTableColumn[]{ null, com2 });
                    }
                } else if(tableName2 == null){
                    columnList1 = DbCache.mysqlDbColumnMap1.get(tableName1);
                    for(int k = 0; k < columnList1.size(); k ++){
                        com1 = (MysqlDbColumn) columnList1.get(k);
                        sameList.add(new DbTableColumn[]{ com1, null });
                    }
                } else {
                    columnList1 = DbCache.mysqlDbColumnMap1.get(tableName1);
                    columnList2 = DbCache.mysqlDbColumnMap2.get(tableName2);
                    
                    com1 = null;
                    com2 = null;
                    if(columnList1 != null && columnList2 != null){
                        // 比较
                        for(int k = 0; k < columnList1.size(); k ++){
                            com1 = (MysqlDbColumn) columnList1.get(k);
                            isSame = 0;
                            for(int j = 0; j < columnList2.size(); j ++){
                                com2 = (MysqlDbColumn) columnList2.get(j);
                                if(com1.getColumnName().equalsIgnoreCase(com2.getColumnName())){
                                    isSame = 1;
                                    if(com1.getColumnType().equalsIgnoreCase(com2.getColumnType())
                                           && com1.getExtra().equalsIgnoreCase(com2.getExtra())
                                           && com1.getColumnKey().equalsIgnoreCase(com2.getColumnKey())
                                           && com1.getIsNullable().equalsIgnoreCase(com2.getIsNullable())
                                           && com1.isPrimaryKey() == com2.isPrimaryKey()
                                           && CommonUtils.excNullToObject(com1.getColumnDefault(), "").equals(CommonUtils.excNullToObject(com2.getColumnDefault(), ""))){
                                        
                                    } else {
                                        isSame = 2;
                                    }
                                    break;
                                }
                            }
                            // 判断
                            if(isSame != 1){
                                if(isSame == 0){
                                    com2 = null;
                                }
                                // 不相同，则标记
                                sameList.add(new DbTableColumn[]{ com1, com2 });
                            }
                        }
                        
                        // 比较
                        for(int k = 0; k < columnList2.size(); k ++){
                            isSame = 0;
                            com2 = (MysqlDbColumn) columnList2.get(k);
                            for(int j = 0; j < columnList1.size(); j ++){
                                com1 = (MysqlDbColumn) columnList1.get(j);
                                if(com1.getColumnName().equalsIgnoreCase(com2.getColumnName())){
                                    isSame = 1;
                                    if(com1.getColumnType().equalsIgnoreCase(com2.getColumnType())
                                           && com1.getExtra().equalsIgnoreCase(com2.getExtra())
                                           && com1.getColumnKey().equalsIgnoreCase(com2.getColumnKey())
                                           && com1.getIsNullable().equalsIgnoreCase(com2.getIsNullable())
                                           && com1.isPrimaryKey() == com2.isPrimaryKey()
                                           && CommonUtils.excNullToObject(com1.getColumnDefault(), "").equals(CommonUtils.excNullToObject(com2.getColumnDefault(), ""))){
                                    } else {
                                        isSame = 2;
                                    }
                                    break;
                                }
                            }
                            // 判断
                            if(isSame != 1){
                                if(isSame == 0){
                                    com1 = null;
                                }
                                // 不相同，则标记
                                sameList.add(new DbTableColumn[]{ com1, com2 });
                            }
                        }
                    }
                }
                
                sameMap.put(key, sameList);
            }
            
        }
        
        // 完成
        boolean show = !showBox.isSelected();
        new CreateThread(sameMap, show).start();
        
    }

	public List<String[]> getSameTableList(List<String> tables1, List<String> tables2){
	    List<String[]>  tableList = new ArrayList<String[]> ();
	    String name1 = null, name2 = null;
	    boolean has = false;
	    for(int i = 0; i < tables1.size(); i ++){
	        has = false;
	        name1 = tables1.get(i);
	        for(int j = 0; j < tables2.size(); j ++){
	            name2 = tables2.get(j);
	            if(name1.equals(name2)){
	                has = true;
	                break;
	            }
	        }
	        if(!has){
	            name2 = null;
	        }
	        tableList.add(new String[]{ name1, name2 });
	        name1 = name2 = null;
	    }
	    
	    for(int i = 0; i < tables2.size(); i ++){
            has = false;
            name2 = tables2.get(i);
            for(int j = 0; j < tables1.size(); j ++){
                name1 = tables1.get(j);
                if(name2.equals(name1)){
                    has = true;
                    break;
                }
            }
            if(!has){
                name1 = null;
            }
            tableList.add(new String[]{ name1, name2 });
            name1 = name2 = null;
        }
	    
	    return tableList;
	}
	
	class User {
		private String name;

		public User(String n) {
			name = n;
		}

		public String toString() {
			return name;
		}
	}

}
