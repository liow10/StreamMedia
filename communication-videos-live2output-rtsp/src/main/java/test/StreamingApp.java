//package test;
//
//import org.bytedeco.ffmpeg.global.avcodec;
//import org.bytedeco.javacpp.Loader;
//import org.bytedeco.javacv.CanvasFrame;
//import org.bytedeco.javacv.FFmpegFrameRecorder;
//import org.bytedeco.javacv.FrameGrabber;
//import org.bytedeco.javacv.OpenCVFrameGrabber;
//import org.bytedeco.opencv.global.opencv_objdetect;
//import org.bytedeco.opencv.opencv_core.CvMemStorage;
//import org.bytedeco.opencv.opencv_core.IplImage;
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
//		IplImage grabbedImage = grabber.g.grab();
//		int width = grabbedImage.width();
//		int height = grabbedImage.height();
//
//		CvMemStorage storage = CvMemStorage.create();
//
//		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("rtsp://ipaddress:portno/testVideoStream.3gp", width,
//				height);
//
//		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//		recorder.setFormat("rtsp");
//		recorder.setFrameRate(grabber.getFrameRate());
//		recorder.setVideoCodec(13);
//		recorder.setPixelFormat(avcodec.AV_PIX_FMT_YUV420P);
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
