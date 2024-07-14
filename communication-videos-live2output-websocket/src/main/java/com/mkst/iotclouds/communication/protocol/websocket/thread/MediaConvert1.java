//package com.mkst.iotclouds.communication.protocol.websocket.thread;
//
//import java.util.List;
//import java.util.Map.Entry;
//import java.util.concurrent.ConcurrentHashMap;
//
//import com.google.common.collect.Lists;
//import com.micro.iotclouds.communication.video.core.entities.Stream;
//import com.micro.iotclouds.communication.video.core.entities.StreamName;
//import com.micro.iotclouds.communication.video.core.manager.StreamManager;
//import com.micro.iotclouds.communication.video.core.messages.RtmpMediaMessage;
//import com.mkst.iotclouds.communication.protocol.websocket.entity.Camera;
//import com.mkst.iotclouds.communication.protocol.websocket.handler.WssFlvHandler1;
//import com.mkst.iotclouds.communication.protocol.websocket.service.MediaService;
//
//import cn.hutool.crypto.digest.MD5;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 拉流转换推流处理线程
// * 
// * @author ZJ
// *
// */
//@Slf4j
//public class MediaConvert1 implements Runnable {
//	
//	/**
//	 * ws客户端
//	 */
//	private ConcurrentHashMap<String, ChannelHandlerContext> wsClients = new ConcurrentHashMap<>();
//	/**
//	 * http客户端
//	 */
//	private ConcurrentHashMap<String, ChannelHandlerContext> httpClients = new ConcurrentHashMap<>();
//
//	/**
//	 * 运行状态
//	 */
//	private boolean runing = false;
//	private boolean isStart = false;
//	
//	private StreamManager streamManager;
//	
//	/**
//	 * flv header
//	 */
////	private byte[] header = null;
//	// 输出流，视频最终会输出到此
////	private ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
//	private ChannelHandlerContext ctx;
//	/**
//	 * 相机
//	 */
//	private Camera camera;
//	
//	public MediaConvert1(Camera camera,StreamManager streamManager, ChannelHandlerContext ctx) {
//		super();
//		this.camera = camera;
//		this.streamManager = streamManager;
//		this.ctx = ctx;
//	}
//
//	/**
//	 * 
//	 */
////	private void convert() {
////		// 拉流器
////		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(camera.getUrl());
////		// 超时时间(15秒)
////		grabber.setOption("stimoout", "15000000");
////		grabber.setOption("threads", "1");
////		// 如果为rtsp流，增加配置
////		if ("rtsp".equals(camera.getUrl().substring(0, 4))
////				|| "rtmp".equals(camera.getUrl().substring(0, 4))) {
////			// 设置打开协议tcp / udp
////			grabber.setOption("rtsp_transport", "tcp");
////			// 设置缓存大小，提高画质、减少卡顿花屏
////			grabber.setOption("buffer_size", "1024000");
////		}
////		
////		try {
////			grabber.start();
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////
////		// 推流器
////		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(bos, grabber.getImageWidth(), grabber.getImageHeight());
////
////		avutil.av_log_set_level(avutil.AV_LOG_ERROR);
////		recorder.setInterleaved(true);
////		recorder.setVideoOption("tune","zerolatency");
////		recorder.setVideoOption("preset", "ultrafast");
////		recorder.setVideoOption("crf", "26");
////		recorder.setVideoOption("threads", "1");
////		recorder.setFormat("flv");
////		recorder.setFrameRate(25);// 设置帧率
////		recorder.setGopSize(5);// 设置gop
//////		recorder.setVideoBitrate(500 * 1000);// 码率500kb/s
////		recorder.setVideoCodecName("libx264");
//////		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
////		recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
////		
////		try {
////			recorder.start();
////			grabber.flush();
////		} catch (org.bytedeco.javacv.FrameRecorder.Exception e1) {
////			
////			log.info("启动录制器失败", e1);
////			e1.printStackTrace();
////		} catch (Exception e1) {
////			log.info("拉流器异常", e1);
////			e1.printStackTrace();
////		}
////
////		if (header == null) {
////			header = bos.toByteArray();
//////			System.out.println(HexUtil.encodeHexStr(header));
////			bos.reset();
////		}
////		
////		runing = true;
////		long startTime = 0;
////		long videoTS = 0;
////		
////		while (runing) {
////			
////			//如果没有客户端，则关闭媒体流
////			if(isStart && wsClients.isEmpty() && httpClients.isEmpty()) {
////				runing = false;
////			} 
////			
////			try {
////				Frame frame = grabber.grabImage();
////				if(frame != null) {
////					if (startTime == 0) {
////						startTime = System.currentTimeMillis();
////					}
////					videoTS = 1000 * (System.currentTimeMillis() - startTime);
////					if (videoTS > recorder.getTimestamp()) {
//////					System.out.println("矫正时间戳: " + videoTS + " : " + recorder.getTimestamp() + " -> "
//////							+ (videoTS - recorder.getTimestamp()));
////						recorder.setTimestamp(videoTS);
////					}
////					recorder.record(frame);
////					
////					if (bos.size() > 0) {
////						byte[] b = bos.toByteArray();
////						bos.reset();
////
////						// ws输出帧流
////						for (Entry<String, ChannelHandlerContext> entry : wsClients.entrySet()) {
////							try {
////								if(entry.getValue().channel().isWritable()) {
////									entry.getValue().writeAndFlush(new BinaryWebSocketFrame(Unpooled.copiedBuffer(b)));
////								} else {
////									wsClients.remove(entry.getKey());
////									log.info("当前 http连接数：{}, ws连接数：{}", httpClients.size(), wsClients.size());
////									needClose();
////								}
////							} catch (java.lang.Exception e) {
////								wsClients.remove(entry.getKey());
////								log.info("当前 http连接数：{}, ws连接数：{}", httpClients.size(), wsClients.size());
////								needClose();
////								e.printStackTrace();
////							}
////						}
////						
////						//http
////						for (Entry<String, ChannelHandlerContext> entry : httpClients.entrySet()) {
////							try {
////								if(entry.getValue().channel().isWritable()) {
////									entry.getValue().writeAndFlush(Unpooled.copiedBuffer(b));
////								} else {
////									httpClients.remove(entry.getKey());
////									log.info("当前 http连接数：{}, ws连接数：{}", httpClients.size(), wsClients.size());
////									needClose();
////								}
////							} catch (java.lang.Exception e) {
////								httpClients.remove(entry.getKey());
////								log.info("当前 http连接数：{}, ws连接数：{}", httpClients.size(), wsClients.size());
////								needClose();
////								e.printStackTrace();
////							}
////						}
////					}
////				}
////			} catch (Exception e) {
////				runing = false;
////				// 这里后续 处理断线重连
////				e.printStackTrace();
////			} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
////				runing = false;
////				e.printStackTrace();
////			}
////		}
////		
////		//close包含stop和release方法。录制文件必须保证最后执行stop()方法
////		try {
////			recorder.close();
////			grabber.close();
////			bos.close();
////		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
////			e.printStackTrace();
////		} catch (Exception e) {
////			e.printStackTrace();
////		} catch (IOException e) {
////			e.printStackTrace();
////		} finally {
////			runing = false;
////		}
////		log.info("关闭媒体流，{} ", camera.getUrl());
////	}
//	/**
//	 * 
//	 */
//	private void convert4Bytes(byte[] b) {
//		runing = true;
//		long startTime = 0;
//		long videoTS = 0;
//		
//		while (runing) {
//			
//			//如果没有客户端，则关闭媒体流
//			if(isStart && wsClients.isEmpty() && httpClients.isEmpty()) {
//				runing = false;
//			} 
//			int iLength = b.length;
//			if (iLength > 0) {
//			//	byte[] b = new byte[iLength];
//			  //  System.arraycopy(bosBytes, 0, b, 0, iLength);;
//				//bos.reset();
//
//				// ws输出帧流
//				for (Entry<String, ChannelHandlerContext> entry : wsClients.entrySet()) {
//					try {
//						if(entry.getValue().channel().isWritable()) {
//							entry.getValue().writeAndFlush(new BinaryWebSocketFrame(Unpooled.copiedBuffer(b)));
//						} else {
//							wsClients.remove(entry.getKey());
//							log.info("当前 http连接数：{}, ws连接数：{}", httpClients.size(), wsClients.size());
//							needClose();
//						}
//					} catch (java.lang.Exception e) {
//						wsClients.remove(entry.getKey());
//						log.info("当前 http连接数：{}, ws连接数：{}", httpClients.size(), wsClients.size());
//						needClose();
//						e.printStackTrace();
//					}
//				}
//			}
////				}
//		}
//		
//		//close包含stop和release方法。录制文件必须保证最后执行stop()方法
////		try {
////			bos.close();
////		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
////			e.printStackTrace();
////		} catch (Exception e) {
////			e.printStackTrace();
////		} catch (IOException e) {
////			e.printStackTrace();
////		} finally {
////			runing = false;
////		}
//		log.info("关闭媒体流，{} ", camera.getUrl());
//	}
//
//	/**
//	 * 新增ws客戶端
//	 * @param session
//	 */
//	public void addWsClient(ChannelHandlerContext ctx) {
//		int timeout = 0;
//		while (true) {
//			try {
//				//if(runing) {
//					try {
//						if(ctx.channel().isWritable()) {
//							//发送帧前先发送header
//							//ChannelFuture writeAndFlush = ctx.writeAndFlush(new BinaryWebSocketFrame(Unpooled.copiedBuffer(header)));
//							//writeAndFlush.sync();
//							WssFlvHandler1.sendWssReqHeader(ctx);
//						} 
//						
//						wsClients.put(ctx.channel().id().toString(), ctx);
//						
//						isStart = true;
//						
//						log.info("当前 http连接数：{}, ws连接数：{}", httpClients.size(), wsClients.size());
//					} catch (java.lang.Exception e) {
//						e.printStackTrace();
//					}
//					//break;
//				//}
//				
//				//等待推拉流启动
//				Thread.currentThread().sleep(500);
//				//启动录制器失败
//				//timeout += 100;
////				if(timeout > 15000) {
////					break;
////				}
//			} catch (java.lang.Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	/**
//	 * 关闭流
//	 */
//	public void needClose() {
//		if(httpClients.isEmpty() && wsClients.isEmpty()) {
//			runing = false;
//			String mediaKey = MD5.create().digestHex(camera.getUrl());
//			MediaService.cameras.remove(mediaKey);
//		}
//	}
//	
//	
//	@Override
//	public void run() {
//		//convert();
//		String uri = camera.getUrl();
//		String[] uris = uri.split("/");
//		StreamName objStreamName = new StreamName(uris[1],uris[2].replace(".flv", ""));
//		
//		Stream stream = streamManager.getStream(objStreamName);
//		//playH264(videoFile);
//		List<RtmpMediaMessage>  lstRtmpMediaMessage = Lists.newArrayList();
//		if(stream==null) {
//			return;
//		}
//		lstRtmpMediaMessage.addAll(stream.getContent());
//		for(RtmpMediaMessage m : lstRtmpMediaMessage) {
//			if(m.raw().length==0) {
//				continue;
//			}
//			byte[] secondHalfNalu = new byte[m.raw().length];		//拿到后半段内容
//			System.arraycopy(m.raw(), 0, secondHalfNalu, 0, secondHalfNalu.length);
//			convert4Bytes(secondHalfNalu);
//		}
//	
////		this.addWsClient(ctx);
//	}
//
//}