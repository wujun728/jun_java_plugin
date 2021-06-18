package book.j2se5;
/**
 * 可变长的参数。
 * 有时候，我们传入到方法的参数的个数是不固定的，为了解决这个问题，我们一般采用下面的方法：
 * 1．  重载，多重载几个方法，尽可能的满足参数的个数。显然这不是什么好办法。
 * 2．  将参数作为一个数组传入。虽然这样我们只需一个方法即可，但是，
 * 为了传递这个数组，我们需要先声明一个数组，然后将参数一个一个加到数组中。
 * 现在，我们可以使用可变长参数解决这个问题，
 * 也就是使用...将参数声明成可变长参数。显然，可变长参数必须是最后一个参数。
 */
public class VarArgs {

	/**
	 * 打印消息，消息数量可以任意多
	 * @param debug	是否debug模式
	 * @param msgs	待打印的消息
	 */
	public static void printMsg(boolean debug, String ... msgs){
		if (debug){
			// 打印消息的长度
			System.out.println("DEBUG: 待打印消息的个数为" + msgs.length);
		}
		for (String s : msgs){
			System.out.println(s);
		}
		if (debug){
			// 打印消息的长度
			System.out.println("DEBUG: 打印消息结束");
		}
	}
	/**
	 * 重载printMsg方法，将第一个参数类型该为int
	 * @param debugMode 是否debug模式
	 * @param msgs	待打印的消息
	 */
	public static void printMsg(int debugMode, String ... msgs){
		if (debugMode != 0){
			// 打印消息的长度
			System.out.println("DEBUG: 待打印消息的个数为" + msgs.length);
		}
		for (String s : msgs){
			System.out.println(s);
		}
		if (debugMode != 0){
			// 打印消息的长度
			System.out.println("DEBUG: 打印消息结束");
		}
	}
	
	public static void main(String[] args) {
		// 调用printMsg(boolean debug, String ... msgs)方法
		VarArgs.printMsg(true);
		VarArgs.printMsg(false, "第一条消息", "这是第二条");
		VarArgs.printMsg(true, "第一条", "第二条", "这是第三条");
		
		// 调用printMsg(int debugMode, String ... msgs)方法
		VarArgs.printMsg(1, "The first message", "The second message");
	}
}