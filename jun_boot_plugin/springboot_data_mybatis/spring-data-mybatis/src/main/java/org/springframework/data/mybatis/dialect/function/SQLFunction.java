package org.springframework.data.mybatis.dialect.function;

/**
 * @author Wujun
 */
public interface SQLFunction {

	boolean hasArguments();

	boolean hasParenthesesIfNoArguments();

}
