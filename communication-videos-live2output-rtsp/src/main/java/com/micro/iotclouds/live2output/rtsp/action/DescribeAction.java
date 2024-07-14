package com.micro.iotclouds.live2output.rtsp.action;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

import com.micro.iotclouds.live2output.rtsp.helper.RtspRequestHelper;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.rtsp.RtspHeaderNames;
import io.netty.handler.codec.rtsp.RtspHeaderValues;
import io.netty.handler.codec.rtsp.RtspHeaders;
import io.netty.handler.codec.rtsp.RtspResponseStatuses;
import io.netty.handler.codec.rtsp.RtspVersions;
import io.netty.util.CharsetUtil;

public class DescribeAction implements Callable<HttpResponse> {
	
	private HttpRequest request = null;
	InetSocketAddress addr;

	public DescribeAction(HttpRequest request, InetSocketAddress addr) {
		this.request = request;
		this.addr = addr;
	}

	public HttpResponse call() throws Exception {
//    HttpResponse response = null;
//    response = new DefaultHttpResponse(RtspVersions.RTSP_1_0, RtspResponseStatuses.OK);

		FullHttpResponse response = new DefaultFullHttpResponse(RtspVersions.RTSP_1_0, RtspResponseStatuses.OK);

		response.headers().set(HttpHeaders.Names.SERVER, RtspRequestHelper.SERVER);
		response.headers().set(RtspHeaders.Names.CSEQ, this.request.headers().get(RtspHeaders.Names.CSEQ));
		//response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, "0");

		//InetSocketAddress addr = (InetSocketAddress) channel.localAddress();
		String sdp = String.format("c=IN IP4 %s \nm=video 0 RTP/AVP 96\na=rtpmap:96 H264/90000\n",
				addr.getHostString());
		response.headers().add(RtspHeaderNames.CONTENT_TYPE, "application/sdp");
		response.content().writeCharSequence(sdp, CharsetUtil.UTF_8);
		response.headers().add(RtspHeaderNames.CONTENT_LENGTH, response.content().writerIndex());
		response.headers().set(RtspHeaderNames.CONNECTION, RtspHeaderValues.KEEP_ALIVE);
		return response;
	}
}
