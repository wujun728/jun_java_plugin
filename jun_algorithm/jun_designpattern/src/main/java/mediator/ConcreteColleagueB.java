package mediator;

public class ConcreteColleagueB extends Colleague {

	public ConcreteColleagueB(Mediator mediator) {
		super(mediator);
	}

	public void otherOperate() {
		getMediator().change(this);
	}
}
