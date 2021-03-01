package org.lxh.demo ;
interface Fruit
{
	// 生长
	public void grow() ;
	// 采摘
	public void pick() ;
}
class Apple implements Fruit
{
	public void grow()
	{
		System.out.println("苹果在生长。。。 ") ;
	}
	public void pick()
	{
		System.out.println("摘苹果。。。") ;
	}
};
class Orange implements Fruit
{
	public void grow()
	{
		System.out.println("橘子在生长。。。 ") ;
	}
	public void pick()
	{
		System.out.println("摘橘子。。。") ;
	}
};
class Factory
{
	public static Fruit getFruitInstance()
	{
		return new Orange() ;
	}
};
public class FactoryDemo01
{
	public static void main(String args[])
	{
		Fruit f = Factory.getFruitInstance() ;
		f.grow() ;
		f.pick() ;
	}
};