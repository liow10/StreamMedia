package com.micro.iotclouds.communication.video.mp4.utils;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameRecorder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StoreMp4MediaUtils {

	private  double frameRate = 5;
	
	//视频类 FFmpegFrameRecorder
	private FFmpegFrameRecorder recorder;
	
	public FFmpegFrameRecorder getRecorder(String url) {
//		FFmpegFrameRecorder recorder = null;
		try {
//		 recorder.setVideoCodec(avcodec.AV_CODEC_ID_H265); // 28
			recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4); // 13
			recorder.setFormat("mp4");
			// recorder.setFormat("mov,mp4,m4a,3gp,3g2,mj2,h264,ogg,MPEG4");
			recorder.setSampleRate(44100);
			recorder.setFrameRate(frameRate);
			recorder.setVideoQuality(0);
			recorder.setVideoOption("crf", "23");
			// 2000 kb/s, 720P视频的合理比特率范围
			recorder.setVideoBitrate(1000000);
		} catch (Exception e) {
			log.error("创建recorder 异常" + e + e.getMessage());
			e.printStackTrace();
		}
//        try {
//            recorder = new FFmpegFrameRecorder(url, 1);
//            log.info("推流：" + url);
//            recorder.setInterleaved(true);
//            recorder.setVideoOption("tune", "zerolatency");
//            recorder.setVideoOption("preset", "ultrafast");
//            recorder.setVideoOption("crf", "28");
//            recorder.setVideoBitrate(2000000);
//            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//            recorder.setFrameRate(FRAME_RATE);
//            recorder.setGopSize(GOP_LENGTH_IN_FRAMES);
//
//            recorder.setFormat("flv");
//
//            recorder.setAudioOption("crf", "0");
//            recorder.setAudioQuality(0);
//            recorder.setAudioBitrate(921600);
//            recorder.setSampleRate((int) 16000f);
//            recorder.setAudioChannels(1);
//            recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
//            recorder.start();
//        } catch (FrameRecorder.Exception e) {
//            log.error("创建recorder 异常" + e + e.getMessage());
//            e.printStackTrace();
//        }
		return recorder;
	}
}
