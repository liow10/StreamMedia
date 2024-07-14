package com.micro.iotclouds.communication.video.mp4.store;

import static org.bytedeco.ffmpeg.global.avcodec.*;
import static org.bytedeco.ffmpeg.global.avformat.*;
import static org.bytedeco.ffmpeg.global.avutil.*;
import static org.bytedeco.ffmpeg.global.swscale.*;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.apache.commons.io.input.BufferedFileChannelInputStream;
import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.avcodec.AVCodecParserContext;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.ffmpeg.swscale.SwsContext;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.indexer.UByteIndexer;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;

public class StoreMp4Handler {
	private static final int WIDTH = 640;
	private static final int HEIGHT = 360;
	private static final int FRAME_COUNT = 100;

	/**
	 * 按帧录制视频
	 * 
	 * @param inputFile-该地址可以是网络直播/录播地址，也可以是远程/本地文件路径
	 * @param outputFile                              -该地址只能是文件地址，如果使用该方法推送流媒体服务器会报错，原因是没有设置编码格式
	 * @throws FrameGrabber.Exception
	 * @throws FrameRecorder.Exception
	 * @throws org.bytedeco.javacv.FrameRecorder.Exception
	 */
	public static void frameRecord(BufferedFileChannelInputStream inputFile, String outputFile, int audioChannel)
			throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
		boolean isStart = true;// 该变量建议设置为全局控制变量，用于控制录制结束
		// 获取视频源
		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
		grabber.setOption("rtsp_transport", "tcp");
		// 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, 1280, 720, audioChannel);
		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
		recorder.setFormat("flv");
		// 开始取视频源
		recordByFrame(grabber, recorder, isStart);
	}

	private static void recordByFrame(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder, Boolean status)
			throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
		try {// 建议在线程中使用该方法
			grabber.start();
			recorder.start();
			Frame frame = null;
			while (status && (frame = grabber.grabFrame()) != null) {
				recorder.record(frame);
			}
			recorder.stop();
			grabber.stop();
		} finally {
			if (grabber != null) {
				grabber.stop();
			}
		}
	}

	/**
	 * 按帧录制视频
	 * 
	 * @param inputFile-该地址可以是网络直播/录播地址，也可以是远程/本地文件路径
	 * @param outputFile                              -该地址只能是文件地址，如果使用该方法推送流媒体服务器会报错，原因是没有设置编码格式
	 * @throws FrameGrabber.Exception
	 * @throws FrameRecorder.Exception
	 * @throws org.bytedeco.javacv.FrameRecorder.Exception
	 */
	public void frameRecord(byte[] dst, String outputFile, int audioChannel)
			throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
		boolean isStart = true;// 该变量建议设置为全局控制变量，用于控制录制结束
		// 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, 1280, 720, audioChannel);
		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
		recorder.setFormat("flv");
		// 开始取视频源
		recordByFrame(dst, recorder, isStart);
	}

	private void recordByFrame(byte[] dst, FFmpegFrameRecorder recorder, Boolean status)
			throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
		try {// 建议在线程中使用该方法
			recorder.start(new AVFormatContext());
			dec_loop(dst, recorder);
//			Frame frame = null;
//			Frame objAVFrame = new Frame();
//			AVPacket pkt = new AVPacket(dst.length);
			// ByteBuffer buffer = ByteBuffer.wrap(dst, 0, dst.length);
//			BytePointer video_data = new BytePointer(buffer);
//			AVBufferRef objAVBufferRef = new AVBufferRef();
			/// objAVBufferRef.data(objBytePointer);
			// pkt.buf(objAVBufferRef);
//			recorder.recordPacket(pkt);
//			AVPacket pkt = new AVPacket(dst.length);
//			AVCodecContext video_c = new AVCodecContext();
//			avcodec.av_packet_alloc();
//			avcodec.av_init_packet(pkt);
//			// avcodec.av_read_frame
//			BytePointer video_data = new BytePointer(dst);
////			AVCodec codec = new AVCodec();//avcodec.avcodec_find_decoder(avcodec.AV_CODEC_ID_MPEG4);
////			video_c = null;
////			video_c = avcodec.avcodec_alloc_context3(codec);
//			video_c.width(320);
//			video_c.height(240);
//			video_c.pix_fmt(0);
////			video_c.flags2(video_c.flags2() | avcodec.AV_CODEC_FLAG2_CHUNKS);
//			pkt.size(dst.length);
//			pkt.data(video_data);
//			recorder.recordPacket(pkt);
//			recorder.stop();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void createVideo(FFmpegFrameRecorder recorder) throws Exception {
		recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
		recorder.setFormat("mp4");
		recorder.setFrameRate(30);
		recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
		recorder.start();
		for (int n = 0; n < FRAME_COUNT; n++) {
			Frame frame = new Frame(WIDTH, HEIGHT, Frame.DEPTH_UBYTE, 3);
			UByteIndexer frameIdx = frame.createIndexer();
			for (int i = 0; i < frameIdx.rows(); i++) {
				for (int j = 0; j < frameIdx.cols(); j++) {
					for (int k = 0; k < frameIdx.channels(); k++) {
						frameIdx.put(i, j, k, n + i + j + k);
					}
				}
			}
			recorder.record(frame);
			((Closeable) frame).close();
		}
		recorder.close();
	}

	public Frame decodeVideo(byte[] data, long timestamp) {
//	      frame.image = null; 
//	      frame.samples = null; 
		AVPacket pkt = null;
		AVCodecContext video_c;
		avcodec.av_init_packet(pkt);
		BytePointer video_data = new BytePointer(data);
		AVCodec codec = avcodec.avcodec_find_decoder(avcodec.AV_CODEC_ID_MPEG4);
		video_c = null;
		video_c = avcodec.avcodec_alloc_context3(codec);
		video_c.width(320);
		video_c.height(240);
		video_c.pix_fmt(0);
		video_c.flags2(video_c.flags2() | avcodec.AV_CODEC_FLAG2_CHUNKS);
//	      avcodec.avcodec_open2(video_c, codec, null);
//	      picture = avcodec.avcodec_alloc_frame() 
		pkt.data(video_data);
		pkt.size(data.length);
//	      int len = avcodec.avcodec_decode_video2(video_c, picture, got_frame, pkt); 
//	      if ((len >= 0) && (got_frame[0] != 0)) { 
//	      .... 
//	       process the decoded frame into **IPLImage of Javacv** and render it with **Imageview** of Android 
//	      } 
		return null;
	}

	public int codec_id;
	public AVCodecContext m_pCodecCtx = null; // URL中视频解码部分内容
	public AVFrame m_pFrame = null; // 全局使用帧对象
	public AVFrame m_pFrameRGB = null;
	public AVCodec m_pCodec = null;
	public AVCodecParserContext pCodecParserCtx = null;
	public AVPacket packet = null;
	public SwsContext m_pImageConvertCtx = null; // 构造全局对象，供sws_scale切割图片使用
	public SwsContext img_convert_ctx = null;
	public Integer count = 0;
	public int count_size;
	public BytePointer cur_ptr;
	public FileOutputStream os;
	private ByteArrayOutputStream myByteArrayOutputStream = new ByteArrayOutputStream();

	public void dec_loop(byte[] H264, FFmpegFrameRecorder recorder) {

		ByteBuffer data = ByteBuffer.allocate(H264.length);
		data.put(H264);
		int cur_size = H264.length;
		IntPointer pktSize = new IntPointer(cur_size);
		BytePointer temp_bp = new BytePointer();
		while (cur_size > 0) {

			data.flip();
			int slen = av_parser_parse2(pCodecParserCtx, m_pCodecCtx, temp_bp, pktSize, new BytePointer(data), cur_size,
					AV_NOPTS_VALUE, AV_NOPTS_VALUE, AV_NOPTS_VALUE);
			packet = packet.size(pktSize.get());

			data.position(slen);
			data = data.compact();
			cur_size -= slen;

			if (pktSize.get() == 0) {
				continue;
			}
			packet = packet.data(temp_bp);
			// for(int i = 0;i <5;i++){
			// byte b = packet.data().get(i);
			// System.out.print(byteToHex(b)+" ");
			// }
			// System.out.println("------------------------------!!!!------------------"+packet.size());
			// packet.data().asBuffer();

			int asp = avcodec_send_packet(m_pCodecCtx, packet);

			if (avcodec_receive_frame(m_pCodecCtx, m_pFrame) == 0) {
				// m_pFrame.data(0);
				// y = m_pFrame->data[0];1920*1088
				// u = m_pFrame->data[1];1920*1088/4
				// v = m_pFrame->data[2];1920*1088/4

				System.err.println(
						"->>> decode success   " + "width :" + m_pFrame.width() + " " + "height :" + m_pFrame.height());
				try {
					recorder.recordPacket(packet);
				} catch (org.bytedeco.javacv.FrameRecorder.Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				sws_scale(img_convert_ctx, m_pFrame.data(), m_pFrame.linesize(), 0, m_pCodecCtx.height(),
//						m_pFrameRGB.data(), m_pFrameRGB.linesize());
//				BytePointer by_bgra_data = m_pFrameRGB.data(0);
				try {
					// String imgName = "C:/Users/user/Desktop/test/test" + count + ".h264";
					// saveImg(m_pFrame, imgName);
					// count++;
					// for (int i = 0; i < 1920 * 1088 * 4; i++) {
					// myByteArrayOutputStream.write(by_bgra_data.get(i));
					// }
					// if (myByteArrayOutputStream.size() == 1920 * 1088 * 4) {
					// File file = new
					// File("C://Users//user//Desktop//test//success.yuv");
					// if (!file.exists()) {
					// file.createNewFile();
					// }
					// FileOutputStream fe = new FileOutputStream(file, true);
					// fe.write(myByteArrayOutputStream.toByteArray());
					// fe.flush();
					// fe.close();
					// myByteArrayOutputStream.reset();
					// }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// av_packet_unref(packet);
		}
		// av_packet_free(packet);
	}

	/**
	 * 视频转码
	 */
	public static void recode() throws FrameGrabber.Exception, FrameRecorder.Exception {
		String filePath = "ef568e9b96be750fba2e918b651aa70c.avi";
		String ext = filePath.substring(filePath.lastIndexOf("."));
		String newFilePath = filePath.replace(ext, "_recode.mp4");
		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(filePath);
		grabber.start();
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(newFilePath, grabber.getImageWidth(),
				grabber.getImageHeight(), grabber.getAudioChannels());
		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
		recorder.setFormat("mp4");
		recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
		recorder.setFrameRate(grabber.getFrameRate());
		int bitrate = grabber.getVideoBitrate();
		if (bitrate == 0) {
			bitrate = grabber.getAudioBitrate();
		}
		recorder.setVideoBitrate(bitrate);
		recorder.start(grabber.getFormatContext());
		AVPacket packet;
		long dts = 0;
		while ((packet = grabber.grabPacket()) != null) {
			long currentDts = packet.dts();
			if (currentDts >= dts) {
				recorder.recordPacket(packet);
			}
			dts = currentDts;
		}
		recorder.stop();
		recorder.release();
		grabber.stop();
	}
}
