package com.yisin.dbc.main;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.yinsin.utils.CommonUtils;
import com.yinsin.utils.DateUtils;
import com.yinsin.utils.SystemUtils;
import com.yisin.dbc.db.DBHelper;
import com.yisin.dbc.entity.DbTableColumn;
import com.yisin.dbc.entity.MysqlDbColumn;
import com.yisin.dbc.form.MainWindow;
import com.yisin.dbc.util.Utililies;

public class CreateThread extends Thread {
	Map<String, List<DbTableColumn[]>> sameMap = null;
	boolean isshow = true;
	String dir = "template/";

	public CreateThread(Map<String, List<DbTableColumn[]>> sameMap, boolean show) {
		this.sameMap = sameMap;
		this.isshow = show;
	}

	public void run() {
	    MainWindow.mainWindow.showProcessPanel();
		if(sameMap != null && sameMap.size() > 0){
    		//TODO 
    		try {
                String path = create();
                
                // 执行完成
                MainWindow.mainWindow.hideProcessPanel();
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(MainWindow.mainWindow, "恭喜，比较完成，已生成对比报告。", "成功", JOptionPane.INFORMATION_MESSAGE);
                
                try {
                    Desktop.getDesktop().open(new File(path)); 
                } catch (Exception e) {
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                // 执行失败
                MainWindow.mainWindow.hideProcessPanel();
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(MainWindow.mainWindow, "失败：" + e1.getMessage(), "失败", JOptionPane.ERROR_MESSAGE);
            }
		} else {
		    // 执行失败
            MainWindow.mainWindow.hideProcessPanel();
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(MainWindow.mainWindow, "恭喜，两个库完全一致", "成功", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	 public String getTlpPath(String name){
        String fix = "";
        return dir + fix + name;
    }
	
	public String create() throws Exception{
	    String tableName = "";
	    List<DbTableColumn[]> coluList = null;
	    DbTableColumn[] colums = null;
	    StringBuilder resultHtml = new StringBuilder("");
	    StringBuilder sb = null;
	    String head = "", table = "", html= "", className = "", columName = "", 
	            trtd1 = "", trtd2 = "", trtd3 = "", trtd4 = "", trtd5 = "", trtd6 = "", trtd7 = "", trtd8 = "";
	    MysqlDbColumn colum1 = null, colum2 = null;
	    boolean issame = false;
	    int nullIndex = 0;
	    Map<String, String> tfMap = new HashMap<String, String>();
	    for(Map.Entry<String, List<DbTableColumn[]>> listEntry : sameMap.entrySet()){
	        sb = new StringBuilder("");
	        tableName = listEntry.getKey();
    	    // 1、头部
    	    head = Utililies.readResourceFile(getTlpPath("table-head.tpl"));
    	    head = Utililies.parseTemplate(head, "tableName", tableName);
    	    
    	    sb.append(head);
    	    
    	    coluList = listEntry.getValue();
    	    
    	    if(coluList.size() > 0){
    	        // 
    	        issame = false;
    	        nullIndex = 0;
    	        
    	        trtd1 = Utililies.readResourceFile(getTlpPath("table-7td.tpl"));
                trtd1 = Utililies.parseTemplate(trtd1, "class", className);
                
                sb.append(trtd1);
                
    	        className = "";
        	    for (int i = 0, k = coluList.size(); i < k; i++) {
        	        colums = coluList.get(i);
        	        colum1 = colum2 = null;
        	        
        	        if(colums[0] != null){
        	            colum1 = (MysqlDbColumn) colums[0];
        	        } else {
        	            nullIndex = 1;
        	        }
        	        
        	        if(colums[1] != null){
                        colum2 = (MysqlDbColumn) colums[1];
                    } else {
                        nullIndex = 2;
                    }
        	        
        	        
        	        if((i + 1) % 2 == 0){
        	            className = "even";
        	        } else {
        	            className = "";
        	        }
        	        
        	        columName = colum1 != null ? colum1.getColumnName() : colum2.getColumnName();
        	        
        	        if(!tfMap.containsKey(tableName + columName)){
        	            tfMap.put(tableName + columName, columName);
        	        } else {
        	            continue;
        	        }
        	        
        	        trtd2 = Utililies.readResourceFile(getTlpPath("table-tdvalue.tpl"));
        	        
        	        trtd2 = Utililies.parseTemplate(trtd2, "class", className);
        	        trtd2 = Utililies.parseTemplate(trtd2, "columnName", columName);
        	        
        	        trtd2 = Utililies.parseTemplate(trtd2, "dbname1", DBHelper.connName1 + "/" + DBHelper.schema1);
        	        trtd2 = Utililies.parseTemplate(trtd2, "dbname2", DBHelper.connName2 + "/" + DBHelper.schema2);
        	        
        	        trtd2 = Utililies.parseTemplate(trtd2, "dataType1", colum1 != null ? colum1.getDataType() : "--");
        	        trtd2 = Utililies.parseTemplate(trtd2, "columnComment1", colum1 != null ? colum1.getColumnComment() : "--");
        	        trtd2 = Utililies.parseTemplate(trtd2, "columnType1", colum1 != null ? colum1.getColumnType() : "--");
        	        trtd2 = Utililies.parseTemplate(trtd2, "isNullable1", colum1 != null ? colum1.getIsNullable() : "--");
        	        trtd2 = Utililies.parseTemplate(trtd2, "columnKey1", colum1 != null ? colum1.getColumnKey() : "--");
        	        trtd2 = Utililies.parseTemplate(trtd2, "columnDefault1", colum1 != null ? (colum1.getColumnDefault() == null ? "" : colum1.getColumnDefault().toString()) : "--");
        	        trtd2 = Utililies.parseTemplate(trtd2, "extra1", colum1 != null ? colum1.getExtra() : "--");
        	        
        	        trtd2 = Utililies.parseTemplate(trtd2, "dataType2", colum2 != null ? colum2.getDataType() : "--");
        	        trtd2 = Utililies.parseTemplate(trtd2, "columnComment2", colum2 != null ? colum2.getColumnComment() : "--");
        	        trtd2 = Utililies.parseTemplate(trtd2, "columnType2", colum2 != null ? colum2.getColumnType() : "--");
        	        trtd2 = Utililies.parseTemplate(trtd2, "isNullable2", colum2 != null ? colum2.getIsNullable() : "--");
        	        trtd2 = Utililies.parseTemplate(trtd2, "columnKey2", colum2 != null ? colum2.getColumnKey() : "--");
        	        trtd2 = Utililies.parseTemplate(trtd2, "columnDefault2", colum2 != null ? (colum2.getColumnDefault() == null ? "" : colum2.getColumnDefault().toString()) : "--");
        	        trtd2 = Utililies.parseTemplate(trtd2, "extra2", colum2 != null ? CommonUtils.excNullToString(colum2.getExtra()).equalsIgnoreCase("auto_increment") ? "YES" : "NO" : "--");
        	       
        	        // 1、数据类型
        	        if(colum1 != null && colum2 != null && colum1.getDataType().equalsIgnoreCase(colum2.getDataType())){
                        trtd2 = Utililies.parseTemplate(trtd2, "same1", "");
                    } else {
                        trtd2 = Utililies.parseTemplate(trtd2, "same1", "same");
                    }
        	        
         	        // 2、描述
        	        if(colum1 != null && colum2 != null && colum1.getColumnComment().equalsIgnoreCase(colum2.getColumnComment())){
        	            trtd2 = Utililies.parseTemplate(trtd2, "same2", "");
                    } else {
                        trtd2 = Utililies.parseTemplate(trtd2, "same2", "same");
                    }
        	        
        	        // 3、字段类型
        	        if(colum1 != null && colum2 != null && colum1.getColumnType().equalsIgnoreCase(colum2.getColumnType())){
        	            trtd2 = Utililies.parseTemplate(trtd2, "same3", "");
                    } else {
                        trtd2 = Utililies.parseTemplate(trtd2, "same3", "same");
                    }
        	        
        	        // 4、可否为空
        	        if(colum1 != null && colum2 != null && colum1.getIsNullable().equalsIgnoreCase(colum2.getIsNullable())){
        	            trtd2 = Utililies.parseTemplate(trtd2, "same4", "");
                    } else {
                        trtd2 = Utililies.parseTemplate(trtd2, "same4", "same");
                    }
        	        
        	        // 5、PK/FK
        	        if(colum1 != null && colum2 != null && colum1.getColumnKey().equalsIgnoreCase(colum2.getColumnKey())){
        	            trtd2 = Utililies.parseTemplate(trtd2, "same5", "");
                    } else {
                        trtd2 = Utililies.parseTemplate(trtd2, "same5", "same");
                    }
        	        
        	        // 6、缺省值
        	        if(colum1 != null && colum2 != null){
                        if(colum1.getColumnDefault() == null && colum2.getColumnDefault() == null){
                            trtd2 = Utililies.parseTemplate(trtd2, "same6", "");
                        } else if(colum1.getColumnDefault() != null && colum2.getColumnDefault() != null
                                && colum1.getColumnDefault().toString().equalsIgnoreCase(colum2.getColumnDefault().toString())){
                            trtd2 = Utililies.parseTemplate(trtd2, "same6", "");
                        } else {
                            trtd2 = Utililies.parseTemplate(trtd2, "same6", "same");
                        }
                    } else {
                        trtd2 = Utililies.parseTemplate(trtd2, "same6", "same");
                    }
        	        
        	        // 7、是否自增长
        	        if(colum1 != null && colum2 != null && colum1.getExtra().equalsIgnoreCase(colum2.getExtra())){
        	            trtd2 = Utililies.parseTemplate(trtd2, "same7", "");
                    } else {
                        trtd2 = Utililies.parseTemplate(trtd2, "same7", "same");
                    }
        	        
                    sb.append(trtd2).append(trtd3);
                    
                }
    	    } else {
    	        issame = true;
    	        // 完全一致
    	        if(isshow){
    	            trtd1 = Utililies.readResourceFile(getTlpPath("table-ok.tpl"));
    	            sb.append(trtd1);
    	        }
    	    }
    	    
    	    if(!issame || isshow){
    	        if(!issame){
    	            trtd1 = Utililies.readResourceFile(getTlpPath("table-no.tpl"));
    	            if(nullIndex > 0){
    	                if(nullIndex == 1){
    	                    trtd1 = Utililies.parseTemplate(trtd1, "text", DBHelper.connName1 + "/" + DBHelper.schema1 + "【" + tableName + "】表或字段缺失");
    	                } else if(nullIndex == 2){
    	                    trtd1 = Utililies.parseTemplate(trtd1, "text", DBHelper.connName2 + "/" + DBHelper.schema2 + "【" + tableName + "】表或字段缺失");
    	                }
    	            } else {
    	                trtd1 = Utililies.parseTemplate(trtd1, "text", "表结构不一致");
    	            }
                    sb.append(trtd1);
                }
    	        
    	        table = Utililies.readResourceFile(getTlpPath("table.tpl"));
    	        table = Utililies.parseTemplate(table, "html", sb.toString());
    	        resultHtml.append(table);
    	    }
	    }
	    
	    // 输出
	    html = Utililies.readResourceFile(getTlpPath("html.tpl"));
	    html = Utililies.parseTemplate(html, "html", resultHtml.toString());
        
	    String filePath = SystemUtils.getUserDir() + "\\" + 
	            DBHelper.schema1 + "-" + DBHelper.schema2 + "-" + 
	            DateUtils.format(new Date(), "yyyyMMddHHmmss") + ".html";
	    System.out.println(filePath);
	    Utililies.writeContentToFile(filePath, html);
	    
	    return filePath;
	}
	
	
	
	
	
}
