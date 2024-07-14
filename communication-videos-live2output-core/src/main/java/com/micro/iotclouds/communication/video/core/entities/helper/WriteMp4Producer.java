/**
 * 
 */
package com.micro.iotclouds.communication.video.core.entities.helper;

import java.io.IOException;
import java.io.PipedOutputStream;

import com.micro.iotclouds.communication.video.core.messages.RtmpMediaMessage;

/**
 * @author Joe
 *
 */
public class WriteMp4Producer implements Runnable {

	// 声明一个 管道输出流对象 作为发送方
	private PipedOutputStream outputStream = new PipedOutputStream();

	public PipedOutputStream getOutputStream() {
		return outputStream;
	}

	RtmpMediaMessage msg;

	public WriteMp4Producer(RtmpMediaMessage msg) {
		this.msg = msg;
	}

	@Override
	public void run() {
		this.writeMp4();
	}

	/**
	 * 写入数据流
	 * 
	 * @param msg
	 */
	private void writeMp4() {
		/// inputStream = ByteToInputStream.byte2Input(msg.raw());
		try {
			byte[] raw = msg.raw();
			outputStream.write(raw, 0, raw.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
