package memento;

/**
 * 原发器对象
 * @author Wujun
 *
 */
public class Originator {
	/**
	 * 原发器状态
	 */
	private String state = "";
	
	/**
	 * 创建保存原发器对象的状态的备忘录对象
	 * @return 创建好的备忘录对象
	 */
	public Memento createMemento() {
		return new MementoImp(state);
	}
	
	/**
	 * 真正的备忘录对象，实现备忘录接口
	 * 实现成似有内部类，不让外部访问
	 * @author Wujun
	 *
	 */
	private static class MementoImp implements Memento {
		private String state = "";
		
		public MementoImp(String state) {
			this.state = state;
		}
		
		public String getState() {
			return state;
		}
	}

}
