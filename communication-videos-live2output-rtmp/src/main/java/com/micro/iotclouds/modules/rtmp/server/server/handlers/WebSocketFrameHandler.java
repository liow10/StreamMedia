//package com.micro.iotclouds.modules.rtmp.server.server.handlers;
//
//import com.google.common.base.Splitter;
//import com.micro.iotclouds.modules.rtmp.server.server.entities.Stream;
//import com.micro.iotclouds.modules.rtmp.server.server.entities.StreamName;
//import com.micro.iotclouds.modules.rtmp.server.server.manager.StreamManager;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.*;
//import io.netty.channel.group.ChannelGroup;
//import io.netty.channel.group.DefaultChannelGroup;
//import io.netty.handler.codec.http.*;
//import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
//import io.netty.handler.codec.http.websocketx.WebSocketFrame;
//import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
//import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
//import io.netty.util.CharsetUtil;
//import io.netty.util.concurrent.GlobalEventExecutor;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//
//import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;
//import static io.vertx.core.http.HttpHeaders.TRANSFER_ENCODING;
//
//@Slf4j
//public class WebSocketFrameHandler extends
//        SimpleChannelInboundHandler<TextWebSocketFrame> {
//
//    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//
//    StreamManager streamManager;
//
//    public WebSocketFrameHandler(StreamManager streamManager) {
//        this.streamManager = streamManager;
//    }
//
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx,
//                                TextWebSocketFrame msg) throws Exception { // (1)
//
//        if (msg instanceof FullHttpRequest) {
//            handleHttpRequest(ctx, (FullHttpRequest) msg);
//        } else if (msg instanceof WebSocketFrame) {
//            ctx.fireChannelRead(((WebSocketFrame) msg).retain());
//        }
//        Channel incoming = ctx.channel();
//        for (Channel channel : channels) {
//            if (channel != incoming){
//                channel.writeAndFlush(new TextWebSocketFrame("[" + incoming.remoteAddress() + "]" + msg.text()));
//            } else {
//                channel.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text() ));
//            }
//        }
//
//
//    }
//
//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
//        Channel incoming = ctx.channel();
//
//        for (Channel channel : channels) {
//            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));
//        }
//        channels.add(ctx.channel());
//        System.out.println("Client:"+incoming.remoteAddress() +"加入");
//    }
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
//        Channel incoming = ctx.channel();
//        for (Channel channel : channels) {
//            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));
//        }
//        System.out.println("Client:"+incoming.remoteAddress() +"离开");
//        channels.remove(ctx.channel());
//    }
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
//        Channel incoming = ctx.channel();
//        System.out.println("Client:"+incoming.remoteAddress()+"在线");
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
//        Channel incoming = ctx.channel();
//        System.out.println("Client:"+incoming.remoteAddress()+"掉线");
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
//            throws Exception {
//        Channel incoming = ctx.channel();
//        System.out.println("Client:"+incoming.remoteAddress()+"异常");
//        // 当出现异常就关闭连接
//        cause.printStackTrace();
//        ctx.close();
//    }
//
//    /**
//     * 描述：处理Http请求，主要是完成HTTP协议到Websocket协议的升级
//     * @param ctx
//     * @param req
//     */
//    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
//        if (!req.decoderResult().isSuccess()) {
//            sendHttpResponse(ctx, req,
//                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
//            return;
//        }
//
//        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
//                "ws:/" + ctx.channel() + "/websocket", null, false);
//        WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
//        //   Constant.webSocketHandshakerMap.put(ctx.channel().id().asLongText(), handshaker);// 保存握手类到全局变量，方便以后关闭连接
//
//        if (handshaker == null) {
//            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
//        } else {
//            handshaker.handshake(ctx.channel(), req);
//        }
//    }
//
//    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
//        // 返回应答给客户端
//        if (res.status().code() != 200) {
//            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
//            res.content().writeBytes(buf);
//            buf.release();
//        }
//        // 如果是非Keep-Alive，关闭连接
//        boolean keepAlive = HttpUtil.isKeepAlive(req);
//        res.headers().set(CONTENT_TYPE, "video/ws-flv");
//        res.headers().set(TRANSFER_ENCODING, "chunked");
//        ChannelFuture f = ctx.channel().writeAndFlush(res);
//        if (!keepAlive) {
//            f.addListener(ChannelFutureListener.CLOSE);
//        }
//    }
//}
