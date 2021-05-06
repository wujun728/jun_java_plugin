package mediator;

public class ConcreteColleagueA extends Colleague {

	public ConcreteColleagueA(Mediator mediator) {
		super(mediator);
	}

	public void someOperate() {
		getMediator().change(this);
	}
}
