package com.designpatterns.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BaoZhou
 * @date 2018/12/29
 */
public class Catalog extends Node {
    String name;
    Integer level;
    List<Node> items = new ArrayList<>();

    public Catalog(String name,Integer level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public void addNode(Node node) throws Exception {
        items.add(node);
    }

    @Override
    public void removeNode(Node node) throws Exception {
        items.remove(node);
    }

    @Override
    public String getNodeName(Node node) throws Exception {
        return this.name;
    }

    @Override
    public void display() {
        System.out.println("- "+name);
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < level; j++) {
                System.out.print("  ");
            }
            System.out.print("  ");
            items.get(i).display();
        }
    }
}
