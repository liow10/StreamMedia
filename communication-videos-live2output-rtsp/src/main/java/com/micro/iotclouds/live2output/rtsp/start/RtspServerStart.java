package com.micro.iotclouds.live2output.rtsp.start;

import com.micro.iotclouds.communication.video.core.manager.StreamManager;
import com.micro.iotclouds.live2output.rtsp.handler.RtspHandler;
import com.micro.iotclouds.live2output.rtsp.handler.UdpHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.rtsp.RtspDecoder;
import io.netty.handler.codec.rtsp.RtspEncoder;
import io.netty.util.ResourceLeakDetector;

/**
 *
 *
 * //
 */
//@Component
public class RtspServerStart implements Runnable {

	StreamManager streamManager;

	private static Bootstrap udpstrap = new Bootstrap();

	int handlerThreadPoolSize;

	int port;

	public static void initUdp(EventLoopGroup group) {
		udpstrap.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_SNDBUF, 1024 * 1024)
				.handler(new ChannelInitializer<NioDatagramChannel>() {
					@Override
					protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
						nioDatagramChannel.pipeline().addLast(new UdpHandler());
					}
				}).option(ChannelOption.SO_BROADCAST, false);
	}

	public static Channel createUdp(String strip, int port) {
		try {
			System.out.printf("start udp bind %s %d \n", strip, port);
			Channel n = udpstrap.bind(strip, port).sync().channel();
			System.out.printf("end udp bind %s %d \n", strip, port);
			System.out.println(n);
			return n;
		} catch (InterruptedException e) {
			return null;
		}
	}

	public RtspServerStart(int port, StreamManager sm, int threadPoolSize) {
		this.port = port;
		this.streamManager = sm;
		this.handlerThreadPoolSize = threadPoolSize;
	}

//	@PostConstruct
	public void init() throws Exception {
//	public static void main(String[] args) {
		ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);

		EventLoopGroup listenGrp = new NioEventLoopGroup(1);
		EventLoopGroup workGrp = new NioEventLoopGroup(4);
		initUdp(workGrp);

		try {
//			ServerBootstrap b = new ServerBootstrap();
//			b.group(listenGrp, workGrp).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
//					.option(ChannelOption.SO_REUSEADDR, true).childOption(ChannelOption.SO_RCVBUF, 1 * 1024 * 1024)
//					.childOption(ChannelOption.SO_SNDBUF, 64 * 1024)
//					.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(32 * 1024, 64 * 1024))
//					.childOption(ChannelOption.SO_KEEPALIVE, true)
//					.childHandler(new ChannelInitializer<SocketChannel>() {
//						@Override
//						protected void initChannel(SocketChannel socketChannel) throws Exception {
//							socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN,
//									1 * 1024 * 1024, 0, 4, 0, 4, true));
//							socketChannel.pipeline().addLast(new ServerHandler());
//						}
//					});

//			ServerBootstrap httpstrap = b.clone();
//			httpstrap.childOption(ChannelOption.SO_RCVBUF, 64 * 1024).childOption(ChannelOption.SO_SNDBUF, 1024 * 1024)
//					.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,
//							new WriteBufferWaterMark(1024 * 1024 / 2, 1024 * 1024))
//					.childHandler(new ChannelInitializer<SocketChannel>() {
//						@Override
//						protected void initChannel(SocketChannel socketChannel) throws Exception {
//							CorsConfig corsConfig = CorsConfigBuilder.forAnyOrigin().allowNullOrigin()
//									.allowCredentials().build();
//
//							socketChannel.pipeline().addLast(new HttpResponseEncoder())
//									.addLast(new HttpRequestDecoder()).addLast(new HttpObjectAggregator(64 * 1024))
//									.addLast(new CorsHandler(corsConfig))// .addLast(new ChunkedWriteHandler())
//									.addLast(new H264FrameToFlvByteBuf()).addLast(new HttpFlvHandler());
//						}
//					});

			// when need open ssl
//			ServerBootstrap httpsslflvstrap = b.clone();
//			httpsslflvstrap.childHandler(new ChannelInitializer<SocketChannel>() {
//				@Override
//				protected void initChannel(SocketChannel socketChannel) throws Exception {
//					CorsConfig corsConfig = CorsConfigBuilder.forAnyOrigin().allowNullOrigin().allowCredentials()
//							.build();
//
//					SSLEngine sslEngine = SSLContextFactory.getSslContext().createSSLEngine();
//					sslEngine.setUseClientMode(false);
//					socketChannel.pipeline().addLast(new SslHandler(sslEngine));
//
//					socketChannel.pipeline().addLast(new HttpResponseEncoder()).addLast(new HttpRequestDecoder())
//							.addLast(new HttpObjectAggregator(64 * 1024)).addLast(new CorsHandler(corsConfig))// .addLast(new
//																												// ChunkedWriteHandler())
//							.addLast(new H264FrameToFlvByteBuf()).addLast(new HttpFlvHandler());
//				}
//			});

//			ServerBootstrap wsstrap = b.clone();
//			wsstrap.childOption(ChannelOption.SO_RCVBUF, 64 * 1024).childOption(ChannelOption.SO_SNDBUF, 1024 * 1024)
//					.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,
//							new WriteBufferWaterMark(1024 * 1024 / 2, 1024 * 1024))
//					.childHandler(new ChannelInitializer<SocketChannel>() {
//						@Override
//						protected void initChannel(SocketChannel socketChannel) throws Exception {
//							socketChannel.pipeline().addLast(new HttpServerCodec())
//									.addLast(new HttpObjectAggregator(64 * 1024))
//									.addLast(new WebSocketServerProtocolHandler("/live/liveflv", null, false,
//											512 * 1024, false, true))
//									.addLast(new H264FrameToFlvWebsocketFrame()).addLast(new WebSocketHandler());
//						}
//					});

//			ServerBootstrap rtspstrap = b.clone();
			ServerBootstrap rtspstrap = new ServerBootstrap();
//			b.group(eventLoopGroup).channel(NioServerSocketChannel.class)
//			.childHandler(new ChannelInitializer<SocketChannel>() {
//				@Override
//				public void initChannel(SocketChannel ch) throws Exception {
//					ch.pipeline().addLast(new HttpRequestDecoder());
//					ch.pipeline().addLast(new HttpResponseEncoder());
//					ch.pipeline().addLast(new HttpFlvHandler(streamManager));
//				}
//			}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
			rtspstrap.group(listenGrp, workGrp).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_REUSEADDR, true);
			rtspstrap.childOption(ChannelOption.SO_RCVBUF, 64 * 1024).childOption(ChannelOption.SO_SNDBUF, 64 * 1024)
					.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,
							new WriteBufferWaterMark(64 * 1024 / 2, 64 * 1024))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							socketChannel.pipeline().addLast(new RtspDecoder()).addLast(new RtspEncoder())
									.addLast(new HttpObjectAggregator(64 * 1024))
									.addLast(new RtspHandler(streamManager));
						}
					});

//			ServerBootstrap httprest = b.clone();
//			httprest.childOption(ChannelOption.SO_RCVBUF, 64 * 1024).childOption(ChannelOption.SO_SNDBUF, 64 * 1024)
//					.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK,
//							new WriteBufferWaterMark(64 * 1024 / 2, 64 * 1024))
//					.childHandler(new ChannelInitializer<SocketChannel>() {
//						@Override
//						protected void initChannel(SocketChannel socketChannel) throws Exception {
//							socketChannel.pipeline().addLast(new HttpResponseEncoder())
//									.addLast(new HttpRequestDecoder()).addLast(new HttpObjectAggregator(64 * 1024))
//									.addLast(new HttpRestHandler());
//						}
//					});

//			ChannelFuture f = b.bind(1985).sync();
			// httpstrap.bind(1984).sync();
			// wsstrap.bind(1983).sync();
			// httpsslflvstrap.bind(1982).sync();

			ChannelFuture f = rtspstrap.bind(554).sync();
			// httprest.bind(80).sync();

			System.out.println("Moriturus te saluto!!!");

			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			listenGrp.shutdownGracefully();
			workGrp.shutdownGracefully();
		}
	}

	@Override
	public void run() {
		try {
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
