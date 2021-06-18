package com.jun.plugin.designpatterns.行为型模式.类和类之间的关系.迭代子模式;

public class Test {

    public static void main(String[] args) {
        
        Collection collection = new MyCollection();
        
        Iterator it = collection.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

}
