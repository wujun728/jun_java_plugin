
package net.jueb.util4j.beta.tools.convert.typebytes;
import java.io.IOException;
import java.util.Arrays;

public class Test {
	
	public static void main(String[] args) throws IOException {
		TypeBytesOutputStream to=new TypeBytesOutputStream();
		//写进
		to.writeLong(Long.MAX_VALUE);
		System.out.println("写入："+Long.MAX_VALUE);
		System.out.println(Arrays.toString(to.toByteArray()));
		System.out.println(to.size());
		
		//读出
		TypeBytesInputStream ti=new TypeBytesInputStream(to.toByteArray());
		System.out.println("读出："+ti.readLong());
	}

}
