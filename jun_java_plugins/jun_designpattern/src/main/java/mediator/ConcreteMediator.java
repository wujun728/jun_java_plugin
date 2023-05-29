package mediator;

public class ConcreteMediator implements Mediator {

	private ConcreteColleagueA colleagueA;
	
	private ConcreteColleagueB colleagueB;
	
	
	
	public ConcreteColleagueA getColleagueA() {
		return colleagueA;
	}



	public void setColleagueA(ConcreteColleagueA colleagueA) {
		this.colleagueA = colleagueA;
	}



	public ConcreteColleagueB getColleagueB() {
		return colleagueB;
	}



	public void setColleagueB(ConcreteColleagueB colleagueB) {
		this.colleagueB = colleagueB;
	}



	public void change(Colleague colleague) {
		if (colleague == colleagueA) {
			
		} else if (colleague == colleagueB) {
			
		}
	}

}
