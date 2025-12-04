package com.mycompany.myproject.base.collection.list;

import java.util.LinkedList;

public class LinkedListTest {

    public static void main(String[] args){

        LinkedList<String> linkedList = new LinkedList<String>();

        linkedList.add("1");

        linkedList.get(0);

        linkedList.forEach( item -> {
            System.out.println(item);
        });

        linkedList.iterator();

    }
}
