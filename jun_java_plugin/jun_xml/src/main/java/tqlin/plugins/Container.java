package tqlin.plugins;

import java.util.LinkedList;
import java.util.List;

public class Container
        implements Widget {
    private LinkedList<Widget> children = new LinkedList<Widget>();

    public Container() {
    }

    public void addChild(Widget child) {
        children.add(child);
    }

    public List<Widget> getChildren() {
        return children;
    }
}