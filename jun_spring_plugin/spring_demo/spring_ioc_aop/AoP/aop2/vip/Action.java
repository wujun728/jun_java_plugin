package aop2.vip;

public class Action {
  
	public String in(String str,Integer num){
		return "in-->"+str+":"+num;
	}
	public String out(String str,Integer num){
		return "out-->"+str+":"+num;
	}
}
