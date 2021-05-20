class Link{
	class Node{
		private String name ;
		private Node next ;	// 单向链表，每个节点指向下一个节点
		public Node(String name){
			this.name = name ;		// 通过构造方法为name属性赋值
		}
		public void addNode(Node newNode){	// 增加节点
			if(this.next==null){
				this.next = newNode ;	// 保存节点
			}else{
				this.next.addNode(newNode) ;	// 继续向下查找
			}
		}
		public void printNode(){	// 输出节点
			System.out.println(this.name) ;
			if(this.next!=null){	// 此节点之后还存在其他的节点
				this.next.printNode() ;
			}
		}
	};
	private Node root ;	// 链表的头
	public void add(String name){	// 增加节点
		Node newNode = new Node(name) ;	// 定义一个新的节点
		/*
			如果是第一个节点，则肯定是根节点，
			如果是第二个节点，则肯定放在根节点next中
			如果是第三个节点，则肯定放在第二个节点的next中
		*/ 
		if(this.root == null){
			this.root = newNode ;	// 将第一个节点设置成根节点
		}else{
			// 肯定要放在最后一个节点之后
			// 通过节点.next来不断的判断
			this.root.addNode(newNode) ; 
		}
	}
	public void print(){
		if(this.root!=null){	// 如果根节点为空了，则没有任何内容
			this.root.printNode() ;
		}
	}
};
public class LinkDemo{
	public static void main(String args[]){
		Link link = new Link() ;
		link.add("A") ;
		link.add("B") ;
		link.add("C") ;
		link.add("D") ;
		link.add("E") ;
		link.print() ;
	}
};