/**
 *
 */
package com.micro.iotclouds.communication.video.core.entities.helper;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.bytedeco.ffmpeg.ffmpeg;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder.Exception;

/**
 * @author Joe
 *
 */
public class WriteMp4Consumer implements Runnable {

	static {
		Loader.load(ffmpeg.class);

	}
//	File f;

	String output;

	// 声明一个 管道输入对象 作为接收方
	private PipedInputStream inputStream = new PipedInputStream();

	public PipedInputStream getInputStream() {
		return inputStream;
	}

	@Override
	public void run() {
//		int len = -1;
//		byte[] buffer = new byte[1024];
//		try {
//			// read(buffer, 0, buffer.length)函数作用有两个：
//			// (1)若有buffer.length个数据可读，则返回buffer.length个数据；
//			// 否则读取当前可读的所有数据，个数小于buffer.length;
//			// (2)若没有数据可读，则让读进程等待(见read()函数)
//			while ((len = inputStream.read(buffer)) != -1) {
//				System.out.println(new String(buffer, 0, len));
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		try {
			this.recordSegment(300, null, null, 25);
		} catch ( org.bytedeco.javacv.FrameGrabber.Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param streamName
	 */
	public WriteMp4Consumer(String output) {
//		this.streamName = streamName;
//		this.stream = stream;
//		filePath = MyLiveConfig.getInstance().getSaveFlVFilePath() + "/" + streamName.getApp() + "_"
//				+ streamName.getName() + "_%Y%m%d%H%M%S.mp4";
		this.output = output;
	}

	/**
	 * 录制视频分片文件
	 *
	 * @author eguid
	 * @param input       可以是动态图片(apng,gif等等)，视频文件（mp4,flv,avi等等）,流媒体地址（http-flv,rtmp，rtsp等等）
	 * @param output      视频名称或者名称模板
	 * @param width       图像宽度
	 * @param height      图像高度
	 * @param frameRate   帧率
	 * @param segmentTime 单个分片时长（单位：秒）
	 */
	public void recordSegment(Integer segmentTime, Integer width, Integer height, Integer frameRate)
			throws Exception, org.bytedeco.javacv.FrameGrabber.Exception {
		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream, 0);
//		grabber.setFormat("flv");
//		grabber.setPixelFormat(AV_PIX_FMT_RGBA);
		// 设置采集器构造超时时间(单位微秒，1秒=1000000微秒)
		grabber.setOption("stimeout", "2000000");
		grabber.start();

		if (width == null || height == null) {
			width = grabber.getImageWidth();
			height = grabber.getImageHeight();
		}

		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(output, width, height, 0);

		recorder.setFormat("segment");

		if (segmentTime == null) {
			segmentTime = 10;// 默认10秒生成一个文件
		}

		recorder.setOption("segment_time", segmentTime.toString());

		// 生成模式：live（实时生成）,cache（边缓存边生成，只支持m3u8清单文件缓存）
		recorder.setOption("segment_list_flags", "live");
		// 强制锁定切片时长
		recorder.setOption("segment_atclocktime", "1");
		// -reset_timestamps 1 -strftime 1 /usr/local/fczn/%Y%m%d%H%M%S.mp4
		recorder.setOption("reset_timestamps", "1");
		recorder.setOption("strftime", "1");
		// -segment_format mp4
		recorder.setOption("segment_format", "mp4");

		if (frameRate == null) {
			frameRate = 25;
		}
		recorder.setFrameRate(frameRate);// 设置帧率
		// 因为我们是直播，如果需要保证最小延迟，gop最好设置成帧率相同或者帧率*2
		// 一个gop表示关键帧间隔，假设25帧/秒视频，gop是50，则每隔两秒有一个关键帧，播放器必须加载到关键帧才能够开始解码播放，也就是说这个直播流最多有2秒延迟
		recorder.setGopSize(frameRate * frameRate);// 设置gop
		recorder.setVideoQuality(1.0); // 视频质量
		recorder.setVideoBitrate(10 * 1024);// 码率,10kb/s

//		recorder.setVideoCodecName("h264");//设置视频编码
		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);// 这种方式也可以
//		recorder.setAudioCodecName("aac");//设置音频编码，这种方式设置音频编码也可以
		recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);// 设置音频编码

		recorder.start();

//		CanvasFrame canvas = new CanvasFrame("视频预览");// 新建一个窗口
//		canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame frame = null;

		// 只抓取图像画面
		for (; (frame = grabber.grabImage()) != null;) {
			try {
				// 显示画面
//				canvas.showImage(frame);
				// 录制/推流
				recorder.record(frame);

			} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
				e.printStackTrace();
			}
		}

		recorder.close();// close包含stop和release方法。录制文件必须保证最后执行stop()方法，才能保证文件头写入完整，否则文件损坏。
		grabber.close();// close包含stop和release方法
//		canvas.dispose();
	}
}
