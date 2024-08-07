//package com.micro.iotclouds.modules.rtmp.server.server;
//
//import com.micro.iotclouds.communication.video.core.manager.StreamManager;
//import com.micro.iotclouds.modules.rtmp.server.server.ssl.Http2FlvHandler;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.http.HttpRequestDecoder;
//import io.netty.handler.codec.http.HttpResponseEncoder;
//import lombok.extern.slf4j.Slf4j;
//
///**
//@author longyubo
//2020年1月7日 下午2:55:47
//**/
//@Slf4j
//public class Http2FlvServer {
//
//	private int port;
//
//	ChannelFuture channelFuture;
//
//	EventLoopGroup eventLoopGroup;
//	StreamManager streamManager;
//	//not used currently
//	int handlerThreadPoolSize;
//	
//
//	public Http2FlvServer(int port, StreamManager sm, int threadPoolSize) {
//		this.port = port;
//		this.streamManager = sm;
//		this.handlerThreadPoolSize = threadPoolSize;
//	}
//	
//	
//	public void run() throws Exception {
//		eventLoopGroup = new NioEventLoopGroup();
//
//		ServerBootstrap b = new ServerBootstrap();
// 
//		b.group(eventLoopGroup).channel(NioServerSocketChannel.class)
//				.childHandler(new ChannelInitializer<SocketChannel>() {
//					@Override
//					public void initChannel(SocketChannel ch) throws Exception {
//						ch.pipeline().addLast(new HttpRequestDecoder());
//					 
//						ch.pipeline().addLast(new HttpResponseEncoder());
//					 
//						ch.pipeline().addLast(new Http2FlvHandler(streamManager));
//					}
//				}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
//
//		channelFuture = b.bind(port).sync();
//		log.info("HttpFlv server start , listen at :{}",port);
//
//	}
//}
//
