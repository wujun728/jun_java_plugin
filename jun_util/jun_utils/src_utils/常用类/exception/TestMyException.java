package book.exception;
/**
 * 自定义异常类的使用
 * @author new
 *
 */
public class TestMyException {

	public static void firstException() throws MyFirstException{
		throw new MyFirstException("\"firstException()\" method occurs an exception!");
	}
	
	public static void secondException() throws MySecondException{
		throw new MySecondException("\"secondException()\" method occurs an exception!");
	}

	public static void main(String[] args) {
		try {
			TestMyException.firstException();
			TestMyException.secondException();
		} catch (MyFirstException e1){
			System.out.println("Exception: " + e1.getMessage());
			e1.printStackTrace();
		} catch (MySecondException e2){
			System.out.println("Exception: " + e2.getMessage());
			e2.printStackTrace();
		}
		//当一个try块后面跟着多个catch块时，如果发生的异常匹配第一个catch块的参数，便将异常处理权利交给第一个catch块。
		//如果发生的异常与第一个catch块不匹配，便看是否与第二个catch块匹配，依次下去，如果到最后依然无法匹配该异常，
		//便需要在方法声明中添加一条throw语句，将该异常抛出。
		//因此，在有多个catch块，而且每次处理的异常类型具有继承关系时，应该首先catch子类异常，再catch父类异常。
		//比如，如果MySecondException继承MyFirstException，那么最好将catch (MySecondException e2)放在前面，
		//把catch (MyFirstException e1)放在后面。
	}
}
