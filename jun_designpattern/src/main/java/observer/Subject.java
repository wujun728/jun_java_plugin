package observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {

	private List<Observer> observers = new ArrayList<Observer>();

	public void dettach(Observer observer) {
		observers.remove(observer);
	}

	public void attach(Observer observer) {
		observers.add(observer);
	}

	protected void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}
}
