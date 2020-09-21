package sample.bean;

public class Message {
    public static final int THREAD0 = 0;
    public static final int THREAD1 = 1;
    public static final int THREAD2 = 2;
    public static final int THREAD3 = 3;
    public static final int THREAD4 = 4;

    private String message;

    private int num;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
