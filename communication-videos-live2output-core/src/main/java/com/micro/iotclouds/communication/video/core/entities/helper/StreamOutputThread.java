package com.micro.iotclouds.communication.video.core.entities.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.micro.iotclouds.communication.video.core.domain.MyLiveConfig;
import com.micro.iotclouds.communication.video.core.entities.StreamName;

import lombok.extern.slf4j.Slf4j;

/**
 * 流媒体文件转存
 * 
 * @author Joe
 *
 */
@Slf4j
public class StreamOutputThread implements Runnable {

	byte[] bufferSave;
	String saveFlVFilePath;
	StreamName streamName;
	String strTime;
	byte[] encodeFlvHeaderAndMetadata;

	public StreamOutputThread(byte[] encodeFlvHeaderAndMetadata, byte[] bufferSave, String saveFlVFilePath,
			StreamName streamName, String strTime) {
		this.bufferSave = bufferSave;
		this.saveFlVFilePath = saveFlVFilePath;
		this.streamName = streamName;
		this.strTime = strTime;
		this.encodeFlvHeaderAndMetadata = encodeFlvHeaderAndMetadata;
	}

	@Override
	public void run() {
		createFileStream();
	}

	private void createFileStream() {
		// 创建文件接收视频流
//		String strTime = formatDateTime(new DateTime());
		// 文件名
//		String fileName = streamName.getApp() + "_" + streamName.getName() + "_" + strTime;
//		File f = new File(saveFlVFilePath + "/" + streamName.getApp() + "_" + strTime);
		// 创建文件接收视频流
//		String strTime = formatDateTime(new DateTime());
		// 文件名
		String fileName = streamName.getApp() + "_" + streamName.getName() + "_" + strTime;
		// 文件路径
		String fileUrl = MyLiveConfig.getInstance().getSaveFlVFilePath() + fileName + ".flv";
		// 保存视频文件
		File f = new File(fileUrl);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			fos.write(encodeFlvHeaderAndMetadata);
			// 写入flv数据
			//byte[] objByte = bufferSave.array();
		//	System.out.println(byteToHex(objByte));
			fos.write(bufferSave);
			// 缓存同步系统
			fos.flush();
			// 同步硬盘
			fos.getFD().sync();

		} catch (IOException e) {
			log.error("create file : {} failed", e);

		} finally {
			try {
				if (fos != null) {
					fos.close();
					fos = null;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	//
	/**
	 * byte数组转hex
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byteToHex(byte[] bytes) {
		String strHex = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < bytes.length; n++) {
			strHex = Integer.toHexString(bytes[n] & 0xFF);
			sb.append((strHex.length() == 1) ? "0" + strHex : strHex); // 每个字节由两个字符表示，位数不够，高位补0
		}
		return sb.toString().trim();
	}

}
