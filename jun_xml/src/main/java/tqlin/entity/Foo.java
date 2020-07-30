package tqlin.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Foo {
    private String name;
    private List<Bar> barList = new ArrayList<Bar>();

    public void addBar(Bar bar) {
        barList.add(bar);
    }

    public Bar findBar(int id) {
        for (Bar bar : barList) {
            if (bar.getId() == id) {
                return bar;
            }
        }
        return null;
    }

    public Iterator getBars() {
        return barList.iterator();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
