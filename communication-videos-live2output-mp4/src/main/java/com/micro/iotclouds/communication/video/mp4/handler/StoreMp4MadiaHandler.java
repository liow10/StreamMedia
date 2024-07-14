/**
 * 
 */
package com.micro.iotclouds.communication.video.mp4.handler;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import com.micro.iotclouds.communication.video.core.entities.StreamName;
import com.micro.iotclouds.communication.video.core.messages.RtmpMediaMessage;
import com.micro.iotclouds.communication.video.mp4.utils.ConvertVideoPakcet;
import com.micro.iotclouds.communication.video.mp4.utils.RecordLiveVideoThread;

/**
 * 转存MP4
 * 
 * @author Joe
 *
 */
//@Slf4j
public class StoreMp4MadiaHandler {

	private static ConvertVideoPakcet t = null;

//	private StreamManager streamManager;

	StreamName streamName;

	RtmpMediaMessage msg;
//
//	Stream2 stream;

	// 视频类 FFmpegFrameRecorder
//	private FFmpegFrameRecorder recorder;

	private static StoreMp4MadiaHandler storeMp4MadiaHandler;

	/**
	 * 实例静态化
	 * 
	 * @return
	 */
	public static StoreMp4MadiaHandler getInstance() {
		if (storeMp4MadiaHandler == null) {
			storeMp4MadiaHandler = new StoreMp4MadiaHandler();
		}
		return storeMp4MadiaHandler;
	}
//	public StoreMp4MadiaHandler(Stream2 s) {
////		super();
////		this.streamName = streamName;
//		this.stream = s;
//	}

//	public StoreMp4MadiaHandler(StreamName streamName, RtmpMediaMessage msg) {
//		this.streamName = streamName;
//		this.msg = msg;
//	}

//	@Override
//	public void run() {
////			try {
////				List<RtmpMediaMessage> lstRtmpMediaMessage = stream.getContent();
////				for (RtmpMediaMessage r : lstRtmpMediaMessage) {
////					System.out.println(HexUtil.byteToHex(r.raw()));
////					// onMediaStream(r.raw(), 0, r.raw().length, false);
////				}
////			} catch (Exception e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//		
//		System.out.println(HexUtil.byteToHex(msg.raw()));
//		onMediaStream(msg.raw(), 0, msg.raw().length, false);
//	}

	public void onMediaStream(byte[] data, int offset, int length, boolean isAudio) {
//		if (t == null) {
//			// 启动javacv解析处理器线程
//			try {
//				t = new ConvertVideoPakcet();
////				t.run();
//				new Thread(t).start();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//		if (t != null) {
//			// 写出视频码流到javacv多线程解析处理器
//			t.push(data, length);
//		}
		mainRecord(data, 0, length);
	}

	RecordLiveVideoThread recordVideoThread;
	PipedInputStream pin = new PipedInputStream();
	PipedOutputStream pout = new PipedOutputStream();

	/**
	 * @param data
	 * @param offset
	 * @param length
	 */
	public void mainRecord(byte[] data, int offset, int length) {
		if (recordVideoThread == null) {
			recordVideoThread = new RecordLiveVideoThread(pin, pout);
			recordVideoThread.outFilePath = "C:\\test.mp4";
			// 最好设置结束时长 如直接停止程序会造成输出文件的损坏无法正常播放
			recordVideoThread.timesSec = 15L;
			recordVideoThread.hasAudio = true;
			new Thread(recordVideoThread).start();
		}
		if (recordVideoThread != null) {
			// 写出视频码流到javacv多线程解析处理器
			recordVideoThread.push(data, length);
		}

	}
}
