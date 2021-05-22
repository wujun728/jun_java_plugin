package cn.hege;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "cn.hege")
// @EnableTransactionManagement // 开启事务管理
@MapperScan("cn.**.dao")
public class BoilerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BoilerApplication.class);
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(BoilerApplication.class).web(true).run(args);
	}

}
