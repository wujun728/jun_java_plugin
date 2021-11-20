package demo.exception;
 
import mine.util.exception.MyException;

public class ExceptionDemo3 {

	public void g() throws MyException {
		throw new MyException("异常2");
	}

	public static void main(String[] args) {
		ExceptionDemo3 ex = new ExceptionDemo3();
		try {
			ex.g();
		} finally {
			//直接return会丢失所以抛出的异常
			return;
		}

	}
}
