package com.jun.plugin.designpatterns.结构型模式.组合模式;

public class Test {

    /**
     * 将多个对象组合在一起进行操作
     * 
     */
    public static void main(String[] args) {
        
        //树A，当作顶级节点
        Tree tree = new Tree("A");
        
        //节点B
        TreeNode nodeB = new TreeNode("B");
        //节点C
        TreeNode nodeC = new TreeNode("C");
        
        //节点C 作为 节点B 的子节点
        nodeB.add(nodeC);
        
        //将节点B 加入到树中，作为 树A的子节点
        tree.root.add(nodeB);
        
        //输出子节点名称
        System.out.println(nodeB.getChildren().nextElement().getName());
        
        System.out.println("构造tree完毕.");
    }

}
