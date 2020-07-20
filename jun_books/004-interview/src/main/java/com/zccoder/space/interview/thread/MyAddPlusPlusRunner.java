package com.zccoder.space.interview.thread;

/**
 * 执行任务
 *
 * @author zc
 * @date 2020/05/02
 */
public class MyAddPlusPlusRunner implements Runnable {

    private MyData myData;

    MyAddPlusPlusRunner(MyData myData) {
        this.myData = myData;
    }

    @Override
    public void run() {

        int count = 10000;
        for (int i = 0; i < count; i++) {
            this.myData.addPlusPlus();
            this.myData.addAtomic();
        }

    }
}
