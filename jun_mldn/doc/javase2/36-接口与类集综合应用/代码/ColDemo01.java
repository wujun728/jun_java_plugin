import java.util.* ;

interface Book
{
	// 书的价格、书的名字、书的信息
	public float getPrice() ;
	public String getName() ;
	public String getInfo() ;
}

class ChildBook implements Book
{
	private String name ;
	private float price ;
	private String info ;

	public ChildBook(String name,float price,String info)
	{
		this.setName(name) ;
		this.setPrice(price) ;
		this.setInfo(info) ;
	}

	public String getName()
	{
		return this.name ;
	}
	public float getPrice()
	{
		return this.price ;
	}
	public String getInfo()
	{
		return this.info ;
	}
	public void setName(String name)
	{
		this.name = name ;
	}
	public void setPrice(float price)
	{
		this.price = price ;
	}
	public void setInfo(String info)
	{
		this.info = info ;
	}
	public String toString()
	{
		return "儿童书：书名："+this.getName()+"，价格："+this.price+"，简介："+this.getInfo() ;
	}
};

class ComputerBook implements Book
{
	private String name ;
	private float price ;
	private String info ;

	public ComputerBook(String name,float price,String info)
	{
		this.setName(name) ;
		this.setPrice(price) ;
		this.setInfo(info) ;
	}

	public String getName()
	{
		return this.name ;
	}
	public float getPrice()
	{
		return this.price ;
	}
	public String getInfo()
	{
		return this.info ;
	}
	public void setName(String name)
	{
		this.name = name ;
	}
	public void setPrice(float price)
	{
		this.price = price ;
	}
	public void setInfo(String info)
	{
		this.info = info ;
	}
	public String toString()
	{
		return "电脑书：书名："+this.getName()+"，价格："+this.price+"，简介："+this.getInfo() ;
	}
};

class BookShop
{
	private String name ;
	// 一个书店包含多种书
	private List allBooks ;

	public BookShop()
	{
		this.allBooks = new ArrayList() ;
	}
	public BookShop(String name)
	{
		this() ;
		this.setName(name) ;
	}

	// 得到全部的书
	public List getAllBooks()
	{
		return this.allBooks ;
	}

	public void append(Book book)
	{
		this.allBooks.add(book) ;
	}

	public void delete(Book book)
	{
		this.allBooks.remove(book) ;
	}

	// 根据书的名字，去找到一本书
	public Book findByName(String name)
	{
		// 从已有的数据中进行依次查询
		Book book = null ;
		Iterator iter = this.allBooks.iterator() ;
		while(iter.hasNext())
		{
			// 进行依次的比较
			Book temp = (Book)iter.next() ;
			if(name.equals(temp.getName()))
			{
				// 如果名字相等，则表示找到了
				book = temp ;
				break ;
			}
		}
		return book ;
	}

	// 书的检索，书的模糊查询
	public List index(String keyWord)
	{
		List l = new ArrayList() ;

		Iterator iter = this.allBooks.iterator() ;
		while(iter.hasNext())
		{
			Book b = (Book)iter.next() ;
			if(b.getName().indexOf(keyWord)!=-1)
			{
				l.add(b) ;
			}
		}

		return l ;
	}

	public void setName(String name)
	{
		this.name = name ;
	}
	public String getName()
	{
		return this.name ;
	}
};

public class ColDemo01
{
	public static void main(String args[])
	{
		Book b1 = new ChildBook("一千零一夜",10.0f,"一些传说故事") ;
		Book b2 = new ChildBook("小鸡吃大灰狼",20.0f,"一件奇怪的事情") ;
		Book b3 = new ChildBook("HALIBOTE",25.0f,"魔幻故事") ;
		Book b4 = new ComputerBook("JAVA",65.0f,"JAVA 语言的核心技术") ;
		Book b5 = new ComputerBook("C++",50.0f,"C++ 语言的核心技术") ;
		Book b6 = new ComputerBook("Linux",50.0f,"服务器搭建") ;

		BookShop bs = new BookShop("MLDN 网上书店") ;
		bs.append(b1) ;
		bs.append(b2) ;
		bs.append(b3) ;
		bs.append(b4) ;
		bs.append(b5) ;
		bs.append(b6) ;

		// print(bs.getAllBooks()) ;
		// print(bs.index("A")) ;
		// 假设将C++这本书从书店删除掉
		Book b = bs.findByName("C++") ;
		// System.out.println(b) ;
		bs.delete(b) ;
		print(bs.getAllBooks()) ;
	}
	public static void print(List all)
	{
		Iterator iter = all.iterator() ;
		while(iter.hasNext())
		{
			Book b = (Book)iter.next() ;
			System.out.println(b) ;
		}
	}
};
