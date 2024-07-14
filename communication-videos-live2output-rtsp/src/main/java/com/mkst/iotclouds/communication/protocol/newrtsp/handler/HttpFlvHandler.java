//package com.mkst.iotclouds.communication.protocol.newrtsp.handler;
//
//import com.micro.iotclouds.modules.rtsp.streamhub.StreamFrame;
//import com.micro.iotclouds.modules.rtsp.streamhub.StreamFrameSink;
//import com.micro.iotclouds.modules.rtsp.streamhub.StreamHub;
//
//import io.netty.buffer.Unpooled;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFutureListener;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.codec.http.DefaultFullHttpResponse;
//import io.netty.handler.codec.http.DefaultHttpResponse;
//import io.netty.handler.codec.http.FullHttpRequest;
//import io.netty.handler.codec.http.FullHttpResponse;
//import io.netty.handler.codec.http.HttpHeaderNames;
//import io.netty.handler.codec.http.HttpHeaderValues;
//import io.netty.handler.codec.http.HttpMethod;
//import io.netty.handler.codec.http.HttpResponse;
//import io.netty.handler.codec.http.HttpResponseStatus;
//import io.netty.handler.codec.http.HttpVersion;
//import io.netty.handler.codec.http.QueryStringDecoder;
//import io.netty.util.CharsetUtil;
//import io.netty.util.ReferenceCountUtil;
//import io.netty.util.internal.StringUtil;
//
//// http://172.16.64.92:1984/live/liveflv?deviceid=123abcdef32153421
//// http://106.15.184.203:1984/live/liveflv?deviceid=123abcdef32153421
//public class HttpFlvHandler extends SimpleChannelInboundHandler<FullHttpRequest> implements StreamFrameSink {
//	protected String strDeviceid = "";
//	protected Channel chn;
//
//	@Override
//	public void CloseThisClient() {
//		if (this.chn.isActive()) {
//			System.out.println("i kill myself");
//			this.chn.close();
//		}
//	}
//
//	@Override
//	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest o) throws Exception {
//		if (!o.decoderResult().isSuccess()) {
//			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
//			return;
//		}
//		if (o.method() != HttpMethod.GET) {
//			sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
//			return;
//		}
//
//		QueryStringDecoder uri = new QueryStringDecoder(o.uri());
//		System.out.println(uri.path());
//		if (!uri.path().equals("/live/liveflv") || !uri.parameters().containsKey("deviceid")) {
//			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
//			return;
//		}
//		strDeviceid = uri.parameters().get("deviceid").get(0);
//		if (StringUtil.isNullOrEmpty(strDeviceid)) {
//			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
//			return;
//		}
//
//		HttpResponse rsp = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
//
//		rsp.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE)
//				.set(HttpHeaderNames.CONTENT_TYPE, "video/x-flv")
//				// .set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
//				.set(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED)
//				.set(HttpHeaderNames.SERVER, "wangxiaohui");
//
//		ctx.writeAndFlush(rsp);
//		StreamHub.EnterStream(strDeviceid, this);
//		System.out.printf("%s enter stream %s from http\n", chn.id(), strDeviceid);
//	}
//
//	@Override
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		super.channelActive(ctx);
//		chn = ctx.channel();
//		System.out.printf("%s new connection %s\n", chn.id(), Thread.currentThread().getName());
//	}
//
//	@Override
//	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//		super.channelInactive(ctx);
//		System.out.printf("%s i am dead\n", ctx.channel().id());
//		if (!StringUtil.isNullOrEmpty(strDeviceid)) {
//			// from stream hub clear this info
//			System.out.printf("%s will leave stream %s \n", chn.id(), strDeviceid);
//			StreamHub.LeaveStream(strDeviceid, this);
//			strDeviceid = "";
//		}
//	}
//
//	@Override
//	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		System.out.println("exception caught");
//		cause.printStackTrace();
//		ctx.channel().close();
//	}
//
//	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
//		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
//				Unpooled.copiedBuffer("Failure: " + status + "\r\n", CharsetUtil.UTF_8));
//		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
//
//		// Close the connection as soon as the error message is sent.
//		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
//	}
//
//	@Override
//	public boolean WriteFrame(StreamFrame frame) {
//		// System.out.printf("%s writeframe active %b writeable %b \n", chn.id(),
//		// chn.isActive(), chn.isWritable());
//		if (this.chn.isActive() && this.chn.isWritable()) {
//			ReferenceCountUtil.retain(frame);
//			chn.writeAndFlush(frame);
//			return true;
//		}
//		return false;
//	}
//}
