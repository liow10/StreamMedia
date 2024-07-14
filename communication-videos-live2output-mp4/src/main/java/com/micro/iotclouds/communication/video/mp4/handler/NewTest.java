package com.micro.iotclouds.communication.video.mp4.handler;

import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_H264;
import static org.bytedeco.ffmpeg.global.avcodec.av_free_packet;
import static org.bytedeco.ffmpeg.global.avcodec.av_new_packet;
import static org.bytedeco.ffmpeg.global.avcodec.av_packet_alloc;
import static org.bytedeco.ffmpeg.global.avcodec.av_parser_init;
import static org.bytedeco.ffmpeg.global.avcodec.av_parser_parse2;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_alloc_context3;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_close;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_encode_video2;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_find_decoder;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_find_encoder;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_open2;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_receive_frame;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_register_all;
import static org.bytedeco.ffmpeg.global.avcodec.avcodec_send_packet;
import static org.bytedeco.ffmpeg.global.avformat.AVIO_FLAG_READ_WRITE;
import static org.bytedeco.ffmpeg.global.avformat.av_dump_format;
import static org.bytedeco.ffmpeg.global.avformat.av_guess_format;
import static org.bytedeco.ffmpeg.global.avformat.av_register_all;
import static org.bytedeco.ffmpeg.global.avformat.av_write_frame;
import static org.bytedeco.ffmpeg.global.avformat.av_write_trailer;
import static org.bytedeco.ffmpeg.global.avformat.avformat_alloc_context;
import static org.bytedeco.ffmpeg.global.avformat.avformat_free_context;
import static org.bytedeco.ffmpeg.global.avformat.avformat_new_stream;
import static org.bytedeco.ffmpeg.global.avformat.avformat_write_header;
import static org.bytedeco.ffmpeg.global.avformat.avio_close;
import static org.bytedeco.ffmpeg.global.avformat.avio_open;
import static org.bytedeco.ffmpeg.global.avutil.AVMEDIA_TYPE_VIDEO;
import static org.bytedeco.ffmpeg.global.avutil.AV_NOPTS_VALUE;
import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_BGRA;
import static org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_YUV420P;
import static org.bytedeco.ffmpeg.global.avutil.av_dict_free;
import static org.bytedeco.ffmpeg.global.avutil.av_dict_set;
import static org.bytedeco.ffmpeg.global.avutil.av_frame_alloc;
import static org.bytedeco.ffmpeg.global.avutil.av_image_fill_arrays;
import static org.bytedeco.ffmpeg.global.avutil.av_image_get_buffer_size;
import static org.bytedeco.ffmpeg.global.avutil.av_malloc;
import static org.bytedeco.ffmpeg.global.swscale.SWS_FAST_BILINEAR;
import static org.bytedeco.ffmpeg.global.swscale.sws_getContext;
import static org.bytedeco.ffmpeg.global.swscale.sws_scale;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.avcodec.AVCodecParserContext;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVIOContext;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.avutil.AVDictionary;
import org.bytedeco.ffmpeg.avutil.AVFrame;
import org.bytedeco.ffmpeg.swscale.SwsContext;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.PointerPointer;

public class NewTest {
	// Load only once formats and codecs
	static {
		av_register_all();
//        avformat_network_init();
		avcodec_register_all();
	}
	public int codec_id;
	public AVCodecContext m_pCodecCtx = null; // URL中视频解码部分内容
	public AVFrame m_pFrame = null; // 全局使用帧对象
	public AVFrame m_pFrameRGB = null;
	public AVCodec m_pCodec = null;
	public AVCodecParserContext pCodecParserCtx = null;
	public AVPacket packet = null;
	public SwsContext img_convert_ctx = null;
	public SwsContext m_pImageConvertCtx = null; // 构造全局对象，供sws_scale切割图片使用
	public Integer count = 0;
	public int count_size;
	public BytePointer cur_ptr;
	public FileOutputStream os;
	private ByteArrayOutputStream myByteArrayOutputStream = new ByteArrayOutputStream();

	public NewTest() {
		System.out.println("init begin");

		m_pFrame = av_frame_alloc();
		codec_id = AV_CODEC_ID_H264;
		m_pFrameRGB = av_frame_alloc();
		m_pCodec = avcodec_find_decoder(codec_id);

		if (m_pCodec == null) {
			System.out.println("Codec not found\n");
		}
		m_pCodecCtx = avcodec_alloc_context3(m_pCodec);
		if (m_pCodecCtx == null) {
			System.out.println("Could not allocate video codec context\n");
		}

		pCodecParserCtx = av_parser_init(codec_id);
		if (pCodecParserCtx == null) {
			System.out.println("Could not allocate video parser context\n");
		}

		if (avcodec_open2(m_pCodecCtx, m_pCodec, (PointerPointer<Pointer>) null) < 0) {
			System.out.println("Could not open codec\n");
		}
		img_convert_ctx = sws_getContext(1920, 1088, AV_PIX_FMT_YUV420P, 1920, 1088, AV_PIX_FMT_BGRA, SWS_FAST_BILINEAR,
				null, null, (DoublePointer) null);

		packet = new AVPacket();

		packet = av_packet_alloc();

		av_new_packet(packet, 30000);

		int numBytes = av_image_get_buffer_size(AV_PIX_FMT_BGRA, 1920, 1088, 1);

		BytePointer rgbData = new BytePointer(av_malloc(numBytes));

		av_image_fill_arrays(m_pFrameRGB.data(), m_pFrameRGB.linesize(), rgbData, AV_PIX_FMT_BGRA, 1920, 1088, 1);

		System.out.println("init end");
	}

	public void dec_loop(byte[] H264) {

		ByteBuffer data = ByteBuffer.allocate(H264.length);
		data.put(H264);
		int cur_size = H264.length;
		IntPointer pktSize = new IntPointer(packet.size());
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

				sws_scale(img_convert_ctx, m_pFrame.data(), m_pFrame.linesize(), 0, m_pCodecCtx.height(),
						m_pFrameRGB.data(), m_pFrameRGB.linesize());
				BytePointer by_bgra_data = m_pFrameRGB.data(0);
				try {
//                    String imgName = "C:/Users/user/Desktop/test/test" + count + ".h264";
//                    saveImg(m_pFrame, imgName);
//                    count++;
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

	public int saveImg(AVFrame pFrame, String out_file) throws IOException {
		AVCodec codec = null;
		AVPacket pkt = null;
		AVStream pAVStream = null;
		int ret = -1;
		AVDictionary avd = new AVDictionary(null);
		int width = pFrame.width(), height = pFrame.height();
		// 分配AVFormatContext对象
		AVFormatContext pFormatCtx = avformat_alloc_context();
		// 设置输出文件格式
		pFormatCtx.oformat(av_guess_format("h264", null, null));
		if (pFormatCtx.oformat() == null) {
			return -1;
		}
		try {
			// 创建并初始化一个和该url相关的AVIOContext
			AVIOContext pb = new AVIOContext();
			if (avio_open(pb, out_file, AVIO_FLAG_READ_WRITE) < 0) {// dont open
																	// file
				return -1;
			}
			pFormatCtx.pb(pb);
			// 构建一个新stream
			pAVStream = avformat_new_stream(pFormatCtx, codec);
			if (pAVStream == null) {
				return -1;
			}
			int codec_id = pFormatCtx.oformat().video_codec();
			// 设置该stream的信息
			AVCodecContext pCodecCtx = pAVStream.codec();
			pCodecCtx.codec_id(codec_id);
			pCodecCtx.codec_type(AVMEDIA_TYPE_VIDEO);
			pCodecCtx.pix_fmt(AV_PIX_FMT_YUV420P);
			pCodecCtx.width(width);
			pCodecCtx.height(height);
			pCodecCtx.time_base().num(1);
			pCodecCtx.time_base().den(25);
			pCodecCtx.qmin(10);
			pCodecCtx.qmax(51);
			pCodecCtx.bit_rate(400000);
			pCodecCtx.gop_size(12);
			pCodecCtx.qcompress(0.6f);

			if (pCodecCtx.codec_id() == AV_CODEC_ID_H264) {
				av_dict_set(avd, "preset", "slow", 0);
				av_dict_set(avd, "tune", "zerolatency", 0);
			}

			// Begin Output some information
			av_dump_format(pFormatCtx, 0, out_file, 1);
			// End Output some information

			// 查找编码器
			AVCodec pCodec = avcodec_find_encoder(pCodecCtx.codec_id());
			if (pCodec == null) {// codec not found
				return -1;
			}
			// 设置pCodecCtx的解码器为pCodec
			if (avcodec_open2(pCodecCtx, pCodec, avd) < 0) {
				System.err.println("Could not open codec.");
				av_dict_free(avd);
				return -1;
			}

			// Write Header
			avformat_write_header(pFormatCtx, (PointerPointer<Pointer>) null);

			// 给AVPacket分配足够大的空间
			pkt = new AVPacket();
			int yuvSize = ((width * height) / 2) * 3;
			if (av_new_packet(pkt, yuvSize) < 0) {
				return -1;
			}

			int[] got_picture = { 0 };
			// encode

			if (avcodec_encode_video2(pCodecCtx, pkt, pFrame, got_picture) >= 0) {
				System.out.println("got_picture[0]:" + got_picture[0]);
				if (got_picture[0] == 1) {
					// flush
					BytePointer pkt_data = pkt.data();
					// 输出pkt数据到文件
					for (int i = 0; i < pkt.size(); i++) {
						myByteArrayOutputStream.write(pkt_data.get(i));
					}
					if (myByteArrayOutputStream.size() == pkt.size()) {
						File file = new File("C://Users//user//Desktop//test//success.h264");
						if (!file.exists()) {
							file.createNewFile();
						}
						FileOutputStream fe = new FileOutputStream(file, true);
						fe.write(myByteArrayOutputStream.toByteArray());
						fe.flush();
						fe.close();
						myByteArrayOutputStream.reset();
					}

					if ((ret = av_write_frame(pFormatCtx, pkt)) >= 0) {
						// Write Trailer
						if (av_write_trailer(pFormatCtx) >= 0) {
							System.err.println("->>> Encode Successful.   pkt.size():" + pkt.size());
						} else {
							System.err.println("Encode failed.");
						}
					}
				}
			}
			return ret;
			// 结束时销毁
		} finally {
			if (pkt != null) {
				av_free_packet(pkt);
			}
			if (pAVStream != null) {
				avcodec_close(pAVStream.codec());
			}
			if (pFormatCtx != null) {
				avio_close(pFormatCtx.pb());
				avformat_free_context(pFormatCtx);
			}
		}
	}

	public static byte[] conver(ByteBuffer byteBuffer) {
		int len = byteBuffer.limit() - byteBuffer.position();
		byte[] bytes = new byte[len];

		if (byteBuffer.isReadOnly()) {
			return null;
		} else {
			byteBuffer.get(bytes);
		}
		return bytes;
	}

	public static String byteToHex(byte b) {
		String hex = Integer.toHexString(b & 0xFF);
		if (hex.length() < 2) {
			hex = "0" + hex;
		}
		return hex;
	}

	public static void main(String[] args) {
		NewTest test = new NewTest();
	}

}
