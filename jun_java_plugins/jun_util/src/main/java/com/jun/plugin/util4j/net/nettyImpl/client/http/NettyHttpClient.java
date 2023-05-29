package com.jun.plugin.util4j.net.nettyImpl.client.http;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.jun.plugin.util4j.cache.callBack.CallBack;
import com.jun.plugin.util4j.net.JConnection;
import com.jun.plugin.util4j.net.JConnectionListener;
import com.jun.plugin.util4j.net.nettyImpl.client.NettyClient;
import com.jun.plugin.util4j.net.nettyImpl.client.NettyClientConfig;
import com.jun.plugin.util4j.net.nettyImpl.handler.http.HttpClientInitHandler;
import com.jun.plugin.util4j.thread.NamedThreadFactory;

import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * http客户端
 */
public class NettyHttpClient{
	
	private static ExecutorService scheduExec=Executors.newCachedThreadPool(new NamedThreadFactory("NettyHttpClient-ASYNC",true));
	private static NettyClientConfig config=new NettyClientConfig();
	
	public HttpResponse syncRequest(URI uri,HttpRequest request)
	{
		initRequest(uri, request);
		return syncRequest(uri.getHost(),getPort(uri), request);
	}
	
	/**
	 * 同步请求
	 * @param host
	 * @param port
	 * @param request
	 * @return 超时返回null
	 */
	public HttpResponse syncRequest(String host,int port,HttpRequest request)
	{
		HttpResponse response=null;
		synchronized (request) {
			if(response==null)
			{
				HttpListener listener=new HttpListener(request);
				NettyClient client=new NettyClient(config,new InetSocketAddress(host, port),new HttpClientInitHandler(listener));
				client.start();
				response=listener.waitResponse();//阻塞等待结果
				client.stop();
			}
		}
		return response;
	}
	
	public HttpResponse syncRequest(URI uri,HttpRequest request,long waiteTimeMills)
	{
		initRequest(uri, request);
		return syncRequest(uri.getHost(),getPort(uri), request,waiteTimeMills);
	}
	
	/**
	 * 同步请求
	 * @param host
	 * @param port
	 * @param request
	 * @param waiteTimeMills
	 * @return 超时返回null
	 */
	public HttpResponse syncRequest(String host,int port,HttpRequest request,long waiteTimeMills)
	{
		HttpResponse response=null;
		synchronized (request) {
			if(response==null)
			{
				HttpListener listener=new HttpListener(request);
				NettyClient client=new NettyClient(config,new InetSocketAddress(host, port),new HttpClientInitHandler(listener));
				client.start();
				response=listener.waitResponse(waiteTimeMills);//阻塞等待结果
				client.stop();
			}
		}
		return response;
	}
	
	public void asyncRequest(URI uri,HttpRequest request,final CallBack<HttpResponse> callback,long timeOut)
	{
		initRequest(uri, request);
		asyncRequest(uri.getHost(),getPort(uri), request,callback,timeOut);
	}
	
	/**
	 * 异步请求
	 * @param host
	 * @param port
	 * @param request
	 * @param callback
	 */
	public void asyncRequest(String host,int port,HttpRequest request,final CallBack<HttpResponse> callback,long timeOut)
	{
		synchronized (request) {
			final HttpListener listener=new HttpListener(request);
			final NettyClient client=new NettyClient(config,new InetSocketAddress(host, port),new HttpClientInitHandler(listener));
			client.start();
			if(callback!=null)
			{
				scheduExec.execute(new Runnable() {
					@Override
					public void run() {
						HttpResponse result=listener.waitResponse(timeOut);
						callback.call(result!=null,Optional.ofNullable(result));
						client.stop();
					}
				});
			}
		}
	}
	
	private  class HttpListener implements JConnectionListener<HttpResponse>{
		private final CountDownLatch latch=new CountDownLatch(1);
		private HttpRequest request;
		private HttpResponse response;
		
		public HttpListener(HttpRequest request) {
			this.request=request;
		}
		
		@Override
		public void connectionOpened(JConnection connection) {
			connection.writeAndFlush(request);
		}

		@Override
		public void messageArrived(JConnection conn, HttpResponse msg) {
			ReferenceCountUtil.retain(msg,1);
			this.response=msg;
			latch.countDown();//释放计数器
		}

		@Override
		public void connectionClosed(JConnection connection) {
			if(latch.getCount()>1)
			{
				latch.countDown();//释放计数器
			}
		}

		/**
		 * 等待回复
		 * @return
		 */
		public HttpResponse waitResponse(long waiteTimeMills)
		{
			try {
				latch.await(waiteTimeMills,TimeUnit.MILLISECONDS);//线程阻塞(count>0 && time<MILLISECONDS)
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return response;
		}
		/**
		 * 等待回复
		 * @return
		 */
		public HttpResponse waitResponse()
		{
			try {
				latch.await();//线程阻塞(count>0 && time<MILLISECONDS)
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return response;
		}
	}
	
	private int getPort(URI uri) {
		int port = uri.getPort();
		if (port == -1) {
			if ("http".equalsIgnoreCase(uri.getScheme())) {
				port = 80;
			}
			else if ("https".equalsIgnoreCase(uri.getScheme())) {
				port = 443;
			}
		}
		return port;
	}

	private void initRequest(URI uri,HttpRequest request) {
		request.headers().set(HttpHeaderNames.HOST, uri.getHost());
		request.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.CLOSE);
	}
	
	public static void main(String[] args) {
		NettyHttpClient client=new NettyHttpClient();
		long time=System.currentTimeMillis();
		HttpRequest request=new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/baidu?tn=monline_6_dg&ie=utf-8&wd=netty+http客户端");
		HttpResponse response=client.syncRequest("www.baidu.com", 80, request);
		System.out.println(System.currentTimeMillis()-time);
		System.out.println(response);
		FullHttpResponse rsp=(FullHttpResponse) response;
		System.out.println("content:"+rsp.content().toString(CharsetUtil.UTF_8));
//		new Scanner(System.in).nextLine();
	}
}
