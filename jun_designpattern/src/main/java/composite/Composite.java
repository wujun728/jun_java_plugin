package composite;

import java.util.ArrayList;
import java.util.List;

public class Composite extends Component {

	/**
	 * 用来存储组合对象包含的子组件对象
	 */
	private List<Component> childComponents = null;
	
	@Override
	public void someOperation() {
		if (childComponents != null) {
			for (Component component : childComponents) {
				component.someOperation();
			}
		}
	}

	@Override
	public void addChildren(Component child) {
		if (childComponents == null) {
			childComponents = new ArrayList<Component>();
		}
		childComponents.add(child);
		
		//添加父组件的引用
		child.setParent(this);
	}

	@Override
	public void removeChildren(Component child) {
		if (childComponents != null) {
			int idx = childComponents.indexOf(child);
			if (idx != -1) {
				for (Component component : child.getChildren()) {
					//删除的组件对象是本实例的一个子组件对象
					component.setParent(this);
					//把被删除的商品类别对象的子组件对象添加到当前实例中
					childComponents.add(component);
				}
			}
			childComponents.remove(child);
		}
	}

	@Override
	public Component getChildren(int index) {
		if (childComponents != null) {
			if (index >= 0 && index < childComponents.size()) {
				return childComponents.get(index);
			}
		}
		return null;
	}

	
}
