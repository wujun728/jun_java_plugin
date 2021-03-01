package book.mutimedia.sound;

/**
 * 蜂鸣、响铃
 */
public class Beep {

	public static void main(String[] args) {
		// 在终端程序中，'\u0007'表示响铃
		System.out.println("Beep!" + '\u0007');
		
		// 在GUI程序中，Toolkit的beep方法响铃
		java.awt.Toolkit.getDefaultToolkit( ).beep( );
	}
}
