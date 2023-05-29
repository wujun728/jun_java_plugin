package net.jueb.util4j.test.DynamicProxy;
/**
 * 真实对象
 * @author juebanlin
 */
public class RealSubject implements Subject
{
    @Override
    public void rent()
    {
        System.out.println("I want to rent my house");
    }
    
    @Override
    public void hello(String str)
    {
        System.out.println("hello: " + str);
    }
}