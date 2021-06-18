class A{
	private String name ;
	public String getName(){
		return this.name ;
	}
};
class B extends A{
	public void fun(){
		System.out.println("name = " + getName()) ;
	}
};