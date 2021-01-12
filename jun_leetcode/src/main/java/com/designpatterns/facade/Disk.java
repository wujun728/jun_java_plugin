package com.designpatterns.facade;

import lombok.extern.slf4j.Slf4j;

/**
 * Disk子系统类
 *
 * @author Administrator
 */
public class Disk {
    public void start() {
        System.out.println("Disk is start...");
    }

    public void shutDown() {
        System.out.println("Disk is shutDown...");
    }
}