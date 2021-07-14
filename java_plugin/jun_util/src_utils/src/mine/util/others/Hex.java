package mine.util.others;

/**
 * 以十六进制查看二进制文件
 */
public class Hex {
	public static String format(byte[] data) {
		StringBuilder result = new StringBuilder();
		int n = 0;
		for (byte b : data) {
            if(n%16==0)
            	result.append(String.format("%05x:  ",n));
            result.append(String.format("%02x  ",b));
            n++;
            if(n%16==0)
            	result.append('\n');
		}
		return result.toString();
	}
}
