//package com.micro.iotclouds.modules.rtmp.server.server.handlers;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.InetSocketAddress;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.Collection;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.TreeMap;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.math.RandomUtils;
//import org.bouncycastle.util.Strings;
//import org.jitsi.impl.neomedia.rtcp.NACKPacket;
//import org.jitsi.impl.neomedia.rtcp.RTCPFBPacket;
//import org.jitsi.service.neomedia.RawPacket;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.alibaba.fastjson.JSONObject;
//import com.micro.iotclouds.modules.rtmp.server.server.manager.StreamManager;
//import com.mkst.iotclouds.communication.protocol.rtsp.RtspNettyServer;
//import com.mkst.iotclouds.communication.protocol.rtsp.handler.RtpHandler;
//import com.mkst.iotclouds.communication.protocol.rtsp.media.MediaSdpInfo;
//import com.mkst.iotclouds.communication.protocol.rtsp.rtcp.AudioSRRtcp;
//import com.mkst.iotclouds.communication.protocol.rtsp.rtcp.VideoSRRtcp;
//import com.mkst.iotclouds.communication.protocol.rtsp.rtp.RtpUtils;
//import com.mkst.iotclouds.communication.protocol.rtsp.transform.RetransmissionRequesterDelegate;
//import com.mkst.iotclouds.communication.protocol.rtsp.utils.HttpConnection;
//import com.mkst.iotclouds.communication.protocol.rtsp.utils.SdpParser;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFutureListener;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.channel.socket.DatagramPacket;
//import io.netty.handler.codec.http.DefaultFullHttpResponse;
//import io.netty.handler.codec.http.FullHttpRequest;
//import io.netty.handler.codec.http.FullHttpResponse;
//import io.netty.handler.codec.http.HttpUtil;
//import io.netty.handler.codec.http.QueryStringDecoder;
//import io.netty.handler.codec.rtsp.RtspHeaderNames;
//import io.netty.handler.codec.rtsp.RtspHeaderValues;
//import io.netty.handler.codec.rtsp.RtspMethods;
//import io.netty.handler.codec.rtsp.RtspResponseStatuses;
//import io.netty.handler.codec.rtsp.RtspVersions;
//import io.netty.util.CharsetUtil;
//import io.netty.util.internal.StringUtil;
//import net.sf.fmj.media.rtp.RTCPPacket;
//
//public class NewRtspHandler extends SimpleChannelInboundHandler<FullHttpRequest>
//{
//	public static final Logger log = LoggerFactory.getLogger(NewRtspHandler.class); 
//    private int remoteVideoRtpPort = 0;						//客户端Video的RTP端口
//    private int remoteVideoRtcpPort = 0;					//客户端Video的RTCP端口
//    private int remoteAudioRtpPort = 0;						//客户端Audio的RTP端口
//    private int remoteAudioRtcpPort = 0;					//客户端Audio的RTCP端口
//    private int videoSsrc = 0;					//如果是record，则由客户端带上来。如果是play，则由服务器下发下去
//    private int audioSsrc = 0;					//如果是record，则由客户端带上来。如果是play，则由服务器下发下去
//    public String strremoteip;								//客户端的IP地址
//    private String session;							
//    public InetSocketAddress dstVideoRtpAddr = null;		//Video目的客户端地址
//    public InetSocketAddress dstVideoRtcpAddr = null;		//Video目的客户端地址
//    public InetSocketAddress dstAudioRtpAddr = null;		//Audio目的客户端地址
//    public InetSocketAddress dstAudioRtcpAddr = null;		//Audio目的客户端地址
//    private volatile int isRtspAlive = 1;						//rtsp连接是否存在，如果不存在，则停止发送udp
//    private int fps = 25;								//默认帧率
//    private String media = "h264";						//默认媒体类型
//    private Map<String, MediaSdpInfo> mediaSdpInfoMap = null;
//    private String keyhash = "";
//    private Channel chn;								//RTSP channel
//    public ArrayBlockingQueue<byte[]> rtpQueue;			//存放音视频数据的阻塞队列
//    public TreeMap<Integer, byte[]> rtpCacheQueue;		//存放音视频播放的时候的发包历史数据
//    public ArrayBlockingQueue<RawPacket> rtcpQueue;			//存放rtcp的阻塞队列
//    public boolean isVideoRtpDetected = false;					//是否收到Video的udp探测包
//    public boolean isVideoRtcpDetected = false;				//是否收到Video的udp rtcp探测包
//    private VideoSRRtcp videoSRRtcp;					//视频发送端报告
//    private AudioSRRtcp audioSRRtcp;					//音频发送端报告
//    private RetransmissionRequesterDelegate retransmissionRequesterDelegate;
//
//    public NewRtspHandler(StreamManager streamManager) {
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception
//    {
//        chn = ctx.channel();
//        log.debug("{} new connection {}", chn.id(), Thread.currentThread().getName());
//        
//        rtcpQueue = new ArrayBlockingQueue<RawPacket>(50);
//        retransmissionRequesterDelegate = RtspNettyServer.retransmissionRequesterDelegate;
//        
//    }
//    
//    public void closeThisClient()
//    {
//        if (this.chn.isActive())
//        {
//            log.debug("close this client {}", this.chn);
//            this.chn.close();
//        }
//    }
//    
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
//    {
//    	log.debug("{}", cause.getMessage());
//        ctx.channel().close();
//    }
//    
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception
//    {
//        super.channelInactive(ctx);
//        log.debug("{} i am dead", ctx.channel().id());
//        
//        if (!StringUtil.isNullOrEmpty(keyhash))
//        {
//            // from stream hub clear this info
//            log.debug("{} will leave stream {} ", chn.id(), keyhash);
//            keyhash = "";
//        }
//    
//        RtpHandler.rtspHandlerMap.remove(String.valueOf(videoSsrc));
//        RtpHandler.rtspHandlerMap.remove(String.valueOf(audioSsrc));
//        
//        isRtspAlive = 0;
//    }
//    
//    private boolean checkUrl(FullHttpRequest r)
//    {
//        if (!StringUtil.isNullOrEmpty(keyhash))
//        {
//            return true;
//        }
//
//        QueryStringDecoder uri = new QueryStringDecoder(r.uri());
//
//        if (!uri.path().endsWith("/live") || !uri.parameters().containsKey("keyhash"))
//        {
//            return false;
//        }
//        
//        keyhash = (Strings.split(uri.toString(), '?')[1].split("###"))[0].substring(8);
//        
//        //设备keyhash，必须
//        if (StringUtil.isNullOrEmpty(keyhash))
//        {
//            return false;
//        }
//        
//        //媒体类型，非必须，值目前可选范围：264、aac
//        if (uri.parameters().get("media") != null) {
//        	media = uri.parameters().get("media").get(0);
//		}
//        if (!"h264".equals(media) && !"h265".equals(media)) {
//        	return false;
//		}
//        
//        //帧率，非必须
//        String fpsString = null;
//        if (uri.parameters().get("fps") != null) {
//        	fpsString = uri.parameters().get("fps").get(0);
//		}
//        if (fpsString != null && StringUtils.isNumeric(fpsString)) {
//        	fps = Integer.parseInt(fpsString);
//		}
//        
//        return true;
//    }
//
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest r) throws Exception
//    {
//    	FullHttpResponse o = new DefaultFullHttpResponse(RtspVersions.RTSP_1_0, RtspResponseStatuses.OK);
//        if (!r.decoderResult().isSuccess())
//        {
//            log.error("error: decode error, invalid rtsp request");
//            closeThisClient();
//            return;
//        }
//        if (false == checkUrl(r))
//        {
//            log.error("error: check url error, 451, Parameter Not Understood");
//            o.setStatus(RtspResponseStatuses.PARAMETER_NOT_UNDERSTOOD);	//Parameter Not Understood,参数无效
//            sendAnswer(ctx, r, o);
//            closeThisClient();
//            return;
//        }
//
//        if (r.method() == RtspMethods.OPTIONS) {
//        	log.debug("options");
//            o.headers().add(RtspHeaderValues.PUBLIC, "DESCRIBE, SETUP, PLAY, TEARDOWN, ANNOUNCE, RECORD, GET_PARAMETER");
//        } else if (r.method() == RtspMethods.DESCRIBE) {
//        	log.debug("describe");
//            InetSocketAddress addr = (InetSocketAddress) ctx.channel().localAddress();
//            
//            //默认是H265
//            String sdp = String.format("c=IN IP4 %s \nm=video 0 RTP/AVP 96\na=rtpmap:96 H265/90000\n"
//										+ "a=fmtp:96 packetization-mode=1; sprop-parameter-sets=Z0IAH5Y1QKALdNwEBAQI,aM4xsg==; profile-level-id=42001F\n"
//										+ "a=control:streamid=0\n"
//					        			+ "m=audio 0 RTP/AVP 97\na=rtpmap:97 MPEG4-GENERIC/16000\n"
//					                	+ "a=fmtp:97 streamtype=5; profile-level-id=15; mode=AAC-hbr; config=140856e500; sizeLength=13; indexLength=3; indexDeltaLength=3; Profile=1;\n"
//					        			+ "a=control:streamid=1\n", addr.getHostString());
//            if ("h264".equals(media)) {
//            	sdp = String.format("c=IN IP4 %s \nm=video 0 RTP/AVP 96\na=rtpmap:96 H264/90000\n"
//            						+ "a=fmtp:96 packetization-mode=1; sprop-parameter-sets=Z0IAH5Y1QKALdNwEBAQI,aM4xsg==; profile-level-id=42001F\n"
//            						+ "a=control:streamid=0\n"
//			            			+ "m=audio 0 RTP/AVP 97\na=rtpmap:97 MPEG4-GENERIC/16000\n"
//			                    	+ "a=fmtp:97 streamtype=5; profile-level-id=15; mode=AAC-hbr; config=140856e500; sizeLength=13; indexLength=3; indexDeltaLength=3; Profile=1;\n"
//			            			+ "a=control:streamid=1\n", addr.getHostString());
//            } else if ("h265".equals(media)) {
//            	
//            }
//            
//            o.headers().add(RtspHeaderNames.CONTENT_TYPE, "application/sdp");
//            o.content().writeCharSequence(sdp, CharsetUtil.UTF_8);
//            o.headers().add(RtspHeaderNames.CONTENT_LENGTH, o.content().writerIndex());
//        } else if (r.method() == RtspMethods.SETUP) {
//        	log.debug("setup");
//        	
//            String transport = r.headers().get(RtspHeaderNames.TRANSPORT);
//            transport = transport.toLowerCase();
//            
//            String uri = r.uri();
//            //streamid=0、streamid=1
//
//            String[] strlist = transport.split(";");
//            if (strlist.length > 0 && strlist[0].contains("rtp/avp"))
//            {
//                for(String i : strlist)
//                {
//                    if (i.startsWith("client_port")) {
//                    	if ((mediaSdpInfoMap != null && mediaSdpInfoMap.containsKey("video") && uri.endsWith(mediaSdpInfoMap.get("video").getControl()))
//                    			|| (mediaSdpInfoMap == null && uri.endsWith("streamid=0"))) {			//视频流
//                    		String[] strclientport = i.split("=|-");
//
//                    		remoteVideoRtpPort = Integer.parseInt(strclientport[1]);
//                    		remoteVideoRtcpPort = Integer.parseInt(strclientport[2]);
//                            strremoteip = ((InetSocketAddress) ctx.channel().remoteAddress()).getHostString();
//                            
//                            videoSsrc = RandomUtils.nextInt();
//                            //如果在ssrcMap中已存在该ssrc，则重新生成
//                            if (RtpHandler.rtspHandlerMap.containsKey(String.valueOf(videoSsrc))) {		
//                            	videoSsrc = RandomUtils.nextInt();			
//							}
//                            RtpHandler.rtspHandlerMap.put(String.valueOf(videoSsrc), this);
//
//                            if (null == dstVideoRtpAddr)
//                            {
//                            	dstVideoRtpAddr = new InetSocketAddress(strremoteip, remoteVideoRtpPort);
//                            	dstVideoRtcpAddr = new InetSocketAddress(strremoteip, remoteVideoRtcpPort);
//                            }
//                            o.headers().add(RtspHeaderNames.TRANSPORT,
//                                    r.headers().get(RtspHeaderNames.TRANSPORT)+String.format(";server_port=%d-%d", RtspNettyServer.rtpPort, RtspNettyServer.rtpPort+1)+";ssrc=" + videoSsrc);
//                            break;
//						} else if ((mediaSdpInfoMap != null && mediaSdpInfoMap.containsKey("audio") && uri.endsWith(mediaSdpInfoMap.get("audio").getControl()))
//								|| (mediaSdpInfoMap == null && uri.endsWith("streamid=1"))) {		//音频流
//							String[] strclientport = i.split("=|-");
//
//							remoteAudioRtpPort = Integer.parseInt(strclientport[1]);
//							remoteAudioRtcpPort = Integer.parseInt(strclientport[2]);
//                            strremoteip = ((InetSocketAddress) ctx.channel().remoteAddress()).getHostString();
//                            
//                            audioSsrc = RandomUtils.nextInt();		
//                            //如果在ssrcMap中已存在该ssrc，则重新生成
//                            if (RtpHandler.rtspHandlerMap.containsKey(String.valueOf(audioSsrc))) {		
//                            	audioSsrc = RandomUtils.nextInt();			
//							}
//                            RtpHandler.rtspHandlerMap.put(String.valueOf(audioSsrc), this);
//
//                            if (null == dstAudioRtpAddr)
//                            {
//                            	dstAudioRtpAddr = new InetSocketAddress(strremoteip, remoteAudioRtpPort);
//                            	dstAudioRtcpAddr = new InetSocketAddress(strremoteip, remoteAudioRtcpPort);
//                            }
//                            o.headers().add(RtspHeaderNames.TRANSPORT,
//                                    r.headers().get(RtspHeaderNames.TRANSPORT)+String.format(";server_port=%d-%d", RtspNettyServer.rtpPort, RtspNettyServer.rtpPort+1)+";ssrc=" + audioSsrc);
//						}
//                    }
//                }
//                
//            } else{
//                log.error("error: SETUP error transport, must contains rtp/avp.  461, Unsupported transport");
//                o.setStatus(RtspResponseStatuses.UNSUPPORTED_TRANSPORT);	//不支持的transport
//                sendAnswer(ctx, r, o);
//                closeThisClient();
//                return;
//            }
//
//            session = r.headers().get(RtspHeaderNames.SESSION);
//            if (session == null)			//如果为空，则将channel id返回
//            {
//            	session = chn.id().toString();
//            	o.headers().add(RtspHeaderNames.SESSION, session);
//            } 
//            else {						//如果不为空，则判断是否等于当前channel id
//				if (!session.equals(chn.id().toString())) {	//如果不等于当前channel id，则关闭连接
//					log.error("error: SETUP session incorrect, 454, Session Not Found");
//					o.setStatus(RtspResponseStatuses.SESSION_NOT_FOUND);	//Session未找到
//	                sendAnswer(ctx, r, o);
//					closeThisClient();
//	                return;
//				}
//			}
//            
//            log.debug("transport request=" + transport + ",channel=" + ctx.channel().id());
//            log.debug("transport response=" + o.headers().get(RtspHeaderNames.TRANSPORT)+ ",channel=" + ctx.channel().id());
//        } else if (r.method() == RtspMethods.PLAY) {
//            // send rtp and rtcp to client
//        	log.debug("play");
//            
//            //校验session是否存在
//            session = r.headers().get(RtspHeaderNames.SESSION);
//            if (session == null || !session.equals(chn.id().toString())) {			
//            	log.error("error: PLAY session incorrect, 454, Session Not Found");
//            	o.setStatus(RtspResponseStatuses.SESSION_NOT_FOUND);	//Session未找到
//                sendAnswer(ctx, r, o);
//				closeThisClient();
//                return;
//			} else {
//	            ///////////用户、设备鉴权/////////////
//	            String tenantId = null;
//	            String token = null;
//	            String mediaName = null;
//	            
//	            QueryStringDecoder uri = new QueryStringDecoder(r.uri());
//	            String[] uris = (Strings.split(uri.toString(), '?')[1].split("###"));
//	            mediaName = uris[1].split("=")[1];
//	            //参数检测
////	            if (uri.parameters().get("tenantId") == null || 
////	            			uri.parameters().get("token") == null ||
////	            			uri.parameters().get("mediaName") == null) {
////	            		log.error("error: PLAY invalid params, 451, Parameter Not Understood");
////	            		o.setStatus(RtspResponseStatuses.PARAMETER_NOT_UNDERSTOOD);	//参数无效
////	            		sendAnswer(ctx, r, o);
////	            		closeThisClient();
////	            		return;
////	    		}
//	            
//	            //如果未收到视频的udp探测包，或者视频的rtcp探测包，则返回错误
////	            if (isVideoRtpDetected == false || isVideoRtcpDetected == false) {
////	            	log.error("error: PLAY not received detect packets, 462, Destination unreachable");
////	            	o.setStatus(RtspResponseStatuses.DESTINATION_UNREACHABLE);	
////            		sendAnswer(ctx, r, o);
////            		closeThisClient();
////            		return;
////				}
//	            
////	            tenantId = uri.parameters().get("tenantId").get(0);
////	            token = uri.parameters().get("token").get(0);
////	            mediaName = uri.parameters().get("mediaName").get(0);
//	            
//	            //newton鉴权
////	            String url = RtspNettyServer.NEWTON_URL + "getPayInfoIsExpire?keyhash=" + keyhash
////	            			+ "&tenantId=" + tenantId + "&token=" + token 
////	            			+ "&chargeMark=3&version=1.0";
////	            String result = HttpConnection.get(url);
//////	            String result = "{\"retcode\": \"200\"}";
////	            
////	            log.debug("PLAY: keyhash={}, tenantId={}, token={}, mediaName={}, newtonUrl={}, result={}", keyhash, tenantId, token, mediaName, url, result);
////	            JSONObject jsonObject = JSONObject.parseObject(result);
////	            if (jsonObject == null) {							//连接newtonWeb失败
////	            	log.error("error: PLAY connect to newton failed, 400, Bad Request");
////	            	o.setStatus(RtspResponseStatuses.BAD_REQUEST);	//请求失败
////	            	o.content().writeCharSequence("{\"retcode\": \"215\"}", CharsetUtil.UTF_8);
////	                sendAnswer(ctx, r, o);
////	                closeThisClient();
////	                return;
////				}
////	            String retcode = jsonObject.getString("retcode");
////	            if (!"200".equals(retcode)) {				//如果返回码不是200，则直接返回newton的错误码，关闭连接
////	            	log.error("error: PLAY newton retcode not 200, 400, Bad Request");
////	            	o.setStatus(RtspResponseStatuses.BAD_REQUEST);	//请求失败
////	            	o.content().writeCharSequence(jsonObject.toString(), CharsetUtil.UTF_8);
////	                sendAnswer(ctx, r, o);
////	                closeThisClient();
////	                return;
////				}
//	            ///////////////////////////////
//	            
//	            //根据rtcp消息作为心跳，一段时间内未收到rtcp包，则关闭rtsp连接，并停止发送音视频数据。
//	            RtspNettyServer.EXECUTOR.execute(new Runnable() {
//	    			@Override
//	    			public void run() {		//处理rtcp请求
//	    				dealWithRtcp();
//	    			}
//	    		});
//	            
//	            rtpCacheQueue = new TreeMap<Integer, byte[]>();
//				
//				//根据文件名获取路径信息
//				SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd");
//				Date date = new Date(Long.parseLong(mediaName)*1000);
//				String today = formatter.format(date);
//				
//				if ("h264".equals(media)) {
//					//播放h264视频文件、aac音频文件
//					String videoFilename = RtspNettyServer.outputPath + keyhash  + "/" + today + "/" + mediaName + ".h264";
//					File videoFile = new File(videoFilename);
//			        if (!videoFile.exists()) {
//			        	log.error("error: PLAY h264 file not exist, 404, Not Found");
//			        	o.setStatus(RtspResponseStatuses.NOT_FOUND);	//文件不存在
//			        	sendAnswer(ctx, r, o);
//			        	closeThisClient();
//			            throw new FileNotFoundException(videoFilename);
//			        }
//			        
//			        String audioFilename = RtspNettyServer.outputPath + keyhash  + "/" + today + "/" + mediaName + ".aac";
//			        File audioFile = new File(audioFilename);
//			        if (!audioFile.exists()) {
//			        	log.error("error: PLAY aac file not exist, 404, Not Found");
//			        	o.setStatus(RtspResponseStatuses.NOT_FOUND);	//文件不存在
//			        	sendAnswer(ctx, r, o);
//			        	closeThisClient();
//			            throw new FileNotFoundException(audioFilename);
//			        }
//			 
//			        sendAnswer(ctx, r, o);
////			        int rtpTimestamp = (int) (System.currentTimeMillis()/1000);	//音视频初始的RTP时间戳
//			        RtspNettyServer.EXECUTOR.execute(new Runnable() {
//						@Override
//						public void run() {
////							playH264(videoFile, rtpTimestamp);
//							playH264(videoFile);
////							sendVideoSenderReport();			//发送视频的SR报告
//						}
//					});
//			        RtspNettyServer.EXECUTOR.execute(new Runnable() {
//						@Override
//						public void run() {
////							playAac(audioFile, rtpTimestamp);
//							//playAac(audioFile);
////							sendAudioSenderReport();			//发送音频的SR报告
//						}
//					});
//					return;
//				} else if ("h265".equals(media)) {
//					//播放h265视频文件、aac音频文件
//					String videoFilename = RtspNettyServer.outputPath + keyhash  + "/" + today + "/" + mediaName + ".h265";;
//					File videoFile = new File(videoFilename);
//			        if (!videoFile.exists()) {
//			        	log.error("error: PLAY h265 file not exist, 404, Not Found");
//			        	o.setStatus(RtspResponseStatuses.NOT_FOUND);
//			        	sendAnswer(ctx, r, o);
//			        	closeThisClient();
//			            throw new FileNotFoundException(videoFilename);
//			        }
//			        
//			        String audioFilename = RtspNettyServer.outputPath + keyhash  + "/" + today + "/" + mediaName + ".aac";
//			        File audioFile = new File(audioFilename);
//			        if (!audioFile.exists()) {
//			        	log.error("error: PLAY acc file not exist, 404, Not Found");
//			        	o.setStatus(RtspResponseStatuses.NOT_FOUND);
//			        	sendAnswer(ctx, r, o);
//			        	closeThisClient();
//			            throw new FileNotFoundException(audioFilename);
//			        }
//			        
//			        sendAnswer(ctx, r, o);
////			        int rtpTimestamp = (int) (System.currentTimeMillis()/1000);		//音视频初始的RTP时间戳
//			        RtspNettyServer.EXECUTOR.execute(new Runnable() {
//						@Override
//						public void run() {
////							playH265(videoFile, rtpTimestamp);
//							playH265(videoFile);
////							sendVideoSenderReport();			//发送视频的SR报告
//						}
//					});
//			        RtspNettyServer.EXECUTOR.execute(new Runnable() {
//						@Override
//						public void run() {
////							playAac(audioFile, rtpTimestamp);
//							playAac(audioFile);
////							sendAudioSenderReport();			//发送音频的SR报告
//						}
//					});
//					return;
//				}
//				
//			}
//
//        } else if (r.method() == RtspMethods.TEARDOWN) {
//        	log.debug("teardown");
//            
//            //校验session是否存在
//            session = r.headers().get(RtspHeaderNames.SESSION);
//            if (session == null || !session.equals(chn.id().toString())) {						
//            	log.error("error: TEARDOWN rtspSession is null or invalid, 454, Session Not Found");
//            	o.setStatus(RtspResponseStatuses.SESSION_NOT_FOUND);	//Session未找到
//                sendAnswer(ctx, r, o);
//				closeThisClient();
//                return;
//			} else {
//				sendAnswer(ctx, r, o);
//				closeThisClient();
//				return;
//			}
//        }  else if (r.method() == RtspMethods.GET_PARAMETER) {
//        	log.debug("get_parameter");
//            
//            //校验session是否存在
//            session = r.headers().get(RtspHeaderNames.SESSION);
//            if (session == null || !session.equals(chn.id().toString())) {						
//            	log.error("error: GET_PARAMETER rtspSession is null or invalid, 454, Session Not Found");
//            	o.setStatus(RtspResponseStatuses.SESSION_NOT_FOUND);	//Session未找到
//                sendAnswer(ctx, r, o);
//				closeThisClient();
//                return;
//			}
//        } else if (r.method() == RtspMethods.ANNOUNCE) {
//        	log.debug("announce");
//            
//            ByteBuf content = r.content();
//        	byte[] sdp = new byte[content.readableBytes()];
//        	content.readBytes(sdp);
//        	
//        	mediaSdpInfoMap = SdpParser.parse(new String(sdp));	//解析出音视频相关参数
//        	if (mediaSdpInfoMap == null || mediaSdpInfoMap.size() == 0 || 
//        			((mediaSdpInfoMap != null && mediaSdpInfoMap.containsKey("video") 
//                		&& !mediaSdpInfoMap.get("video").getCodec().equals("H264")) 
//                		&& !mediaSdpInfoMap.get("video").getCodec().equals("H265")) ||
//                	((mediaSdpInfoMap != null && mediaSdpInfoMap.containsKey("audio") 
//                		&& !mediaSdpInfoMap.get("audio").getCodec().equals("MPEG4-GENERIC")))) {
//        		log.error("error: ANNOUNCE 415, Unsupported Media Type");
//        		o.setStatus(RtspResponseStatuses.UNSUPPORTED_MEDIA_TYPE);	//不支持的媒体类型
//                sendAnswer(ctx, r, o);
//                closeThisClient();
//                return;
//			}
//        } else if (r.method() == RtspMethods.RECORD) {
//        	log.debug("record");
//            
//            //校验session是否存在
//            session = r.headers().get(RtspHeaderNames.SESSION);
//            if (session == null || !session.equals(chn.id().toString())) {						
//            	log.error("error: RECORD rtspSession is null or invalid, 454, Session Not Found");
//            	o.setStatus(RtspResponseStatuses.SESSION_NOT_FOUND);	//Session未找到
//                sendAnswer(ctx, r, o);
//				closeThisClient();
//                return;
//			} else {
//				///////////设备鉴权/////////////
//	            String content = null;			//keyhash+aeskey拼接后的字符串，再进行MD5运算后的值。
//	            QueryStringDecoder uri = new QueryStringDecoder(r.uri());
//	            if (uri.parameters().get("content") == null) {		//content不能为空，否则报错
//	            	 log.error("error: RECORD content param cannot be null, 451, Parameter Not Understood");
//	            	 o.setStatus(RtspResponseStatuses.PARAMETER_NOT_UNDERSTOOD);	//参数无效
//	                 sendAnswer(ctx, r, o);
//	                 closeThisClient();
//	                 return;
//				} else {
//	            	content = uri.parameters().get("content").get(0);
//	    		}
//	            
//	            String url = RtspNettyServer.NEWTON_URL + "getPayInfoIsExpire2?keyhash=" + URLEncoder.encode(keyhash) 
//            			+ "&content=" + content + "&chargeMark=3&version=1.0";
//	            String result = HttpConnection.get(url);
//	            
//	            log.debug("RECORD: keyhash={}, content={}, newtonUrl={}, result={}", keyhash, content, url, result);
////	            String result = "{\"retcode\": \"200\"}";
//	            JSONObject jsonObject = JSONObject.parseObject(result);
//	            if (jsonObject == null) {							//连接newtonWeb失败
//	            	log.error("error: RECORD connect to newton failed, 400, Bad Request");
//	            	o.setStatus(RtspResponseStatuses.BAD_REQUEST);	//请求失败
//	            	o.content().writeCharSequence("{\"retcode\": \"215\"}", CharsetUtil.UTF_8);
//	                sendAnswer(ctx, r, o);
//	                closeThisClient();
//	                return;
//				}
//	            String retcode = jsonObject.getString("retcode");
//	            if (!"200".equals(retcode)) {				//如果返回码不是200，则直接返回newton的错误码，关闭连接
//	            	log.error("error: RECORD newton retcode not 200, 400, Bad Request");
//	            	o.setStatus(RtspResponseStatuses.BAD_REQUEST);	//请求失败
//	            	o.content().writeCharSequence(jsonObject.toString(), CharsetUtil.UTF_8);
//	                sendAnswer(ctx, r, o);
//	                closeThisClient();
//	                return;
//				}
//	            ///////////////////////////////
//				
//				long now = System.currentTimeMillis();
//				o.headers().add("filename", now/1000);
//				sendAnswer(ctx, r, o);
//				
//				rtpQueue = new ArrayBlockingQueue<byte[]>(1000);		//初始化rtp队列
//				
//				RtspNettyServer.EXECUTOR.execute(new Runnable() {
//					@Override
//					public void run() {
//						OutputStream videoOutputStream = null;
//						OutputStream audioOutputStream = null;
//						int videoPayloadType = mediaSdpInfoMap.containsKey("video")?mediaSdpInfoMap.get("video").getRtpPayloadType():0;
//						String videoCodec = mediaSdpInfoMap.containsKey("video")?mediaSdpInfoMap.get("video").getCodec():null;
//						int audioPayloadType = mediaSdpInfoMap.containsKey("audio")?mediaSdpInfoMap.get("audio").getRtpPayloadType():0;
//						String audioCodec = mediaSdpInfoMap.containsKey("audio")?mediaSdpInfoMap.get("audio").getCodec():null;
//						try {
//							SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd");
//							Date date = new Date(now);
//							String today = formatter.format(date);
//							String keyhashDir = keyhash.replace('/', '-');			//keyhash中的/符号需要转换成-号
//							
//							//H264
//							if ("H264".equals(videoCodec)) {
//								File file = new File(RtspNettyServer.outputPath + keyhashDir + "/" + today + "/" + (now/1000) + ".h264");
//								if (!file.getParentFile().exists()) {			//如果目录不存在，则创建
//									file.getParentFile().mkdirs();
//								}
//								videoOutputStream = new BufferedOutputStream(new FileOutputStream(file, true));
//							//H265
//							} else if ("H265".equals(videoCodec)) {
//								File file = new File(RtspNettyServer.outputPath + keyhashDir + "/" + today + "/" + (now/1000) + ".h265");
//								if (!file.getParentFile().exists()) {			//如果目录不存在，则创建
//									file.getParentFile().mkdirs();
//								}
//								videoOutputStream = new BufferedOutputStream(new FileOutputStream(file, true));
//							}
//							//AAC
//							if("MPEG4-GENERIC".equals(audioCodec)) {
//								File file = new File(RtspNettyServer.outputPath + keyhashDir + "/" + today + "/" + (now/1000) + ".aac");
//								if (!file.getParentFile().exists()) {			//如果目录不存在，则创建
//									file.getParentFile().mkdirs();
//								}
//								audioOutputStream = new BufferedOutputStream(new FileOutputStream(file, true));
//							}
//							
//							TreeMap<Integer, RawPacket> map = new TreeMap<Integer, RawPacket>();
//							
//							int count = 0;
//							while (true) {
//								if (isRtspAlive == 0) {			//如果rtsp连接中断，则停止接收udp
//									//将map中剩下的所有RTP包都写入文件,foreach循环是有序的
//									for (Entry<Integer, RawPacket> entrySet : map.entrySet()) {
//										recordMedia(entrySet.getValue(), videoPayloadType, videoCodec, videoOutputStream, audioPayloadType, audioCodec, audioOutputStream);
//									}
//									break;
//								}
//								
//								byte[] take = rtpQueue.poll(RtspNettyServer.RTP_IDLE_TIME, TimeUnit.SECONDS);
//								if (take == null) {
//									//将map中剩下的所有RTP包都写入文件,foreach循环是有序的
//									for (Entry<Integer, RawPacket> entrySet : map.entrySet()) {
//										recordMedia(entrySet.getValue(), videoPayloadType, videoCodec, videoOutputStream, audioPayloadType, audioCodec, audioOutputStream);
//									}
//									break;
//								}
//								
//								System.out.println("count=" + count++);
//								RawPacket rawPacket = new RawPacket(take, 0, take.length);
//								retransmissionRequesterDelegate.packetReceived(rawPacket.getSSRCAsLong(), rawPacket.getSequenceNumber());
//								
//								///////////////////
////								recordMedia(rawPacket, videoPayloadType, videoCodec, videoOutputStream, audioPayloadType, audioCodec, audioOutputStream);
//								//////////////////
//								
//								map.put(rawPacket.getSequenceNumber(), rawPacket);
//								if (map.size() >= 300) {		//大概是3秒时间内产生的RTP数量
//									for (int i = 0; i < 100; i++) {		//取第一秒的RTP包
//										RawPacket minimumPacket = map.remove(map.firstKey());	//每次取第一个
//										recordMedia(minimumPacket, videoPayloadType, videoCodec, videoOutputStream, audioPayloadType, audioCodec, audioOutputStream);
//									}
//								}
//								
//							}
//						} catch (InterruptedException | IOException e) {
//							e.printStackTrace();
//						} finally {
//							try {
//								if (videoOutputStream != null) {
//									videoOutputStream.close();
//								}
//								if (audioOutputStream != null) {
//									audioOutputStream.close();
//								}
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//						return;
//					}
//				});
//				return;
//			}
//        } else {
//        	log.error("error: unknown message, 405, Method Not Allowed");
//            o.setStatus(RtspResponseStatuses.METHOD_NOT_ALLOWED);
//            sendAnswer(ctx, r, o);
//            closeThisClient();
//            return;
//        }
//        sendAnswer(ctx, r, o);
//    }
//    
//
//    private void sendAnswer(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse rep)
//    {
//        final String cseq = req.headers().get(RtspHeaderNames.CSEQ);
//        if (cseq != null)
//        {
//            rep.headers().add(RtspHeaderNames.CSEQ, cseq);
//        }
//        final String session = req.headers().get(RtspHeaderNames.SESSION);
//        if (session != null)
//        {
//            rep.headers().add(RtspHeaderNames.SESSION, session);
//        }
//        if (!HttpUtil.isKeepAlive(req))
//        {
//            ctx.writeAndFlush(rep).addListener(ChannelFutureListener.CLOSE);
//        } else
//        {
//            rep.headers().set(RtspHeaderNames.CONNECTION, RtspHeaderValues.KEEP_ALIVE);
//            ctx.writeAndFlush(rep);
//        }
//    }
//    
////    public void playH264(File f, int rtpTimestamp){
//    public void playH264(File f){
////    	int time = rtpTimestamp;
//    	int time = 0;
//		//播放h264视频文件
//        BufferedInputStream in = null;
//        try {
//            in = new BufferedInputStream(new FileInputStream(f));
//            int buf_size = 64*1024;
//            byte[] buffer = new byte[buf_size];		//从文件读的字节存入的地方
//            byte[] nalu;							//临时存储一个nalu单元内容
//            byte[] firstHalfNalu = null;			//nalu前半段
//            byte[] secondHalfNalu = null;			//nalu后半段
//            int len = 0;							//每次从文件读的字节数
//            int state = 0;							//状态机，值范围：0、1、2、3、4
//            int first = 1;							//是否是第一个起始码
//            int cross = 0;							//某个nalu是否跨buffer
//            RtpUtils rtpUtils = new RtpUtils();
//            
//            while (-1 != (len = in.read(buffer, 0, buf_size))) {
////            	if (isRtspAlive == 0) {			//如果rtsp连接中断，则停止发送udp
////					break;
////				}
//            	
//            	int start = 0;							//第一个nalu的起始位置
//            	int offset = 0;							//当前循环中的偏移量
//        		while (offset <= len-4) {
//        			if (state == 0) {							//没有遗留状态
//    					if (buffer[offset] == 0x00 &&
//    							buffer[offset + 1] == 0x00 &&
//    							buffer[offset + 2] == 0x00 &&
//    							buffer[offset + 3] == 0x01) {
//    						if (cross == 1) {				//跨buffer
//    							if (first == 0) {			//不是第一个起始码
//    								secondHalfNalu = new byte[offset];		//拿到后半段内容
//    								System.arraycopy(buffer, start, secondHalfNalu, 0, secondHalfNalu.length);
//    								
//    								//拼接前半段与后半段内容, 拷贝到新的数组中
//    								int naluSize = firstHalfNalu.length + secondHalfNalu.length;
//    								nalu = new byte[naluSize];
//    								System.arraycopy(firstHalfNalu, 0, nalu, 0, firstHalfNalu.length);
//    								System.arraycopy(secondHalfNalu, 0, nalu, firstHalfNalu.length, secondHalfNalu.length);
//    								
////    								List<byte[]> rtpList = rtpUtils.naluToRtpPack(nalu, videoSsrc, fps, time);
//    								Map<Integer, byte[]> rtpMap = rtpUtils.naluToRtpPack(nalu, videoSsrc, fps, time);
//    								for (Entry<Integer, byte[]> entry: rtpMap.entrySet()) {
//    									ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//    									byteBuf.writeBytes(entry.getValue());
//    									RtspNettyServer.rtpChannel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//    									
//    									//存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
//    									if (rtpCacheQueue.size() > 800) {
//											rtpCacheQueue.pollFirstEntry();
//										}
//    									rtpCacheQueue.put(entry.getKey(), entry.getValue());
//    								}
//    								time += (90000/fps);			//rtp时间戳刻度递增
//    							}
//    							
//    							offset += 4;
//    							state = 0;
//    							first = 0;	
//    							start = offset;					//当前位置变成新的起始位置
//    							cross = 0;						//跨buffer标志位重置成0
//							} else {							//没有跨buffer
//								if (first == 0) {			//不是第一个起始码
//    								int naluSize = offset - start;
//    								nalu = new byte[naluSize];
//    								System.arraycopy(buffer, start, nalu, 0, naluSize);
//    								
//    								Map<Integer, byte[]> rtpMap = rtpUtils.naluToRtpPack(nalu, videoSsrc, fps, time);
//    								for (Entry<Integer, byte[]> entry: rtpMap.entrySet()) {
//    									ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//    									byteBuf.writeBytes(entry.getValue());
//    									RtspNettyServer.rtpChannel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//    									
//    									//存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
//    									if (rtpCacheQueue.size() > 800) {
//											rtpCacheQueue.pollFirstEntry();
//										}
//    									rtpCacheQueue.put(entry.getKey(), entry.getValue());
//    								}
//    								time += (90000/fps);			//rtp时间戳刻度递增
//    							}
//    							
//    							offset += 4;
//    							state = 0;
//    							first = 0;	
//    							start = offset;					//当前位置变成新的起始位置
//							}
//    						
//    						
//    					} else {
//    						state = 0;
//    						offset ++;
//    					}
//					} else if (state == 1) {
//						if (buffer[offset] == 0x00 &&
//							buffer[offset + 1] == 0x00 &&
//							buffer[offset + 2] == 0x01) {
//							
//							//拿到两个起始码之间的一个nalu的数据
//			            	int naluSize = firstHalfNalu.length - 1;
//			            	nalu = new byte[naluSize];
//			            	System.arraycopy(firstHalfNalu, 0, nalu, 0, naluSize);
//							
//			            	Map<Integer, byte[]> rtpMap = rtpUtils.naluToRtpPack(nalu, videoSsrc, fps, time);
//							for (Entry<Integer, byte[]> entry: rtpMap.entrySet()) {
//								ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//								byteBuf.writeBytes(entry.getValue());
//								RtspNettyServer.rtpChannel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//								
//								//存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
//								if (rtpCacheQueue.size() > 800) {
//									rtpCacheQueue.pollFirstEntry();
//								}
//								rtpCacheQueue.put(entry.getKey(), entry.getValue());
//							}
//							time += (90000/fps);			//rtp时间戳刻度递增
//							
//							offset += 3;
//							state = 0;
//							start = offset;					//当前位置变成新的起始位置
//						} else {
//							state = 0;
//							offset ++;
//						}
//					} else if (state == 2) {
//						if (buffer[offset] == 0x00 &&
//							buffer[offset + 1] == 0x01) {
//							
//							//拿到两个起始码之间的一个nalu的数据
//			            	int naluSize = firstHalfNalu.length - 2;
//			            	nalu = new byte[naluSize];
//			            	System.arraycopy(firstHalfNalu, 0, nalu, 0, naluSize);
//							
//			            	Map<Integer, byte[]> rtpMap = rtpUtils.naluToRtpPack(nalu, videoSsrc, fps, time);
//							for (Entry<Integer, byte[]> entry: rtpMap.entrySet()) {
//								ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//								byteBuf.writeBytes(entry.getValue());
//								RtspNettyServer.rtpChannel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//								
//								//存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
//								if (rtpCacheQueue.size() > 800) {
//									rtpCacheQueue.pollFirstEntry();
//								}
//								rtpCacheQueue.put(entry.getKey(), entry.getValue());
//							}
//							time += (90000/fps);			//rtp时间戳刻度递增
//							
//							offset += 2;
//							state = 0;
//							start = offset;					//当前位置变成新的起始位置
//						} else {
//							state = 0;
//							offset ++;
//						}
//					} else if (state == 3) {
//						if (buffer[offset] == 0x01) {
//							//拿到两个起始码之间的一个nalu的数据
//			            	int naluSize = firstHalfNalu.length - 3;
//			            	nalu = new byte[naluSize];
//			            	System.arraycopy(firstHalfNalu, 0, nalu, 0, naluSize);
//							
//			            	Map<Integer, byte[]> rtpMap = rtpUtils.naluToRtpPack(nalu, videoSsrc, fps, time);
//							for (Entry<Integer, byte[]> entry: rtpMap.entrySet()) {
//								ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//								byteBuf.writeBytes(entry.getValue());
//								RtspNettyServer.rtpChannel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//								
//								//存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
//								if (rtpCacheQueue.size() > 800) {
//									rtpCacheQueue.pollFirstEntry();
//								}
//								rtpCacheQueue.put(entry.getKey(), entry.getValue());
//							}
//							time += (90000/fps);			//rtp时间戳刻度递增
//							
//							offset += 1;
//							state = 0;
//							start = offset;					//当前位置变成新的起始位置
//						} else {
//							state = 0;
//							offset ++;
//						}
//					}
//				}
//            	
//        		
//            	//指针指向最后3位时
//            	if (offset == len-3) {
//            		if (buffer[offset] == 0x00 && 
//            			buffer[offset + 1] == 0x00	&&
//            			buffer[offset + 2] == 0x00) {
//						state = 3;
//					} else if (buffer[offset + 1] == 0x00 &&
//            			buffer[offset + 2] == 0x00) {
//						state = 2;
//					} else if (buffer[offset + 2] == 0x00) {
//						state = 1;
//					}
//            		cross = 1;				//一定会跨buffer
//            		firstHalfNalu = new byte[offset + 3 - start];	//初始化前半段nalu数组，将前半段内容放进去
//            		System.arraycopy(buffer, start, firstHalfNalu, 0, firstHalfNalu.length);
//				}
//            	
//			}
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                in.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    
////    public void playH265(File f, int rtpTimestamp){
//    public void playH265(File f){
////    	int time = rtpTimestamp;
//    	int time = 0;
//		//播放h265视频文件
//        BufferedInputStream in = null;
//        try {
//            in = new BufferedInputStream(new FileInputStream(f));
//            int buf_size = 64*1024;
//            byte[] buffer = new byte[buf_size];		//从文件读的字节存入的地方
//            byte[] nalu;							//临时存储一个nalu单元内容
//            byte[] firstHalfNalu = null;			//nalu前半段
//            byte[] secondHalfNalu = null;			//nalu后半段
//            int len = 0;							//每次从文件读的字节数
//            int state = 0;							//状态机，值范围：0、1、2、3、4
//            int first = 1;							//是否是第一个起始码
//            int cross = 0;							//某个nalu是否跨buffer
//            RtpUtils rtpUtils = new RtpUtils();
//            
//            while (-1 != (len = in.read(buffer, 0, buf_size))) {
//            	if (isRtspAlive == 0) {			//如果rtsp连接中断，则停止发送udp
//					break;
//				}
//            	
//            	int start = 0;							//第一个nalu的起始位置
//            	int offset = 0;							//当前循环中的偏移量
//        		while (offset <= len-4) {
//        			if (state == 0) {							//没有遗留状态
//    					if (buffer[offset] == 0x00 &&
//    							buffer[offset + 1] == 0x00 &&
//    							buffer[offset + 2] == 0x00 &&
//    							buffer[offset + 3] == 0x01) {
//    						if (cross == 1) {				//跨buffer
//    							if (first == 0) {			//不是第一个起始码
//    								secondHalfNalu = new byte[offset];		//拿到后半段内容
//    								System.arraycopy(buffer, start, secondHalfNalu, 0, secondHalfNalu.length);
//    								
//    								//拼接前半段与后半段内容, 拷贝到新的数组中
//    								int naluSize = firstHalfNalu.length + secondHalfNalu.length;
//    								nalu = new byte[naluSize];
//    								System.arraycopy(firstHalfNalu, 0, nalu, 0, firstHalfNalu.length);
//    								System.arraycopy(secondHalfNalu, 0, nalu, firstHalfNalu.length, secondHalfNalu.length);
//    								
//    								Map<Integer, byte[]> rtpMap = rtpUtils.naluH265ToRtpPack(nalu, videoSsrc, fps, time);
//    								for (Entry<Integer, byte[]> entry: rtpMap.entrySet()) {
//    									ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//    									byteBuf.writeBytes(entry.getValue());
//    									RtspNettyServer.rtpChannel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//    									
//    									//存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
//    									if (rtpCacheQueue.size() > 800) {
//											rtpCacheQueue.pollFirstEntry();
//										}
//    									rtpCacheQueue.put(entry.getKey(), entry.getValue());
//    								}
//    								time += (90000/fps);			//rtp时间戳刻度递增
//    							}
//    							
//    							offset += 4;
//    							state = 0;
//    							first = 0;	
//    							start = offset;					//当前位置变成新的起始位置
//    							cross = 0;						//跨buffer标志位重置成0
//							} else {							//没有跨buffer
//								if (first == 0) {			//不是第一个起始码
//    								int naluSize = offset - start;
//    								nalu = new byte[naluSize];
//    								System.arraycopy(buffer, start, nalu, 0, naluSize);
//    								
//    								Map<Integer, byte[]> rtpMap = rtpUtils.naluH265ToRtpPack(nalu, videoSsrc, fps, time);
//    								for (Entry<Integer, byte[]> entry: rtpMap.entrySet()) {
//    									ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//    									byteBuf.writeBytes(entry.getValue());
//    									RtspNettyServer.rtpChannel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//    									
//    									//存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
//    									if (rtpCacheQueue.size() > 800) {
//											rtpCacheQueue.pollFirstEntry();
//										}
//    									rtpCacheQueue.put(entry.getKey(), entry.getValue());
//    								}
//    								time += (90000/fps);			//rtp时间戳刻度递增
//    							}
//    							
//    							offset += 4;
//    							state = 0;
//    							first = 0;	
//    							start = offset;					//当前位置变成新的起始位置
//							}
//    						
//    						
//    					} else {
//    						state = 0;
//    						offset ++;
//    					}
//					} else if (state == 1) {
//						if (buffer[offset] == 0x00 &&
//							buffer[offset + 1] == 0x00 &&
//							buffer[offset + 2] == 0x01) {
//							
//							//拿到两个起始码之间的一个nalu的数据
//			            	int naluSize = firstHalfNalu.length - 1;
//			            	nalu = new byte[naluSize];
//			            	System.arraycopy(firstHalfNalu, 0, nalu, 0, naluSize);
//			            	
//			            	Map<Integer, byte[]> rtpMap = rtpUtils.naluH265ToRtpPack(nalu, videoSsrc, fps, time);
//							for (Entry<Integer, byte[]> entry: rtpMap.entrySet()) {
//								ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//								byteBuf.writeBytes(entry.getValue());
//								RtspNettyServer.rtpChannel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//								
//								//存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
//								if (rtpCacheQueue.size() > 800) {
//									rtpCacheQueue.pollFirstEntry();
//								}
//								rtpCacheQueue.put(entry.getKey(), entry.getValue());
//							}
//							time += (90000/fps);			//rtp时间戳刻度递增
//							
//							offset += 3;
//							state = 0;
//							start = offset;					//当前位置变成新的起始位置
//						} else {
//							state = 0;
//							offset ++;
//						}
//					} else if (state == 2) {
//						if (buffer[offset] == 0x00 &&
//							buffer[offset + 1] == 0x01) {
//							
//							//拿到两个起始码之间的一个nalu的数据
//			            	int naluSize = firstHalfNalu.length - 2;
//			            	nalu = new byte[naluSize];
//			            	System.arraycopy(firstHalfNalu, 0, nalu, 0, naluSize);
//			            	
//			            	Map<Integer, byte[]> rtpMap = rtpUtils.naluH265ToRtpPack(nalu, videoSsrc, fps, time);
//							for (Entry<Integer, byte[]> entry: rtpMap.entrySet()) {
//								ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//								byteBuf.writeBytes(entry.getValue());
//								RtspNettyServer.rtpChannel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//								
////								//存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
//								if (rtpCacheQueue.size() > 800) {
//									rtpCacheQueue.pollFirstEntry();
//								}
//								rtpCacheQueue.put(entry.getKey(), entry.getValue());
//							}
//							time += (90000/fps);			//rtp时间戳刻度递增
//							
//							offset += 2;
//							state = 0;
//							start = offset;					//当前位置变成新的起始位置
//						} else {
//							state = 0;
//							offset ++;
//						}
//					} else if (state == 3) {
//						if (buffer[offset] == 0x01) {
//							//拿到两个起始码之间的一个nalu的数据
//			            	int naluSize = firstHalfNalu.length - 3;
//			            	nalu = new byte[naluSize];
//			            	System.arraycopy(firstHalfNalu, 0, nalu, 0, naluSize);
//			            	
//			            	Map<Integer, byte[]> rtpMap = rtpUtils.naluH265ToRtpPack(nalu, videoSsrc, fps, time);
//							for (Entry<Integer, byte[]> entry: rtpMap.entrySet()) {
//								ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//								byteBuf.writeBytes(entry.getValue());
//								RtspNettyServer.rtpChannel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//								
//								//存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
//								if (rtpCacheQueue.size() > 800) {
//									rtpCacheQueue.pollFirstEntry();
//								}
//								rtpCacheQueue.put(entry.getKey(), entry.getValue());
//							}
//							time += (90000/fps);			//rtp时间戳刻度递增
//							
//							offset += 1;
//							state = 0;
//							start = offset;					//当前位置变成新的起始位置
//						} else {
//							state = 0;
//							offset ++;
//						}
//					}
//				}
//            	
//        		
//            	//指针指向最后3位时
//            	if (offset == len-3) {
//            		if (buffer[offset] == 0x00 && 
//            			buffer[offset + 1] == 0x00	&&
//            			buffer[offset + 2] == 0x00) {
//						state = 3;
//					} else if (buffer[offset + 1] == 0x00 &&
//            			buffer[offset + 2] == 0x00) {
//						state = 2;
//					} else if (buffer[offset + 2] == 0x00) {
//						state = 1;
//					}
//            		cross = 1;				//一定会跨buffer
//            		firstHalfNalu = new byte[offset + 3 - start];	//初始化前半段nalu数组，将前半段内容放进去
//            		System.arraycopy(buffer, start, firstHalfNalu, 0, firstHalfNalu.length);
//				}
//            	
//			}
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                in.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    
////    public void playAac(File f, int rtpTimestamp){
//    public void playAac(File f){
////    	int time = rtpTimestamp;
//    	int time = 0;
//    	BufferedInputStream in = null;
//        try {
//			in = new BufferedInputStream(new FileInputStream(f));
//			int seqNum = 1;							//rtp的seqnum
//			int len = 0;							//每次从文件读的实际字节数
//			int aacDataLen = 0;						//aac data长度
//			int sampling = 16000;					//采样率，默认值16000
//			byte[] adtsHeaderBuffer = new byte[7];	//临时存储adts头
//			byte[] aacDataBuffer;	//临时存储aac data
//			RtpUtils rtpUtils = new RtpUtils();
//			
//			int isHeader = 1;						//标志位，表示当前读取的是adts头还是aac data
//			len = in.read(adtsHeaderBuffer, 0, 7);				//刚开始读取adts头
//			while (len != -1) {
//				if (isRtspAlive == 0) {			//如果rtsp连接中断，则停止发送udp
//					break;
//				}
//				
//				if (isHeader == 1) {				//adts头，下一个就是aac data
//					aacDataLen = ((adtsHeaderBuffer[3]&0x03)<<11)		//获取aac data长度
//								+ (adtsHeaderBuffer[4]<<3)
//								+ ((adtsHeaderBuffer[5]&0xE0)>>5) - 7;
//					byte samp = (byte) (adtsHeaderBuffer[2]&0x3C);		//获取采样率
//					sampling = RtpUtils.getSampling(samp);
//					isHeader = 0;
//				} else {							//aac data，下一个就是adts头
//					aacDataBuffer = new byte[aacDataLen];				
//					len = in.read(aacDataBuffer, 0, aacDataLen);		//读取aac data
//					byte[] rptPackage = rtpUtils.aacToRtpPack(aacDataBuffer, seqNum, audioSsrc, time);
//					ByteBuf byteBuf = Unpooled.buffer(rptPackage.length);
//					byteBuf.writeBytes(rptPackage);
//					RtspNettyServer.rtpChannel.writeAndFlush(new DatagramPacket(byteBuf, this.dstAudioRtpAddr));
//					time += 1024;			//rtp时间戳刻度递增, 音频固定递增1024
//					seqNum ++;
//
//					Thread.sleep(1024*1000/sampling);		//延时发送帧
//					len = in.read(adtsHeaderBuffer, 0, 7);
//					isHeader = 1;
//				}
//			}
//		} catch (IOException | InterruptedException e) {
//			e.printStackTrace();
//		} finally{
//			try {
//				if (in != null) {
//					in.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//    }
//    
//    //保存音视频
//    public void recordMedia(RawPacket rawPacket, int videoPayloadType, String videoCodec
//    					, OutputStream videoOutputStream, int audioPayloadType, String audioCodec, OutputStream audioOutputStream) throws IOException{
//		byte type = rawPacket.getPayloadType();
//		if (type == videoPayloadType) {
//			if ("H264".equals(videoCodec)) {				//如果sdp中是H264，就按照H264规则解码
//				byte[] b = RtpUtils.rtpToNaluPack(rawPacket);
//				if (b != null && b.length != 0) {
//					videoOutputStream.write(b);
//				}
//			} else if ("H265".equals(videoCodec)) {			//如果sdp中是H265，就按照H265规则解码
//				byte[] b = RtpUtils.rtpToNaluH265Pack(rawPacket);
//				if (b != null && b.length != 0) {
//					videoOutputStream.write(b);
//				}
//			}
//		} else if (type == audioPayloadType) {
//			if ("MPEG4-GENERIC".equals(audioCodec)) {		//如果sdp中是MPEG4-GENERIC，就按照AAC规则解码
//				List<byte[]> adtsList = RtpUtils.rtpToAdtsPack(rawPacket, mediaSdpInfoMap.get("audio").getClockRate());
//				if (adtsList != null && adtsList.size() != 0) {
//					for (byte[] b : adtsList) {
//						audioOutputStream.write(b);
//					}
//				}
//			} 
//		}
//    }
//    
//    //处理RTCP
//    public void dealWithRtcp(){
//		try {
//			while (true) {
//				RawPacket poll = rtcpQueue.poll(RtspNettyServer.RTCP_IDLE_TIME, TimeUnit.SECONDS);
//				log.debug("get rtcpQueue length = {}", rtcpQueue.size());
//				if (poll == null) {
//					closeThisClient();
//					break;
//				}
//				
//				if (!poll.isInvalid()) {
//		        	switch (poll.getRTCPPacketType()) {
//			    		case RTCPPacket.SR:						//发送端报告200
//			    			break;
//			    		case RTCPPacket.RR:						//接收端报告201
//			    			break;
//			    		case RTCPFBPacket.RTPFB:				//RTPFB 205, FMT是1,NACK; FMT是15, transport-cc
//			    			int fmt = (poll.getBuffer()[0] & 0x1F);
//			    			if (fmt == 1) {			//NACK，从历史记录中找到丢的包并重传
//								Collection<Integer> lostPackets = NACKPacket.getLostPackets(poll);
//								for (Integer seqnum : lostPackets) {
//									byte[] lostPacket = rtpCacheQueue.get(seqnum);
//									if (lostPacket == null) {	//如果不存在于队列中，则跳过
//										continue;
//									}
//									ByteBuf byteBuf = Unpooled.buffer(lostPacket.length);
//									byteBuf.writeBytes(lostPacket);
//									RtspNettyServer.rtpChannel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//								}
//							}
//			    			break;
//			    		case RTCPFBPacket.PSFB:					//PSFB 206, FMT是1, PLI; FMT是4, FIR; FMT是15, REMB
//			    			break;
//			    		default:
//			    			break;
//		    		}
//				} 
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//    }
// 
//}