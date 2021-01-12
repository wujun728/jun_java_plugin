import java.util.* ;
class Person
{
	private String name ;
	private int age ;
	private List<Email> allEmails ;
	
	public Person()
	{
		this.allEmails = new ArrayList<Email>() ;
	}

	public Person(String name,int age)
	{
		this() ;
		this.setName(name) ;
		this.setAge(age) ;
	}
	public List getAllEmails()
	{
		return this.allEmails ;
	}
	public void setAllEmails(List allEmails)
	{
		this.allEmails = allEmails ;
	}
	public void setName(String name)
	{
		this.name = name ;
	}
	public void setAge(int age)
	{
		this.age = age ;
	}
	public String getName()
	{
		return this.name ;
	}
	public int getAge()
	{
		return this.age ;
	}
	public String toString()
	{
		return "姓名："+this.getName()+"，年龄："+this.getAge() ;
	}
};
class Email
{
	private String url ;
	private String userName ;
	private String flag ;

	public Email(String url,String userName,String flag)
	{
		this.setUrl(url) ;
		this.setUserName(userName) ;
		this.setFlag(flag) ;
	}

	public void setUrl(String url)
	{
		this.url = url ;
	}
	public void setUserName(String userName)
	{
		this.userName = userName ;
	}
	public void setFlag(String flag)
	{
		this.flag = flag ;
	}
	public String getUrl()
	{
		return this.url ;
	}
	public String getUserName()
	{
		return this.userName ;
	}
	public String getFlag()
	{
		return this.flag ;
	}
	public String toString()
	{
		return "网站地址："+this.getUrl()+"，EMAIL名称："+this.getUserName()+this.getFlag() ;
	}
};

public class ColDemo01
{
	public static void main(String args[])
	{
		Person p = new Person("LXH",28) ;
		Email e1 = new Email("www.163.com","mldnqa","@163.com") ;
		Email e2 = new Email("www.263.com","li_xing_hua","@263.net") ;
		Email e3 = new Email("www.163.com","mldnkf","@163.com") ;

		p.getAllEmails().add(e1) ;
		p.getAllEmails().add(e2) ;
		p.getAllEmails().add(e3) ;

		// 一个人拥有3个email地址
		// 通过输出体现
		List<Email> all = p.getAllEmails() ;
		Iterator iter = all.iterator() ;
		System.out.println(p) ;
		while(iter.hasNext())
		{
			Email e = (Email)iter.next() ;
			System.out.println("  |- "+e) ;
		}
	}
};
