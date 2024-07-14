package com.micro.iotclouds.communication.video.mp4.utils;

import static org.bytedeco.ffmpeg.global.avcodec.av_free_packet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;

/**
 * rtsp转rtmp（转封装方式）
 * 
 * @author eguid
 */
public class ConvertVideoPakcet implements Runnable {

	// grabber输入管道，用于提供视频流字节数据给FrameGrabber
//	private PipedInputStream pis;
//
//	// sdk回调输出管道，用于从sdk回调函数读取视频字节流
//	private PipedOutputStream pos = new PipedOutputStream();

	/**
	 * 
	 */
	PipedInputStream pin;

	/**
	 * 
	 */
	PipedOutputStream pout;

	FFmpegFrameGrabber grabber = null;
	FFmpegFrameRecorder record = null;
	int width = -1, height = -1;

	// 视频参数
	protected int audiocodecid;
	protected int codecid;
	protected double framerate;// 帧率
	protected int bitrate;// 比特率

	// 音频参数
	// 想要录制音频，这三个参数必须有：audioChannels > 0 && audioBitrate > 0 && sampleRate > 0
	private int audioChannels;
	private int audioBitrate;
	private int sampleRate;
	
	InputStream inputStream;

	/**
	 * 创建用于把字节数组转换为inputstream流的管道流
	 * 
	 * @throws IOException
	 */
	public ConvertVideoPakcet() throws IOException {
		pout = new PipedOutputStream();
	}

	/**
	 * 异步接收回调实时视频裸流数据
	 * 
	 * @param data
	 * @param size
	 */
	public void push(byte[] data, int size) {
		try {
			pout.write(data, 0, size);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * InputStream
	 * 
	 * @param buf
	 * @return
	 */
	public static final InputStream byte2Input(byte[] buf) {
		return new ByteArrayInputStream(buf);
	}

	/**
	 * 选择视频源
	 * 
	 * @param src
	 * @author eguid
	 * @throws Exception
	 */
	public ConvertVideoPakcet from() throws Exception {
		// 采集/抓取器
//		try {
//			pin.connect(pout);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			pin = new PipedInputStream(pout, 1024);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		grabber = new FFmpegFrameGrabber(pin, 0);
//		if (src.indexOf("rtsp") >= 0) {
//			grabber.setOption("rtsp_transport", "tcp");
//		}
		grabber.start();// 开始之后ffmpeg会采集视频信息，之后就可以获取音视频信息
		if (width < 0 || height < 0) {
			width = grabber.getImageWidth();
			height = grabber.getImageHeight();
		}
		// 视频参数
		audiocodecid = grabber.getAudioCodec();
		System.err.println("音频编码：" + audiocodecid);
		codecid = grabber.getVideoCodec();
		framerate = grabber.getVideoFrameRate();// 帧率
		bitrate = grabber.getVideoBitrate();// 比特率
		// 音频参数
		// 想要录制音频，这三个参数必须有：audioChannels > 0 && audioBitrate > 0 && sampleRate > 0
		audioChannels = grabber.getAudioChannels();
		audioBitrate = grabber.getAudioBitrate();
		if (audioBitrate < 1) {
			audioBitrate = 128 * 1000;// 默认音频比特率
		}
		return this;
	}

	/**
	 * 选择输出
	 * 
	 * @param out
	 * @author eguid
	 * @throws IOException
	 */
	public ConvertVideoPakcet to(String out) throws IOException {
		// 录制/推流器
		record = new FFmpegFrameRecorder(out, width, height);
		record.setVideoOption("crf", "18");
		record.setGopSize(2);
		record.setFrameRate(framerate);
		record.setVideoBitrate(bitrate);

		record.setAudioChannels(audioChannels);
		record.setAudioBitrate(audioBitrate);
		record.setSampleRate(sampleRate);
		AVFormatContext fc = null;
//		if (out.indexOf("rtmp") >= 0 || out.indexOf("flv") > 0) {
//			// 封装格式flv
//			record.setFormat("flv");
//			record.setAudioCodecName("aac");
//			record.setVideoCodec(codecid);
//			fc = grabber.getFormatContext();
//		}
		record.start(fc);
		return this;
	}

	/**
	 * 转封装
	 * 
	 * @author eguid
	 * @throws IOException
	 */
	public ConvertVideoPakcet go() throws IOException {
		long err_index = 0;// 采集或推流导致的错误次数
		// 连续五次没有采集到帧则认为视频采集结束，程序错误次数超过1次即中断程序
		for (int no_frame_index = 0; no_frame_index < 5 || err_index > 1;) {
			AVPacket pkt = null;
			try {
				// 没有解码的音视频帧
				pkt = grabber.grabPacket();
				if (pkt == null || pkt.size() <= 0 || pkt.data() == null) {
					// 空包记录次数跳过
					no_frame_index++;
					continue;
				}
				// 不需要编码直接把音视频帧推出去
				err_index += (record.recordPacket(pkt) ? 0 : 1);// 如果失败err_index自增1
				av_free_packet(pkt);
			} catch (Exception e) {// 推流失败
				err_index++;
			} catch (IOException e) {
				err_index++;
			}
		}
		return this;
	}

//	public static void main(String[] args) throws Exception, IOException {
//
////运行，设置视频源和推流地址
//		new ConvertVideoPakcet().from("rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov")
//				.to("rtmp://eguid.cc:1935/rtmp/eguid").go();
//	}

	@Override
	public void run() {
		try {
			from().to("D:\\resources\\iot\\rtmp\\mp4\\2323.mp4").go();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	public void onVideo(Frame frame) {
//		// 分段录制
//		if (timeLimit > 0 && System.currentTimeMillis() - startTime > timeLimit) {
//			try {
//				RecordINF inf = stop();
//				if (timeoutCB != null) {
//					timeoutCB.accept(inf);
//				}
//				startTime = System.currentTimeMillis();
//				timestamp = 0;
//				videoFile = new File(videoDir, startTime + ".mp4");
//				start();
//			} catch (Exception e) {
//				LOG.error(e.getMessage(), e);
//			}
//		}
//
//		try {
//			if (timestamp == 0) {
//				timestamp = System.currentTimeMillis();
//			}
//			videoTS = 1000 * (System.currentTimeMillis() - timestamp);
//			if (videoTS > recorder.getTimestamp()) {
//				recorder.setTimestamp(videoTS);
//			}
//			recorder.record(frame);
//		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
//			LOG.error(e.getMessage(), e);
//		}
//	}
}