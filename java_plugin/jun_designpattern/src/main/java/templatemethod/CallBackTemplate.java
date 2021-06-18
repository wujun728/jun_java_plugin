package templatemethod;

public class CallBackTemplate {
	public void doSome(Callback callback) {
		//其他操作
		callback.call();
		//其他操作
	}
}
