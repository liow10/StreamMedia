package com.micro.iotclouds.communication.video.mp4.start;

import java.net.InetSocketAddress;

import com.micro.iotclouds.communication.video.core.manager.StreamManager;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * flv流媒体服务
 * 
 * @author ZJ
 *
 */
@Slf4j
//@Component
public class Mp4MediaServer implements Runnable {

	StreamManager streamManager;
	// not used currently
	int handlerThreadPoolSize;
	int mp4Port;

	public Mp4MediaServer(int mp4Port, StreamManager streamManager, int handlerThreadPoolSize) {
		this.streamManager = streamManager;
		this.handlerThreadPoolSize = handlerThreadPoolSize;
		this.mp4Port = mp4Port;
	}
//	public void run(String... args) throws Exception {
//		start(new InetSocketAddress("0.0.0.0", wssPort));
//	}

	public void start(InetSocketAddress socketAddress) {
//		// new 一个主线程组
//		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
//		// new 一个工作线程组
//		EventLoopGroup workGroup = new NioEventLoopGroup(200);
//		ServerBootstrap bootstrap = new ServerBootstrap().group(bossGroup, workGroup)
//				.channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
//					@Override
//					protected void initChannel(SocketChannel socketChannel) throws Exception {
//						CorsConfig corsConfig = CorsConfigBuilder.forAnyOrigin().allowNullOrigin().allowCredentials()
//								.build();
//
//						socketChannel.pipeline().addLast(new HttpResponseEncoder()).addLast(new HttpRequestDecoder())
//								.addLast(new ChunkedWriteHandler()).addLast(new HttpObjectAggregator(64 * 1024))  
//								.addLast(new CorsHandler(corsConfig))
//								// .addLast(new WebSocketServerProtocolHandler("/live"))
//								.addLast(new Mp4FlvHandler(streamManager));
//					}
//				}).localAddress(socketAddress).option(ChannelOption.SO_BACKLOG, 128)
//				// 首选直接内存
//				.option(ChannelOption.ALLOCATOR, PreferredDirectByteBufAllocator.DEFAULT)
//				// 设置队列大小
////                .option(ChannelOption.SO_BACKLOG, 1024)
//				// 两小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
//				.childOption(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_KEEPALIVE, true)
//				.childOption(ChannelOption.SO_RCVBUF, 128 * 1024).childOption(ChannelOption.SO_SNDBUF, 1024 * 1024)
//				.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,
//						new WriteBufferWaterMark(1024 * 1024 / 2, 1024 * 1024));
//		// 绑定端口,开始接收进来的连接
//		try {
//			ChannelFuture future = bootstrap.bind(socketAddress).sync();
//			log.info("流媒体服务启动开始监听端口: {}", socketAddress.getPort());
//			future.channel().closeFuture().sync();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} finally {
//			// 关闭主线程组
//			bossGroup.shutdownGracefully();
//			// 关闭工作线程组
//			workGroup.shutdownGracefully();
//		}
	}

	@Override
	public void run() {
//		//start(new InetSocketAddress("0.0.0.0", wssPort));
//		String app = "live";//appAndStreamName.get(0);
//		String streamName = "ppp";//appAndStreamName.get(1);
////		if (streamName.endsWith(".flv")) {
////			streamName = streamName.substring(0, streamName.length() - 4);
////		}
//		StreamName sn = new StreamName(app, streamName);
//		log.info("mp4 stream :{} requested", sn);
//		Stream2 stream = streamManager.getStream(sn);
//
////		if (stream == null) {
////			Map<String, Object> map = new HashMap<>();
////			map.put("token", streamName);
////			HttpUtil.post("http://" + MyLiveConfig.getInstance().getIotLinkip() + ":"
////					+ MyLiveConfig.getInstance().getIotLinkport() + "/" + MyLiveConfig.getInstance().getIotLink()
////					+ "/open/noFlv", map);
////			return;
////		}
//		List<RtmpMediaMessage>  lstRtmpMediaMessage = stream.getContent();
//		
//		new StoreMp4Handler();
//		Channel l = 
	}
}