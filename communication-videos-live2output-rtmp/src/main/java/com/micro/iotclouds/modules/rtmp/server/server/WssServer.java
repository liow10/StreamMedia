//package com.micro.iotclouds.modules.rtmp.server.server;
//
//import com.micro.iotclouds.modules.rtmp.server.server.handlers.*;
//import com.micro.iotclouds.modules.rtmp.server.server.manager.StreamManager;
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
//import io.netty.util.concurrent.DefaultEventExecutorGroup;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class WssServer {
//
//    private int port;
//
//    ChannelFuture channelFuture;
//
//    EventLoopGroup eventLoopGroup;
//    StreamManager streamManager;
//    int handlerThreadPoolSize;
//
//
//    public WssServer(int port, StreamManager sm,int threadPoolSize) {
//        this.port = port;
//        this.streamManager = sm;
//        this.handlerThreadPoolSize = threadPoolSize;
//    }
//
//    public void run() throws Exception {
//        eventLoopGroup = new NioEventLoopGroup();
//
//        ServerBootstrap b = new ServerBootstrap();
//
//        b.group(eventLoopGroup).channel(NioServerSocketChannel.class)
//                .childHandler(new ChannelInitializer<SocketChannel>() {
//                    @Override
//                    public void initChannel(SocketChannel ch) throws Exception {
//                        ch.pipeline().addLast(new HttpRequestDecoder());
//
//                        ch.pipeline().addLast(new HttpResponseEncoder());
//
//                        ch.pipeline().addLast(new WssHandler(streamManager));
//                    }
//                }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
//
//        channelFuture = b.bind(port).sync();
//        log.info("Wss server start , listen at :{}",port);
//
//    }
//
//}