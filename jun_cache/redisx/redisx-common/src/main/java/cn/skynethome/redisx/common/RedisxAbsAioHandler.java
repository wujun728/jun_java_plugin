package cn.skynethome.redisx.common;

import java.nio.ByteBuffer;

import com.talent.aio.common.ChannelContext;
import com.talent.aio.common.GroupContext;
import com.talent.aio.common.exception.AioDecodeException;
import com.talent.aio.common.intf.AioHandler;

/**
  * 项目名称:[redisx-common]
  * 包:[cn.skynethome.redisx.common]    
  * 文件名称:[RedisxAbsAioHandler]  
  * 描述:[redisx aio handler]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月12日 下午3:05:20]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月12日 下午3:05:20]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public abstract class RedisxAbsAioHandler implements AioHandler<Object, RedisxPacket, Object>
{
	/**
	 * 编码：把业务消息包编码为可以发送的ByteBuffer
	 */
	@Override
	public ByteBuffer encode(RedisxPacket packet, GroupContext<Object, RedisxPacket, Object> groupContext, ChannelContext<Object, RedisxPacket, Object> channelContext)
	{
		byte[] body = packet.getBody();
		int bodyLen = 0;
		if (body != null)
		{
			bodyLen = body.length;
		}

		int allLen = RedisxPacket.HEADER_LENGHT  + bodyLen;
		ByteBuffer buffer = ByteBuffer.allocate(allLen);
		buffer.order(groupContext.getByteOrder());

		buffer.putInt(bodyLen);

		buffer.putShort(packet.getCommand());
		
		if (body != null)
		{
			buffer.put(body);
		}
		return buffer;
	}

	/**
	 * 解码：把接收到的ByteBuffer，解码成应用可以识别的业务消息包
	 */
	@Override
	public RedisxPacket decode(ByteBuffer buffer, ChannelContext<Object, RedisxPacket, Object> channelContext) throws AioDecodeException
	{
		int readableLength = buffer.limit() - buffer.position();
		if (readableLength < RedisxPacket.HEADER_LENGHT)
		{
			return null;
		}

		int bodyLength = buffer.getInt();
		
		short command = buffer.getShort();
		
		if (bodyLength < 0)
		{
			throw new AioDecodeException("bodyLength [" + bodyLength + "] is not right, remote:" + channelContext.getClientNode());
		}

		int neededLength = RedisxPacket.HEADER_LENGHT + bodyLength;
		int test = readableLength - neededLength;
		if (test < 0) // 不够消息体长度(剩下的buffe组不了消息体)
		{
			return null;
		} else
		{
			RedisxPacket imPacket = new RedisxPacket();
			imPacket.setCommand(command);
			
			if (bodyLength > 0)
			{
				byte[] dst = new byte[bodyLength];
				buffer.get(dst);
				imPacket.setBody(dst);
			}
			return imPacket;
		}
	}
}
