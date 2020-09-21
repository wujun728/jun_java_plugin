
package cn.skynethome.redisx.common;

import com.talent.aio.common.intf.Packet;

/**
  * 项目名称:[redisx-common]
  * 包:[cn.skynethome.redisx.common]    
  * 文件名称:[RedisxPacket]  
  * 描述:[redisx packet class]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月12日 下午3:05:46]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月12日 下午3:05:46]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class RedisxPacket extends Packet
{
	public static final int HEADER_LENGHT = 6;//消息头的长度
	public static final String CHARSET = "utf-8";
	
	private short command;
	
	

    public short getCommand()
    {
        return command;
    }

    public void setCommand(short command)
    {
        this.command = command;
    }

    private byte[] body;

	/**
	 * @return the body
	 */
	public byte[] getBody()
	{
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(byte[] body)
	{
		this.body = body;
	}
}
