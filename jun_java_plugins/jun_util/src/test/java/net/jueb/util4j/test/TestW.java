package net.jueb.util4j.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

import com.jun.plugin.util4j.buffer.ArrayBytesBuff;
import com.jun.plugin.util4j.buffer.BytesBuff;
import com.jun.plugin.util4j.net.nettyImpl.handler.LoggerHandler;
import com.jun.plugin.util4j.net.nettyImpl.server.NettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.logging.LogLevel;

public class TestW {
	
	public static void main(String[] args) throws URISyntaxException, IOException {
		NettyServer ns=new NettyServer("0.0.0.0",1234,new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ch.pipeline().addLast(new LoggerHandler(LogLevel.INFO));
				ch.pipeline().addLast(new ByteToMessageCodec<ByteBuf>() {

					@Override
					protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
						System.out.println(msg+",Thread:"+Thread.currentThread());
 					}

					@Override
					protected void decode(final ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
						int a=in.readByte();
						int x=in.readIntLE();
						short z=in.readShortLE();
						
						int size=in.readInt();
						short code=in.readShort();
						int len=size-2;
						byte[] data=new byte[len];
						in.readBytes(data);
						BytesBuff buff=new ArrayBytesBuff(data);
						byte type=buff.readByte();
						String token=null;
						if(buff.readByte()!=0)
						{
							int slen=buff.readInt();
							token=new String(buff.readBytes(slen).getBytes());
						}
						System.out.println("code="+code+",type="+type+",token="+token+",Thread:"+Thread.currentThread());
						Thread t=new Thread(new Runnable() {
							
							@Override
							public void run() {
								for(int i=0;i<100;i++)
								{
									ByteBuf buff=ByteBufAllocator.DEFAULT.buffer();
									buff.writeBoolean(true);
									ctx.channel().write(buff);
									try {
										Thread.sleep(3000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						});
						t.start();
					}
				});
			}
		});
		ns.start();
		new Scanner(System.in).nextLine();
	}
}
