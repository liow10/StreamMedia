///**
// * 
// */
//package com.micro.iotclouds.communication.video.core.entities.helper;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.Semaphore;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.mina.core.buffer.IoBuffer;
//import org.red5.codec.AudioCodec;
//import org.red5.codec.VideoCodec;
//import org.red5.io.flv.impl.FLVWriter;
//import org.red5.server.stream.IStreamData;
//import org.red5.server.stream.consumer.ImmutableTag;
//
//import com.micro.iotclouds.communication.video.core.amf.AMF0;
//import com.micro.iotclouds.communication.video.core.domain.Constants;
//import com.micro.iotclouds.communication.video.core.domain.MyLiveConfig;
//import com.micro.iotclouds.communication.video.core.entities.StreamName;
//import com.micro.iotclouds.communication.video.core.messages.RtmpMediaMessage;
//
//import cn.hutool.core.date.DatePattern;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Flv 转存
// * 
// * @author Administrator
// *
// */
//@Slf4j
//public class FlvWriterHelper {
//
//	static byte[] flvHeader = new byte[] { 0x46, 0x4C, 0x56, 0x01, 0x05, 00, 00, 00, 0x09 };
//
//	private AtomicBoolean started = new AtomicBoolean(false);
//
//	private Semaphore lock = new Semaphore(1, true);
//
//	private File file;
//
//	private FLVWriter writer;
//
//	private volatile int previousTagSize;
//	
//	Map<String, Object> metadata;
//	
//	private volatile int segmentSize;
//	
//	static FlvWriterHelper objFlvWriterHelper ;
//	public static FlvWriterHelper getInstance() {
//		if(objFlvWriterHelper==null) {
//			objFlvWriterHelper = new FlvWriterHelper();
//		}
//		return objFlvWriterHelper;
//	}
//	public static String formatDateTime(Date date) {
//		return null == date ? null : DatePattern.PURE_DATETIME_FORMAT.format(date);
//	}
//	String fileUrl = "";
//	int i = 0;
//	boolean append = true;
//	@SuppressWarnings("rawtypes")
//	public boolean processFlvWriter(StreamName streamName, /* boolean append, */ /* int tagType, */RtmpMediaMessage msg) {
//		log.debug("start");
//		boolean result = false;
//		
////		if (started.compareAndSet(false, true)) {
////			try {
//			//	lock.acquire();
//				
//				if(StringUtils.isBlank(fileUrl)) {
//					// 文件名
//					String fileName = streamName.getApp() + "_"
//							+ streamName.getName()/* + "_" + formatDateTime(new Date()) */;
//					// 文件路径
//					 fileUrl = MyLiveConfig.getInstance().getSaveFlVFilePath() + fileName +(i++)+ ".flv";
//				}
////				if(segmentSize>0&&segmentSize<=10*1024*1024) {
////					append = true;
////				}
//				file = new File(fileUrl);
//				// remove previous flv if it exists
//				if (!file.exists()) {
////					file.delete();
//					try {
//						file.createNewFile();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				
//				writer = new FLVWriter(file, append);
////				if (tagType == Constants.MSG_TYPE_AUDIO_MESSAGE) {
////					writer.setAudioCodecId(AudioCodec.SPEEX.getId());
////				} else if (tagType == Constants.MSG_TYPE_VIDEO_MESSAGE) {
////					writer.setVideoCodecId(VideoCodec.AVC.getId());
////				}
////				byte[] bufs = msg.raw();
////				int bufLength = bufs.length;
//				IoBuffer buf = IoBuffer.wrap( msg.raw());
//				int bufLength = buf.limit();
//				try {
//					result = writer.writeTag(ImmutableTag.build((byte)msg.getMsgType(), msg.getTimestamp(), buf, previousTagSize));
//					segmentSize = (int) (segmentSize + bufLength);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				this.previousTagSize = bufLength + 11; // add 11 for the tag header length (fixed for flv at 11)
////			} catch (InterruptedException e) {
////				log.warn("Exception during start", e);
////			} finally {
////				lock.release();
////			}
////		} else {
////			log.debug("Already started");
////		}
//		return result;
//	}
//	
//
//	public void writeFileStream(StreamName streamName, RtmpMediaMessage msg) {
//		// 创建文件接收视频流
////		String strTime = formatDateTime(new DateTime());
//		// 文件名
////		String fileName = streamName.getApp() + "_" + streamName.getName() + "_" + strTime;
////		File f = new File(saveFlVFilePath + "/" + streamName.getApp() + "_" + strTime);
//		// 创建文件接收视频流
////		String strTime = formatDateTime(new DateTime());
////		// 文件名
////		String fileName = streamName.getApp() + "_" + streamName.getName() + "_" + strTime;
////		// 文件路径
////		String fileUrl = MyLiveConfig.getInstance().getSaveFlVFilePath() + fileName + ".flv";
//		if(StringUtils.isBlank(fileUrl)) {
//			// 文件名
//			String fileName = streamName.getApp() + "_"
//					+ streamName.getName()/* + "_" + formatDateTime(new Date()) */;
//			// 文件路径
//			 fileUrl = MyLiveConfig.getInstance().getSaveFlVFilePath() + fileName +(i++)+ ".flv";
//		}
//		// 保存视频文件
//		File f = new File(fileUrl);
//		FileOutputStream fos = null;
//		try {
//			fos = new FileOutputStream(f);
//			fos.write(encodeFlvHeaderAndMetadata());
//			// 写入flv数据
//			//byte[] objByte = bufferSave.array();
//		//	System.out.println(byteToHex(objByte));
//			fos.write(msg.raw());
//			// 缓存同步系统
//			fos.flush();
//			// 同步硬盘
//			fos.getFD().sync();
//
//		} catch (IOException e) {
//			log.error("create file : {} failed", e);
//
//		} finally {
//			try {
//				if (fos != null) {
//					fos.close();
//					fos = null;
//				}
//
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//	}
//	/**
//	 * @param msg
//	 * @return
//	 */
//	public byte[] encodeMediaAsFlvTagAndPrevTagSize(RtmpMediaMessage msg) {
//		int tagType = msg.getMsgType();
//		byte[] data = msg.raw();
//		int dataSize = data.length;
//		int timestamp = msg.getTimestamp() & 0xffffff;
//		int timestampExtended = ((msg.getTimestamp() & 0xff000000) >> 24);
//
//		ByteBuf buffer = Unpooled.buffer();
//
//		buffer.writeByte(tagType);
//		buffer.writeMedium(dataSize);
//		buffer.writeMedium(timestamp);
//		buffer.writeByte(timestampExtended);// timestampExtended
//		buffer.writeMedium(0);// streamid
//		buffer.writeBytes(data);
//		buffer.writeInt(data.length + 11); // prevousTagSize
//
//		byte[] r = new byte[buffer.readableBytes()];
//		buffer.readBytes(r);
//
//		return r;
//	}
//	/**
//	 * 封装flv头信息
//	 * 
//	 * @return
//	 */
////	private byte[] encodeFlvHeaderAndMetadata() {
////		ByteBuf encodeMetaData = encodeMetaData();
////		ByteBuf buf = Unpooled.buffer();
////
////		RtmpMediaMessage msg = content.get(0);
////		int timestamp = msg.getTimestamp() & 0xffffff;
////		int timestampExtended = ((msg.getTimestamp() & 0xff000000) >> 24);
////
////		buf.writeBytes(flvHeader);
////		buf.writeInt(0); // previousTagSize0
////
////		int readableBytes = encodeMetaData.readableBytes();
////		buf.writeByte(0x12); // script
////		buf.writeMedium(readableBytes);
////		// make the first script tag timestamp same as the keyframe
////		buf.writeMedium(timestamp);
////		buf.writeByte(timestampExtended);
//////		buf.writeInt(0); // timestamp + timestampExtended
////		buf.writeMedium(0);// streamid
////		buf.writeBytes(encodeMetaData);
////		buf.writeInt(readableBytes + 11);
////
////		byte[] result = new byte[buf.readableBytes()];
////		buf.readBytes(result);
////
////		return result;
////
////	}
//	
//	private ByteBuf encodeMetaData() {
//		ByteBuf buffer = Unpooled.buffer();
//		List<Object> meta = new ArrayList<>();
//		meta.add("onMetaData");
//		meta.add(metadata);
//		log.info("Metadata:{}", metadata);
//		AMF0.encode(buffer, meta);
//
//		return buffer;
//	}
//}
