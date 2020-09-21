package org.drools.examples;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.drools.QueryResults;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.drools.spi.Activation;

/**
 * This is a sample file to launch a rule package from a rule source file.
 */
public class HelloWorldExample {

	public static void main(String[] args) throws Exception {
		// 设置系统时间格式
		System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
		
		//String filepath = "D:/workspace2/DroolsDemo/src/org/drools/examples/HelloWorld.drl";
		String filepath2 = "D:/workspace2/DroolsDemo/src/org/drools/examples/Practise.drl";
		
		final Reader source = new FileReader(filepath2);
		//final Reader source2 = new FileReader(filepath);
//		final PackageBuilderConfiguration configuration = new PackageBuilderConfiguration();
		final PackageBuilder builder = new PackageBuilder();

		// this will parse and compile in one step
		builder.addPackageFromDrl(source);
		//builder.addPackageFromDrl(source2);
		
		// Check the builder for errors
		if (builder.hasErrors()) {
			System.out.println("规则脚本存在错误:"+builder.getErrors().toString());
		}

		// get the compiled package (which is serializable)
		final Package pkg = builder.getPackage();

		// add the package to a rulebase (deploy the rule package).
		final RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		Package[] packages = ruleBase.getPackages();
		for(Package p :packages){
			ruleBase.removePackage(p.getName());
		}
		
		ruleBase.addPackage(pkg);
		
		final StatefulSession session = ruleBase.newStatefulSession();
//		session.setGlobal("myGlobalList", new ArrayList<String>());
		
//		session.addEventListener(new DebugAgendaEventListener());
//		session.addEventListener(new DebugWorkingMemoryEventListener());

		final List<Order> orders = new ArrayList<Order>();
		final Order o = new Order();
		o.setName("qu");
		orders.add(o);
		
		final Message message = new Message();
		message.setMessage("Hello World");
		message.setStatus(Message.HELLO);
		message.setOrders(orders);
		message.names.add("网易");
		message.setTime(new Date());
		
		message.setStringUtils(new StringUtils());
		
		session.insert(o);
		session.insert(message);
				
		// fire
		session.fireAllRules(new org.drools.spi.AgendaFilter() {
			public boolean accept(Activation activation) {
				return !activation.getRule().getName().contains("_test");
			}
		});
		session.dispose();
		
		QueryResults qrs = session.getQueryResults("pcount");
		System.out.println(qrs.size());
		
		QueryResults qrs2 = session.getQueryResults("pc2", new Object[]{10});
		System.out.println(qrs2.size());
		
		System.out.println("引擎执行完成后："+message.getStatus()+message.getNames());
	}

	public static class Message {
		public static final int HELLO = 0;
		public static final int GOODBYE = 1;

		private String message;

		private int status;
		
		private Date time;
		
		private List<Order> orders;
		
		private StringUtils stringUtils;
		
		public List<String> names = new ArrayList<String>();

		public Message() {

		}
		
		public StringUtils getStringUtils() {
			return stringUtils;
		}

		public void setStringUtils(StringUtils stringUtils) {
			this.stringUtils = stringUtils;
		}

		public int getFromDB(){
			return stringUtils.getc();
		}

		public String getMessage() {
			return this.message;
		}

		public void setMessage(final String message) {
			this.message = message;
		}

		public int getStatus() {
			return this.status;
		}

		public void setStatus(final int status) {
			this.status = status;
		}

		public List<Order> getOrders() {
			return orders;
		}

		public void setOrders(List<Order> orders) {
			this.orders = orders;
		}

		public List<String> getNames() {
			return names;
		}

		public void setNames(List<String> names) {
			this.names = names;
		}

		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
		}
		
	}
	
	public static class Order {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
