package com.jun.plugin.designpatterns.行为型模式.类和类之间的关系.迭代子模式;

public class MyIterator implements Iterator {

    private Collection collection;
    private int pos = -1;

    public MyIterator(Collection collection) {
        this.collection = collection;
    }

    @Override
    public Object previous() {
        if (pos > 0) {
            pos--;
        }
        return collection.get(pos);
    }

    @Override
    public Object next() {
        if (pos < collection.size() - 1) {
            pos++;
        }
        return collection.get(pos);
    }

    @Override
    public boolean hasNext() {
        if (pos < collection.size() - 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object first() {
        pos = 0;
        return collection.get(pos);
    }

}