package com.micro.iotclouds.communication.video.mp4.utils;

import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;

/**
 * 录制直播视频到本地
 */
public class RecordLiveVideoThread implements Runnable {
	/**
	 * 流地址 例如：rtmp://58.200.131.2:1935/livetv/hunantv 湖南卫视
	 */
	private String streamUrl = "https://flvopen.ys7.com:9188/openlive/0db58788a9df411cb0977f08c804b98a.hd.flv";
	/**
	 * 停止录制时长 0为不限制时长
	 */
	public long timesSec = 0L;
	/**
	 * 视频文件的输出路径
	 */
	public String outFilePath;
	/**
	 * 录制的视频文件格式(文件后缀名)
	 */
	private String filenameExtension = "mp4";
	/**
	 * 是否录制音频
	 */
	public boolean hasAudio = false;

	private PipedInputStream piInputStream;// 抓图输入流
	private PipedOutputStream piOutputStream;// 抓图输出流
	private FFmpegFrameGrabber grabber;// 抓流器
	private ArrayList<String> picturePaths = new ArrayList<>();

	public RecordLiveVideoThread(PipedInputStream piInputStream, PipedOutputStream piOutputStream) {
		this.piInputStream = piInputStream;
		this.piOutputStream = piOutputStream;
	}

	int frameRate = 60;
	
	String format = "h264";
	
	int bitrate  = 8000000;
	
	int numBuffers = 0;
	
	@Override
	public void run() {
//		if (outFilePath == null || outFilePath.length() == 0) {
//			System.out.println("文件输出路径不能为空。");
//			return;
//		}
		// 根据直播链接实例FFmpeg抓帧器
//		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(streamUrl);
		try {
			piInputStream.connect(piOutputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		grabber = new FFmpegFrameGrabber(piInputStream, 0);
		grabber.setFrameRate(frameRate);// 60

		grabber.setFormat(format);// h264

		grabber.setVideoBitrate(bitrate);// 8000000

//		grabber.setVideoOption("preset", preset);

		grabber.setNumBuffers(numBuffers);// 0
		
		FFmpegFrameRecorder recorder = null;
		try {
			grabber.start();
			Frame frame = grabber.grabFrame();
			if (frame != null) {
				// 保存到本地的文件
				File outFile = new File(outFilePath);
				// 文件不存在 || 文件不是一个普通文件
				if (!outFile.exists() || !outFile.isFile()) {
					if (!outFile.createNewFile()) {
						System.out.println("文件创建失败");
						return;
					}
				}
				// 视频输出地址,视频宽分辨率(宽,高),是否录制音频（0:不录制/1:录制）
				recorder = new FFmpegFrameRecorder(outFilePath, frame.imageWidth, frame.imageHeight, hasAudio ? 1 : 0);
				// 直播流格式
				recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
				// 录制的视频格式
				recorder.setFormat(filenameExtension);
				// 视频帧数
				recorder.setFrameRate(60);
				// 开始录制
				recorder.start();
				// 计算结束时间
				long endTime = System.currentTimeMillis() + timesSec * 1000;
				// 如果没有到录制结束时间并且获取到了下一帧则继续录制
				while ((System.currentTimeMillis() < endTime) && (frame != null)) {
					// 录制
					recorder.record(frame);
					// 获取下一帧
					frame = grabber.grabFrame();
				}
				recorder.record(frame);
			}
			System.out.println("录制完成。");
		} catch (IOException e) {
			System.out.println("录制出错。");
			e.printStackTrace();
		} finally {
			// 停止录制
			try {
				grabber.stop();
			} catch (FrameGrabber.Exception e) {
				e.printStackTrace();
			}
			if (recorder != null) {
				try {
					recorder.stop();
				} catch (FrameRecorder.Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param data
	 * @param length
	 */
	public void push(byte[] data, int length) {
		try {
			piOutputStream.write(data, 0, length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) {
//		RecordLiveVideoThread recordVideoThread = new RecordLiveVideoThread();
//		recordVideoThread.outFilePath = "C:\\test.mp4";
//		// 最好设置结束时长 如直接停止程序会造成输出文件的损坏无法正常播放
//		recordVideoThread.timesSec = 15L;
//		recordVideoThread.hasAudio = true;
//		new Thread(recordVideoThread).start();
//	}
}