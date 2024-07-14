//package com.micro.iotclouds.modules.rtmp.server.server.handlers;
//
//import cn.hutool.http.HttpUtil;
//import com.google.common.base.Splitter;
//import com.micro.iotclouds.modules.rtmp.common.cfg.MyLiveConfig;
//import com.micro.iotclouds.modules.rtmp.server.server.entities.Stream;
//import com.micro.iotclouds.modules.rtmp.server.server.entities.StreamName;
//import com.micro.iotclouds.modules.rtmp.server.server.manager.StreamManager;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.*;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.handler.codec.http.*;
//import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
//import io.netty.handler.stream.ChunkedWriteHandler;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static io.netty.handler.codec.http.HttpHeaderNames.*;
//import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
//
//@Slf4j
//public class WssHandler extends ChannelInitializer<SocketChannel> {
//
//    StreamManager streamManager;
//
//    public WssHandler(StreamManager streamManager) {
//        this.streamManager = streamManager;
//    }
//
//    @Override
//    public void initChannel(SocketChannel ch) throws Exception {//2
//        ChannelPipeline pipeline = ch.pipeline();
//
//        pipeline.addLast(new HttpServerCodec());
//        pipeline.addLast(new HttpObjectAggregator(64*1024));
//        pipeline.addLast(new ChunkedWriteHandler());
////        pipeline.addLast(new HttpRequestHandler("/ws"));
//        pipeline.addLast(new WebSocketServerProtocolHandler("/wss"));
//        pipeline.addLast(new WebSocketFrameHandler(streamManager));
//
//    }
//
//}
