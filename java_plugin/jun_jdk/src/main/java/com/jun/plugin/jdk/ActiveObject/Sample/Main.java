package com.jun.plugin.jdk.ActiveObject.Sample;

import com.jun.plugin.jdk.ActiveObject.Sample.activeobject.ActiveObject;
import com.jun.plugin.jdk.ActiveObject.Sample.activeobject.ActiveObjectFactory;

public class Main {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakerClientThread("Alice", activeObject).start();
        new MakerClientThread("Bobby", activeObject).start();
        new DisplayClientThread("Chris", activeObject).start();
    }
}
