package net.jueb.util4j.study.jdk8.function;

public interface Runner2 extends Runner {

    void run();

    @Override
    default void say() {
    	System.out.println("I'm a Runner2");
    }
    
    default void doSomething() {
    	System.out.println("run started ...");
    	run();
    	System.out.println("run runEnd");
    }
}