package io.github.wujun728.groovy;

public class GroovyClassLoaderApp3 {
	public static void main(String[] args) {
        GroovyAutoBuildClassUtils groovy = new GroovyAutoBuildClassUtils();
        try {
            //classUUid: 需要保证唯一
            //code：通常从数据库查询获取，为了做测试直接将代码放进去
            Class<?> clazz = groovy.buildClass("test", "package io.github.wujun728.groovy;\n" +
                    "\n" +
                    "public class GroovyTestImpl implements GroovyTest {\n" +
                    "    @Override\n" +
                    "    public int sum(int a, int b) {\n" +
                    "        return a + b;\n" +
                    "    }\n" +
                    "}");
            Class<?> clazz2 = groovy.buildClass("test2", "package io.github.wujun728.groovy;\n" +
            		"\n" +
            		"public class GroovyTestImpl2 implements GroovyTest {\n" +
            		"    @Override\n" +
            		"    public int sum(int a, int b) {\n" +
            		"        return a + b+111111111;\n" +
            		"    }\n" +
            		"}");
//            Class<?> clazz = groovy.buildClass("test", "package io.github.wujun728.groovy;\n" +
//            		"\n" +
//            		"public class GroovyTestImpl implements GroovyTest {\n" +
//            		"    @Override\n" +
//            		"    public int sum(int a, int b) {\n" +
//            		"        return a + b;\n" +
//            		"    }\n" +
//            		"}");
//            Class<?> clazz2 = groovy.buildClass("test2", "package io.github.wujun728.groovy;\n" +
//            		"\n" +
//            		"public class GroovyTestImpl2 implements GroovyTest {\n" +
//            		"    @Override\n" +
//            		"    public int sum(int a, int b) {\n" +
//            		"        return a + b+111111111;\n" +
//            		"    }\n" +
//            		"}");
            GroovyTest groovyTest = (GroovyTest) clazz.newInstance();
            GroovyTest groovyTest2 = (GroovyTest) clazz2.newInstance();
            int sum = groovyTest.sum(1, 2);
            int sum2 = groovyTest2.sum(1, 2);
            System.out.println("运算结果: " + sum);
            System.out.println("运算结果: " + sum2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}