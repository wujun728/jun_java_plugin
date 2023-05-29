package mine.util.others;

public class TestGetRandom {
	public static void main(String[] args) {
		System.out.println("测试随机生成字符：");
		for (int i = 1; i <= 100; i++) {
			System.out.print(GetRandom.getRandomChar('A', 'Z') + "  ");
			if (i % 10 == 0)
				System.out.println();
		}
		System.out.println("测试随机生成自然数：");
		for (int i = 1; i <= 100; i++) {
			System.out.print(GetRandom.getRandomInt(0, 9) + "  ");
			if (i % 10 == 0)
				System.out.println();
		}
	}
}
