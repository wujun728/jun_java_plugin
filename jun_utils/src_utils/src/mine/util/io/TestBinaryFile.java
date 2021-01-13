package mine.util.io;

import java.util.Arrays;

public class TestBinaryFile {
	public static void main(String[] args) {
		BinaryFile.write("file/binaryfile.dat", new byte[] { 1, 2, 3, 4, 5, 6,
				7, 8, 9, 10, 'a', 'b', 'c' });
		byte[] data = BinaryFile.read("file/binaryfile.dat");
		System.out.println(Arrays.toString(data));
	}
}
