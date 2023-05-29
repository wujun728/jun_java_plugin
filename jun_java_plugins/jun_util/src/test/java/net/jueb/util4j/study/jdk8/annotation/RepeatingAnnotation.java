package net.jueb.util4j.study.jdk8.annotation;

import java.lang.annotation.Repeatable;

/**
 * 创建重复注解Authority时，加上@Repeatable,指向存储注解Authorities，
 * 在使用时候，直接可以重复使用Authority注解。
 * @author Administrator
 */
public class RepeatingAnnotation {

	@Repeatable(Authorities.class)
	public @interface Authority {
	     String role();
	}

	public @interface Authorities {
	    Authority[] value();
	}
	public class RepeatAnnotationUseNewVersion {
	    @Authority(role="Admin")
	    @Authority(role="Manager")
	    public void doSomeThing(){
	    }
	}
}
