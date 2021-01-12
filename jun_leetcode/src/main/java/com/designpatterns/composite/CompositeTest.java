package com.designpatterns.composite;

import org.junit.jupiter.api.Test;

/**
 * 组合模式
 * @author BaoZhou
 * @date 2018/12/29
 */
public class CompositeTest {
    @Test
    void test() throws Exception {
        MiFile a = new MiFile("a.dll");
        MiFile b = new MiFile("b.psd");
        MiFile c = new MiFile("b.txt");
        Catalog program = new Catalog("program files",1);
        Catalog windows = new Catalog("windows",0);
        program.addNode(b);
        program.addNode(c);
        windows.addNode(a);
        windows.addNode(program);
        windows.display();
    }
}
