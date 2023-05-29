package net.jueb.util4j.study.jdk8.function;

public interface Person {
	
	default void say()
	{
		System.out.println("I��m a Person");
	}
    
    void doSomething();
}