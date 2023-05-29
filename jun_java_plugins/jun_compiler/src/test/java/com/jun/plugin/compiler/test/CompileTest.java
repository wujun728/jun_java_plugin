package com.jun.plugin.compiler.test;

import org.junit.Test;

import com.jun.plugin.compiler.JCompileResult;
import com.jun.plugin.compiler.JMemoryJavaCompiler;

public class CompileTest {
    @Test
    public void test() throws Exception {
        String content = "package com.jianggujin.compiler.test;"
                + "public class Test implements com.jianggujin.compiler.test.CompileTest.Parent {"
                + "public void say(){" + "System.out.println(\"this is dynamic compile class.\");" + "}}";
        JMemoryJavaCompiler compiler = new JMemoryJavaCompiler();
        JCompileResult result = compiler.compile(content);
        System.out.println(result);
        if (result.isSuccess()) {
            ((Parent) result.newInstance()).say();
        }
        compiler.close();
    }

    public interface Parent {
        void say();
    }

}
