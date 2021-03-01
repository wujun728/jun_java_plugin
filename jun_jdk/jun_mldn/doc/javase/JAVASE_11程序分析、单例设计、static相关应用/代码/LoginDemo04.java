class Login{	// 这样的操作叫做真实的业务操作
	private String name ;
	private String password ;
	public Login(String name,String password){
		this.name = name ;
		this.password = password ;
	}
	public boolean validate(){
		if(name.equals("abc")&&password.equals("123")){
			return true ;
		}else{
			return false ;
		}
	}
};
class Operate{
	private String args[] ;
	public Operate(String args[]){
		this.args = args ;
		if(args.length!=2){
			System.out.println("输入的参数不正确！") ;
			System.exit(1) ;	// 系统退出，只要设置了一个非零的数字
		}
	}
	public String getInfo(){
		if(new Login(this.args[0],this.args[1]).validate()){
			return "登陆成功！" ;
		}else{
			return "登陆失败！" ;
		}
	}
};
public class LoginDemo04{
	public static void main(String args[]){
		System.out.println(new Operate(args).getInfo()) ;
	}
};