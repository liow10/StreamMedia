//package com.micro.iotclouds.communication.video.mp4.utils;
//
//import java.io.IOException;
//import java.io.PipedInputStream;
//import java.io.PipedOutputStream;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//import org.bytedeco.ffmpeg.avcodec.AVPacket;
//import org.bytedeco.ffmpeg.global.avcodec;
//import org.bytedeco.ffmpeg.global.avutil;
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.FFmpegFrameRecorder;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.javacv.FrameRecorder.Exception;
//
//import com.micro.iotclouds.rtsp2rtmp.util.IpUtil;
//
//public class VideoRecord {
//	// 线程池 screenTimer,录制视频
//	private ScheduledThreadPoolExecutor screenTimer;
//	// 获取屏幕尺寸
////	private final Rectangle rectangle = new Rectangle(Constant.WIDTH, Constant.HEIGHT); // 截屏的大小
//	// 视频类 FFmpegFrameRecorder
//	private FFmpegFrameRecorder recorder;
////	private Robot robot;
//
//	// 线程池 exec，录制音频
//	private ScheduledThreadPoolExecutor exec;
////	private TargetDataLine line;
////	private AudioFormat audioFormat;
////	private DataLine.Info dataLineInfo;
//	/// 是否开启录音设备
//	private boolean isHaveDevice = true;
//	private long startTime = 0;
//	private long videoTS = 0;
//	private long pauseTimeStart = 0;// 开始暂停的时间
//	private long pauseTime = 0;// 暂停的时长
//	private double frameRate = 5;
//	
//	//grabber输入管道，用于提供视频流字节数据给FrameGrabber
//	private PipedInputStream pis;
//	
//	//sdk回调输出管道，用于从sdk回调函数读取视频字节流
//	private PipedOutputStream pos = new PipedOutputStream();
//	
//
//	private String state = "start";// 录制状态：start正在录制，pause暂停录制，stop停止录制
//
//	public String getState() {
//		return state;
//	}
//
//	public VideoRecord(String fileName, boolean isHaveDevice) {
//		// TODO Auto-generated constructor stub
//		recorder = new FFmpegFrameRecorder(fileName + ".mp4", Constant.WIDTH, Constant.HEIGHT);
////		 recorder.setVideoCodec(avcodec.AV_CODEC_ID_H265); // 28
//		recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4); // 13
//		recorder.setFormat("mp4");
//		// recorder.setFormat("mov,mp4,m4a,3gp,3g2,mj2,h264,ogg,MPEG4");
//		recorder.setSampleRate(44100);
//		recorder.setFrameRate(frameRate);
//		recorder.setVideoQuality(0);
//		recorder.setVideoOption("crf", "23");
//		// 2000 kb/s, 720P视频的合理比特率范围
//		recorder.setVideoBitrate(1000000);
//		/**
//		 * 权衡quality(视频质量)和encode speed(编码速度) values(值)： ultrafast(终极快),superfast(超级快),
//		 * veryfast(非常快), faster(很快), fast(快), medium(中等), slow(慢), slower(很慢),
//		 * veryslow(非常慢)
//		 * ultrafast(终极快)提供最少的压缩（低编码器CPU）和最大的视频流大小；而veryslow(非常慢)提供最佳的压缩（高编码器CPU）的同时降低视频流的大小
//		 * 参考：https://trac.ffmpeg.org/wiki/Encode/H.264 官方原文参考：-preset ultrafast as the
//		 * name implies provides for the fastest possible encoding. If some tradeoff
//		 * between quality and encode speed, go for the speed. This might be needed if
//		 * you are going to be transcoding multiple streams on one machine.
//		 */
//		recorder.setVideoOption("preset", "slow");
//		recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P); // yuv420p
//		recorder.setAudioChannels(2);
//		recorder.setAudioOption("crf", "0");
//		// Highest quality
//		recorder.setAudioQuality(0);
//		recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
////		try {
////			robot = new Robot();
////		} catch (AWTException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		try {
//			recorder.start();
//		} catch (Exception e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//
////		if (isHaveDevice) {
////			/**
////			 * float sampleRate：采样率；每秒采样数 ； int sampleSizeInBits：采样位数；每个样本中的位数 ； int
////			 * channels：音频通道数，1为mono，2为立体声； boolean signed； boolean
////			 * bigEndian：是否为大端存储；指示单个样本的数据是否以大字节顺序存储
////			 */
////			audioFormat = new AudioFormat(44100.0F, 16, 2, true, false);
////			dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
////			try {
////				line = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
////			} catch (LineUnavailableException e1) {
////				// TODO Auto-generated catch block
////				System.out.println("未获得音频线路，" + e1);
////			}
////		}
////		this.isHaveDevice = isHaveDevice;
//	}
//
//	/**
//	 * 开始录制
//	 */
//	public void start() {
//		state = "start";
//		if (startTime == 0) {
//			startTime = System.currentTimeMillis();
//		}
//		if (pauseTimeStart != 0) {
//			// 计算暂停的时长
//			pauseTime = System.currentTimeMillis() - pauseTimeStart;
//			pauseTimeStart = 0;// 归零
//		} else {
//			// 没有暂停过，暂停时长为0
//			pauseTime = 0;
//		}
//
////		// 如果有录音设备则启动录音线程
////		if (isHaveDevice) {
////			new Thread(new Runnable() {
////				@Override
////				public void run() {
////					// TODO Auto-generated method stub
////					SoundCaputre();
////				}
////			}).start();
////
////		}
////
////		// 录屏
////		screenCaptrue();
//	}
//
//	// 开启录屏的线程
////	private void screenCaptrue() {
////		// 录屏
////		screenTimer = new ScheduledThreadPoolExecutor(1);
////		/***
////		 * 参数： command - 要执行的任务 initialDelay - 延迟第一次执行的时间
////		 * ，延迟一帧的时间，我们设置的mp4的帧速为frameRate=每秒5帧，所以一帧的时间为 1秒/5 period - 连续执行之间的时期
////		 * ，执行周期，为1帧的时间 unit -
////		 * initialDelay和period参数的时间单位，TimeUnit.MILLISECONDS为千分之一秒，就是1毫秒
////		 */
////		screenTimer.scheduleAtFixedRate(new Runnable() {
////			@Override
////			public void run() {
////				BufferedImage screenCapture = robot.createScreenCapture(rectangle); // 截屏
////
////				BufferedImage videoImg = new BufferedImage(Constant.WIDTH, Constant.HEIGHT,
////						BufferedImage.TYPE_3BYTE_BGR); // 声明一个BufferedImage用重绘截图
////
////				Graphics2D videoGraphics = videoImg.createGraphics();// 创建videoImg的Graphics2D
////
////				videoGraphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
////				videoGraphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
////						RenderingHints.VALUE_COLOR_RENDER_SPEED);
////				videoGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
////				videoGraphics.drawImage(screenCapture, 0, 0, null); // 重绘截图
////
////				Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
////
////				Frame frame = java2dConverter.convert(videoImg);
////				try {
////					// 计算总时长
////					videoTS = 1000L * (System.currentTimeMillis() - startTime - pauseTime);
////
////					// 检查偏移量
////					if (videoTS > recorder.getTimestamp()) {
////						recorder.setTimestamp(videoTS);
////					}
////					recorder.record(frame); // 录制视频
////				} catch (Exception e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
////				// 释放资源
////				videoGraphics.dispose();
////				videoGraphics = null;
////				videoImg.flush();
////				videoImg = null;
////				java2dConverter = null;
////				screenCapture.flush();
////				screenCapture = null;
////			}
////
////		}, (int) (1000 / frameRate), (int) (1000 / frameRate), TimeUnit.MILLISECONDS);
////	}
//	/**
//	 * 接收视频流转存
//	 * 
//	 */
//	public void videoRecording() {
//		// 录屏
//		screenTimer = new ScheduledThreadPoolExecutor(1);
//		/***
//		 * 参数： command - 要执行的任务 initialDelay - 延迟第一次执行的时间
//		 * ，延迟一帧的时间，我们设置的mp4的帧速为frameRate=每秒5帧，所以一帧的时间为 1秒/5 period - 连续执行之间的时期
//		 * ，执行周期，为1帧的时间 unit -
//		 * initialDelay和period参数的时间单位，TimeUnit.MILLISECONDS为千分之一秒，就是1毫秒
//		 */
//		screenTimer.scheduleAtFixedRate(new Runnable() {
//			@Override
//			public void run() {
////				BufferedImage screenCapture = robot.createScreenCapture(rectangle); // 截屏
////
////				BufferedImage videoImg = new BufferedImage(Constant.WIDTH, Constant.HEIGHT,
////						BufferedImage.TYPE_3BYTE_BGR); // 声明一个BufferedImage用重绘截图
////
////				Graphics2D videoGraphics = videoImg.createGraphics();// 创建videoImg的Graphics2D
////
////				videoGraphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
////				videoGraphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
////						RenderingHints.VALUE_COLOR_RENDER_SPEED);
////				videoGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
////				videoGraphics.drawImage(screenCapture, 0, 0, null); // 重绘截图
////
////				Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
//
//				Frame frame = java2dConverter.convert(videoImg);
//				AVPacket packet = null;
//				try {
//					// 计算总时长
//					videoTS = 1000L * (System.currentTimeMillis() - startTime - pauseTime);
//
//					// 检查偏移量
//					if (videoTS > recorder.getTimestamp()) {
//						recorder.setTimestamp(videoTS);
//					}
//					recorder.record(frame); // 录制视频
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// 释放资源
//				videoGraphics.dispose();
//				videoGraphics = null;
//				videoImg.flush();
//				videoImg = null;
//				java2dConverter = null;
//				screenCapture.flush();
//				screenCapture = null;
//			}
//
//		}, (int) (1000 / frameRate), (int) (1000 / frameRate), TimeUnit.MILLISECONDS);
//	}
//
//	/**
//	 * 选择视频源
//	 *
//	 * @author wuguodong
//	 * @throws org.bytedeco.javacv.FrameGrabber.Exception
//	 * @throws org.bytedeco.javacv.FrameGrabber.Exception
//	 * @throws org.bytedeco.javacv.FrameGrabber.Exception
//	 * @throws Exception
//	 */
//	public void fromMediaBytes() throws Exception {
//		if (grabber == null) {
//			
//			//默认1024，该管道流用于提供视频流字节数据给FrameGrabber，该管道流从sdk回调输出管道读取字节数据
//			pis = new PipedInputStream(pos,1024);
//			//这里要调成0，防止reset错误，因为管道流是不能reset的
//			grabber = new FFmpegFrameGrabber(pis,0);
//			//该方法会阻塞，直到管道流有数据
//			grabber.start();
//			
//			
//		
//
//			logger.debug("******   TCPCheck    END     ******");
//
//			if (cameraPojo.getRtsp().indexOf("rtsp") >= 0) {
//				grabber.setOption("rtsp_transport", "tcp");// tcp用于解决丢包问题
//			}
//			// 设置采集器构造超时时间
//			grabber.setOption("stimeout", "2000000");
//			try {
//				logger.debug("******   grabber.start()    BEGIN   ******");
//
//				if ("sub".equals(cameraPojo.getStream())) {
//					grabber.start(config.getSub_code());
//				} else if ("main".equals(cameraPojo.getStream())) {
//					grabber.start(config.getMain_code());
//				} else {
//					grabber.start(config.getMain_code());
//				}
//
//				logger.debug("******   grabber.start()    END     ******");
//
//				// 开始之后ffmpeg会采集视频信息，之后就可以获取音视频信息
//				if (width < 0 || height < 0) {
//					width = grabber.getImageWidth();
//					height = grabber.getImageHeight();
//				}
//				// 若视频像素值为0，说明拉流异常，程序结束
//				if (width == 0 && height == 0) {
//					logger.error(cameraPojo.getRtsp() + "  拉流异常！");
//					grabber.stop();
//					grabber.close();
//					grabber = null;
//					return null;
//				}
//				// 视频参数
//				audiocodecid = grabber.getAudioCodec();
//				codecid = grabber.getVideoCodec();
//				framerate = grabber.getVideoFrameRate();// 帧率
//				bitrate = grabber.getVideoBitrate();// 比特率
//				// 音频参数
//				// 想要录制音频，这三个参数必须有：audioChannels > 0 && audioBitrate > 0 && sampleRate > 0
//				audioChannels = grabber.getAudioChannels();
//				audioBitrate = grabber.getAudioBitrate();
//				if (audioBitrate < 1) {
//					audioBitrate = 128 * 1000;// 默认音频比特率
//				}
//			} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
//				logger.error("ffmpeg错误信息：", e);
//				grabber.stop();
//				grabber.close();
//				return null;
//			}
//		}
//		return this;
//	}
//	/**
//	 * 开启抓取声音的线程
//	 */
////	public void SoundCaputre() {
////
////		try {
////			if (!line.isRunning()) {
////				line.open(audioFormat);
////				line.start();
////			}
////		} catch (LineUnavailableException e1) {
////			// TODO Auto-generated catch block
////			e1.printStackTrace();
////		}
////
////		final int sampleRate = (int) audioFormat.getSampleRate();
////		final int numChannels = audioFormat.getChannels();
////
////		int audioBufferSize = sampleRate * numChannels;
////		final byte[] audioBytes = new byte[audioBufferSize];
////
////		exec = new ScheduledThreadPoolExecutor(1);
////		exec.scheduleAtFixedRate(new Runnable() {
////			@Override
////			public void run() {
////				try {
////					int nBytesRead = line.read(audioBytes, 0, line.available());
////					int nSamplesRead = nBytesRead / 2;
////					short[] samples = new short[nSamplesRead];
////
////					// Let's wrap our short[] into a ShortBuffer and
////					// pass it to recordSamples
////					ByteBuffer.wrap(audioBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(samples);
////					ShortBuffer sBuff = ShortBuffer.wrap(samples, 0, nSamplesRead);
////
////					// recorder is instance of
////					// org.bytedeco.javacv.FFmpegFrameRecorder
////					recorder.recordSamples(sampleRate, numChannels, sBuff);
////					// System.gc();
////				} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
////					e.printStackTrace();
////				}
////			}
////		}, (int) (1000 / frameRate), (int) (1000 / frameRate), TimeUnit.MILLISECONDS);
////	}
//
//	/**
//	 * 暂停录制
//	 */
//	public void pause() {
//		state = "pause";
//		screenTimer.shutdownNow();
//		screenTimer = null;
//		if (isHaveDevice) {
//			exec.shutdownNow();
//			exec = null;
//		}
//		pauseTimeStart = System.currentTimeMillis();
//
//	}
//
//	/**
//	 * 停止录制
//	 */
//	public void stop() {
//		state = "stop";
//		if (null != screenTimer) {
//			screenTimer.shutdownNow();
//		}
//		try {
////			if (isHaveDevice) {
////				if (null != exec) {
////					exec.shutdownNow();
////				}
////				if (null != line) {
////					line.stop();
////					line.close();
////				}
////				dataLineInfo = null;
////				audioFormat = null;
////			}
//			recorder.stop();
//			recorder.release();
//			recorder.close();
//			screenTimer = null;
//			// screenCapture = null;
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public static String cmdString = "start";
//
//	public static void startInstance() {
//
//		VideoRecord videoRecord = new VideoRecord("C:\\Users\\Administrator\\Desktop\\视频2", true);
//		videoRecord.start();
//		System.out.println("****start继续录制，pause暂停录制，stop停止录制****");
//		while (true) {
//			// Scanner sc = new Scanner(System.in);
//			// if(sc.hasNext()) {
//			// String cmd=sc.next();
//			if (cmdString.equalsIgnoreCase("stop")) {
//				videoRecord.stop();
//				System.out.println("****已经停止录制****");
//				break;
//			}
//			if (cmdString.equalsIgnoreCase("pause")) {
//				if (videoRecord.getState().equals("pause")) {
//					System.out.println("*error:已经暂停，请勿重复操作pause*");
//					continue;
//				}
//				videoRecord.pause();
//				System.out.println("****已暂停，start继续录制，stop结束录制****");
//			}
//			if (cmdString.equalsIgnoreCase("start")) {
//				if (videoRecord.getState().equals("start")) {
//					System.out.println("*error:请勿重复操作start*");
//					continue;
//				}
//				videoRecord.start();
//				System.out.println("****正在录制****");
//			}
//			// }
//		}
//	}
//
//}