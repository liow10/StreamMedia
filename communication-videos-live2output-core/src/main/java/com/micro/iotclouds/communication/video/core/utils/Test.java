package com.micro.iotclouds.communication.video.core.utils;
//package com.micro.iotclouds.modules.rtmp.client.utils;
//
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.FFmpegFrameRecorder;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.javacv.OpenCVFrameConverter;
//
//public class Test {
//
//
//static boolean exit  = false;
//	public static void main(String[] args) throws Exception {
//		System.out.println("start...");
//		String rtmpPath = "rtmp://127.0.0.1:1935/live/YFZX2";
//		String rtspPath = "rtsp://admin:a1234567@192.168.1.247:554/h264/ch1/main/av_stream";
//		
//		int audioRecord =0; // 0 = 不录制，1=录制
//		boolean saveVideo = false;
//		push(rtmpPath,rtspPath,audioRecord,saveVideo);
//		
//		System.out.println("end...");
//	}
// 
//	public static void push(String rtmpPath,String rtspPath,int audioRecord,boolean saveVideo ) throws Exception  {
//		// 使用rtsp的时候需要使用 FFmpegFrameGrabber，不能再用 FrameGrabber
//		int width = 640,height = 480;
//		FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault(rtspPath); 
//		grabber.setOption("rtsp_transport", "tcp"); // 使用tcp的方式，不然会丢包很严重
//		
//		grabber.setImageWidth(width);
//		grabber.setImageHeight(height);
//		System.out.println("grabber start");
//		grabber.start();
//		// 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
//		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(rtmpPath,width,height, audioRecord);
//		recorder.setInterleaved(true);
//		//recorder.setVideoOption("crf","28");
//		recorder.setVideoCodec(org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_H264); // 28
//		recorder.setFormat("flv"); // rtmp的类型
//		recorder.setFrameRate(25);
//		recorder.setImageWidth(width);recorder.setImageHeight(height);
//	 	recorder.setPixelFormat(0); // yuv420p
//	 	System.out.println("recorder start");
//		recorder.start();
//		//
//		OpenCVFrameConverter.ToIplImage conveter = new OpenCVFrameConverter.ToIplImage();
//		System.out.println("all start!!");
//		int count = 0;
//		while(!exit){
//			count++;
//			Frame frame = grabber.grabImage();
//			if(frame == null){
//				continue;
//			}
//			if(count % 100 == 0){
//				System.out.println("count="+count);
//			}
//			recorder.record(frame); 
//		}
//		
//		grabber.stop();
//		grabber.release();
//		recorder.stop();
//		recorder.release();
//	}
//
//}
