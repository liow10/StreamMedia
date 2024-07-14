//package com.micro.iotclouds.communication.video.mp4.utils;
//import java.awt.AWTException;
//import java.awt.Graphics2D;
//import java.awt.Rectangle;
//import java.awt.RenderingHints;
//import java.awt.Robot;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.nio.ShortBuffer;
//import java.util.Scanner;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//import javax.sound.sampled.AudioFormat;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.DataLine;
//import javax.sound.sampled.LineUnavailableException;
//import javax.sound.sampled.TargetDataLine;
//
//import org.bytedeco.ffmpeg.global.avcodec;
//import org.bytedeco.ffmpeg.global.avutil;
//import org.bytedeco.javacv.FFmpegFrameRecorder;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.javacv.FrameRecorder.Exception;
//import org.bytedeco.javacv.Java2DFrameConverter;
//
//public class VideoRecord2 {
//	//线程池 screenTimer,录制视频
//	private ScheduledThreadPoolExecutor screenTimer;
//	//获取屏幕尺寸
//	private final Rectangle rectangle = new Rectangle(Constant.WIDTH, Constant.HEIGHT); // 截屏的大小
//	//视频类 FFmpegFrameRecorder
//	private FFmpegFrameRecorder recorder;
//	private Robot robot;
//	
//	//线程池 exec，录制音频
//	private ScheduledThreadPoolExecutor exec;
//	private TargetDataLine line;
//	private AudioFormat audioFormat;
//	private DataLine.Info dataLineInfo;
//	///是否开启录音设备
//	private boolean isHaveDevice = true;
//	private long startTime = 0;
//	private long videoTS = 0;
//	private long pauseTimeStart = 0;//开始暂停的时间
//	private long pauseTime = 0;//暂停的时长
//	private double frameRate = 5;
//	
//	private String state="start";//录制状态：start正在录制，pause暂停录制，stop停止录制
//	public String getState() {
//		return state;
//	}
//	
//	
//	public VideoRecord2(String fileName, boolean isHaveDevice) {
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
//		try {
//			robot = new Robot();
//		} catch (AWTException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			recorder.start();
//		} catch (Exception e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//		
//		if(isHaveDevice) {
//			/**
//			 * float sampleRate：采样率；每秒采样数 ；
//			 * int sampleSizeInBits：采样位数；每个样本中的位数 ；
//			 * int channels：音频通道数，1为mono，2为立体声；
//			 * boolean signed；
//			 * boolean bigEndian：是否为大端存储；指示单个样本的数据是否以大字节顺序存储
//			 */
//			audioFormat = new AudioFormat(44100.0F, 16, 2, true, false);
//			dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
//			try {
//				line = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
//			} catch (LineUnavailableException e1) {
//				// TODO Auto-generated catch block
//				System.out.println("未获得音频线路，"+e1);
//			}
//		}
//		this.isHaveDevice = isHaveDevice;
//	}
//	
//	
//	/**
//	 * 开始录制
//	 */
//	public void start() {
//		state="start";
//		if (startTime == 0) {
//			startTime = System.currentTimeMillis();
//		}
//		if(pauseTimeStart!=0) {
//			//计算暂停的时长
//			pauseTime=System.currentTimeMillis()-pauseTimeStart;
//			pauseTimeStart=0;//归零
//		}
//		else {
//			//没有暂停过，暂停时长为0
//			pauseTime=0;
//		}
//		
//		// 如果有录音设备则启动录音线程
//		if (isHaveDevice) {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					SoundCaputre();
//				}
//			}).start();
//
//		}
//		
//		//录屏
//		screenCaptrue();
//	}
//	
//	//开启录屏的线程
//	private void screenCaptrue() {
//		// 录屏
//		screenTimer = new ScheduledThreadPoolExecutor(1);
//		/***
//		 * 参数：
//		 * 	command - 要执行的任务 
//			initialDelay - 延迟第一次执行的时间 ，延迟一帧的时间，我们设置的mp4的帧速为frameRate=每秒5帧，所以一帧的时间为 1秒/5
//			period - 连续执行之间的时期 ，执行周期，为1帧的时间
//			unit - initialDelay和period参数的时间单位，TimeUnit.MILLISECONDS为千分之一秒，就是1毫秒
//		 */
//		screenTimer.scheduleAtFixedRate(new Runnable() {
//			@Override
//			public void run() {
//				BufferedImage screenCapture = robot.createScreenCapture(rectangle); // 截屏
//				
//				BufferedImage videoImg = new BufferedImage(Constant.WIDTH, Constant.HEIGHT,
//						BufferedImage.TYPE_3BYTE_BGR); // 声明一个BufferedImage用重绘截图
//				
//				Graphics2D videoGraphics = videoImg.createGraphics();// 创建videoImg的Graphics2D
//				
//				videoGraphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
//				videoGraphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
//						RenderingHints.VALUE_COLOR_RENDER_SPEED);
//				videoGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
//				videoGraphics.drawImage(screenCapture, 0, 0, null); // 重绘截图
//				
//				Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
//				
//				Frame frame = java2dConverter.convert(videoImg);
//				try {
//					//计算总时长
//					videoTS = 1000L*(System.currentTimeMillis()-startTime-pauseTime);
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
//	 * 开启抓取声音的线程
//	 */
//	public void SoundCaputre() {	
//		
//		try {
//			if(!line.isRunning()){
//				line.open(audioFormat);
//				line.start();
//			}
//		} catch (LineUnavailableException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//
//		final int sampleRate = (int) audioFormat.getSampleRate();
//		final int numChannels = audioFormat.getChannels();
//
//		int audioBufferSize = sampleRate * numChannels;
//		final byte[] audioBytes = new byte[audioBufferSize];
//
//		exec = new ScheduledThreadPoolExecutor(1);
//		exec.scheduleAtFixedRate(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					int nBytesRead = line.read(audioBytes, 0, line.available());
//					int nSamplesRead = nBytesRead / 2;
//					short[] samples = new short[nSamplesRead];
//
//					// Let's wrap our short[] into a ShortBuffer and
//					// pass it to recordSamples
//					ByteBuffer.wrap(audioBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(samples);
//					ShortBuffer sBuff = ShortBuffer.wrap(samples, 0, nSamplesRead);
//
//					// recorder is instance of
//					// org.bytedeco.javacv.FFmpegFrameRecorder
//					recorder.recordSamples(sampleRate, numChannels, sBuff);
//					// System.gc();
//				} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}, (int) (1000 / frameRate), (int) (1000 / frameRate), TimeUnit.MILLISECONDS);
//	}
//	
//	/**
//	 * 暂停录制
//	 */
//	public void pause() {
//		state="pause";
//		screenTimer.shutdownNow();
//		screenTimer = null;
//		if (isHaveDevice) {
//			exec.shutdownNow();
//			exec = null;
//		}
//		pauseTimeStart = System.currentTimeMillis();
//		
//	}
//	/**
//	 * 停止录制
//	 */
//	public void stop() {
//		state="stop";
//		if (null != screenTimer) {
//			screenTimer.shutdownNow();
//		}
//		try {
//			if (isHaveDevice) {
//				if (null != exec) {
//					exec.shutdownNow();
//				}
//				if (null != line) {
//					line.stop();
//					line.close();
//				}
//				dataLineInfo = null;
//				audioFormat = null;
//			}
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
//	
//	public static void main(String[] args) {
//		VideoRecord2 videoRecord = new VideoRecord2("C:\\Users\\Administrator\\Desktop\\视频2", true);
//		videoRecord.start();
//		System.out.println("****start继续录制，pause暂停录制，stop停止录制****");
//		while (true) {
//			Scanner sc = new Scanner(System.in);
//			if(sc.hasNext()) {
//				String cmd=sc.next();
//				if (cmd.equalsIgnoreCase("stop")) {
//					videoRecord.stop();
//					System.out.println("****已经停止录制****");
//					break;
//				}
//				if (cmd.equalsIgnoreCase("pause")) {
//					if(videoRecord.getState().equals("pause")) {
//						System.out.println("*error:已经暂停，请勿重复操作pause*");
//						continue;
//					}
//					videoRecord.pause();
//					System.out.println("****已暂停，start继续录制，stop结束录制****");
//				}
//				if (cmd.equalsIgnoreCase("start")) {
//					if(videoRecord.getState().equals("start")) {
//						System.out.println("*error:请勿重复操作start*");
//						continue;
//					}
//					videoRecord.start();
//					System.out.println("****正在录制****");
//				}
//			}
//		}
//	}
//	
//}