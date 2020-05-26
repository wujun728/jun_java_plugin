/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		decorator.Test.java
 * Class:			Test
 * Date:			2012-5-7
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package decorator;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-5-7 上午10:38:48 
 */

public class Test {

	/**  
	 * 描述
	 * @param args  
	 */
	public static void main(String[] args) {
		Decorator decorator = new ConcreteDecoratorA(new ConcreteComponentA());
		decorator.setAddedState("我是新增功能！");
		decorator.operation();
	}

}
