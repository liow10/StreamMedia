//package com.mkst.iotclouds.communication.protocol.newrtsp;
//
//import static org.bytedeco.opencv.global.opencv_core.cvClearMemStorage;
//
//import org.bytedeco.ffmpeg.global.avcodec;
//import org.bytedeco.ffmpeg.global.avutil;
//import org.bytedeco.javacpp.Loader;
//import org.bytedeco.javacv.CanvasFrame;
//import org.bytedeco.javacv.FFmpegFrameRecorder;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.javacv.FrameGrabber;
//import org.bytedeco.javacv.OpenCVFrameGrabber;
//import org.bytedeco.opencv.global.opencv_objdetect;
//import org.bytedeco.opencv.opencv_core.CvMemStorage;
//
//public class StreamingApp {
//
//	public static void main(String[] args) throws Exception {
//
//		Loader.load(opencv_objdetect.class);
//		CanvasFrame frame = new CanvasFrame("webcam");
//		FrameGrabber grabber = new OpenCVFrameGrabber(0);
//
//		grabber.setImageHeight(500);
//		grabber.setImageWidth(500);
//		grabber.start();
//
//		Frame grabbedImage = grabber.grab();
//		int width = grabbedImage.imageWidth;
//		int height = grabbedImage.imageHeight;
//
//		CvMemStorage storage = CvMemStorage.create();
//
//		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("rtsp://127.0.0.1:554/testVideoStream.3gp", width,
//				height);
//
//		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//		recorder.setFormat("rtp");
//		recorder.setFrameRate(grabber.getFrameRate());
//		recorder.setVideoCodec(13);
//		recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
//		recorder.setVideoBitrate(10 * 1024 * 1024);
//		recorder.start();
//
//		int i = 0;
//
//		while (frame.isVisible() && (grabbedImage = grabber.grab()) != null) {
//			System.out.println("(" + i++ + ") Invio. . .");
//			frame.showImage(grabbedImage);
//			recorder.record(grabbedImage);
//		}
//
//		cvClearMemStorage(storage);
//		recorder.stop();
//		grabber.stop();
//	}
//
//}