package com.concurrent.juc.atomic;

import com.concurrent.juc.annotation.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
class AtomicIntegerThread implements Runnable {
    AtomicInteger serialNumber = new AtomicInteger(0);
    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());

    }

    public int getSerialNumber() {
        return serialNumber.getAndIncrement();
    }

    public void setSerialNumber(AtomicInteger serialNumber) {
        this.serialNumber = serialNumber;
    }

}