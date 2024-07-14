package com.micro.iotclouds.live2output.rtsp.helper;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.Callable;

import com.micro.iotclouds.communication.video.core.entities.Stream2;
import com.micro.iotclouds.live2output.rtsp.action.AnnounceAction;
import com.micro.iotclouds.live2output.rtsp.action.DescribeAction;
import com.micro.iotclouds.live2output.rtsp.action.GetParameterAction;
import com.micro.iotclouds.live2output.rtsp.action.OptionsAction;
import com.micro.iotclouds.live2output.rtsp.action.PauseAction;
import com.micro.iotclouds.live2output.rtsp.action.PlayAction;
import com.micro.iotclouds.live2output.rtsp.action.SetupAction;
import com.micro.iotclouds.live2output.rtsp.action.TeardownAction;
import com.micro.iotclouds.live2output.rtsp.config.ServerConfig;
import com.micro.iotclouds.live2output.rtsp.handler.RtspListener;
import com.micro.iotclouds.live2output.rtsp.session.DefaultSessionAccessor;
import com.micro.iotclouds.live2output.rtsp.session.RtspSessionAccessor;
import com.micro.iotclouds.live2output.rtsp.session.RtspSessionKeyFactory;
import com.micro.iotclouds.live2output.rtsp.session.SimpleRandomKeyFactory;
import com.micro.iotclouds.live2output.rtsp.start.RtspServerStart;
import com.micro.iotclouds.live2output.rtsp.streamhub.StreamHub;
import com.micro.iotclouds.live2output.rtsp.utils.MediaSdpInfo;
import com.micro.iotclouds.live2output.rtsp.utils.SdpParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.rtsp.RtspHeaderNames;
import io.netty.handler.codec.rtsp.RtspHeaderValues;
import io.netty.handler.codec.rtsp.RtspMethods;
import io.netty.handler.codec.rtsp.RtspResponseStatuses;
import io.netty.handler.codec.rtsp.RtspVersions;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * rtsp握手处理辅助类
 * 
 * @author Joe
 *
 */
@Slf4j
public class RtspRequestHelper implements RtspListener{

	public static final String SERVER = "RtspServer";

	public static final String REQUIRE_VALUE_NGOD_R2 = "com.comcast.ngod.r2";

	public static final String REQUIRE_VALUE_NGOD_C1 = "com.comcast.ngod.c1";

	private String media = "h264";

	public static final RtspSessionAccessor sessionAccessor = new DefaultSessionAccessor();

	public static final RtspSessionKeyFactory keyFactory = new SimpleRandomKeyFactory();

	private String strDeviceid = "123456";

	private ServerConfig serverConfig = new ServerConfig();

	private static RtspRequestHelper rtspHelper;
	
	private RtspPlayHelper objRtspPlayHelper;
	
	private Channel channel;
//	private Channel rtpchn;
//	
//	private Channel rtcpchn;
	
//	private static int localrtpport = 54000;
	
	private int remotertpport = 0;
	private int remotertcpport = 0;
	private String strremoteip;
	InetSocketAddress dstaddr;

	public static RtspRequestHelper getInstance(Channel channel) {
		if (rtspHelper == null) {
			rtspHelper = new RtspRequestHelper(channel);
		}
//		if(objRtspPlayHelper==null) {
//			objRtspPlayHelper = new RtspPlayHelper(channel);
//		}
//		
		return rtspHelper;
	}

	/**
	 * @param channel2
	 */
	public RtspRequestHelper(Channel channel2) {
		this.channel = channel2;
		objRtspPlayHelper = new RtspPlayHelper(channel);
	}
	
	/**
	 * rtsphandler逻辑处理
	 * 
	 * @param request
	 * @param stream 
	 * @param channel
	 * @param stream
	 */
	@Override
	public void onRtspRequest(HttpRequest request, Stream2 stream) {
		// log.debug("Receive request ==> \n " + request);
		FullHttpResponse response = new DefaultFullHttpResponse(RtspVersions.RTSP_1_0, RtspResponseStatuses.OK);
		try {
			if (request.getMethod().equals(RtspMethods.SETUP)) {
				request.headers().set(RtspHeaderNames.REQUIRE, RtspRequestHelper.REQUIRE_VALUE_NGOD_R2);
				//request.headers().set(RtspHeaderNames.TRANSPORT, RtspHeaderNames.TRANSPORT);
				request.headers().set("OnDemandSessionId", "OnDemandSessionId");
				String transport = request.headers().get(RtspHeaderNames.TRANSPORT);
				transport = transport.toLowerCase();
	
				String[] strlist = transport.split(";");
				if (strlist.length > 0 && strlist[0].contains("rtp/avp")) {
					for (String i : strlist) {
						if (i.startsWith("client_port")) {
							String[] strclientport = i.split("=|-");
	
							remotertpport = Integer.parseInt(strclientport[1]);
							remotertcpport = Integer.parseInt(strclientport[2]);
							strremoteip = ((InetSocketAddress) channel.remoteAddress()).getHostString();
	
							System.out.println(remotertpport);
							System.out.println(remotertcpport);
							System.out.println(strremoteip);
							if (null == dstaddr) {
								dstaddr = new InetSocketAddress(strremoteip, remotertpport);
							}
							break;
						}
					}
				}
				onSetupRequest(request, channel);

			} else if (request.getMethod().equals(RtspMethods.PLAY)) {
				request.headers().set(RtspHeaderNames.REQUIRE, RtspRequestHelper.REQUIRE_VALUE_NGOD_R2);
				request.headers().set(RtspHeaderNames.TRANSPORT, RtspHeaderNames.TRANSPORT);
				request.headers().set("OnDemandSessionId", "OnDemandSessionId");
				onPlayRequest(request, channel);
				RtspPlayHelper.EXECUTOR.execute(new Runnable() {
					@Override
					public void run() {
						objRtspPlayHelper.playH264(stream, channel,dstaddr);
						//objRtspPlayHelper.playH264(stream, channel);
//						playH264(videoFile, rtpTimestamp);
						//RtspPlayHelper.playH264(stream, channel);
//						sendVideoSenderReport();			//发送视频的SR报告
//						StreamHub.EnterStream(strDeviceid, this);
					}
				});
			} else if (request.getMethod().equals(RtspMethods.PAUSE)) {

				final String cseq = response.headers().get(RtspHeaderNames.CSEQ);
				if (cseq != null) {
					response.headers().add(RtspHeaderNames.CSEQ, cseq);
				}
				request.headers().set(RtspHeaderNames.REQUIRE, RtspRequestHelper.REQUIRE_VALUE_NGOD_R2);
				request.headers().set("OnDemandSessionId", "OnDemandSessionId");
				onPauseRequest(request, channel);
			} else if (request.getMethod().equals(RtspMethods.GET_PARAMETER)) {
				onGetParameterRequest(request, channel);
			} else if (request.getMethod().equals(RtspMethods.TEARDOWN)) {
				onTeardownRequest(request, channel);
			} else if (request.getMethod().equals(RtspMethods.OPTIONS)) {
				// request.headers().set(RtspHeaderNames.REQUIRE,
				// RtspController.REQUIRE_VALUE_NGOD_R2);
				// request.headers().set(RtspHeaderNames.TRANSPORT, RtspHeaderNames.TRANSPORT);
				// request.headers().set("OnDemandSessionId", "OnDemandSessionId");
				onOptionRequest(request, channel);
			} else if (request.getMethod().equals(RtspMethods.DESCRIBE)) {
				log.debug("describe");
//				InetSocketAddress addr = (InetSocketAddress) channel.localAddress();
//
//				// 默认是H265
//				String sdp = String.format("c=IN IP4 %s \nm=video 0 RTP/AVP 96\na=rtpmap:96 H265/90000\n"
//						+ "a=fmtp:96 packetization-mode=1; sprop-parameter-sets=Z0IAH5Y1QKALdNwEBAQI,aM4xsg==; profile-level-id=42001F\n"
//						+ "a=control:streamid=0\n" + "m=audio 0 RTP/AVP 97\na=rtpmap:97 MPEG4-GENERIC/16000\n"
//						+ "a=fmtp:97 streamtype=5; profile-level-id=15; mode=AAC-hbr; config=140856e500; sizeLength=13; indexLength=3; indexDeltaLength=3; Profile=1;\n"
//						+ "a=control:streamid=1\n", addr.getHostString());
//				if ("h264".equals(media)) {
//					sdp = String.format("c=IN IP4 %s \nm=video 0 RTP/AVP 96\na=rtpmap:96 H264/90000\n"
//							+ "a=fmtp:96 packetization-mode=1; sprop-parameter-sets=Z0IAH5Y1QKALdNwEBAQI,aM4xsg==; profile-level-id=42001F\n"
//							+ "a=control:streamid=0\n" + "m=audio 0 RTP/AVP 97\na=rtpmap:97 MPEG4-GENERIC/16000\n"
//							+ "a=fmtp:97 streamtype=5; profile-level-id=15; mode=AAC-hbr; config=140856e500; sizeLength=13; indexLength=3; indexDeltaLength=3; Profile=1;\n"
//							+ "a=control:streamid=1\n", addr.getHostString());
//				} else if ("h265".equals(media)) {
//
//				}
//
//				response.headers().add(RtspHeaderNames.CONTENT_TYPE, "application/sdp");
//				response.content().writeCharSequence(sdp, CharsetUtil.UTF_8);
//				response.headers().add(RtspHeaderNames.CONTENT_LENGTH, response.content().writerIndex());
//				final String cseq = request.headers().get(RtspHeaderNames.CSEQ);
//				if (cseq != null) {
//					response.headers().add(RtspHeaderNames.CSEQ, cseq);
//				}
//				response.headers().set(RtspHeaderNames.CONNECTION, RtspHeaderValues.KEEP_ALIVE);
//				log.debug("DESCRIBE response content =====> \n" + response.content().toString(CharsetUtil.UTF_8));
//				InetSocketAddress addr = (InetSocketAddress) channel.localAddress();
//				String sdp = String.format("c=IN IP4 %s \nm=video 0 RTP/AVP 96\na=rtpmap:96 H264/90000\n",
//						addr.getHostString());
//				response.headers().add(RtspHeaderNames.CONTENT_TYPE, "application/sdp");
//				response.content().writeCharSequence(sdp, CharsetUtil.UTF_8);
//				response.headers().add(RtspHeaderNames.CONTENT_LENGTH, response.content().writerIndex());
				 //  System.out.println(response);
//				channel.writeAndFlush(response);
				
				this.onDescribeRequest(request, channel);
			} else if (request.getMethod().equals(RtspMethods.ANNOUNCE)) {
//				onAnnounceRequest(request, channel);

				final String cseq = response.headers().get(RtspHeaderNames.CSEQ);
				if (cseq != null) {
					response.headers().add(RtspHeaderNames.CSEQ, cseq);
				}
				response.headers().set(RtspHeaderNames.CONNECTION, RtspHeaderValues.KEEP_ALIVE);
				   System.out.println(response.content().toString(CharsetUtil.UTF_8));
				channel.writeAndFlush(response);
			}
         
		} catch (Exception e) {
			log.error("Unexpected error during processing,Caused by ", e);
		}
	}

	/**
	 * rtsp的setup逻辑处理
	 * 
	 * @param request
	 * @param channel
	 */
	private void onSetupRequest(HttpRequest request, Channel channel) {
		try {
			Callable<FullHttpResponse> action = new SetupAction(serverConfig, request);
			FullHttpResponse setupResponse = action.call();

			log.debug("setup response header =====> \n" + setupResponse);
			log.debug("setup response content =====> \n" + setupResponse.content().toString(CharsetUtil.UTF_8));
			System.out.println(setupResponse);
			channel.writeAndFlush(setupResponse);
		} catch (Exception e) {
			log.error("Setup Request Handle Error.........", e);
		}
	}

	/**
	 * rtsp的play逻辑处理
	 * 
	 * @param request
	 * @param channel
	 */
	private void onPlayRequest(HttpRequest request, Channel channel) {
		try {
			Callable<HttpResponse> action = new PlayAction(request);
			HttpResponse playResponse = action.call();
			log.debug("play response =====> \n" + playResponse);
			System.out.println("play response =====> \n" +playResponse);
			channel.writeAndFlush(playResponse);
			// StreamHub.EnterStream(strDeviceid, this);
//		if (0 == first) {
//			first = 1;
//		///	strDevice = byteBuf.toString(CharsetUtil.UTF_8);
//			System.out.println(strDeviceid);
//			map = StreamHub.GetStream(strDeviceid);
//		} else {
//		if (map == null) {
//			map = StreamHub.GetStream(strDeviceid);
//		}
//		 StreamHub.EnterStream(strDeviceid, this);
		} catch (Exception e) {
			log.error("Play Request Handle Error.........", e);
		}
	}
	/**
	 * rtsp的play逻辑处理
	 * 
	 * @param request
	 * @param channel
	 */
	private void onDescribeRequest(HttpRequest request, Channel channel) {
		try {
			InetSocketAddress addr = (InetSocketAddress) channel.localAddress();
			Callable<HttpResponse> action = new DescribeAction(request,addr);
			HttpResponse describeResponse = action.call();
			log.debug("play response =====> \n" + describeResponse);
			 System.out.println("=============================================> \n\r");
			   System.out.println("Describe response header =====>" + describeResponse);
			channel.writeAndFlush(describeResponse);
			//sendAnswer(channel, (FullHttpRequest) request, describeResponse);
		} catch (Exception e) {
			log.error("Play Request Handle Error.........", e);
		}
	}
	/**
	 * rtsp的Announce逻辑处理
	 * 
	 * @param request
	 * @param channel
	 */
	private void onAnnounceRequest(HttpRequest request, Channel channel) {
//    try {
//      Callable<HttpResponse> action = new AnnounceResAction(request);
//      HttpResponse playResponse = action.call();
//      log.debug("play response =====> \n" + playResponse);
//      channel.writeAndFlush(playResponse);
//    } catch (Exception e) {
//      log.error("Play Request Handle Error.........", e);
//    }
		log.debug("announce");
		FullHttpRequest r = (FullHttpRequest) request;
		ByteBuf content = r.content();
		byte[] sdp = new byte[content.readableBytes()];
		content.readBytes(sdp);
//  	
		Map<String, MediaSdpInfo> mediaSdpInfoMap = SdpParser.parse(new String(sdp)); // 解析出音视频相关参数
		FullHttpResponse response = new DefaultFullHttpResponse(RtspVersions.RTSP_1_0, RtspResponseStatuses.OK);
		if (mediaSdpInfoMap == null || mediaSdpInfoMap.size() == 0
				|| ((mediaSdpInfoMap != null && mediaSdpInfoMap.containsKey("video")
						&& !mediaSdpInfoMap.get("video").getCodec().equals("H264"))
						&& !mediaSdpInfoMap.get("video").getCodec().equals("H265"))
				|| ((mediaSdpInfoMap != null && mediaSdpInfoMap.containsKey("audio")
						&& !mediaSdpInfoMap.get("audio").getCodec().equals("MPEG4-GENERIC")))) {
			log.error("error: ANNOUNCE 415, Unsupported Media Type");
			response.setStatus(RtspResponseStatuses.UNSUPPORTED_MEDIA_TYPE); // 不支持的媒体类型
			sendAnswer(channel, r, response);
			// closeThisClient();
			return;
		}
		response.headers().set(RtspHeaderNames.REQUIRE, RtspRequestHelper.REQUIRE_VALUE_NGOD_R2);
		sendAnswer(channel, r, response);
	}

	/**
	 *      指令應答
	 * 
	 * @param channel
	 * @param req
	 * @param rep
	 */
	private void sendAnswer(Channel channel, FullHttpRequest req, FullHttpResponse rep) {
		final String cseq = req.headers().get(RtspHeaderNames.CSEQ);
		if (cseq != null) {
			rep.headers().add(RtspHeaderNames.CSEQ, cseq);
		}
		final String session = req.headers().get(RtspHeaderNames.SESSION);
		if (session != null) {
			rep.headers().add(RtspHeaderNames.SESSION, session);
		}
		if (!HttpUtil.isKeepAlive(req)) {
			channel.writeAndFlush(rep).addListener(ChannelFutureListener.CLOSE);
		} else {
			rep.headers().set(RtspHeaderNames.CONNECTION, RtspHeaderValues.KEEP_ALIVE);
			channel.writeAndFlush(rep);
		}
	}
//    public void closeThisClient(Channel channel)
//    {
//        if (this.channel..isActive())
//        {
//            log.debug("close this client {}", this.chn);
//            this.channel.close();
//        }
//    }

	/**
	 * rtsp的Pause逻辑处理
	 * 
	 * @param request
	 * @param channel
	 */
	private void onPauseRequest(HttpRequest request, Channel channel) {
		try {
			Callable<HttpResponse> responseAction = new PauseAction(request);
			HttpResponse pauseResponse = responseAction.call();
			log.debug("pause response header =====> \n" + pauseResponse);

			Callable<HttpRequest> announceAction = new AnnounceAction(request);
			HttpRequest announceRequest = announceAction.call();
			log.debug("announce request =====> \n" + announceRequest);

			channel.writeAndFlush(pauseResponse);
			channel.writeAndFlush(announceRequest);
		} catch (Exception e) {
			log.error("Pause Request Handle Error.........", e);
		}

	}

	/**
	 * rtsp的GetParameter逻辑处理
	 * 
	 * @param request
	 * @param channel
	 */
	private void onGetParameterRequest(HttpRequest request, Channel channel) {
		try {
			Callable<HttpResponse> action = new GetParameterAction(request);
			HttpResponse response = action.call();
			log.debug("get_parameter response =====> \n" + response);
			channel.writeAndFlush(response);
		} catch (Exception e) {
			log.error("get_parameter Request Handle Error.........", e);
		}
	}

	/**
	 * rtsp的Teardown逻辑处理
	 * 
	 * @param request
	 * @param channel
	 */
	private void onTeardownRequest(HttpRequest request, Channel channel) {
		try {
			Callable<HttpResponse> action = new TeardownAction(request);
			HttpResponse response = action.call();
			log.debug("teardown response =====> \n" + response);
			System.out.println(response);
			channel.writeAndFlush(response);
		} catch (Exception e) {
			log.error("teardown Request Handle Error.........", e);
		}
	}

	/**
	 * rtsp的Option逻辑处理
	 * 
	 * @param request
	 * @param channel
	 */
	private void onOptionRequest(HttpRequest request, Channel channel) {
		try {
			///// TODO 2022-02-07
//      Callable<FullHttpResponse> action = new SetupAction(serverConfig, request);
			Callable<FullHttpResponse> action = new OptionsAction(serverConfig, request);
			FullHttpResponse optionResponse = action.call();

			log.debug("options response header =====> \n" + optionResponse);
			 System.out.println("=============================================> \n\r");
			   System.out.println("options response header =====>" + optionResponse);
			// log.debug("setup response content =====> \n" +
			// setupResponse.content().toString(CharsetUtil.UTF_8));
			channel.writeAndFlush(optionResponse);
		} catch (Exception e) {
			log.error("Setup Request Handle Error.........", e);
		}
	}

	/**
	 * 檢測流是否存在
	 * 
	 * @param ctx
	 * @param uri
	 */
	public void httpResponseStreamNotExist(ChannelHandlerContext ctx, String uri) {
		ByteBuf body = Unpooled.wrappedBuffer(("stream [" + uri + "] not exist").getBytes());
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
				HttpResponseStatus.NOT_FOUND, body);
		response.headers().set(CONTENT_TYPE, "text/plain");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void onRtspResponse(HttpResponse response) {
		// TODO Auto-generated method stub
		
	}

}
