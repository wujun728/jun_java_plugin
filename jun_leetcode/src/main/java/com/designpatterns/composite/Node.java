package com.designpatterns.composite;


/**
 * 将文件与目录统一看作是一类节点，做一个抽象类来定义这种节点，
 * 然后以其实现类来区分文件与目录，在实现类中分别定义各自的具体实现内容
 *
 * @author: BaoZhou
 * @date : 2018/12/29 14:29
 */

public abstract class Node {

    /**
     * 新增节点：文件节点无此方法，目录节点重写此方法
     */
    public void addNode(Node node) throws Exception {
        throw new UnsupportedOperationException("不支持添加操作");
    }

    /**
     * 删除节点：文件节点无此方法，目录节点重写此方法
     */
    public void removeNode(Node node) throws Exception {
        throw new UnsupportedOperationException("不支持删除操作");
    }

    /**
     * 获取名称
     */
    public String getNodeName(Node node) throws Exception {
        throw new UnsupportedOperationException("不支持获取名称操作");
    }

    /**
     * 显示节点：文件与目录均实现此方法
     */
    public void display() {
        throw new UnsupportedOperationException("不支持打印操作");
    }

}