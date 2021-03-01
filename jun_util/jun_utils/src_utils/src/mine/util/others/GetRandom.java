package mine.util.others;

import java.util.Random;

public class GetRandom {
	// 返回ch1和ch2之间(包括ch1,ch2)的任意一个字符,如果ch1 > ch2，返回'\0'
	public static char getRandomChar(char ch1, char ch2) {
		if (ch1 > ch2)
			return 0;
		// 下面两种形式等价
		// return (char) (ch1 + new Random().nextDouble() * (ch2 - ch1 + 1));
		return (char) (ch1 + Math.random() * (ch2 - ch1 + 1));
	}

	// 返回a到b之間(包括a,b)的任意一個自然数,如果a > b || a < 0，返回-1
	public static int getRandomInt(int a, int b) {
		if (a > b || a < 0)
			return -1;
		// 下面两种形式等价
		// return a + (int) (new Random().nextDouble() * (b - a + 1));
		return a + (int) (Math.random() * (b - a + 1));
	}
}
