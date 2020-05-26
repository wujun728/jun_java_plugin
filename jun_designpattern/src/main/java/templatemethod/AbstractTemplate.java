package templatemethod;

public abstract class AbstractTemplate {
	/**
	 * 模板方法，定义算法骨架
	 */
	public final void templateMethod() {
		//第一步
		this.operation1();
		//第二步
		this.operation2();
		//第三步
		this.doPrimitiveOperation1();
		//第四步
		this.doPrimitiveOperation2();
		//第五步
		this.hookOperation1();
	}

	/**
	 * 具体的AbstractClass操作，子类的公共功能
	 * 但通常不是具体的算法步骤
	 */
	protected void commonOperation() {
		
	}
	
	/**
	 * 钩子操作，算法中的步骤，不一定需要，提供默认实现
	 * 由子类选择并实现
	 */
	protected void hookOperation1() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 原语操作2，算法中必要的步骤，延迟到子类实现
	 */
	protected abstract void doPrimitiveOperation2();

	/**
	 * 原语操作1，算法中必要的步骤，延迟到子类实现
	 */
	protected abstract void doPrimitiveOperation1();

	/**
	 * 具体操作2，算法中的步骤，固定实现
	 */
	private void operation2() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 具体操作1，算法中的步骤，固定实现
	 */
	private void operation1() {
		// TODO Auto-generated method stub
		
	}
}
