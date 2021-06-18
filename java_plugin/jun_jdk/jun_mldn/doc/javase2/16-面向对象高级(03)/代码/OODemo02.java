// 标识此类不能被继承，即不能有子类
class A
{
	public final void fun()
	{}
};
class B extends A
{
	// 覆写类A中的final定义的fun方法
	public void fun()
	{}
};