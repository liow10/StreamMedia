//package test;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.Map.Entry;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.bytedeco.ffmpeg.global.avutil;
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.FFmpegFrameRecorder;
//import org.bytedeco.javacv.FFmpegLogCallback;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.javacv.FrameGrabber.Exception;
//import org.jitsi.service.neomedia.MediaService;
//
//import com.micro.iotclouds.communication.video.core.manager.StreamManager;
//
//import cn.hutool.crypto.digest.MD5;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
//import io.netty.util.concurrent.Future;
//import io.netty.util.concurrent.GenericFutureListener;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 拉流转换推流处理线程
// * 
// * @author ZJ
// *
// */
//@Slf4j
//public class MediaConvert extends Thread {
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
//
//	private boolean grabberStatus = false;
//
//	/**
//	 * 是否可以自动关闭流
//	 */
//	private boolean autoClose = true;
//
//	private int hcSize, wcSize = 0;
//
//	/**
//	 * 没有客户端计数
//	 */
//	private int noClient = 0;
//
//	/**
//	 * flv header
//	 */
//	private byte[] header = null;
//	// 输出流，视频最终会输出到此
//	private ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
//	/**
//	 * 相机
//	 */
//	private Camera camera;
//
//	private StreamManager streamManager;
//
//	/**
//	 * @param camera
//	 * @param auto   流是否可以自动关闭
//	 */
//	public MediaConvert(Camera camera, boolean autoClose, StreamManager streamManager) {
//		super();
//		this.autoClose = autoClose;
//		this.camera = camera;
//		this.streamManager = streamManager;
//	}
//
//	public boolean isRuning() {
//		return runing;
//	}
//
//	public void setRuning(boolean runing) {
//		this.runing = runing;
//	}
//
//	/**
//	 * 
//	 */
//	private void convert() {
//		// 拉流器
//		FFmpegFrameGrabber grabber = null;
////		if(StringUtils.isNotBlank(camera.getUrl())) {
//		// 拉流器
//		grabber = new FFmpegFrameGrabber(camera.getUrl());
////		}else {
////			 InputStream inputStream = null;
////			 int maximumSize = 0;
////			 StreamName sn = new StreamName("live", camera);
////			 Stream stream = streamManager.getStream(sn);
////			 grabber = new FFmpegFrameGrabber(inputStream,maximumSize);
////		}
//		// 超时时间(15秒)
//		grabber.setOption("stimoout", "15000000");
//		grabber.setOption("threads", "1");
//		grabber.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
//		// 设置缓存大小，提高画质、减少卡顿花屏
//		grabber.setOption("buffer_size", "1024000");
//		// 如果为rtsp流，增加配置
//		if ("rtsp".equals(camera.getUrl().substring(0, 4))) {
//			// 设置打开协议tcp / udp
//			grabber.setOption("rtsp_transport", "tcp");
//		}
//
//		try {
//			grabber.start();
//			grabberStatus = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		}
//
//		// 推流器
//		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(bos, grabber.getImageWidth(), grabber.getImageHeight());
//
//		avutil.av_log_set_level(avutil.AV_LOG_ERROR);
//		FFmpegLogCallback.set();
//
//		recorder.setInterleaved(false);
//		recorder.setVideoOption("tune", "zerolatency");
//		recorder.setVideoOption("preset", "ultrafast");
//		recorder.setVideoOption("crf", "26");
//		recorder.setVideoOption("threads", "1");
//		recorder.setFormat("flv");
//		recorder.setFrameRate(25);// 设置帧率
//		recorder.setGopSize(5);// 设置gop
////		recorder.setVideoBitrate(500 * 1000);// 码率500kb/s
//		recorder.setVideoCodecName("libx264");
////		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//		recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
////		recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
//		recorder.setAudioCodecName("aac");
//		recorder.setAudioChannels(grabber.getAudioChannels());
//
//		try {
//			recorder.start();
//			grabber.flush();
//		} catch (org.bytedeco.javacv.FrameRecorder.Exception e1) {
//
//			log.info("启动录制器失败", e1);
//			e1.printStackTrace();
//		} catch (Exception e1) {
//			log.info("拉流器异常", e1);
//			e1.printStackTrace();
//		}
//
//		if (header == null) {
//			header = bos.toByteArray();
////			System.out.println(HexUtil.encodeHexStr(header));
//			bos.reset();
//		}
//
//		runing = true;
//		long startTime = 0;
//		long videoTS = 0;
//		long lastTimes = System.currentTimeMillis();
//
//		while (runing && grabberStatus) {
//
//			if ((System.currentTimeMillis() - lastTimes) > 1000) {
//				try {
//					grabber.restart(); // grabber.grabFrame() avformat
//					grabber.flush();
////					log.info("\r\n{}\r\n重连成功》》》", camera.getUrl());
//				} catch (Exception e) {
////					log.info("\r\n{}\r\n重连失败！", camera.getUrl());
//				} finally {
//					lastTimes = System.currentTimeMillis();
//				}
//			}
//
//			hasClient();
//
//			try {
//				Frame frame = grabber.grabFrame();
//				if (frame != null) {
//					lastTimes = System.currentTimeMillis();
//
//					if (startTime == 0) {
//						startTime = System.currentTimeMillis();
//					}
//					videoTS = 1000 * (System.currentTimeMillis() - startTime);
//					// 判断时间偏移
//					if (videoTS > recorder.getTimestamp()) {
////					System.out.println("矫正时间戳: " + videoTS + " : " + recorder.getTimestamp() + " -> "
////							+ (videoTS - recorder.getTimestamp()));
//						recorder.setTimestamp((videoTS));
//					}
//					recorder.record(frame);
//
//					if (bos.size() > 0) {
//						byte[] b = bos.toByteArray();
//						bos.reset();
//
//						// ws输出帧流
//						for (Entry<String, ChannelHandlerContext> entry : wsClients.entrySet()) {
//							try {
//								if (entry.getValue().channel().isWritable()) {
//									entry.getValue().writeAndFlush(new BinaryWebSocketFrame(Unpooled.copiedBuffer(b)));
//								} else {
//									wsClients.remove(entry.getKey());
//									hasClient();
//								}
//							} catch (java.lang.Exception e) {
//								wsClients.remove(entry.getKey());
//								hasClient();
//								e.printStackTrace();
//							}
//						}
//
//						// http
//						for (Entry<String, ChannelHandlerContext> entry : httpClients.entrySet()) {
//							try {
//								if (entry.getValue().channel().isWritable()) {
//									entry.getValue().writeAndFlush(Unpooled.copiedBuffer(b));
//								} else {
//									httpClients.remove(entry.getKey());
//									hasClient();
//								}
//							} catch (java.lang.Exception e) {
//								httpClients.remove(entry.getKey());
//								hasClient();
//								e.printStackTrace();
//							}
//						}
//					}
//				}
//
//			} catch (Exception e) {
////				log.info("\r\n{}\r\n尝试重连。。。", camera.getUrl());
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e1) {
//				}
////				e.printStackTrace();
//			} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
////				runing = false;
//				log.info("\r\n{}\r\n录制器出现异常。。。", camera.getUrl());
//				e.printStackTrace();
//			}
//		}
//
//		// close包含stop和release方法。录制文件必须保证最后执行stop()方法
//		try {
//			recorder.close();
//			grabber.close();
//			bos.close();
//		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			runing = false;
//		}
//		log.info("关闭媒体流，{} ", camera.getUrl());
//	}
//
//	/**
//	 * 新增ws客戶端
//	 * 
//	 * @param session
//	 */
//	public void addWsClient(ChannelHandlerContext ctx) {
//		int timeout = 0;
//		while (true) {
//			try {
//				if (runing) {
//					try {
//						if (ctx.channel().isWritable()) {
//							// 发送帧前先发送header
//							ChannelFuture future = ctx
//									.writeAndFlush(new BinaryWebSocketFrame(Unpooled.copiedBuffer(header)));
//							future.addListener(new GenericFutureListener<Future<? super Void>>() {
//								@Override
//								public void operationComplete(Future<? super Void> future) throws Exception {
//									if (future.isSuccess()) {
//										wsClients.put(ctx.channel().id().toString(), ctx);
//									}
//								}
//							});
//						}
//
//					} catch (java.lang.Exception e) {
//						e.printStackTrace();
//					}
//					break;
//				}
//
//				// 等待推拉流启动
//				Thread.sleep(100);
//				// 启动录制器失败
//				timeout += 100;
//				if (timeout > 15000) {
//					break;
//				}
//			} catch (java.lang.Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * 关闭流
//	 * 
//	 * @return
//	 */
//	public void hasClient() {
//
//		int newHcSize = httpClients.size();
//		int newWcSize = wsClients.size();
//		if (hcSize != newHcSize || wcSize != newWcSize) {
//			hcSize = newHcSize;
//			wcSize = newWcSize;
//			log.info("\r\n{}\r\nhttp连接数：{}, ws连接数：{} \r\n", camera.getUrl(), newHcSize, newWcSize);
//		}
//
//		// 自动拉流无需关闭
//		if (!autoClose) {
//			if (httpClients.isEmpty() && wsClients.isEmpty()) {
//				try {
//					Thread.sleep(5); // 不能太久
//				} catch (InterruptedException e) {
//				}
//			}
//			return;
//		}
//		if (httpClients.isEmpty() && wsClients.isEmpty()) {
//			// 5*2000=10000=10，等待10秒还没有客户端，则关闭推流
//			if (noClient > 2000) {
//				runing = false;
//				String mediaKey = MD5.create().digestHex(camera.getUrl());
//				// MediaService.cameras.remove(mediaKey);
//
//			} else {
//				try {
//					Thread.sleep(5);
//				} catch (InterruptedException e) {
//				}
//				noClient += 1;
//			}
//		} else {
//			noClient = 0;
//		}
//	}
//
//	/**
//	 * 新增http客戶端
//	 * 
//	 * @param session
//	 */
//	public void addHttpClient(ChannelHandlerContext ctx) {
//		int timeout = 0;
//		while (true) {
//			try {
//				if (runing) {
//					try {
//						if (ctx.channel().isWritable()) {
//							// 发送帧前先发送header
//							ChannelFuture future = ctx.writeAndFlush(Unpooled.copiedBuffer(header));
//							future.addListener(new GenericFutureListener<Future<? super Void>>() {
//								@Override
//								public void operationComplete(Future<? super Void> future) throws Exception {
//									if (future.isSuccess()) {
//										httpClients.put(ctx.channel().id().toString(), ctx);
//									}
//								}
//							});
//						}
//
//					} catch (java.lang.Exception e) {
//						e.printStackTrace();
//					}
//					break;
//				}
//
//				// 等待推拉流启动
//				Thread.sleep(100);
//
//				// 启动录制器失败
//				timeout += 100;
//				if (timeout > 15000) {
//					break;
//				}
//			} catch (java.lang.Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@Override
//	public void run() {
//		convert();
//	}
//
//}