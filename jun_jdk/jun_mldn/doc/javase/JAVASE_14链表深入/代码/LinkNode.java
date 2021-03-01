class Node{
	private String name;
	private Node nextNode;
	
	public Node(){
		this.name = "表头";
	}
	public Node(String name){
		this.setName(name);
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setNextNode(Node nextNode){
		this.nextNode = nextNode;
	}
	
	public String getName(){
		return name;
	}
	
	public Node getNextNode(){
		return nextNode;
	}
}

class LinkNode{
	private static Node node;
	private static Node firstNode = new Node();	
	private static LinkNode linkNode = new LinkNode(firstNode);
	
	private LinkNode(Node node){
		this.setNode(node);
	}
	
	public static LinkNode getLinkNode(){	
		return linkNode;
	}
	
	public void setNode(Node node){
		this.node = node;
	}
	
	public Node getNode(){
		return node;
	}
	
	public Node getFirstNode(){
		return firstNode;
	}
	
	public void addNode(String name){
		Node node = new Node(name);
		this.getNode().setNextNode(node);
		this.setNode(node);	
	}
	
	public boolean delNode(String name){
		Node temp;
		Node node = this.getFirstNode();
		while(node.getNextNode()!= null){
			temp = node;
			if(node.getName().equals(name)){
				node = node.getNextNode();
				temp.setNextNode(temp.getNextNode().getNextNode());				
				break;
			}
		return true;		
		}
		return false;
	}
	
	
	public String selectNode(String name){
		Node node = this.getFirstNode();
		while(node.getNextNode()!= null){
			if(!node.getName().equals(name)){
				node = node.getNextNode();
				continue;
			}
			return "查找内容在此表中！";		
		}
		return "没有要查找的内容！！";
	}
	
	public String displayLinkNode(){
		String temp = "链表:";
		Node node = this.getFirstNode();
		while(node.getNextNode() != null){
			temp += node.getName();
			temp += "-->";
			node = node.getNextNode();
		}
		return temp + "表尾";
	}
	
	public static void main(String args[]){		
		LinkNode ln = LinkNode.getLinkNode();
		ln.addNode("girl");
		ln.addNode("boy");
		ln.addNode("ggg");
		ln.addNode("hhhh");
		ln.delNode("girl");
		System.out.println(ln.displayLinkNode());
		System.out.println(ln.selectNode("bo"));
	}
}