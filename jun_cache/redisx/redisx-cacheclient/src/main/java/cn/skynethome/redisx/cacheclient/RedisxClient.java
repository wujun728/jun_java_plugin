/**
 * **************************************************************************
 *
 * @说明: 
 * @项目名称: talent-aio-examples-server
 *
 * @author: tanyaowu 
 * @创建时间: 2016年11月17日 下午5:59:24
 *
 * **************************************************************************
 */
package cn.skynethome.redisx.cacheclient;

import com.talent.aio.client.AioClient;
import com.talent.aio.client.ClientChannelContext;
import com.talent.aio.client.ClientGroupContext;
import com.talent.aio.client.intf.ClientAioHandler;
import com.talent.aio.client.intf.ClientAioListener;
import com.talent.aio.common.Aio;
import com.talent.aio.common.Node;
import com.talent.aio.common.ReconnConf;

import cn.skynethome.redisx.common.Constants;
import cn.skynethome.redisx.common.RedisxPacket;

/**
  * 项目名称:[redisx-cacheclient]
  * 包:[cn.skynethome.redisx.cacheclient]    
  * 文件名称:[RedisxClient]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月16日 下午4:59:56]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月16日 下午4:59:56]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class RedisxClient
{
	private Node serverNode = null; //服务器的IP地址
	private  AioClient<Object, RedisxPacket, Object> aioClient = null;
	private  ClientGroupContext<Object, RedisxPacket, Object> clientGroupContext = null;
	private  ClientAioHandler<Object, RedisxPacket, Object> aioClientHandler = null;
	private  ClientAioListener<Object, RedisxPacket, Object> aioListener = null;
	private  ReconnConf<Object, RedisxPacket, Object> reconnConf = new ReconnConf<Object, RedisxPacket, Object>(5000L);//用来自动连接的，不想自动连接请传null
	private ClientChannelContext<Object, RedisxPacket, Object> clientChannelContext = null;
	
	public RedisxClient()
	{
	    
	}
	
	public RedisxClient(String serverIp,int serverPort, ClientAioListener<Object, RedisxPacket, Object> aioListener,ClientAioHandler<Object, RedisxPacket, Object> aioClientHandler)
	{
	    this.serverNode = new Node(serverIp, serverPort);
	    this.aioListener = aioListener;
	    this.aioClientHandler = aioClientHandler;
	    
	    try
        {
            init();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	public void  init() throws Exception
	{
       
        aioListener = null;
        clientGroupContext = new ClientGroupContext<>(aioClientHandler, aioListener, reconnConf);
        aioClient = new AioClient<>(clientGroupContext);
        clientChannelContext = aioClient.connect(serverNode);

	}
	
	public void send(byte[] bytes)
	{
	    RedisxPacket packet = new RedisxPacket();
	    packet.setCommand(Constants.COMMAND_SENT_DATA);
        packet.setBody(bytes);
        Aio.send(clientChannelContext, packet);
	}
	
	public void sendJoinGroup(String groupId)
    {
        RedisxPacket packet = new RedisxPacket();
        packet.setCommand(Constants.COMMAND_JOIN_GROUP);
        packet.setBody(groupId.getBytes());
        Aio.send(clientChannelContext, packet);
    }
	
	public static void main(String[] args)
    {
	    RedisxClient redisxClient =  new RedisxClient("127.0.0.1",8888,null,new RedisXSharedSentinelAioHandler());
	    
	    redisxClient.sendJoinGroup("xdfsdf");
	    
    }

}
