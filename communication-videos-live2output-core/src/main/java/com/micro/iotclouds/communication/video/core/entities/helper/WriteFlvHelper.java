/**
 * 
 */
package com.micro.iotclouds.communication.video.core.entities.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import com.micro.iotclouds.communication.video.core.domain.Constants;
import com.micro.iotclouds.communication.video.core.domain.MyLiveConfig;
import com.micro.iotclouds.communication.video.core.entities.Stream2;
import com.micro.iotclouds.communication.video.core.entities.StreamName;
import com.micro.iotclouds.communication.video.core.messages.RtmpMediaMessage;
import com.micro.iotclouds.communication.video.core.utils.IOUtils;
import com.micro.iotclouds.communication.video.core.utils.RollingCalendar;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * flv转存辅助类
 * 
 * @author Joe
 *
 */
@Slf4j
public class WriteFlvHelper {

	/**
	 * 
	 */
	StreamName streamName;

	Stream2 stream;

	FileOutputStream flvout;

//	File f;

	String filePath;

	private Calendar initCalendar = Calendar.getInstance();

	/**
	 * The next time we estimate a rollover should occur.
	 */

	private int periodTimeBySecond = 120;

	Date now = new Date();

	Date last = new Date();

	boolean flvHeadAndMetadataWritten = false;

	private long nextCheck = RollingCalendar.getInstance().getNextCheckMillis(now, periodTimeBySecond);

	public FileOutputStream getFlvout() {
		return flvout;
	}

	/**
	 * @param streamName
	 */
	public WriteFlvHelper(StreamName streamName, Stream2 stream) {
		this.streamName = streamName;
		this.stream = stream;
//		Calendar compareCalendar = Calendar.getInstance();
//		compareCalendar.setTime(now);
//		compareCalendar.add(Calendar.SECOND, periodTimeBySecond);
//		nextCheck = compareCalendar.getTimeInMillis();
		initCalendar.setTime(new Date());
		filePath = MyLiveConfig.getInstance().getSaveFlVFilePath() + "/" + streamName.getApp() + "_"
				+ streamName.getName() + ".flv";
		// 打开文件流
		openFileStream(filePath, true);
	}

//	/**
//	 * 创建当前文件的读写流
//	 * 
//	 * @param streamName
//	 * @param flvout
//	 */
//	public void createFileStream() {
//		f = new File(MyLiveConfig.getInstance().getSaveFlVFilePath() + "/" + streamName.getApp() + "_"
//				+ streamName.getName() + ".flv");
//		try {
//			FileOutputStream fos = new FileOutputStream(f);
//
//			flvout = fos;
//
//		} catch (IOException e) {
//			log.error("create file : {} failed", e);
//
//		}
//
//	}
	/**
	 * 打开当前文件的读写流
	 * 
	 * @param filepath
	 * 
	 * @param streamName
	 * @param flvout
	 */
	public void openFileStream(String filepath, boolean append) {
//		f = new File(MyLiveConfig.getInstance().getSaveFlVFilePath() + "/" + streamName.getApp() + "_"
//				+ streamName.getName() + ".flv");
//		try {
//			FileOutputStream fos = new FileOutputStream(f);
//
//			flvout = fos;
//
//		} catch (IOException e) {
//			log.error("create file : {} failed", e);
//
//		}

		flvout = IOUtils.getFileOutputStream(filepath, append);
	}

	/**
	 * 关闭当前文件的读写流
	 * 
	 * @param streamName
	 * @param flvout
	 */
	public void closeFileStream() {
		IOUtils.closeQuietly(flvout);
	}

	/**
	 * @param msg
	 * @param flvHeadAndMetadataWritten
	 * @param flvout
	 */
	public void writeFlv(RtmpMediaMessage msg) {
		try {
			if (flvout == null) {
				log.error("no flv file existed for stream : {}", streamName);
				return;
			}
			if (msg.getMsgType()==Constants.MSG_TYPE_VIDEO_MESSAGE&&checkAfterCurrentDate()) {
				flvHeadAndMetadataWritten = false;
				copyFlvAndShrink();
			}
			if (!flvHeadAndMetadataWritten) {
				writeFlvHeaderAndMetadata();
				flvHeadAndMetadataWritten = true;
			}
			byte[] encodeMediaAsFlv = stream.encodeMediaAsFlvTagAndPrevTagSize(msg);
			flvout.write(encodeMediaAsFlv);
			flvout.flush();

		} catch (IOException e) {
			log.error("writting flv file failed , stream is :{}", streamName, e);
		}
	}

	/**
	 * 输出flv文件头封装flv头
	 * 
	 * 
	 * @throws IOException
	 */
	public void writeFlvHeaderAndMetadata() throws IOException {
		byte[] encodeFlvHeaderAndMetadata = stream.encodeFlvHeaderAndMetadata();
		flvout.write(encodeFlvHeaderAndMetadata);
		flvout.flush();
		flvout.getFD().sync();
	}

	/**
	 * 复制并重命名文件
	 */
	private void copyFlvAndShrink() {
		try {
			File srcFile = new File(filePath);

			if (srcFile.exists()) {
				// File targetFile = new File(f.getPath().replace(".flv",formatDateTime(last)
				// +".flv"));
				// FileUtil.move(srcFile, targetFile, true);
				String destPath = filePath.replace(".flv", formatDateTime(last) + ".flv");
				FileUtil.copy(filePath, destPath, true);
				closeFileStream();
				flvout = IOUtils.getFileOutputStream(filePath, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 复制文件
		// RenameUtil.rename4New(f.getPath(), f.getPath().replace(".flv",
		// formatDateTime(last) +".flv"));
		// 清空收缩源文件
//		RenameUtil.clearInfoForFile(f.getPath());
//		try {
//		flvout.write("".get,false);
//		flvout.flush();
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
	}

	/**
	 * @return
	 */
	private boolean checkAfterCurrentDate() {
//		Calendar compareCalendar = Calendar.getInstance();
//		compareCalendar.setTime(initCalendar.getTime());
//		
//		Calendar currentCalendar = Calendar.getInstance();
//		Date now = new Date();
//		currentCalendar.setTime(now);// 也给初始日期 把分钟加五
//////		initCalendar.add(Calendar.SECOND, iCheckMinute);// 初始时间加上五分钟
//////		System.out.println("初始时间:"+initCalendar.getTime());
//////		System.out.println("当前时间:"+currentCalendar.getTime());
//////		if (initCalendar.after(currentCalendar)) {
//////			System.out.println("五分钟之外");
//////			initCalendar.setTime(now);
//////			return true;
//////		}
////////         else {
////////             System.out.println("五分钟之内");
////////         }
//		System.out.println("初始时间:"+initCalendar.getTime());
//		System.out.println("当前时间:"+currentCalendar.getTime());
//		compareCalendar.add(Calendar.SECOND, 60);
//		System.out.println("compareCalendar+ 1分钟:" +compareCalendar.getTime() );
//		//if (currentCalendar.getTimeInMillis() >= compareCalendar.getTimeInMillis()) {
//		if(compareCalendar.after(currentCalendar)) {
//			initCalendar.setTime(compareCalendar.getTime());
//			return true;
//		}
////		initCalendar.add(Calendar.SECOND, iCheckMinute * 60);// 初始时间加上五分钟
////		if (currentCalendar.getTime().before(initCalendar.getTime())) {
//////			initCalendar.setTime(currentCalendar.getTime());
////			return true;
////		}

		long n = System.currentTimeMillis();
		if (n >= nextCheck) {
			last = now;
			now.setTime(n);
			nextCheck = RollingCalendar.getInstance().getNextCheckMillis(now, periodTimeBySecond);
			return true;
//			try {
////		rollOver();
//			} catch (IOException ioe) {
//				if (ioe instanceof InterruptedIOException) {
//					Thread.currentThread().interrupt();
//				}
//			}
		}
		return false;
	}

	/**
	 * 时间格式化
	 * 
	 * @param date
	 * @return
	 */
	private static String formatDateTime(Date date) {
		return null == date ? null : DatePattern.PURE_DATETIME_FORMAT.format(date);
	}
}
