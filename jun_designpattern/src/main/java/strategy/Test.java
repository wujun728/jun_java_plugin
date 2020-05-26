/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		strategy.Test.java
 * Class:			Test
 * Date:			2012-5-7
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package strategy;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-5-7 上午10:59:53 
 */

public class Test {

	/**  
	 * 描述
	 * @param args  
	 */
	public static void main(String[] args) {
		Context context = new Context(new ConcreteStrategyA());
		context.contextInterface();
		
		Context context2 = new Context(new ConcreteStrategyB());
		context2.contextInterface();
	}

}
