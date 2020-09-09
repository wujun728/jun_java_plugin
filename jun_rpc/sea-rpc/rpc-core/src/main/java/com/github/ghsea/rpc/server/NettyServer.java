package com.github.ghsea.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.ghsea.rpc.common.modle.NodeId;
import com.github.ghsea.rpc.common.modle.ServiceProfile;
import com.github.ghsea.rpc.common.util.IpUtils;
import com.github.ghsea.rpc.common.util.PropertiesConfigHelper;
import com.github.ghsea.rpc.core.exception.RpcException;
import com.github.ghsea.rpc.server.registry.ServiceRegistry;
import com.github.ghsea.rpc.server.registry.ZkServiceRegistry;
import com.google.common.base.Preconditions;

class NettyServer implements Server {

	private ServerBootstrap server;

	private EventLoopGroup boss;

	private EventLoopGroup worker;

	private Channel channel;

	private AtomicBoolean started = new AtomicBoolean(false);

	private ServiceRegistry registry;

	private List<ServiceProfile> profileOfRegisterList;

	private Logger log = LoggerFactory.getLogger(NettyServer.class);

	private String host;

	private int port;

	public NettyServer() {
		registry = new ZkServiceRegistry();
		profileOfRegisterList = new ArrayList<ServiceProfile>();
		PropertiesConfiguration config = PropertiesConfigHelper.loadIfAbsent("netty_server.properties");
		this.port = config.getInt("port");

		this.host = IpUtils.getLocalIp();
	}

	@Override
	public void start() {
		Preconditions.checkState(!started.get(), "Netty Server has started");
		if (started.compareAndSet(false, true)) {
			final int port = this.port;
			final String host = this.host;
			server = new ServerBootstrap();
			// TODO 配置从外部读
			boss = new NioEventLoopGroup(2);
			// TODO 配置从外部读
			worker = new NioEventLoopGroup(20);
			server.channel(NioServerSocketChannel.class).group(boss, worker).option(ChannelOption.TCP_NODELAY, true)
					.option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ResponseChannelInitializer());

			log.debug("==========         Netty Server is starting at {}:{}  ==========         ", this.host, this.port);
			final long start = System.nanoTime();
			ChannelFuture future = server.bind(host, port);
			try {
				future.sync();
			} catch (Exception ex) {
				log.error("==========         Netty server failed to start up.Caused by:", ex);
				doShutdown();
				throw new RpcException(ex);
			}
			long end = System.nanoTime();
			doRegister();
			log.debug("==========         Netty Server started at {}:{} in {} ms ==========         ", host, port,
					TimeUnit.NANOSECONDS.toMillis(end - start));
			channel = future.channel();
		}
	}

	@Override
	public void shutdown() {
		doShutdown();
		log.debug("==========         Netty Server has shutdown ==========         ");
	}

	private void doShutdown() {
		if (channel != null) {
			channel.close().syncUninterruptibly();
		}
		if (boss != null) {
			boss.shutdownGracefully();
		}

		if (worker != null) {
			worker.shutdownGracefully();
		}
	}

	@Override
	public void register(ServiceProfile profile) throws Exception {
		profileOfRegisterList.add(profile);
	}

	private void doRegister() {
		try {
			log.debug("                   Start Register ServiceProfile                   ");
			for (ServiceProfile profile : profileOfRegisterList) {
				registry.register(profile);
				log.debug("               registered ServiceProfile:" + JSON.toJSONString(profile));
			}
			log.debug("               Succeed in Register ServiceProfile                  ");
		} catch (Exception ex) {
			throw new RpcException(ex);
		}
	}

	@Override
	public void unregister(ServiceProfile profile) throws Exception {
		registry.unregister(profile);
	}

	@Override
	public void update(ServiceProfile profile) throws Exception {
		registry.update(profile);
	}

	@Override
	public void addNodeForGroup(String pool, String group, NodeId node) throws Exception {
		registry.addNodeForGroup(pool, group, node);

	}

	@Override
	public void deleteNodeForGroup(String pool, String group, NodeId node) throws Exception {
		registry.deleteNodeForGroup(pool, group, node);
	}

	@Override
	public String getHost() {
		return this.host;
	}

	@Override
	public int getPort() {
		return this.port;
	}

}
