package io.github.wujun728.groovy.controller;

import groovy.lang.GroovyClassLoader;
import io.github.wujun728.common.base.Result;
import io.github.wujun728.groovy.util.SpringUtils;
import io.github.wujun728.groovy.groovy.GroovyDynamicLoader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.script.*;
import java.lang.reflect.Method;
import java.util.HashMap;

@RestController
//@Api(tags = "Groovy脚本执行接口")
@RequestMapping(path = "/groovy")
public class GroovyScriptController {
//    private ScriptEngineManager scriptEngineManager;
//    private ApplicationContext applicationContext;
    
    @Resource
	private GroovyDynamicLoader groovyDynamicLoader;
	
	@RequestMapping("/test")
	public int test(int number1, int number2) {
		return number1 + number2;
		
	}

	@RequestMapping("/refresh")
	public Result refresh() {
		try {
			groovyDynamicLoader.refreshNew();
		} catch (Exception e) {
			e.printStackTrace();
            return Result.fail("緩存刷新失败！" + e.getMessage());
        }
		return Result.success("緩存刷新成功");
	}

    /*@Autowired
    public GroovyScriptController(ScriptEngineManager scriptEngineManager, ApplicationContext applicationContext) {
        Assert.notNull(scriptEngineManager, "scriptEngineManager is not allowed null.");
        Assert.notNull(applicationContext, "applicationContext is not allowed null.");
        this.scriptEngineManager = scriptEngineManager;
        this.applicationContext = applicationContext;
    }*/

    @PostMapping("/execute")
    //@ApiOperation(notes = "执行Groovy脚本", value = "执行groovy脚本")
    public Object execute(String script) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("groovy");
        ScriptContext context = new SimpleScriptContext();
        context.setBindings(new SimpleBindings(new HashMap<String, Object>(1) {{
            put("spring", SpringUtils.getApplicationContext());
        }}), ScriptContext.ENGINE_SCOPE);
        return engine.eval(script, context).toString();
    }

    @RequestMapping("/runScript")
    public Object runScript(String script) throws Exception {
        if (script != null) {
            // 这里其实就是groovy的api动态的加载生成一个Class，然后反射生成对象，然后执行run方法，最后返回结果
            // 最精华的地方就是SpringContextUtils.autowireBean，可以实现自动注入bean，
            Class clazz = new GroovyClassLoader().parseClass(script);
            Method run = clazz.getMethod("run");
            Object o = clazz.newInstance();
            SpringUtils.autowireBean(o);
            Object ret = run.invoke(o);
            return ret;
        } else {
            return "no script";
        }
    }
}
