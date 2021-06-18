package demo.exception;

import mine.util.exception.MyException;
/*
 * 观察异常栈及常用异常处理方式
 */
public class ExceptionDemo1 {
	public void f() throws MyException {
		throw new MyException("自定义异常");
	}

	public void g() throws MyException {
		f();
	}

	public  void h() throws MyException  {
		try {
			g();
		} catch (MyException e) {
			//1、通过获取栈轨迹中的元素数组来显示异常抛出的轨迹
			for (StackTraceElement ste : e.getStackTrace())
				System.out.println(ste.getMethodName());
			//2、直接将异常栈信息输出至标准错误流或标准输出流
			e.printStackTrace();//输出到标准错误流
			e.printStackTrace(System.err);
			e.printStackTrace(System.out);
			//3、将异常信息输出到文件中
			//e.printStackTrace(new PrintStream("file/exception.txt"));
			//4、重新抛出异常,如果直接抛出那么栈路径是完整的，如果用fillInStackTrace()
			//那么将会从这个方法（当前是h()方法）作为异常发生的原点。
			//throw e;
			throw (MyException)e.fillInStackTrace();
		}
	}
	public static void main(String[] args) {
			try {
				new ExceptionDemo1().h();
			} catch (MyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
