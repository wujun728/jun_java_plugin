package com.jun.plugin.demo;
//TreeNodeDemo.java 

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class JTreeDemo extends JFrame {
	protected JTree m_tree;
	protected JTextField m_display;

	public JTreeDemo() {
		super("SNMP Tree [OID]");
        // ����5�������û��������� 
        Object[] nodes = new Object[5]; 
		//�������ĸ�ڵ㣨��һ��ڵ㣩 
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(new OidNode(1,"ISO"));
		DefaultMutableTreeNode parent = top;
		//��������ڵ���ӽڵ㣨�ڶ���ڵ㣩 
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(new OidNode(1,"standard"));
		parent.add(node);
		node = new DefaultMutableTreeNode(new OidNode(2, "member-body"));
		parent.add(node);
		node = new DefaultMutableTreeNode(new OidNode(3, "org"));
		parent.add(node);
		parent = node;
		//�����ڶ���ڵ���ӽڵ㣨�����ڵ㣩 
		node = new DefaultMutableTreeNode(new OidNode(1, "dod"));
		parent.add(node);
		parent = node;
		//���������ڵ���ӽڵ㣨���Ĳ�ڵ㣩 
		node = new DefaultMutableTreeNode(new OidNode(1, "internet"));
		parent.add(node);
		parent = node;
		//�������Ĳ�ڵ���ӽڵ㣨�����ڵ㣩 
		node = new DefaultMutableTreeNode(new OidNode(1, "directory"));
		parent.add(node);
		node = new DefaultMutableTreeNode(new OidNode(2, "mgmt"));
		parent.add(node);
		node.add(new DefaultMutableTreeNode(new OidNode(1, "mib-2")));
		node = new DefaultMutableTreeNode(new OidNode(3, "experimental"));
		parent.add(node);
		node = new DefaultMutableTreeNode(new OidNode(4, "private"));
		node.add(new DefaultMutableTreeNode(new OidNode(1, "enterprises")));
		parent.add(node);
		node = new DefaultMutableTreeNode(new OidNode(5, "security"));
		parent.add(node);
		node = new DefaultMutableTreeNode(new OidNode(6, "snmpV2"));
		parent.add(node);
		node = new DefaultMutableTreeNode(new OidNode(7, "mail"));
		parent.add(node);

		//�����ĸ�ڵ㹹�������    
        m_tree =new JTree(top) 
        { 
             //�趨JTree�Ĺ�����ʾ 
             public String getToolTipText(MouseEvent e) {
				//��ȡ������������ѡ��λ�õ�����·��
				TreePath path = m_tree.getPathForLocation(e.getX(), e.getY());
				//������û��ѡ�����ڵ��ϣ���ֱ�ӷ���
				if (path == null)
					return null;
				//��ȡ��ǰ��·���Ĵ洢�����ڵ��������飨�Ӹ�ڵ㵽��ǰ���ڵ㣩
				Object[] nodes = path.getPath();
				String oid = "";
				for (int k = 0; k < nodes.length; k++) {
					//ǿ��ת�������ڵ�
					DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) nodes[k];
					//�����ڵ��л�ȡ���û�����OidNode��ʵ��
					OidNode nd = (OidNode) treenode.getUserObject();
					//���û�����Ķ����ʶ�����ۻ�
					oid += "." + nd.getId();
				}
				return oid;
			}
		}; 
		m_tree.setToolTipText(m_tree.getToolTipText());
        //ToolTipManager.sharedInstance().registerComponent(m_tree); 
        
		//��ʾJTree���
		m_tree.setShowsRootHandles(true);
		//ʹJTree��������Windows�ļ���������ֱ�������ߡ�
		m_tree.putClientProperty("JTree.lineStyle", "Angled");
		//�����������ڵ��ǲ��ɱ༭��
		m_tree.setEditable(false);
		//��Ӽ�����������¼�
		m_tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				tvwTreeView_mouseClicked(e);
			}
		});
		//����������뵽��������� 
		JScrollPane s = new JScrollPane(m_tree);
		getContentPane().add(s, BorderLayout.CENTER);
		m_display = new JTextField();
		m_display.setEditable(false);
		getContentPane().add(m_display, BorderLayout.SOUTH);
		//��ʾ��ܴ��� 
		setSize(400, 300);
		setVisible(true);
	}

	//�������������굥���¼� 
	private void tvwTreeView_mouseClicked(MouseEvent e) {
		//��ȡ������������ѡ��λ�õ�����·�� 
		TreePath path = m_tree.getPathForLocation(e.getX(), e.getY());
		//������û��ѡ�����ڵ��ϣ���ֱ�ӷ��� 
		if (path == null)
			return;
		//��ȡ��ǰ��·���Ĵ洢�����ڵ��������飨�Ӹ�ڵ㵽��ǰ���ڵ㣩 
		Object[] nodes = path.getPath();
		String oid = "";
		for (int k = 0; k < nodes.length; k++) {
			//ǿ��ת�������ڵ� 
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodes[k];
			//�����ڵ��л�ȡ���û�����OidNode��ʵ�� 
			OidNode nd = (OidNode) node.getUserObject();
			//���û�����Ķ�����ƽ����ۻ� 
			oid += "." + nd.getName();
		}
		m_display.setText(oid);
	}
	
	public static void main(String argv[]) {
		JTreeDemo frame = new JTreeDemo();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}

//�����û������ࣨUserObject)�����ڷ�װSNMP��OID 
class OidNode {
	//�����ʶ 
	private int m_id;
	//������� 
	private String m_name;
	//���췽�� 
	public OidNode(int id, String name) {
		m_id = id;
		m_name = name;
	}
	//��ȡ�����ʶ 
	public int getId() {
		return m_id;
	}
	//�õ�������� 
	public String getName() {
		return m_name;
	}
	//�û�������ַ������������д��Object.toString() 
	public String toString() {
		return m_name;
	}
}