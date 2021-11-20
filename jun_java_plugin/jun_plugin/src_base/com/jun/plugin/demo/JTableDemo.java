package com.jun.plugin.demo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class JTableDemo implements TableModelListener
{
    JTable table = null;
    MyTable mt = null;
    JLabel label = null; //��ʾ�޸��ֶ�λ��
    
    public JTableDemo() {
       	JFrame f = new JFrame();
       	mt=new MyTable();
       	// ����JTable�¼�������
    	mt.addTableModelListener(this);
       	table=new JTable(mt);
       	// ������Ͽ�
       	JComboBox c = new JComboBox();
       	// ����Ͽ�������Ŀ
       	c.addItem("����");
       	c.addItem("�Ϻ�");
       	c.addItem("����");
       	// ������Ͽ���Ϊtable�ĵ�һ��ֵ��ѡ��
       	table.getColumnModel().
		   getColumn(1).setCellEditor(new DefaultCellEditor(c));    
       	// �趨table����ʾ�Ĵ�С
       	table.setPreferredScrollableViewportSize(new Dimension(550, 40));
       	// ��table���ӹ�����
        JScrollPane s = new JScrollPane(table);    
        label = new JLabel("�޸��ֶ�λ�ã�");
        f.getContentPane().add(s, BorderLayout.CENTER);
        f.getContentPane().add(label, BorderLayout.SOUTH);
	    f.setTitle("TableEventHandle");
        f.pack();
        f.setVisible(true);
        
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void tableChanged(TableModelEvent e)
    {
    	int row = e.getFirstRow();  // �õ����޸ĵ��У���һ��)������
        int column = e.getColumn(); // �õ����޸ĵ�����
        label.setText("�޸��ֶ�λ�ã�"+(row+1)+" �� "+(column+1)+" ��");
        //  �õ���row�е�6�е�ֵ���������Ƿ�����
        boolean cheat =((Boolean)(mt.getValueAt(row,6))).booleanValue();
        int grade1=((Integer)(mt.getValueAt(row,2))).intValue();
        int grade2=((Integer)(mt.getValueAt(row,3))).intValue();
	    int total = grade1+grade2;
    	if(cheat)        
    	{   //  ������ף����ܷ�����
    		if(total > 120)	
    			mt.mySetValueAt(new Integer(119),row,4);
    		else
    			mt.mySetValueAt(new Integer(total),row,4);
    		mt.mySetValueAt(new Boolean(false),row,5);
    	}
    	else
    	{
    		if(total > 120)	 // �жϸ�ѧ���Ƿ񼰸�
    			mt.mySetValueAt(new Boolean(true),row,5);
    		else
    			mt.mySetValueAt(new Boolean(false),row,5);   
    		
    		mt.mySetValueAt(new Integer(total),row,4);
    	}
    	table.repaint();
    }
	
    public static void main(String args[]) {
        new JTableDemo();
    }
}

class MyTable extends AbstractTableModel {
    Object[][] p = {
    {"С��", "����",new Integer(66), new Integer(32), new Integer(98),
      new Boolean(false),new Boolean(false)},
    {"С��", "�Ϻ�",new Integer(85), new Integer(69), new Integer(154),
      new Boolean(true),new Boolean(false)},
	{"С��", "����",new Integer(88), new Integer(70), new Integer(158),
      new Boolean(false),new Boolean(false)}};
    String[] n = {"����","��ס��","����","��ѧ","�ܷ�","����","����"};

    // ����������
    public int getColumnCount() { 
        return n.length;
    }
    
    // ����������
    public int getRowCount() {
        return p.length;
    }
    
    // ��������
    public String getColumnName(int col) {
        return n[col];
    }
    
    // ����table��һ���ֶΣ���û�������б����򷵻�Ĭ��ֵ,����ΪA,B,C,...Z,AA,AB,..;
    // ���޴�column,�򷵻�һ���յ�String
    public Object getValueAt(int row, int col) {
        return p[row][col];
    }
    
    // �����ֶ�������͵������
	public Class getColumnClass(int c) {
        	return getValueAt(0, c).getClass();
    }
	
	// ����ָ���ֶ��Ƿ���޸�
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;	
    }
    
	// ��ָ���ֶθ�ֵ����֪ͨtable���޸�
	public void setValueAt(Object value, int row, int col) {
        	p[row][col] = value;
        	fireTableCellUpdated(row, col);
    }
    
	// �Զ����޸�ĳָ���ֶ�
    public void mySetValueAt(Object value, int row, int col) {
    	p[row][col] = value;
    }
}
