package com.designpatterns.composite;

/**
 * @author BaoZhou
 * @date 2018/12/29
 */
public class MiFile extends Node {
    String name;

    public MiFile(String name) {
        this.name = name;
    }

    @Override
    public String getNodeName(Node node) throws Exception {
        return name;
    }

    @Override
    public void display() {
        System.out.println("| " + name);
    }
}
