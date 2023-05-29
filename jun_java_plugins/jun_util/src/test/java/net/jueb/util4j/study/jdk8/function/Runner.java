package net.jueb.util4j.study.jdk8.function;

@FunctionalInterface
public interface Runner extends Person {

    void run();

    @Override
    default void say() {
    	System.out.println("I'm a Runner");
    }
    
    default void doSomething() {
    	System.out.println("run started ...");
    	run();
    	System.out.println("run runEnd");
    }
}