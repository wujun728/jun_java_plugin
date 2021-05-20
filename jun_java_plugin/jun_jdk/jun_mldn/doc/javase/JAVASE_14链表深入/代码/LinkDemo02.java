class Link{
	class Node{
		private String name ;	// 保存节点的名字
		private Node next ;		// 保存下一个节点
		public Node(String name){
			this.name = name ;
		}
		public String getName(){
			return this.name ;
		}
		public void addNode(Node newNode){
			if(this.next==null){	// 后面没有东西
				this.next = newNode ;
			}else{
				this.next.addNode(newNode) ;	// 向下继续查
			}
		}
		public void printNode(){
			System.out.print(this.name + " --> " ) ;
			if(this.next!=null){
				this.next.printNode() ;	// 向下继续列出
			}
		}
		public boolean searchNode(String name){
			if(this.name.equals(name)){
				return true ;
			}else{
				if(this.next!=null){
					return this.next.searchNode(name) ;
				}else{
					return false ;
				}
			}
		}
		public void deleteNode(Node preNode,String name){
			if(this.name.equals(name)){
				preNode.next = this.next ;
			}else{
				this.next.deleteNode(this,name) ;
			}
		}
	};
	private Node root ;	// 要定义出根节点
	public void add(String name){
		Node newNode = new Node(name) ;
		if(this.root==null){	// 没有根节点，则把第一个作为根节点
			this.root = newNode ;
		}else{
			this.root.addNode(newNode) ;
		}
	}
	public void print(){
		if(this.root!=null){
			this.root.printNode() ;
		}
	}
	public boolean search(String name){	// 指定查找的名字
		if(this.root!=null){
			return this.root.searchNode(name) ;
		}else{
			return false ;
		}
	}
	public void delete(String name){
		if(this.search(name)){	// 判断此节点是否存在
			if(this.root.name.equals(name)){
				if(this.root.next!=null){
					this.root = this.root.next ;	// 改变根节点
				}else{
					this.root = null ;	// 取消
				}
			}else{
				if(this.root.next!=null){
					this.root.next.deleteNode(root,name) ;
				}
			}
		}
	}
};
public class LinkDemo02{
	public static void main(String args[]){
		Link link = new Link() ;
		link.add("根节点") ;
		link.add("第一节点") ;
		link.add("第二节点") ;
		link.add("第三节点") ;
		link.add("第四节点") ;
		link.add("第五节点") ;
		link.print() ;
		System.out.println() ;
		// System.out.println(link.search("第x节点")) ;
		link.delete("第四节点") ;
		link.delete("根节点") ;
		link.print() ;
	}
};