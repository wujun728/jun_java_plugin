package com.jun.plugin.util4j.net.nettyImpl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jun.plugin.util4j.net.JConnection;

/**
 * 实现的连接
 * @author Administrator
 */
public class NettyConnection implements JConnection{
	public static AttributeKey<NettyConnection> CHANNEL_KEY=AttributeKey.newInstance("NettyConnection");
	protected InternalLogger log=NetLogFactory.getLogger(NettyConnection.class);
	protected final Map<String,Object> attributes=new HashMap<String,Object>();
	protected final Channel channel;
	protected int id;
	private Object attachment;

	public NettyConnection(Channel channel) {
		this.channel=channel;
		this.id=getChannelId(channel);
		channel.attr(CHANNEL_KEY).set(this);
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public static int getChannelId(Channel channel)
	{
		return channel.hashCode();
	}
	
	public static NettyConnection findConnection(Channel channel)
	{
		return channel.attr(CHANNEL_KEY).get();
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public boolean isActive() {
		return channel!=null && channel.isActive() && channel.isWritable();
	}

	@Override
	public void close() {
		if(channel!=null && channel.isActive())
		{
			channel.close();
		}
	}

	@Override
	public boolean hasAttribute(String key) {
		return attributes.containsKey(key);
	}

	@Override
	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}

	@Override
	public Set<String> getAttributeNames() {
		return attributes.keySet();
	}

	@Override
	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public Object removeAttribute(String key) {
		return attributes.remove(key);
	}

	@Override
	public void clearAttributes() {
		attributes.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAttachment() {
		if(attachment !=null)
		{
			return (T) attachment;
		}
		return null;
	}

	@Override
	public <T> void setAttachment(T attachment) {
		this.attachment=attachment;
	}

	@Override
	public InetSocketAddress getRemoteAddress() {
		return (InetSocketAddress) channel.remoteAddress();
	}

	@Override
	public InetSocketAddress getLocalAddress() {
		return (InetSocketAddress) channel.localAddress();
	}

	@Override
	public void write(Object obj) {
		channel.write(obj);
	}

	@Override
	public void writeAndFlush(Object obj) {
		channel.writeAndFlush(obj);
	}

	@Override
	public void write(byte[] bytes) {
		if(bytes!=null && bytes.length>0 && isActive())
		{
			ByteBuf buf=PooledByteBufAllocator.DEFAULT.buffer();
			buf.writeBytes(bytes);
			write(buf);
		}
	}
	@Override
	public void writeAndFlush(byte[] bytes) {
		if(bytes!=null && bytes.length>0 && isActive())
		{
			ByteBuf buf=PooledByteBufAllocator.DEFAULT.buffer();
			buf.writeBytes(bytes);
			writeAndFlush(buf);
		}
	}
	@Override
	public void flush() {
		if(channel!=null)
		{
			channel.flush();
		}
	}
	
	@Override
	public String toString() {
		return channel!=null?channel.toString()+",isActive:"+channel.isActive():super.toString();
	}
}
