package king.quartz.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		//下面的非web形式直接加载可能会导致job同时加载并执行2次,原因不明(历史印象是用了log4j的日志会导致该情况)
		//只能做普通测试用,最好在Tomcat等容器中以web形式运行,且指明docBase路径
		new ClassPathXmlApplicationContext("applicationContext-quartz.xml");
	}

}
