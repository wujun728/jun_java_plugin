package com.rinog.connect;

import java.io.IOException;
import java.io.InputStream;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SshConnect {
	private String URL = "192.168.32.240";
	private String USER = "root";
	private String PASSWORD = "8b411@";

	public SshConnect(String uRL, String uSER, String pASSWORD) {
		super();
		URL = uRL;
		USER = uSER;
		PASSWORD = pASSWORD;
	}

	public SshConnect() {
	}

	public Connection login() throws IOException {
		// 创建远程连接，默认连接端口为22，如果不使用默认，可以使用方法
		// new Connection(ip, port)创建对象
		Connection conn = new Connection(URL);
		// 连接远程服务器
		conn.connect();
		// 使用用户名和密码登录
		if (conn.authenticateWithPassword(USER, PASSWORD))
			return conn;
		return null;
	}

	/**
	 * 执行脚本
	 * 
	 * @param conn Connection对象
	 * @param cmds 要在linux上执行的指令
	 * @throws IOException
	 */
	public int exec(Connection conn, String cmds) throws IOException {
		InputStream stdOut = null;
		InputStream stdErr = null;
		int ret = -1;
		// 在connection中打开一个新的会话
		Session session = conn.openSession();
		// 在远程服务器上执行linux指令
		session.execCommand(cmds);
		// 指令执行结束后的输出
		stdOut = new StreamGobbler(session.getStdout());
		// 指令执行结束后的错误
		stdErr = new StreamGobbler(session.getStderr());
		// 等待指令执行结束
		session.waitForCondition(ChannelCondition.EXIT_STATUS, 10000);
		// 取得指令执行结束后的状态
		ret = session.getExitStatus();
		conn.close();
		return ret;
	}

}
