package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import groovy.lang.Binding;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;

/**
 * @author lyz
 * 类说明 java调用 groovy 学习
 */
@RestController
public class Groovy {

    @Autowired
    Environment env;

    /**
     * @param filepath
     *            groovy脚本文件的路径(当参数为null默认指定工程路径下的groovy文件夹)
     * @param filename
     *            groovy脚本文件名字
     * @param params
     *            执行脚本的参数
     * @return 返回执行结果
     */
    public Object runGroovyScriptByFile(String[] filepath, String filename, Map<String, Object> params) {

        if (filepath == null || filepath.length == 0)
            filepath = new String[] { "grovvy\\" };// 定义Groovy脚本引擎的根路径

        try {
            // String[]{".\\src\\main\\java\\com\\senyint\\util\\"}
            GroovyScriptEngine engine = new GroovyScriptEngine(filepath);
            return engine.run(filename, new Binding(params));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param filepath
     *            groovy脚本文件的路径(当参数为null默认指定工程路径下的groovy文件夹)
     * @param filename
     *            groovy脚本文件名字
     * @param funname
     *            执行指定的方法名称
     * @param params
     *            执行脚本的参数
     * @return 返回执行结果
     */
    public Object runGroovyScriptByFile(String[] filepath, String filename, String funname, Object[] params) {

        if (filepath == null || filepath.length == 0)
            filepath = new String[] { "grovvy\\" };// 定义Groovy脚本引擎的根路径
        try {
            // String[]{".\\src\\main\\java\\com\\senyint\\util\\"}
            GroovyScriptEngine engine = new GroovyScriptEngine(filepath);
            Script script = engine.createScript(filename, new Binding());
            return script.invokeMethod(funname, params);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @desc 执行groovy脚本(不指定方法)
     * @param script
     *            要执行的脚本 通过字符串传入，应用场景 如从数据库中读取出来的脚本等
     * @param params
     *            执行grovvy需要传入的参数
     * @return 脚本执行结果
     */
    public Object runGroovyScript(String script, Map<String, Object> params) {
        if (script == null || "".equals(script))
            throw new RuntimeException("方法runGroovyScript无法执行，传入的脚本为空");

        try {
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName("groovy");
            Bindings bindings = engine.createBindings();
            bindings.putAll(params);
            return engine.eval(script, bindings);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @desc 执行groovy脚本(需要指定方法名)
     * @param script
     *            要执行的脚本 通过字符串传入，应用场景 如从数据库中读取出来的脚本等
     * @param funName
     *            要执行的方法名
     * @param params
     *            执行grovvy需要传入的参数
     * @return
     */
    public Object runGroovyScript(String script, String funName, Object[] params) {
        try {
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName("groovy");
            // String HelloLanguage = "def hello(language) {return \"Hello
            // $language\"}";
            engine.eval(script);
            Invocable inv = (Invocable) engine;
            return inv.invokeFunction(funName, params);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void getScriptEngineFactoryList() {
        ScriptEngineManager manager = new ScriptEngineManager();

        List<ScriptEngineFactory> factories = manager.getEngineFactories();

        for (ScriptEngineFactory factory : factories) {

            System.out.printf(
                    "Name: %s%n" + "Version: %s%n" + "Language name: %s%n" + "Language version: %s%n"
                            + "Extensions: %s%n" + "Mime types: %s%n" + "Names: %s%n",
                    factory.getEngineName(), factory.getEngineVersion(), factory.getLanguageName(),
                    factory.getLanguageVersion(), factory.getExtensions(), factory.getMimeTypes(), factory.getNames());
            // 得到当前的脚本引擎

            ScriptEngine engine = factory.getScriptEngine();
            System.out.println(engine);
        }
    }

    public static void main(String[] args) {
        test1();
//        test2();

     /*   Object res3 = groovy.runGroovyScriptByFile(null, "hello.groovy", "hello", new String[] { "param3", "param4" });
        System.out.println(res3);
*/
    }

    private static void test2() {
        Groovy groovy = new Groovy();
        groovy.getScriptEngineFactoryList();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("language", "groovy test");
        Object res = groovy.runGroovyScript("return \"Hello $language\"", params);
        String script = "def hello(param1,param2) {return \"the params is $param1 and $param2\"}";
        Object res1 = groovy.runGroovyScript(script, "hello", new String[] { "param1", "param2" });
        System.out.println(res);
        System.out.println(res1);
    }
    private static void test3() {
        Groovy groovy = new Groovy();
        groovy.getScriptEngineFactoryList();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("language", "groovy test");
        Object res = groovy.runGroovyScript(" class GroovyClass {\n" +
                "    String name\n" +
                "        def message = \"Hello from Groovy\"\n" +
                "        def abc(a,b){\n" +
                "                return a+b;\n" +
                "        }\n" +
                "    void greet() {\n" +
                "        println(\"Hello 111 , ${name}\")\n" +
                "    }\n" +
                "}\n" +
                "def obj = new GroovyClass(name: \"World\")\n" +
                "obj.greet()", params);
        System.out.println(res);
    }

    private static void test1() {
        String script = "package groovy;\n" +
                "\n" +
                "import com.alibaba.fastjson.JSON;\n" +
                "import com.alibaba.fastjson.JSONObject;\n" +
                "public class Test001 {\n" +
                "    public JSONObject run (String map) {\n" +
                "       return  JSON.parseObject(map);\n" +
                "    }\n" +
                "}\n";
        String param = "        {\"1\":2,\"3\":\"4\"}";

        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        GroovyClassLoader loader = new GroovyClassLoader(parent);
        Class groovyClass = loader.parseClass(script);
        GroovyObject groovyObject = null;
        try {
            groovyObject = (GroovyObject) groovyClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object res = groovyObject.invokeMethod("run", param);
        System.out.println(JSONUtil.toJsonPrettyStr(res));
    }
}
