package decorator;

public abstract class Decorator extends Component {

	private String addedState;
	
	public String getAddedState() {
		return addedState;
	}

	public void setAddedState(String addedState) {
		this.addedState = addedState;
	}
	
	protected Component component;
	
	public Decorator(Component component) {
		this.component = component;
	}

	@Override
	public void operation() {
		component.operation();
	}

}
