package com.mkst.iotclouds.communication.protocol.websocket.handler;
//package com.mkst.iotclouds.communication.protocol.websocket.handler;
//
//import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import cn.hutool.http.HttpUtil;
//import com.google.common.base.Splitter;
//import com.micro.iotclouds.communication.video.core.domain.MyLiveConfig;
//import com.micro.iotclouds.communication.video.core.entities.Stream;
//import com.micro.iotclouds.communication.video.core.entities.StreamName;
//import com.micro.iotclouds.communication.video.core.manager.StreamManager;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelFutureListener;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.codec.http.DefaultFullHttpResponse;
//import io.netty.handler.codec.http.DefaultHttpResponse;
//import io.netty.handler.codec.http.FullHttpRequest;
//import io.netty.handler.codec.http.HttpContent;
//import io.netty.handler.codec.http.HttpHeaderNames;
//import io.netty.handler.codec.http.HttpHeaderValues;
//import io.netty.handler.codec.http.HttpObject;
//import io.netty.handler.codec.http.HttpRequest;
//import io.netty.handler.codec.http.HttpResponse;
//import io.netty.handler.codec.http.HttpResponseStatus;
//import io.netty.handler.codec.http.HttpVersion;
//import io.netty.handler.codec.http.QueryStringDecoder;
//import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
//import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
//import lombok.extern.slf4j.Slf4j;
//
//import static io.netty.handler.codec.http.HttpHeaderNames.*;
//
///**
// * @author longyubo 2020年1月7日 下午3:19:43
// **/
//@Slf4j
//public class WssFlvHandler extends SimpleChannelInboundHandler<Object> {
//
//	private StreamManager streamManager;
//	private WebSocketServerHandshaker handshaker;
//
//	public WssFlvHandler(StreamManager streamManager) {
//		this.streamManager = streamManager;
//	}
//
//	@Override
//	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//		if (msg instanceof FullHttpRequest) {
//			FullHttpRequest req = (FullHttpRequest) msg;
////			Map<String, String> parmMap = new RequestParser(msg).parse();
//			QueryStringDecoder decoder = new QueryStringDecoder(req.uri());
////
////			// 判断请求uri
////			if (!decoder.path().contains("/live")) {
////				log.info("uri有误");
////				sendError(ctx, HttpResponseStatus.BAD_REQUEST);
////				return;
////			}
////		if (msg instanceof HttpRequest) {
////			FullHttpRequest req = (HttpRequest) msg;
//
//			String uri = req.uri();
//			List<String> appAndStreamName = Splitter.on("/").omitEmptyStrings().splitToList(uri);
//			if (appAndStreamName.size() != 2) {
//				httpResponseStreamNotExist(ctx, uri);
//				return;
//			}
//
//
//			String app=appAndStreamName.get(0);
//			String streamName= appAndStreamName.get(1);
//			if(streamName.endsWith(".flv")) {
//				streamName=streamName.substring(0, streamName.length()-4);
//			}
//			StreamName sn = new StreamName(app, streamName);
//			log.info("http stream :{} requested",sn);
//			Stream stream = streamManager.getStream(sn);
//
////			if (stream == null) {
////				httpResponseStreamNotExist(ctx, uri);
////				Map<String,Object> map = new HashMap<>();
////				map.put("token",streamName);
////				HttpUtil.post("http://" + MyLiveConfig.getInstance().getIotLinkip() + ":" + MyLiveConfig.getInstance().getIotLinkport() + "/" + MyLiveConfig.getInstance().getIotLink() + "/open/noFlv",map);
////				return;
////			}
////			DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
////			response.headers().set(CONTENT_TYPE, "video/x-flv");
////			response.headers().set(TRANSFER_ENCODING, "chunked");
////			response.headers().set( ACCESS_CONTROL_ALLOW_ORIGIN, "*");
////			response.headers().set( ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept");
////			response.headers().set( ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT,DELETE");
////			ctx.writeAndFlush(response);
////
////			stream.addHttpFlvSubscriber(ctx.channel());
//
//				// websocket握手，请求升级
//				
//				// 参数分别是ws地址，子协议，是否扩展，最大frame长度
//				WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
//						getWebSocketLocation(req), null, true, 5 * 1024 * 1024);
//				handshaker = factory.newHandshaker(req);
//				if (handshaker == null) {
//					WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
//				} else {
//					handshaker.handshake(ctx.channel(), req);
//				//	sendWssReqHeader(ctx);
//				//	stream.addHttpFlvSubscriber(ctx.channel());
//					//mediaService.playForWs(camera, ctx,streamManager);
//				}
//		}
//
//		if (msg instanceof HttpContent) {
//
//		}
//
//	}
//
//	private void httpResponseStreamNotExist(ChannelHandlerContext ctx, String uri) {
//		ByteBuf body = Unpooled.wrappedBuffer(("stream [" + uri + "] not exist").getBytes());
//		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
//				HttpResponseStatus.NOT_FOUND, body);
//		response.headers().set(CONTENT_TYPE, "text/plain");
//		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
//	}
//	/**
//	 * ws握手地址
//	 */
//	private String getWebSocketLocation(FullHttpRequest request) {
//		String location = request.headers().get(HttpHeaderNames.HOST) + request.uri();
//		return "ws://" + location;
//	}
//	/**
//	 * 发送req header，告知浏览器是flv格式
//	 * @param ctx
//	 */
//	public static void sendWssReqHeader(ChannelHandlerContext ctx) {
//		HttpResponse rsp = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
//
//		rsp.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE)
//				.set(HttpHeaderNames.CONTENT_TYPE, "video/ws-flv").set(HttpHeaderNames.ACCEPT_RANGES, "bytes")
//				.set(HttpHeaderNames.PRAGMA, "no-cache").set(HttpHeaderNames.CACHE_CONTROL, "no-cache")
//				.set(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED).set(HttpHeaderNames.SERVER, "zhang");
//		ctx.writeAndFlush(rsp);
//	}
//}
