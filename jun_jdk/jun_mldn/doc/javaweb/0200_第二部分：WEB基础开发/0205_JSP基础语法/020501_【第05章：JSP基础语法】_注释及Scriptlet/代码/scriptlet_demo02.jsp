<%!
	public static final String INFO = "www.MLDNJAVA.cn" ;
	int x = 10 ;
%>
<%
	out.println("<h2>x = " + x++ + "</h2>") ;	// Óï¾ä
%>
<%!
	public int add(int x,int y){
		return x + y ;
	}
%>
<%!
	class Person{
		private String name ;
		private int age ;
		public Person(String name,int age){
			this.name = name ;
			this.age = age ;
		}
		public String toString(){
			return "name = " + this.name + ";age = " + this.age ;
		}
	}
%>

<%
	out.println("<h3>INFO = " + INFO + "</h3>") ;
	out.println("<h3>3 + 5 = " + add(3,5) + "</h3>") ;
	out.println("<h3>" + new Person("zhangsan",30) + "</h3>") ;
%>