package spring_mvc.validator.Inf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import spring_mvc.validator.impl.BetweenImpl;
@Documented
@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD,ElementType.METHOD})
@Constraint(validatedBy = { BetweenImpl.class})
public @interface Between {
	String startDate();
	String endDate();
	String format() default "yyyy-MM-DD";
	String message() default "{Between.date}";
}
