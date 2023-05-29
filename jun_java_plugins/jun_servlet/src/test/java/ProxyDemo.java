

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.jun.plugin.proxy.MyProxy2;
public class ProxyDemo {
	@Test
	public void proxyDemo(){
		List list = new ArrayList();
		Map  mm = new HashMap();
		
//		List list2 = (List)MyProxy.getProxy(list);
//		list2.add("ddd");
		
		Map mm2 =(Map) MyProxy2.factory(mm);
		mm2.put("ddd", "sfds");
		mm2.remove("sfsd");
		
		P p = new P();
		IP p2 = (IP) MyProxy2.factory(p);
		p2.run();
	}
	
	class P implements IP{
		public void run(){
			System.err.println("run...");
		}
	}
	interface IP{
		void run();
	}
	
	
	
	public static void main(String[] args) throws Exception {
		//声明被代理类
		final List list = new ArrayList();
		final Map  mm = new HashMap();
		//生成代理类
		Object obj = Proxy.newProxyInstance(
				ProxyDemo.class.getClassLoader(),
				new Class[]{Map.class},
				new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						System.err.println("执行某个方法了:"+method.getName());
						//执行被代理类
						Object returnValue = method.invoke(mm, args);
						return returnValue;
					}
				});
		//将代理类转换成接口的对象
//		List list2 = (List) obj;
//		list2.add("ddd");
//		System.err.println(list2.get(0));
	
	    Map m2 = (Map) obj;
	    m2.put("dddd","ddd");
	}
}
