class A
{
	private String name ;
	public void setName(String name)
	{
		this.name = name ;
	}
	public String getName()
	{
		return this.name ;
	}
};
class B extends A
{
	public void say()
	{
		System.out.println(getName()) ;
	}
};