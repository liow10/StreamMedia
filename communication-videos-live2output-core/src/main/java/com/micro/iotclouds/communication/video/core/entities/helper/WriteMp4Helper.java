/**
 * 
 */
package com.micro.iotclouds.communication.video.core.entities.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import com.micro.iotclouds.communication.video.core.domain.MyLiveConfig;
import com.micro.iotclouds.communication.video.core.entities.StreamName;
import com.micro.iotclouds.communication.video.core.messages.RtmpMediaMessage;

/**
 * @author Joe
 *
 */
public class WriteMp4Helper {

	/**
	 * 
	 */
	StreamName streamName;

	FileOutputStream flvout;

//	private InputStream inputStream;// 抓图输入流

//	File f;

	String filePath;

	/**
	 * @param streamName
	 */
	@SuppressWarnings("resource")
	public WriteMp4Helper(StreamName streamName) {
		this.streamName = streamName;
		filePath = MyLiveConfig.getInstance().getSaveFlVFilePath() + "/" + streamName.getApp() + "_"
				+ streamName.getName() + "_%Y%m%d%H%M%S.mp4";
	}

	/**
	 * 写入数据流
	 * 
	 * @param msg
	 */
	public void writeMp4(RtmpMediaMessage msg) {
		/// inputStream = ByteToInputStream.byte2Input(msg.raw());
//		try {
//			byte[] raw = msg.raw();
//			po.write(raw, 0, raw.length);
//			po.flush();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		// 初始化数据生产者
		WriteMp4Producer objWriteMp4Producer = new WriteMp4Producer(msg);
		// 初始化数据消费者
		WriteMp4Consumer objWriteMp4Handler = new WriteMp4Consumer(filePath);

		// 获取输出管道流
		PipedOutputStream outputStream = objWriteMp4Producer.getOutputStream();

		// 获取输入管道流
		PipedInputStream inputStream = objWriteMp4Handler.getInputStream();
		// 链接两个管道，这一步很重要，把输入流和输出流联通起来
		try {
			outputStream.connect(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(objWriteMp4Handler).start();
		new Thread(objWriteMp4Producer).start();
	}
}
