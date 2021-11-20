package demo.exception;

import java.io.IOException;

import mine.util.exception.MyException;
/*
 * 异常链
 */
public class ExceptionDemo2 {
	public void f() throws MyException {
		throw new MyException("自定义异常");
	}

	public void g() throws Exception  {
		try {
			f();
		} catch (MyException e) {
			e.printStackTrace();
			throw new Exception("重新抛出的异常1",e);
		}
	}

	public  void h() throws IOException    {
		try {
			g();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			IOException io=new IOException("重新抛出异常2");
			io.initCause(e);
			throw io;
		}
	}
	public static void main(String[] args) {
			try {
				new ExceptionDemo2().h();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
