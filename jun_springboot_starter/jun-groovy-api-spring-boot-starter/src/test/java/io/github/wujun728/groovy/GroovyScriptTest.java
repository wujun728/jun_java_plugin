package io.github.wujun728.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import io.github.wujun728.groovy.cache.ApiConfigCache;
import io.github.wujun728.groovy.interfaces.IRun;
import io.github.wujun728.sql.entity.ApiConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Groovy 脚本执行模块单元测试
 */
public class GroovyScriptTest {

    @Before
    public void setUp() {
        ApiConfigCache.clear();
    }

    @After
    public void tearDown() {
        ApiConfigCache.clear();
    }

    @Test
    public void testGroovyShellExecute() {
        Binding binding = new Binding();
        binding.setVariable("x", 10);
        binding.setVariable("y", 20);
        GroovyShell shell = new GroovyShell(binding);
        Object result = shell.evaluate("x + y");
        assertEquals(30, result);
    }

    @Test
    public void testGroovyShellExecuteString() {
        GroovyShell shell = new GroovyShell();
        Object result = shell.evaluate("'Hello' + ' ' + 'Groovy'");
        assertEquals("Hello Groovy", result);
    }

    @Test
    public void testGroovyClassLoaderExecute() throws Exception {
        GroovyClassLoader classLoader = new GroovyClassLoader();
        String scriptText = "class Calculator { int add(int a, int b) { return a + b } }";
        Class<?> clazz = classLoader.parseClass(scriptText);
        Object instance = clazz.newInstance();
        Object result = clazz.getMethod("add", int.class, int.class).invoke(instance, 3, 7);
        assertEquals(10, result);
        classLoader.close();
    }

    @Test
    public void testGroovyClassLoaderScript() throws Exception {
        GroovyClassLoader classLoader = new GroovyClassLoader();
        String scriptText = "def compute() { return 42 }; compute()";
        Class<?> clazz = classLoader.parseClass(scriptText);
        Object instance = clazz.newInstance();
        if (instance instanceof Script) {
            Object result = ((Script) instance).run();
            assertEquals(42, result);
        }
        classLoader.close();
    }

    @Test
    public void testScriptEngineExecute() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        if (engine != null) {
            engine.put("name", "World");
            Object result = engine.eval("'Hello ' + name");
            assertEquals("Hello World", result);
        }
    }

    @Test
    public void testScriptEngineArithmetic() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        if (engine != null) {
            Object result = engine.eval("(1..10).sum()");
            assertEquals(55, result);
        }
    }

    @Test
    public void testIRunInterface() throws Exception {
        GroovyClassLoader classLoader = new GroovyClassLoader();
        String scriptText =
                "import io.github.wujun728.groovy.interfaces.IRun\n" +
                "class TestRunner implements IRun {\n" +
                "    Object run(Map<String, Object> params) {\n" +
                "        return 'result:' + params.get('key')\n" +
                "    }\n" +
                "}";
        Class<?> clazz = classLoader.parseClass(scriptText);
        Object instance = clazz.newInstance();
        assertTrue("Should implement IRun", instance instanceof IRun);
        IRun runner = (IRun) instance;
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        params.put("key", "test_value");
        Object result = runner.run(params);
        assertEquals("result:test_value", result);
        classLoader.close();
    }

    @Test
    public void testApiConfigCachePutAndGet() {
        ApiConfig config = new ApiConfig();
        config.setPath("/api/test");
        config.setBeanName("testBean");
        config.setName("Test API");

        ApiConfigCache.put(config);

        ApiConfig retrieved = ApiConfigCache.get("/api/test");
        assertNotNull("Should retrieve config", retrieved);
        assertEquals("/api/test", retrieved.getPath());
        assertEquals("testBean", retrieved.getBeanName());
    }

    @Test
    public void testApiConfigCacheGetAll() {
        ApiConfig config1 = new ApiConfig();
        config1.setPath("/api/test1");
        config1.setBeanName("bean1");

        ApiConfig config2 = new ApiConfig();
        config2.setPath("/api/test2");
        config2.setBeanName("bean2");

        ApiConfigCache.put(config1);
        ApiConfigCache.put(config2);

        Collection<ApiConfig> all = ApiConfigCache.getAll();
        assertEquals(2, all.size());
    }

    @Test
    public void testApiConfigCacheRemove() {
        ApiConfig config = new ApiConfig();
        config.setPath("/api/remove");
        config.setBeanName("removeBean");

        ApiConfigCache.put(config);
        assertNotNull(ApiConfigCache.get("/api/remove"));

        ApiConfigCache.remove(config);
        assertNull(ApiConfigCache.get("/api/remove"));
    }

    @Test
    public void testApiConfigCacheClear() {
        ApiConfig config1 = new ApiConfig();
        config1.setPath("/api/clear1");
        config1.setBeanName("clearBean1");

        ApiConfig config2 = new ApiConfig();
        config2.setPath("/api/clear2");
        config2.setBeanName("clearBean2");

        ApiConfigCache.put(config1);
        ApiConfigCache.put(config2);
        assertEquals(2, ApiConfigCache.getAll().size());

        ApiConfigCache.clear();
        assertEquals(0, ApiConfigCache.getAll().size());
    }

    @Test
    public void testApiConfigCacheGetByBeanName() {
        ApiConfig config = new ApiConfig();
        config.setPath("/api/byname");
        config.setBeanName("targetBean");

        ApiConfigCache.put(config);

        ApiConfig found = ApiConfigCache.getByBeanName("targetBean");
        assertNotNull("Should find by bean name", found);
        assertEquals("/api/byname", found.getPath());
    }

    @Test
    public void testApiConfigCachePutAll() {
        java.util.List<ApiConfig> configs = new java.util.ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ApiConfig config = new ApiConfig();
            config.setPath("/api/batch" + i);
            config.setBeanName("batchBean" + i);
            configs.add(config);
        }

        ApiConfigCache.putAll(configs);
        assertEquals(5, ApiConfigCache.getAll().size());

        // putAll clears and re-adds
        java.util.List<ApiConfig> newConfigs = new java.util.ArrayList<>();
        ApiConfig single = new ApiConfig();
        single.setPath("/api/new");
        single.setBeanName("newBean");
        newConfigs.add(single);

        ApiConfigCache.putAll(newConfigs);
        assertEquals(1, ApiConfigCache.getAll().size());
    }
}
