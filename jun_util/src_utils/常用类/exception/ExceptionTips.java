package book.exception;

import java.util.Date;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * 使用异常的几点注意
 */
public class ExceptionTips {

	public static void main(String[] args) {

		//（1）尽量避免使用异常，将异常情况提前检测出来。
		Stack stack = new Stack();
		try {
			stack.pop();
		} catch (EmptyStackException e){
			//...
		}
		//应该使用下面的方式，以避免使用异常
		if (!stack.isEmpty()){
			stack.pop();
		}
		
		//（2）不要为每个可能会出现异常语句都设置try和catch。
		try {
			stack.pop();
		} catch (EmptyStackException e){
			//...
		}
		String data = "123";
		try {
			Double.parseDouble(data);
		} catch (NumberFormatException e){
			//...
		}
		//应该使用下面的方式，将两个语句放在一个try块中
		try {
			stack.pop();
			Double.parseDouble(data);
		} catch (EmptyStackException e){
			//...
		} catch (NumberFormatException e){
			//...
		}
		
		//（3）避免在方法中throw或者捕获运行时异常RuntimeException，比如内存错误等。
		//避免出现下面的情况
		String[] array;
		try {
			array = new String[1000];
			//array = new String[10000000];此时会出现OutOfMemoryError异常
		} catch (OutOfMemoryError e){
			throw e;
		}
		//直接用下面的代码
		array = new String[1000];
		
		//（4）避免总是catch Exception或Throwable，而要catch具体的异常。这样可以根据不同的异常做不同的处理，是程序更加清晰。
		try {
			stack.pop();
			Double.parseDouble(data);
		} catch (Exception e){
			//应该避免catch Exception!!!
		}
		//（5）不要压制、隐瞒异常。将不能处理的异常往外抛，而不是catch住之后随便处理。
		try {
			Double.parseDouble(data);
		} catch (NumberFormatException e){
			//...
			throw e; //抛出不能处理的异常，而不是隐瞒。
		}
		//（6）不要在循环中使用try catch，尽量将try catch放在循环外或者避免使用try catch。
		//请看下面的例子，在循环中使用try和catch将耗费更多的时间，尽管没有异常发生
		int i = 0;
		int ntry = 1000000;
		Stack s = new Stack();
		long s1;
		long s2;
		System.out.println("Testing for empty stack");
		s1 = new Date().getTime();
		for (i = 0; i <= ntry; i++){
			if (!s.empty()){
				s.pop();
			}
		}
		s2 = new Date().getTime();
		System.out.println((s2 - s1) + " milliseconds");

		System.out.println("Catching EmptyStackException");
		s1 = new Date().getTime();
		for (i = 0; i <= ntry; i++){
			try {
				s.pop();
			} catch (EmptyStackException e){
			}
		}
		s2 = new Date().getTime();
		System.out.println((s2 - s1) + " milliseconds");
	}
}
