package composite;

import java.util.List;

public abstract class Component {
	/**
	 * 记录父组件对象
	 */
	private Component parent = null;
	
	public Component getParent() {
		return parent;
	}

	public void setParent(Component parent) {
		this.parent = parent;
	}
	
	/**
	 * 返回组件的子对象
	 * @return
	 */
	public List<Component> getChildren() {
		throw new UnsupportedOperationException("对象不支持这个功能！");
	}
	
	/**
	 * 示意方法，子组件对象可能有的功能方法
	 */
	public abstract void someOperation();
	
	/**
	 * 向组合中添加组件对象
	 * @param child
	 */
	public void addChildren(Component child) {
		//缺省的实现，抛出列外，因为叶子对象没有这个功能
		//或者子组件没有实现这个功能
		throw new UnsupportedOperationException("对象不支持这个功能！");
	}
	
	public void removeChildren(Component child) {
		throw new UnsupportedOperationException("对象不支持这个功能！");
	}
	
	public Component getChildren(int index) {
		throw new UnsupportedOperationException("对象不支持这个功能！");
	}
}
