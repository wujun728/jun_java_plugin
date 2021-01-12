package com.designpatterns.adapter.classAdapter;

import org.junit.jupiter.api.Test;

/**
 * @author BaoZhou
 * @date 2018/12/28
 */
public class ClassAdapterTest {
    @Test
    void test(){
        Target target = new TargetImpl();
        target.request();

        Target adapter = new Adapter();
        adapter.request();
    }
}
