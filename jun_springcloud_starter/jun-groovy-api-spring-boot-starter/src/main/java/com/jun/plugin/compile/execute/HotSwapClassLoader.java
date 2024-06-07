package com.jun.plugin.compile.execute;

public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader() {

        super(HotSwapClassLoader.class.getClassLoader());
    }



    public HotSwapClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Class loadByte(byte[] classBytes) {
        return defineClass(null, classBytes, 0, classBytes.length);
    }
}
