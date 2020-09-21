package cn.skynethome.redisx.cacheserver;

import java.io.IOException;
import com.talent.aio.server.AioServer;
import com.talent.aio.server.ServerGroupContext;
import com.talent.aio.server.intf.ServerAioHandler;
import com.talent.aio.server.intf.ServerAioListener;
import cn.skynethome.redisx.common.RedisxPacket;

/**
  * 项目名称:[redisx-cacheserver]
  * 包:[cn.skynethome.redisx.cacheserver]    
  * 文件名称:[RedisxServerStarter]  
  * 描述:[aio server start]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月12日 下午3:06:28]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月12日 下午3:06:28]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class RedisxServerStarter
{
	static ServerGroupContext<Object, RedisxPacket, Object> serverGroupContext = null;
	static AioServer<Object, RedisxPacket, Object> aioServer = null; //可以为空
	static ServerAioHandler<Object, RedisxPacket, Object> aioHandler = null;
	static ServerAioListener<Object, RedisxPacket, Object> aioListener = null;
	static String ip = "0.0.0.0";
	static int port = 8888;

	public static void main(String[] args) throws IOException
	{
	    if(null!= args && args.length >=2 )
	    {
	        ip = args[0];
	        port = Integer.parseInt(args[1]);
	    }
	    
		aioHandler = new RedisxServerAioHandler();
		aioListener = new  RedisxServerAioListener(); //可以为空
		serverGroupContext = new ServerGroupContext<>(aioHandler, aioListener);
		aioServer = new AioServer<>(serverGroupContext);
		aioServer.start(ip, port);
	}
}