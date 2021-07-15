package com.jun.plugin.demo;
//
//
//import org.w3c.dom.*;
//import org.w3c.dom.traversal.*;
//import org.apache.xerces.parsers.*;
//import org.xml.sax.*;
//import javax.swing.*;
//import javax.swing.tree.*;
//import javax.swing.event.*;
//import java.io.*;
//
//public class DOMTreeWalker implements TreeModel {
//	TreeWalker walker;
//
//	public DOMTreeWalker(TreeWalker walker) {
//		this.walker = walker;
//	}
//
//	public DOMTreeWalker(Document document) {
//		DocumentTraversal dt = (DocumentTraversal) document;
//		walker =
//			dt.createTreeWalker(document, NodeFilter.SHOW_ALL, null, false);
//	}
//
//	public DOMTreeWalker(Element element) {
//		DocumentTraversal dt = (DocumentTraversal) element.getOwnerDocument();
//		walker = dt.createTreeWalker(element, NodeFilter.SHOW_ALL, null, false);
//	}
//
//	// �������ĸ� 
//	public Object getRoot() {
//		return walker.getRoot();
//	}
//
//	// �ж��Ƿ�ΪҶ��
//	public boolean isLeaf(Object node) {
//		walker.setCurrentNode((Node) node);  
//		Node child = walker.firstChild(); 
//		return (child == null); 
//	}
//
//	// ����ýڵ���ӽڵ���Ŀ
//	public int getChildCount(Object node) {
//		walker.setCurrentNode((Node) node); 
//		int numkids = 0;
//		Node child = walker.firstChild(); 
//		while (child != null) { 
//			numkids++;
//			child = walker.nextSibling(); // ��һ������
//		}
//		return numkids; 
//	}
//
//	// ���ؽڵ��ָ���ӽڵ�
//	public Object getChild(Object parent, int index) {
//		walker.setCurrentNode((Node) parent);
//		Node child = walker.firstChild();
//		while (index-- > 0)
//			child = walker.nextSibling();
//		return child;
//	}
//
//	// �����ӽڵ��ڸ��ڵ㵱�е�λ��
//	public int getIndexOfChild(Object parent, Object child) {
//		walker.setCurrentNode((Node) parent);
//		int index = 0;
//		Node c = walker.firstChild();
//		while ((c != child) && (c != null)) { 
//			index++;
//			c = walker.nextSibling();
//		}
//		return index; 
//	}
//
//	// �����޸ĵ���ʹ�õķ���������û��ʵ��
//	public void valueForPathChanged(TreePath path, Object newvalue) {
//	}
//
//	// ��Ϊ����ʵ�ֵ�DOM Tree�ǲ����޸ĵģ���������κ��¼�
//	public void addTreeModelListener(TreeModelListener l) {
//	}
//	public void removeTreeModelListener(TreeModelListener l) {
//	}
//
//	public static void main(String[] args) throws IOException, SAXException {
//		//������ڽ��� DOM tree��Xerces parser�Ķ���
//		// �����õ��� Apache Xerces APIs
//		DOMParser parser = new org.apache.xerces.parsers.DOMParser();
//
//		// ���Ҫ������xml�ļ�
//		Reader in = new BufferedReader(new FileReader("web.xml"));
//		InputSource input = new org.xml.sax.InputSource(in);
//
//	    // ��ʼ����
//		parser.parse(input);
//
//		// �ӷ������л�� DOM Document
//		Document document = parser.getDocument();
//		DocumentTraversal traversal = (DocumentTraversal) document;
//
//		// ����ֻ���пո�Ľڵ�
//		NodeFilter filter = new NodeFilter() {
//			public short acceptNode(Node n) {
//				if (n.getNodeType() == Node.TEXT_NODE) {
//					if (((Text) n).getData().trim().length() == 0)
//						return NodeFilter.FILTER_REJECT;
//				}
//				return NodeFilter.FILTER_ACCEPT;
//			}
//		};
//
//		// ������ʾ���нڵ㵫����ʾ����
//		int whatToShow = NodeFilter.SHOW_ALL & ~NodeFilter.SHOW_COMMENT;
//
//		TreeWalker walker =
//			traversal.createTreeWalker(document, whatToShow, filter, false);
//		JTree tree = new JTree(new DOMTreeWalker(walker));
//
//		// ����һ�����ں͹�����
//		JFrame frame = new JFrame("DOMTreeWalker Demo");
//		frame.getContentPane().add(new JScrollPane(tree));
//		frame.setSize(500, 250);
//		frame.setVisible(true);
//	}
//}
