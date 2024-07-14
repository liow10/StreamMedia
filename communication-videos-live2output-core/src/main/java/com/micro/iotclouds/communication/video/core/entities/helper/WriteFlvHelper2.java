/**
 * 
 */
package com.micro.iotclouds.communication.video.core.entities.helper;

import java.util.Date;

import com.micro.iotclouds.communication.video.core.domain.MyLiveConfig;
import com.micro.iotclouds.communication.video.core.entities.StreamName;
import com.micro.iotclouds.communication.video.core.messages.RtmpMediaMessage;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Joe
 *
 */
@Slf4j
public class WriteFlvHelper2 {

	private static long startDateNum = System.currentTimeMillis();
	private static ByteBuf buffer = Unpooled.buffer();
//	private List<Byte> bufferList = Lists.newArrayList();
//	ByteBuffer bb = ByteBuffer.allocate(128);

	/**
	 * @param msg
	 * @param streamName
	 */
	public static void writeFlv4Redis(RtmpMediaMessage msg, byte[] encodeFlvHeaderAndMetadata, StreamName streamName) {
		byte[] encodeMediaAsFlv = encodeMediaAsFlvTagAndPrevTagSize(msg);
		buffer.writeBytes(encodeMediaAsFlv, 0, encodeMediaAsFlv.length);
		// bufferList.add(Bytes);
//		bb = bb.put(encodeMediaAsFlv);
		// if (System.currentTimeMillis() - startDateNum >= 60000) {
		System.out.println("当前时间：" + new Date(System.currentTimeMillis()) + "-------------->" + new Date(startDateNum));
		startDateNum = System.currentTimeMillis();
		// ByteBuf bufferSave = Unpooled.buffer();
		// byte[] bufferSave = buffer.array();
		// byte[] bufferSave = new byte[buffer.readableBytes()];
		// buffer.readBytes(bufferSave);
		// buffer.discardReadBytes();
//			byte[] bufferSave = bb.array();
//			bb.clear();
		// bufferSave =buffer.duplicate();
//			buffer = null;
//			buffer = ByteBuf.w;
		// buffer = Unpooled.buffer();
		// buffer= buffer.clear();
		// buffer.writeBytes(encodeMediaAsFlv, 0, encodeMediaAsFlv.length);
		String saveFlVFilePath = MyLiveConfig.getInstance().getSaveFlVFilePath();
//			byte[] encodeFlvHeaderAndMetadata = Stream.encodeFlvHeaderAndMetadata();
		StreamOutputThread objStreamOutputThread = new StreamOutputThread(encodeFlvHeaderAndMetadata,
				encodeFlvHeaderAndMetadata, saveFlVFilePath, streamName,
				DateUtil.format(new DateTime(startDateNum), "yyyyMMddHHmmss"));
		new Thread(objStreamOutputThread).start();
		// }
//			// 写入flv数据
//			flvout.write(encodeMediaAsFlv);
//			// 缓存同步系统
//			flvout.flush();
//			// 同步硬盘
//			flvout.getFD().sync();
	}

	public static byte[] encodeMediaAsFlvTagAndPrevTagSize(RtmpMediaMessage msg) {
		int tagType = msg.getMsgType();
		byte[] data = msg.raw();
		int dataSize = data.length;
		int timestamp = msg.getTimestamp() & 0xffffff;
		int timestampExtended = ((msg.getTimestamp() & 0xff000000) >> 24);

		ByteBuf buffer = Unpooled.buffer();

		buffer.writeByte(tagType);
		buffer.writeMedium(dataSize);
		buffer.writeMedium(timestamp);
		buffer.writeByte(timestampExtended);// timestampExtended
		buffer.writeMedium(0);// streamid
		buffer.writeBytes(data);
		buffer.writeInt(data.length + 11); // prevousTagSize

		byte[] r = new byte[buffer.readableBytes()];
		buffer.readBytes(r);

		return r;
	}

//	public String SplitFlv(final String oldfilepath) throws IOException {
//
////		if (!checkfile(oldfilepath))
////
////			return null;
//
//		String newFilePath = null;
//
//		final List<String> commend = new java.util.ArrayList<String>();
//
//		commend.add("ffmpeg");
//
//		commend.add(oldfilepath);
//
//		commend.add("-quiet");
//
//		commend.add("-vf");
//
//		commend.add("scale=" + width + ":-3,harddup");
//
//		commend.add("-of");
//
//		commend.add("lavf");
//
//		commend.add("-ovc");
//
//		commend.add("lavc");
//
//		commend.add("-lavcopts");
//
//		commend.add("vcodec=flv:vbitrate=300");
//
//		commend.add("-ofps");
//
//		commend.add("12");
//
//		commend.add("-srate");
//
//		commend.add("22050");
//
//		commend.add("-oac");
//
//		commend.add("mp3lame");
//
//		commend.add("-lameopts");
//
//		commend.add("abr:br=32:mode=3");
//
//		commend.add("-o");
//
//		commend.add(newFilePath);
//
//		InputStream stderr = null;
//
//		InputStreamReader isr = null;
//
//		BufferedReader br = null;
//
//		Process proc = null;
//
//		try {
//
//			final ProcessBuilder builder = new ProcessBuilder();
//
//			builder.command(commend);
//
//			builder.redirectErrorStream(true);
//
//			proc = builder.start();
//
//			stderr = proc.getInputStream();
//
//			isr = new InputStreamReader(stderr);
//
//			br = new BufferedReader(isr);
//
//			boolean flag = false;
//
//			while (br.readLine() != null) {
//
//			}
//
//			if (flag) {
//
//				return newFilePath;
//
//			} else {
//
//				return null;
//
//			}
//
//		} catch (final Exception e) {
//
//			log.error("Convert Error!", e);
//
//			return null;
//
//		} finally {
//
//			proc.destroy();
//
//			br.close();
//
//			isr.close();
//
//			stderr.close();
//
//		}
//
//	}

}
