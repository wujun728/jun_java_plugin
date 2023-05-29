package net.jueb.util4j.beta.net.nettyImpl.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

import java.net.InetSocketAddress;

public class PoolTest {

	public static void main(String[] args) {
		EventLoopGroup group = new NioEventLoopGroup();
		final Bootstrap cb = new Bootstrap();
		cb.group(group).channel(NioSocketChannel.class);
		InetSocketAddress addr1 = new InetSocketAddress("10.0.0.10", 8888);
		InetSocketAddress addr2 = new InetSocketAddress("10.0.0.11", 8888);

		//连接池map
		ChannelPoolMap<InetSocketAddress, SimpleChannelPool> poolMap = new AbstractChannelPoolMap<InetSocketAddress, SimpleChannelPool>() {
		    @Override
		    protected SimpleChannelPool newPool(InetSocketAddress key) {
		        return new SimpleChannelPool(cb.remoteAddress(key), new TestChannelPoolHandler());
		    }
		};

		final SimpleChannelPool pool1 = poolMap.get(addr1);//取出連接addr1地址的连接池
		final SimpleChannelPool pool2 = poolMap.get(addr2);//取出連接addr2地址的连接池
		Future<Channel> f1 = pool1.acquire();//获取一个连接
		f1.addListener(new FutureListener<Channel>() {
		    @Override
		    public void operationComplete(Future<Channel> f) {
		        if (f.isSuccess()) {
		            Channel ch = f.getNow();
		           //连接地址1的某个channel
		            //使用连接发送消息
//		            ch.write(msg)
		            //用完释放
		            pool1.release(ch);
		        }
		    }
		});

	}
	//连接池handler
	 static class TestChannelPoolHandler implements ChannelPoolHandler{

		@Override
		public void channelReleased(Channel ch) throws Exception {
			//ch回收
		}

		@Override
		public void channelAcquired(Channel ch) throws Exception {
			//ch被取出
		}

		@Override
		public void channelCreated(Channel ch) throws Exception {
			//当连接池创建一个连接后,可以对ch配置初始化handler
		}
	}
}
