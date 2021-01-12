import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

//森林状的关系图

public class JTreeDemo extends JFrame{
	JTextField jtfInfo; //文本域,用于显示点击的节点名称
	
	public JTreeDemo(){
		super("森林状的关系图");  //调用父类构造函数
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("设置"); //生成根节点
		DefaultMutableTreeNode node1=new DefaultMutableTreeNode("常规"); //生成节点一
		node1.add(new DefaultMutableTreeNode("默认路径")); //增加新节点到节点一上
		node1.add(new DefaultMutableTreeNode("保存选项"));
		root.add(node1);  //增加节点一到根节点上
    	root.add(new DefaultMutableTreeNode("界面"));    
    	root.add(new DefaultMutableTreeNode("提示声音"));  
    	root.add(new DefaultMutableTreeNode("打印"));    
    	
		JTree tree = new JTree(root);  //得到JTree的实例			
   	    DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)tree.getCellRenderer(); //得到JTree的Renderer
    	renderer.setLeafIcon(null); //设置叶子节点图标为空
    	renderer.setClosedIcon(null);  //设置关闭节点的图标为空
    	renderer.setOpenIcon(null); //设置打开节点的图标为空
    	
    	tree.addTreeSelectionListener(new TreeSelectionListener() {  //选择节点的事件处理
        public void valueChanged(TreeSelectionEvent evt) {
            TreePath path = evt.getPath();  //得到选择路径
            String info=path.getLastPathComponent().toString(); //得到选择的节点名称
			jtfInfo.setText(info);  //在文本域中显示名称
        }
   		});


		JScrollPane jsp=new JScrollPane(tree); //增加JTree到滚动窗格
		jtfInfo=new JTextField(); //实例化文本域
		jtfInfo.setEditable(false); //文本域不可编辑
		getContentPane().add(jsp,BorderLayout.CENTER);  //增加组件到容器上
		getContentPane().add(jtfInfo,BorderLayout.SOUTH);
		
				
		setSize(250,200);  //设置窗口尺寸
		setVisible(true);  //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}
	
	public static void main(String[] args){
		new JTreeDemo();
	}
}