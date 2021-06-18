package com.lyx.test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class Main {
    public static void main(String[] argv) throws Exception {

        BeanInfo bi = Introspector.getBeanInfo(MyBean.class);
        PropertyDescriptor[] pds = bi.getPropertyDescriptors();
        for (int i = 0; i < pds.length; i++) {

            String propName = pds[i].getDisplayName();
            System.out.println(propName);

//            System.out.println(pds[i].getName());
        }

    }
}

class MyBean {
    public String getProp1() {
        return null;
    }

    public void setProp1(String s) {
    }

    public int getProp2() {
        return 0;
    }

    public void setProp2(int i) {
    }

    public byte[] getPROP3() {
        return null;
    }

    public void setPROP3(byte[] bytes) {
    }
}