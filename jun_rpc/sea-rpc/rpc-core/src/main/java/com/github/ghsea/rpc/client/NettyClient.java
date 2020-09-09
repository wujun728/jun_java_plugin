package com.github.ghsea.rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ghsea.rpc.common.modle.NodeId;
import com.github.ghsea.rpc.core.DefaultRpcResponseFuture;
import com.github.ghsea.rpc.core.RpcRequest;
import com.github.ghsea.rpc.core.RpcResponseFuture;
import com.github.ghsea.rpc.core.exception.RpcTimeoutException;
import com.github.ghsea.rpc.core.message.RpcRequestMsg;
import com.github.ghsea.rpc.core.message.RpcResponseMsg;

public class NettyClient implements Client {

	private FixedChannelPool channelPool;

	private Map<String, RpcResponseFuture> requestId2Future = new ConcurrentHashMap<String, RpcResponseFuture>();

	// TODO 外部配置
	final int MAX_EXECUTING_TASK = 100;

	private String host;
	private int port;

	private Logger log = LoggerFactory.getLogger(NettyClient.class);

	public NettyClient(NodeId serviceNode) {
		this.host = serviceNode.getHost();
		this.port = serviceNode.getPort();
		init();
	}

	private void init() {
		EventLoopGroup ioGroup = new NioEventLoopGroup();
		Bootstrap client = new Bootstrap();
		client.channel(NioSocketChannel.class).group(ioGroup).option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.TCP_NODELAY, true);
		// client.handler(new RequestChannelInitializer(this));
		client.remoteAddress(new InetSocketAddress(host, port));
		// client.connect().syncUninterruptibly();

		ChannelPoolHandler poolHandler = new ChannelPoolHandlerForBootstrapSetUp(this);

		channelPool = new FixedChannelPool(client, poolHandler, 100);
	}

	public Object request(final RpcRequest request) throws RejectedExecutionException, Exception {
		RpcResponseFuture future = internalRequest(request);
		Object rest = future.get(request.getTimeout(), TimeUnit.MILLISECONDS);
		return rest;
	}

	public java.util.concurrent.Future<?> requestAsync(final RpcRequest request) throws RejectedExecutionException,
			Exception {
		RpcResponseFuture future = internalRequest(request);
		return future;
	}

	private RpcResponseFuture internalRequest(final RpcRequest request) {
		ensureExecutable(request);

		final RpcRequestMsg requestMsg = request.getMsg();
		log.debug("Client is to send RpcRequestMsg:{} ", requestMsg);

		Future<Channel> f = channelPool.acquire();
		f.addListener(new GenericFutureListener<Future<Channel>>() {
			@Override
			public void operationComplete(Future<Channel> future) throws Exception {
				Channel ch = future.get();
				ChannelFuture cf = ch.pipeline().writeAndFlush(requestMsg);
				cf.addListener(new GenericFutureListener<ChannelFuture>() {
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						if (future.isSuccess()) {
							log.debug("Client has sent RpcRequestMsg:{} ", requestMsg);
						} else {
							log.error("Client failed in sending RpcRequestMsg,case:", future.cause());
						}
						// 写操作完成，释放链接
						Channel ch = future.channel();
						channelPool.release(ch);
					}
				});
			}
		});

		RpcResponseFuture future = new DefaultRpcResponseFuture();
		requestId2Future.put(requestMsg.getRequestId(), future);
		RequestTimerTask timerTask = new RequestTimerTask(this, requestMsg.getRequestId());
		TimerFactory.getTimer().newTimeout(timerTask, request.getTimeout(), TimeUnit.MILLISECONDS);
		return future;
	}

	public void timeout(String requetId) {
		RpcResponseFuture future = requestId2Future.remove(requetId);
		if (!future.isDone()) {
			RpcResponseMsg response = new RpcResponseMsg();
			long eslapsedTimeMs = System.currentTimeMillis() - future.getCreateTime().getTime();
			response.setCause(new RpcTimeoutException(eslapsedTimeMs));
			future.operationComplete(response);
		}
	}

	private void ensureExecutable(final RpcRequest request) {
		synchronized (this) {
			if (requestId2Future.size() >= MAX_EXECUTING_TASK) {
				throw new RejectedExecutionException("Too many task executing.Max limit is" + MAX_EXECUTING_TASK);
			}
		}
	}

	@Override
	public RpcResponseFuture getFuture(String requestId) {
		return requestId2Future.get(requestId);
	}
}
