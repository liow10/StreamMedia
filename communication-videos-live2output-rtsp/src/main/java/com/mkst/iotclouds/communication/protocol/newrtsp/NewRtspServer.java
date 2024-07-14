///**
// * 
// */
//package com.mkst.iotclouds.communication.protocol.newrtsp;
//
//import com.mkst.iotclouds.communication.protocol.newrtsp.handler.NewRtspHandler;
//import com.mkst.iotclouds.communication.protocol.newrtsp.handler.UdpHandler;
//import com.mkst.iotclouds.communication.protocol.rtsp.handler.HeartBeatServerHandler;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.WriteBufferWaterMark;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioDatagramChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.rtsp.RtspDecoder;
//import io.netty.handler.codec.rtsp.RtspEncoder;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @author Joe
// *
// *视频上传
//
//ffmpeg -i a.mp4 -vcodec libx264 -f rtsp rtsp://127.0.0.1:5050/ok
//
//视频接收
//
//ffmpeg -rtsp_flags listen -f rtsp -i rtsp://127.0.0.1:5050/ok b.mp4
// */
//@Slf4j
//public class NewRtspServer implements Runnable {
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//
//	}
//
//	public static void main(String[] args) throws Exception {
//		start();
//	}
//
//	public static void start() throws Exception {
//		// public static void main(String[] args) {*/
//		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
//		EventLoopGroup workGroup = new NioEventLoopGroup();
//		initUdp(workGroup);
////		createUdp(rtpPort);
//
//		try {
//			ServerBootstrap rtspstrap = new ServerBootstrap();
//			rtspstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
//					.option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_REUSEADDR, true)
//					.childOption(ChannelOption.SO_RCVBUF, 64 * 1024).childOption(ChannelOption.SO_SNDBUF, 64 * 1024)
//					.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,
//							new WriteBufferWaterMark(64 * 1024 / 2, 64 * 1024))
//					.childHandler(new ChannelInitializer<SocketChannel>() {
//						@Override
//						protected void initChannel(SocketChannel socketChannel) throws Exception {
//							socketChannel.pipeline()
//									// .addLast(new IdleStateHandler(0, 0, RTSP_IDLE_TIME, TimeUnit.SECONDS))//
//									// 5秒内既没有读，也没有写，则关闭连接
//									.addLast(new RtspDecoder()).addLast(new RtspEncoder())
//									.addLast(new HttpObjectAggregator(64 * 1024)).addLast(new NewRtspHandler())
//									.addLast(new HeartBeatServerHandler());
//						}
//					});
//
//			ChannelFuture rtspFuture = rtspstrap.bind(554).sync();
//
//			log.info("RtspNettyServer start success ...");
//
//			rtspFuture.channel().closeFuture().sync();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} finally {
//			bossGroup.shutdownGracefully();
//			workGroup.shutdownGracefully();
//		}
//	}
//
//	public static Channel createUdp(String strip, int port) {
//		try {
//			System.out.printf("start udp bind %s %d \n", strip, port);
//			Channel n = udpstrap.bind(strip, port).sync().channel();
//			System.out.printf("end udp bind %s %d \n", strip, port);
//			System.out.println(n);
//			return n;
//		} catch (InterruptedException e) {
//			return null;
//		}
//	}
//
//	private static Bootstrap udpstrap = new Bootstrap();
//
//	public static void initUdp(EventLoopGroup group) {
//		udpstrap.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_SNDBUF, 1024 * 1024)
//				.handler(new ChannelInitializer<NioDatagramChannel>() {
//					@Override
//					protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
//						nioDatagramChannel.pipeline().addLast(new UdpHandler());
//					}
//				}).option(ChannelOption.SO_BROADCAST, false);
//	}
//}
