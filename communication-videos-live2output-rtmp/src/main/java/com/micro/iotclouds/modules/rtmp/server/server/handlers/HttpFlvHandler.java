package com.micro.iotclouds.modules.rtmp.server.server.handlers;

import static io.netty.handler.codec.http.HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS;
import static io.netty.handler.codec.http.HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS;
import static io.netty.handler.codec.http.HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderNames.TRANSFER_ENCODING;

import java.text.SimpleDateFormat;
import java.util.*;

import com.google.common.base.Splitter;
import com.micro.iotclouds.communication.video.core.domain.MyLiveConfig;
import com.micro.iotclouds.communication.video.core.entities.Stream2;
import com.micro.iotclouds.communication.video.core.entities.StreamName;
import com.micro.iotclouds.communication.video.core.manager.StreamManager;

import cn.hutool.http.HttpUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import lombok.extern.slf4j.Slf4j;

/**
 * @author longyubo 2020年1月7日 下午3:19:43
 **/
@Slf4j
public class HttpFlvHandler extends SimpleChannelInboundHandler<HttpObject> {

	StreamManager streamManager;

//	@Autowired
//	private static MyLiveConfig INSTANCE;// = SpringUtil.getBean(MyLiveConfig.class);

	public HttpFlvHandler(StreamManager streamManager) {
		this.streamManager = streamManager;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		if (msg instanceof HttpRequest) {
			HttpRequest req = (HttpRequest) msg;

			String uri = req.uri();
			List<String> appAndStreamName = Splitter.on("/").omitEmptyStrings().splitToList(uri);
			if (appAndStreamName.size() != 2) {
				httpResponseStreamNotExist(ctx, uri);
				return;
			}

			String app = appAndStreamName.get(0);
			String streamName = appAndStreamName.get(1);
			if (streamName.endsWith(".flv")) {
				streamName = streamName.substring(0, streamName.length() - 4);
			}
//			else {
//				streamName = streamName.substring(0,streamName.lastIndexOf("."));
//			}
			StreamName sn = new StreamName(app, streamName);
			log.info("http stream :{} requested", sn);
			Stream2 stream = streamManager.getStream(sn);
			System.out.println("请求uri: " + req.uri());
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd hh:mm:ss");
			String format = simpleDateFormat.format(date);
			System.out.println("请求时间："+format);
			if(stream == null){
				System.out.println("流为空...");
				httpResponseStreamNotExist(ctx,uri);
				return;
			}else {
				System.out.println("取到流...");
			}
			if (stream == null) {
				httpResponseStreamNotExist(ctx, uri);
				Map<String, Object> map = new HashMap();
				map.put("token", streamName);
				HttpUtil.post("http://" + MyLiveConfig.getInstance().getIotLinkip() + ":"
						+ MyLiveConfig.getInstance().getIotLinkport() + "/" + MyLiveConfig.getInstance().getIotLink()
						+ "/open/noFlv", map);
				return;
			}
			DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
			response.headers().set(CONTENT_TYPE, "video/x-flv");
			response.headers().set(TRANSFER_ENCODING, "chunked");
			response.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
			response.headers().set(ACCESS_CONTROL_ALLOW_METHODS, "*");
			response.headers().set(ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept");
			response.headers().set(ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT,DELETE");
			ctx.writeAndFlush(response);

			stream.addHttpFlvSubscriber(ctx.channel());

		}

		if (msg instanceof HttpContent) {

		}

	}

	private void httpResponseStreamNotExist(ChannelHandlerContext ctx, String uri) {
		ByteBuf body = Unpooled.wrappedBuffer(("stream [" + uri + "] not exist").getBytes());
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
				HttpResponseStatus.INTERNAL_SERVER_ERROR, body);
		response.headers().set(CONTENT_TYPE, "text/plain");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

}
