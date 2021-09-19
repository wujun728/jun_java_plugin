package observer;

import java.util.Observable;
import java.util.Observer;

public class Reader implements Observer {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(name + "--有新收报纸" + arg);
	}

}
