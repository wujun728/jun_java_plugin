package net.jueb.util4j.study.jdk8.methodReference;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.jun.plugin.util4j.cache.callBack.CallBack;
import com.jun.plugin.util4j.cache.callBack.impl.CallBackCache;
import com.jun.plugin.util4j.hotSwap.classFactory.IScript;
import com.jun.plugin.util4j.thread.NamedThreadFactory;

/**
 * 方法引用
 * 其实是lambda表达式的一个简化写法，所引用的方法其实是lambda表达式的方法体实现，
 * 语法也很简单，左边是容器（可以是类名，实例名），中间是"::"，右边是相应的方法名。如下所示：
 * ObjectReference::methodName
 * 一般方法的引用格式是
 * 如果是静态方法，则是ClassName::methodName。如 Object ::equals
 * 如果是实例方法，则是Instance::methodName。如Object obj=new Object();obj::equals;
 * 构造函数.则是ClassName::new
 * @author Administrator
 */
public class TestMethodReference_callBack implements IScript{

    public static void main(String[] args)   {
        new TestMethodReference_callBack().run();
    }
    public static final ScheduledThreadPoolExecutor scheduExec = new ScheduledThreadPoolExecutor(2,new NamedThreadFactory("ServerExecutor", true));
    public static final CallBackCache cb=new CallBackCache(Executors.newCachedThreadPool());
    {
    	scheduExec.scheduleAtFixedRate(cb.getCleanTask(), 10, 30, TimeUnit.MILLISECONDS);
    }
    
    public void run()  {
    	CallBack<String> callBack=null;
    	//表达式实现
    	callBack=(timeOut,result)->{
    		System.out.println("登录结果:timeOut="+timeOut+",result="+result);
    	};
    	//方法引用
    	callBack=this::login_call;
    	
    	//正常测试
    	long key=cb.put(callBack, TimeUnit.SECONDS.toMillis(5));
    	sleep(TimeUnit.SECONDS.toMillis(3));
    	String result="正常测试";
    	cb.poll(result, key).call(false,result);
    	
    	//超时测试
    	cb.put(callBack, TimeUnit.SECONDS.toMillis(5));
    	sleep(TimeUnit.SECONDS.toMillis(10));
    }
    
    public void sleep(long t)
    {
    	try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void login_call(boolean timeOut,Optional<String> result)
    {
    	System.out.println("登录结果:timeOut="+timeOut+",result="+result);
    }
    
	@Override
	public int getMessageCode() {
		return 0;
	}
}