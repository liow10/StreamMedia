//package com.micro.iotclouds.modules.rtmp.server.server;
//
//import com.micro.iotclouds.modules.rtmp.server.server.handlers.*;
//import com.micro.iotclouds.modules.rtmp.server.server.manager.StreamManager;
//import com.mkst.iotclouds.communication.protocol.rtsp.RtspNettyServer;
//import com.mkst.iotclouds.communication.protocol.rtsp.transform.RetransmissionRequesterDelegate;
//import io.netty.bootstrap.Bootstrap;
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.*;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.rtsp.RtspDecoder;
//import io.netty.handler.timeout.ReadTimeoutHandler;
//import io.netty.util.concurrent.DefaultEventExecutorGroup;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.ScheduledExecutorService;
//
//@Slf4j
//public class RtspServer {
//
//    private int port;
//
//    ChannelFuture channelFuture;
//
//    EventLoopGroup eventLoopGroup;
//    StreamManager streamManager;
//    int handlerThreadPoolSize;
//
//    public static final Logger log = LoggerFactory.getLogger(RtspNettyServer.class);
//    private static Bootstrap udpRtpstrap = new Bootstrap();
//    private static Bootstrap udpRtcpstrap = new Bootstrap();
//    public static Channel rtpChannel;
//    public static Channel rtcpChannel;
//    public static int RTSP_IDLE_TIME;						//读和写的超时时间
//    public static int RTCP_IDLE_TIME;
//    public static int RTP_IDLE_TIME;
//    public static ExecutorService EXECUTOR;					//处理的线程池
//    public static int WORKER_GROUP;							//worker的线程数
//    public static ScheduledExecutorService SCHEDULED_EXECUTOR;		//定时线程，用来定时发送RTCP包
//    public static int SCHEDULE_RTCP_SR_TIME;				//定时发送RTCP SR的间隔时间
//    public static String NEWTON_URL;
//
//    public static int rtpPort = 54000;
//    public static int rtspPort = 554;
//    public static String outputPath = null;
//    public static RetransmissionRequesterDelegate retransmissionRequesterDelegate;
//
//
//    public RtspServer(int port, StreamManager sm,int threadPoolSize) {
//        this.port = port;
//        this.streamManager = sm;
//        this.handlerThreadPoolSize = threadPoolSize;
//    }
//
//    public void run() throws Exception {
//        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try {
//            ServerBootstrap b = new ServerBootstrap(); // (2)
//            b.group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class) // (3)
//                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
//                        @Override
//                        public void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline()
//                                    .addLast(new RtspDecoder())   /** 添加netty自带的rtsp消息解析器 */
//                                    .addLast(new RtspHandler(streamManager))  /** 上一步将消息解析完成之后, 再交给自定义的处理器 */
//                                    .addLast(new ReadTimeoutHandler(30));   /** idle超时处理 */
//                        }
//                    })
//                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
//            ChannelFuture f = b.bind(port).sync(); // (7)
//            f.channel().closeFuture().sync();
//        } catch (Exception ex){
//        } finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
//        }
//
//        log.info("RTSP Server start , listen at :{}",port);
//
//    }
//
//    public void close() {
//        try {
//            channelFuture.channel().closeFuture().sync();
//            eventLoopGroup.shutdownGracefully();
//        } catch (Exception e) {
//            log.error("close rtmp server failed", e);
//        }
//    }
//}
