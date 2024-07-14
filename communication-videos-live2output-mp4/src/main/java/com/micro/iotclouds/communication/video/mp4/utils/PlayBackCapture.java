package com.micro.iotclouds.communication.video.mp4.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: PlayBackCapture
 * @Description:抓图
 * @author: banmajio
 * @date: 2020-11-13
 */
public class PlayBackCapture {

	private final static Logger logger = LoggerFactory.getLogger(PlayBackCapture.class);

	private PipedInputStream picInputStream;// 抓图输入流
	private PipedOutputStream picOutputStream;// 抓图输出流
	private FFmpegFrameGrabber grabber;// 抓流器
	private ArrayList<String> picturePaths = new ArrayList<>();

	public PlayBackCapture(PipedInputStream picInputStream, PipedOutputStream picOutputStream) {
		this.picInputStream = picInputStream;
		this.picOutputStream = picOutputStream;
	}

	public void setPicturePath(String picturepath) {
		picturePaths.add(picturepath);
	}

	public void playBackCapture(String token) throws IOException, InterruptedException {
		try {
			picInputStream.connect(picOutputStream);
			grabber = new FFmpegFrameGrabber(picInputStream, 0);
			//检测管道流中是否存在数据，如果2s后依然没有写入1024的数据，则认为管道流中无数据，避免grabber.start();发生阻塞
			long stime = new Date().getTime();
			while (true) {
				Thread.sleep(100);
				if (new Date().getTime() - stime > 2000) {
					return;
				}
				if (picInputStream.available() == 1024) {
					break;
				}
			}
			grabber.start();
			String rotate = grabber.getVideoMetadata("rotate");// 视频的旋转角度
			Frame frame = null;
			int frameIndex = 0;
			int pictureIndex = 0;
			while (frameIndex < 10) {
			    // 获取image帧
				frame = grabber.grabImage();
				if (null != frame && null != frame.image) {
					IplImage src = null;
					if (null != rotate && rotate.length() > 1) {
						OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
						src = converter.convert(frame);
						frame = converter.convert(rotate(src, Integer.valueOf(rotate)));
					}
					doExecuteFrame(frame, picturePaths.get(pictureIndex), frameIndex);
					logger.info("hcsdk " + " 抓图完成 保存路径为：" + picturePaths.get(pictureIndex));
					pictureIndex++;
					if (pictureIndex < picturePaths.size()) {
						continue;
					} else {
						break;
					}
				}
				frameIndex++;
			}
		} catch (Exception e) {
			logger.info("hcsdk " + " 抓图失败");
			grabber.stop();
			grabber.close();
			picInputStream.close();
			picOutputStream.close();
			e.printStackTrace();
		}
		grabber.stop();
		grabber.close();
		picInputStream.close();
		picOutputStream.close();
	}

	private void doExecuteFrame(Frame frame, String picturepath, int frameIndex) throws IOException {
		if (null == frame || null == frame.image) {
			return;
		}
		Java2DFrameConverter converter = new Java2DFrameConverter();
		BufferedImage bi = converter.getBufferedImage(frame);

		File output = new File(picturepath);
		try {
			ImageIO.write(bi, "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private IplImage rotate(IplImage src, Integer angle) {
		IplImage img = IplImage.create(src.height(), src.width(), src.depth(), src.nChannels());
		opencv_core.cvTranspose(src, img);
		opencv_core.cvFlip(img, img, angle);
		return img;
	}
}