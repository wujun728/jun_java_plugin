package net.jueb.util4j.study.jdk8.function;
public class RunnerImpl implements Runner {

    public static void main(String[] args) {
        Person func = new RunnerImpl();
        func.say();
        func.doSomething();
    }

    @Override
    public void run() {
	    try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}