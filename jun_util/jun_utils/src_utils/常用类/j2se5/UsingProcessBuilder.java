package book.j2se5;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 通过ProcessBuilder执行本地命令。此类用于创建操作系统进程。
 * 获取本机的MAC地址
 * 
 * 每个进程生成器管理这些进程属性：
 * (1)命令 是一个字符串列表，它表示要调用的外部程序文件及其参数（如果有）
 * (2)环境 是从变量 到值 的依赖于系统的映射。初始值是当前进程环境的一个副本.
 * (3)工作目录。默认值是当前进程的当前工作目录，通常根据系统属性 user.dir 来命名.
 * (4)redirectErrorStream 属性。最初，此属性为 false，
 * 意思是子进程的标准输出和错误输出被发送给两个独立的流，
 * 这些流可以通过 Process.getInputStream() 和 Process.getErrorStream() 方法来访问。
 * 如果将值设置为 true，标准错误将与标准输出合并。这使得关联错误消息和相应的输出变得更容易。
 * 在此情况下，合并的数据可从 Process.getInputStream() 返回的流读取，
 * 而从 Process.getErrorStream() 返回的流读取将直接到达文件尾。
 */
public class UsingProcessBuilder {

	/**
	 * 获取Windows系统下的网卡的MAC地址
	 * @return
	 */
	public static List<String> getPhysicalAddress() {
		Process p = null;
		//物理网卡列表 
		List<String> address = new ArrayList<String>();

		try {
			//执行ipconfig /all命令 
			p = new ProcessBuilder("ipconfig", "/all").start();
		} catch (IOException e) {
			return address;
		}
		byte[] b = new byte[1024];
		int readbytes = -1;
		StringBuffer sb = new StringBuffer();
		//读取进程输出值 
		InputStream in = p.getInputStream();
		try {
			while ((readbytes = in.read(b)) != -1) {
				sb.append(new String(b, 0, readbytes));
			}
		} catch (IOException e1) {
		} finally {
			try {
				in.close();
			} catch (IOException e2) {
			}
		}
		//以下分析输出值，得到物理网卡 
		String rtValue = sb.toString();
		int i = rtValue.indexOf("Physical Address. . . . . . . . . :");
		while (i > 0) {
			rtValue = rtValue.substring(i
					+ "Physical Address. . . . . . . . . :".length());
			address.add(rtValue.substring(1, 18));
			i = rtValue.indexOf("Physical Address. . . . . . . . . :");
		}
		
		return address;
	}
	/**
	 * 执行自定义的一个命令，该命令放在C:/temp下，并且需要2个环境变量的支持。
	 */
	public static boolean executeMyCommand(){
		// 创建系统进程创建器
		ProcessBuilder pb = new ProcessBuilder("myCommand", "myArg1", "myArg2");
		// 获得进程的环境
		Map<String, String> env = pb.environment();
		// 设置和去除环境变量
		env.put("VAR1", "myValue");
		env.remove("VAR0");
		env.put("VAR2", env.get("VAR1") + ";");
		// 切换工作目录
		pb.directory(new File("C:/temp"));
		try {
			// 得到进程实例
			Process p = pb.start();
			// 等待该进程执行完毕
			if (p.waitFor() != 0){
				// 如果进程运行结果不为0，表示进程是错误退出的
				// 获得进程实例的错误输出
				InputStream error = p.getErrorStream();
				// do something
			}
			// 获得进程实例的标准输出
			InputStream sdin = p.getInputStream();
			// do something
		} catch (IOException e) {
		} catch (InterruptedException e) {
		}
		return true;
	}
	
	public static void main(String[] args) {
		List<String> address = UsingProcessBuilder.getPhysicalAddress();
		for (String add : address) {
			System.out.printf("物理网卡地址: %s%n", add);
		}
	}
}