package book.net.chat;

/**
 * 定义聊天室程序中用到的常量
 */
public class Constants {

	// 服务器的端口号
	public static final int SERVER_PORT = 2525;
	public static final int MAX_CLIENT = 10;
	
	// 消息标识符与消息体之间的分隔符
	public static final String SEPERATOR = "：";
	
	// 消息信息的标识符
	public static final String MSG_IDENTIFER = "MSG";
	// 用户列表信息的标识符
	public static final String PEOPLE_IDENTIFER = "PEOPLE";
	// 连接服务器信息的标识符
	public static final String CONNECT_IDENTIFER = "INFO";
	// 退出信息标识符
	public static final String QUIT_IDENTIFER = "QUIT";
	
}
