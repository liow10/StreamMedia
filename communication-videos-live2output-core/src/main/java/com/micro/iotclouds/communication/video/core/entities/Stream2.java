package com.micro.iotclouds.communication.video.core.entities;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.micro.iotclouds.communication.video.core.amf.AMF0;
import com.micro.iotclouds.communication.video.core.domain.Constants;
import com.micro.iotclouds.communication.video.core.domain.MyLiveConfig;
import com.micro.iotclouds.communication.video.core.domain.StreamFrame;
//import com.micro.iotclouds.communication.video.core.entities.helper.WriteFlvHelper;
//import com.micro.iotclouds.communication.video.core.entities.helper.WriteMp4Helper;
import com.micro.iotclouds.communication.video.core.messages.AudioMessage;
import com.micro.iotclouds.communication.video.core.messages.RtmpMediaMessage;
import com.micro.iotclouds.communication.video.core.messages.RtmpMessage;
import com.micro.iotclouds.communication.video.core.messages.UserControlMessageEvent;
import com.micro.iotclouds.communication.video.core.messages.VideoMessage;

import cn.hutool.core.date.DatePattern;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.DefaultLastHttpContent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("serial")
@Data
@Slf4j
public class Stream2 implements Serializable {

	static byte[] flvHeader = new byte[] { 0x46, 0x4C, 0x56, 0x01, 0x05, 00, 00, 00, 0x09 };

	Map<String, Object> metadata;

	transient  Channel publisher;

	public VideoMessage avcDecoderConfigurationRecord;

	public AudioMessage aacAudioSpecificConfig;
	Set<Channel> subscribers;

	protected List<RtmpMediaMessage> content;

	protected StreamName streamName;
	Map<String, String> map;
	int videoTimestamp;
	int audioTimestamp;
//	static TimerTask saveTask;
//	static TimerTask deleteTask;

//	FileOutputStream flvout;
//	boolean flvHeadAndMetadataWritten = false;

	Set<Channel> httpFLvSubscribers;

	boolean isFirst = true;

//	WriteFlvHelper objWriteFlvHelper;
//
//	WriteMp4Helper objWriteMp4Helper;

	public Stream2(StreamName streamName) {
		subscribers = new LinkedHashSet();
		httpFLvSubscribers = new LinkedHashSet();
		content = new ArrayList();
		map = new HashMap();
		this.streamName = streamName;
		if (MyLiveConfig.getInstance().isSaveFlvFile()) {
//			createFile();
			/*******************
			 * 基于WriteFlvHelper写入存储
			 *
			 * @date 2022-1-10
			 *********************/
			// objWriteFlvHelper = new WriteFlvHelper(streamName, this);
//			objWriteFlvHelper.createFileStream();
//			flvout = objWriteFlvHelper.getFlvout();
		}
		if (MyLiveConfig.getInstance().isSaveMp4File()) {
			/****************
			 * 写入MP4
			 *****************/
			// objWriteMp4Helper = new WriteMp4Helper(streamName);
		}
	}

	public synchronized void addContent(RtmpMediaMessage msg) {

		if (msg instanceof VideoMessage) {
			VideoMessage vm = (VideoMessage) msg;
			if (vm.getTimestamp() != null) {
				// we may encode as FMT1 ,so we need timestamp delta
				vm.setTimestampDelta(vm.getTimestamp() - videoTimestamp);
				videoTimestamp = vm.getTimestamp();
			} else if (vm.getTimestampDelta() != null) {
				videoTimestamp += vm.getTimestampDelta();
				vm.setTimestamp(videoTimestamp);
			}

			if (vm.isAVCDecoderConfigurationRecord()) {
				log.info("avcDecoderConfigurationRecord  ok");
				avcDecoderConfigurationRecord = vm;
			}

			if (vm.isH264KeyFrame()) {
				log.debug("video key frame in stream :{}", streamName);
				content.clear();
			}
		}

		if (msg instanceof AudioMessage) {

			AudioMessage am = (AudioMessage) msg;
			if (am.getTimestamp() != null) {
				am.setTimestampDelta(am.getTimestamp() - audioTimestamp);
				audioTimestamp = am.getTimestamp();
			} else if (am.getTimestampDelta() != null) {
				audioTimestamp += am.getTimestampDelta();
				am.setTimestamp(audioTimestamp);
			}

			if (am.isAACAudioSpecificConfig()) {
				aacAudioSpecificConfig = am;
			}
		}
		// if (flvout != null) {
		content.add(msg);
		// }

		// FlvWriterHelper.getInstance().processFlvWriter(streamName, msg);
		if (MyLiveConfig.getInstance().isSaveFlvFile()) {
			// 需要保存文件则持续写入文件
//			if (!isFirst) {
//				createFileStream(msg, true);
//			}
//			writeFlv(msg);
//			isFirst = false;
			/*********************
			 * 持续写入文件
			 *
			 * @date 2022-1-10
			 *********************/
			// objWriteFlvHelper.writeFlv(msg);
		}
		if (MyLiveConfig.getInstance().isSaveMp4File()) {
			// 需要保存文件则持续写入文件
			/*********************
			 * 持续写入文件
			 *
			 * @date 2022-1-10
			 *********************/
			// objWriteMp4Helper.writeMp4(msg);
		}
		broadCastToSubscribers(msg);
	}

	/**
	 * @param msg
	 * @return
	 */
	public byte[] encodeMediaAsFlvTagAndPrevTagSize(RtmpMediaMessage msg) {
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

	// ByteBuf firstAudioTag = Unpooled.buffer();
	// 写入flv文件
//	private void writeFlv(RtmpMediaMessage msg) {
//		if (flvout == null) {
//			log.error("no flv file existed for stream : {}", streamName);
//			return;
//		}
//		try {
//			if (!flvHeadAndMetadataWritten) {
//				writeFlvHeaderAndMetadata();
//				flvHeadAndMetadataWritten = true;
////
////				if (!isFirst) {
////					if (Constants.MSG_TYPE_AUDIO_MESSAGE == msg.getMsgType()) {
////						// 记录第一甄tag
////						// firstAudioTag.writeBytes(encodeMediaAsFlv);
////						// }else {
////						// byte[] r = new byte[firstAudioTag.readableBytes()];//[8, 0, 0, 7, 0, 0, 0, 0,
////						// 0, 0, 0, -81, 0, 20, 16, 86, -27, 0, 0, 0, 0, 18]
////						byte[] firstAudioTag = new byte[] { 0x08, 0x00, 0x00, 0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
////								0x00, (byte) 0xAF, 0x00, 0x14, 0x10, 0x56, (byte) 0xE5, 0x00 };
////						// firstAudioTag.readBytes(r);
////						flvout.write(firstAudioTag);
////						byte[] preTagSize = new byte[] { 0x00, 0x00, 0x00, 0x12 };
////						flvout.write(preTagSize);
////						// 缓存同步系统
////						flvout.flush();
////						// 同步硬盘
////						flvout.getFD().sync();
////					} else if (Constants.MSG_TYPE_VIDEO_MESSAGE == msg.getMsgType()) {
////						// 记录第一甄tag
////						// firstAudioTag.writeBytes(encodeMediaAsFlv);
////						// }else {
////						// byte[] r = new byte[firstAudioTag.readableBytes()];//[8, 0, 0, 7, 0, 0, 0, 0,
////						// 0, 0, 0, -81, 0, 20, 16, 86, -27, 0, 0, 0, 0, 18]
//////						byte[] firstVideoTagHeader = new byte[] { 0x09, 0x00, 0x00, 0x31, 0x00, 0x00, 0x00, 0x00, 0x00,
//////								0x00, 0x00 };
//////						// firstAudioTag.readBytes(r);
//////						flvout.write(firstVideoTagHeader);
//////						byte[] firstVideoTagData = new byte[] { 0x17, 0x00, 0x00, 0x00, 0x00, 0x01, 0x64, 0x00, 0x2A,
//////								(byte) 0xFF, (byte) 0xE1, 0x00, 0x19, 0x67, 0x64, 0x00, 0x67, 0x2A, (byte) 0xAC, 0x2C,
//////								0x6A, (byte) 0x81, (byte) 0xE0, 0x08, (byte) 0x9F, (byte) 0x96, 0x6E, 0x02, 0x02, 0x02,
//////								(byte) 0x80, 0x00, 0x03, 0x04, 0x68, (byte) 0xEE, 0x3C, (byte) 0xB0, (byte) 0xFD,
//////								(byte) 0xF8, (byte) 0xF8, 0x00 };
//////						// firstAudioTag.readBytes(r);
//////						flvout.write(firstVideoTagData);
//////						byte[] preTagSize = new byte[] { 0x00, 0x00, 0x00, 0x3C };
//////						flvout.write(preTagSize);
////						flvout.write(avcDecoderConfigurationRecord.raw());
////						// 缓存同步系统
////						flvout.flush();
////						// 同步硬盘
////						flvout.getFD().sync();
////					}
////				}
//			}
//			byte[] encodeMediaAsFlv = encodeMediaAsFlvTagAndPrevTagSize(msg);
//			// 写入flv数据
//			flvout.write(encodeMediaAsFlv);
//			// 缓存同步系统
//			flvout.flush();
//			// 同步硬盘
//			flvout.getFD().sync();
//
//		} catch (IOException e) {
//			log.error("writting flv file failed , stream is :{}", streamName, e);
//		}
//	}

	/**
	 * 封装flv头信息
	 *
	 * @return
	 */
	public byte[] encodeFlvHeaderAndMetadata() {
		ByteBuf encodeMetaData = encodeMetaData();
		ByteBuf buf = Unpooled.buffer();

		RtmpMediaMessage msg = content.get(0);
		int timestamp = msg.getTimestamp() & 0xffffff;
		int timestampExtended = ((msg.getTimestamp() & 0xff000000) >> 24);

		buf.writeBytes(flvHeader);
		buf.writeInt(0); // previousTagSize0

		int readableBytes = encodeMetaData.readableBytes();
		buf.writeByte(0x12); // script
		buf.writeMedium(readableBytes);
		// make the first script tag timestamp same as the keyframe
		buf.writeMedium(timestamp);
		buf.writeByte(timestampExtended);
//		buf.writeInt(0); // timestamp + timestampExtended
		buf.writeMedium(0);// streamid
		buf.writeBytes(encodeMetaData);
		buf.writeInt(readableBytes + 11);

		byte[] result = new byte[buf.readableBytes()];
		buf.readBytes(result);

		return result;

	}

	/**
	 * 输出flv文件头封装flv头
	 *
	 *
	 * @throws IOException
	 */
//	public void writeFlvHeaderAndMetadata() throws IOException {
//		byte[] encodeFlvHeaderAndMetadata = encodeFlvHeaderAndMetadata();
//		flvout.write(encodeFlvHeaderAndMetadata);
//		flvout.flush();
//		flvout.getFD().sync();
//	}

	private ByteBuf encodeMetaData() {
		ByteBuf buffer = Unpooled.buffer();
		List<Object> meta = new ArrayList();
		meta.add("onMetaData");
		meta.add(metadata);
		log.info("Metadata:{}", metadata);
		AMF0.encode(buffer, meta);

		return buffer;
	}

	Date currentDate = new Date();

//	/**
//	 * 创建文件输出流
//	 *
//	 * @param msg
//	 *
//	 */
//	private void createFileStream(RtmpMediaMessage msg, boolean append) {
//		// 如果是音频信息则要回到音频关键帧，如果是视频的则要回到视频关键帧
//		if (!WriteFlvCheckingHelper.checkIsVideoKeyFrame(msg)) {
//			return;
//		}
//		// System.out.println("execute");
//		// 保存数据时间间隔(小时)
//		Double Isava = MyLiveConfig.getInstance().getSaveDataTimeInterval() == null ? 1
//				: MyLiveConfig.getInstance().getSaveDataTimeInterval();
//		long saveDelay = (long) (1 * 60 * 1000);
//		Date currentDate2 = new Date();
//		if (currentDate2.getTime() - currentDate.getTime() <= saveDelay) {
//			return;
//		}
//		currentDate = currentDate2;
//		flvHeadAndMetadataWritten = false;
//		// 删除文件时间间隔(小时)
////		Double Idelete = MyLiveConfig.getInstance().getSaveDataTimeInterval() == null ? 1
////				: MyLiveConfig.getInstance().getSaveDataTimeInterval();
////		long deleteDelay = (long) (Idelete * 30 * 1000);
////		// 删除过期文件(小时)
////		Double deleteDataHour = MyLiveConfig.getInstance().getDeleteDataHour() == null ? 60
////				: MyLiveConfig.getInstance().getDeleteDataHour();
////		long lHour = (long) (deleteDataHour * 300 * 1000);
//
//		String deviceId = JedisUtil.get("MKST:VIDEO:TOKEN:" + streamName.getName());
//
////        saveTask = new TimerTask() {
////            @Override
////            public void run() {
////                // TODO Auto-generated method stub
//		try {
//			if (flvout != null) {
//				flvout.flush();
//				flvout.close();
//			}
//		} catch (IOException e) {
//			log.error("close file:{} failed", flvout);
//		}
//		// 创建文件接收视频流
//		String strTime = formatDateTime(currentDate);
//		// 文件名
//		String fileName = streamName.getApp() + "_" + streamName.getName() + "_" + strTime;
//		// 文件路径
//		String fileUrl = MyLiveConfig.getInstance().getSaveFlVFilePath() + "/" + fileName + ".flv";
//		// 保存视频文件
//		File f = new File(fileUrl);
//
//		map.put(strTime, fileUrl);
//
////                String deviceId = JedisUtil.get("MKST:VIDEO:TOKEN:"+streamName.getName());
////        log.info("视频文件信息写入redis,KEY为:"+"MKST:VIDEOS:"+deviceId+",map为:"+strTime+":"+fileUrl);
//
//		JedisUtil.hmset("MKST:VIDEOS:" + deviceId, map);
//
//		try {
//			FileOutputStream fos = new FileOutputStream(f, append);
//			flvout = fos;
//			// flvHeadAndMetadataWritten = false;
//
//		} catch (IOException e) {
//			log.error("create file : {} failed", e);
//		}
//	}

//	private synchronized void createFile() {
//
//		System.out.println("execute");
//		// 保存数据时间间隔(小时)
//		Double Isava = MyLiveConfig.getInstance().getSaveDataTimeInterval() == null ? 1
//				: MyLiveConfig.getInstance().getSaveDataTimeInterval();
//		long saveDelay = (long) (Isava * 30 * 1000);
//		// 删除文件时间间隔(小时)
//		Double Idelete = MyLiveConfig.getInstance().getSaveDataTimeInterval() == null ? 1
//				: MyLiveConfig.getInstance().getSaveDataTimeInterval();
//		long deleteDelay = (long) (Idelete * 30 * 1000);
//		// 删除过期文件(小时)
//		Double deleteDataHour = MyLiveConfig.getInstance().getDeleteDataHour() == null ? 60
//				: MyLiveConfig.getInstance().getDeleteDataHour();
//		long lHour = (long) (deleteDataHour * 300 * 1000);
//
//		String deviceId = JedisUtil.get("MKST:VIDEO:TOKEN:" + streamName.getName());
//
////        saveTask = new TimerTask() {
////            @Override
////            public void run() {
////                // TODO Auto-generated method stub
//		try {
//			if (flvout != null) {
//				flvout.flush();
//				flvout.close();
//			}
//		} catch (IOException e) {
//			log.error("close file:{} failed", flvout);
//		}
//		// 创建文件接收视频流
//		String strTime = formatDateTime(new DateTime());
//		// 文件名
//		String fileName = streamName.getApp() + "_" + streamName.getName() + "_" + strTime;
//		// 文件路径
//		String fileUrl = MyLiveConfig.getInstance().getSaveFlVFilePath() + "/" + fileName + ".flv";
//		// 保存视频文件
//		File f = new File(fileUrl);
//
//		map.put(strTime, fileUrl);
//
////                String deviceId = JedisUtil.get("MKST:VIDEO:TOKEN:"+streamName.getName());
////        log.info("视频文件信息写入redis,KEY为:"+"MKST:VIDEOS:"+deviceId+",map为:"+strTime+":"+fileUrl);
//
//		JedisUtil.hmset("MKST:VIDEOS:" + deviceId, map);
//
//		try {
//			FileOutputStream fos = new FileOutputStream(f);
//			flvout = fos;
//			flvHeadAndMetadataWritten = false;
//
//		} catch (IOException e) {
//			log.error("create file : {} failed", e);
//		}
////
////            }
////
////        };
////
////        deleteTask=new TimerTask() {
////
////            @Override
////            public void run() {
////                //删除文件代码
////                String key = "MKST:VIDEOS:"+deviceId;
////                HashMap<String,String> map = JedisUtil.queryHashMapByVal(key,lHour);
////
////                if(map!=null){
////                    for (Map.Entry<String, String> entry : map.entrySet()) {
////                        String strFileUrl = entry.getValue().substring(1,entry.getValue().length()-1);
////                        File file = new File(strFileUrl);
////                        if(file.exists()){
////                            file.delete();
////                            JedisUtil.hdel(key,entry.getKey());
////                            log.info("删除过期视频文件:"+strFileUrl);
////                        }
////                    }
////                }
////            }
////
////        };
////
////        //立即执行，每隔delay秒循环执行
////        new Timer().schedule(saveTask, 0,saveDelay);
////        new Timer().schedule(deleteTask, 0,deleteDelay);
//	}

//	public static void stopTask() {
//		if (saveTask != null) {
//			saveTask.cancel();
//		}
//		if (deleteTask != null) {
//			deleteTask.cancel();
//		}
//	}

	public static String formatDateTime(Date date) {
		return null == date ? null : DatePattern.PURE_DATETIME_FORMAT.format(date);
	}

	public synchronized void addSubscriber(Channel channel) {
		subscribers.add(channel);
		log.info("subscriber : {} is added to stream :{}", channel, streamName);

		if (avcDecoderConfigurationRecord != null && content.size() > 0) {
			avcDecoderConfigurationRecord.setTimestamp(content.get(0).getTimestamp());
			log.info("avcDecoderConfigurationRecord:{}", avcDecoderConfigurationRecord);
			channel.writeAndFlush(avcDecoderConfigurationRecord);
			for (RtmpMessage msg : content) {
				channel.writeAndFlush(msg);
			}
		}

		if (aacAudioSpecificConfig != null && content.size() > 0) {
			// 3. write aacAudioSpecificConfig
			aacAudioSpecificConfig.setTimestamp(content.get(0).getTimestamp());
			log.info("aacAudioSpecificConfig:{}", aacAudioSpecificConfig);
			channel.writeAndFlush(aacAudioSpecificConfig);
		}

	}

	public synchronized void addHttpFlvSubscriber(Channel channel) {
		httpFLvSubscribers.add(channel);
		log.info("http flv subscriber : {} is added to stream :{}", channel, streamName);

		// 1. write flv header and metaData
		byte[] meta = encodeFlvHeaderAndMetadata();
		channel.writeAndFlush(Unpooled.wrappedBuffer(meta));

		// 2. write avcDecoderConfigurationRecord
		if (avcDecoderConfigurationRecord != null) {
			avcDecoderConfigurationRecord.setTimestamp(content.get(0).getTimestamp());
			byte[] config = encodeMediaAsFlvTagAndPrevTagSize(avcDecoderConfigurationRecord);
			channel.writeAndFlush(Unpooled.wrappedBuffer(config));
		}

		if (aacAudioSpecificConfig != null) {
			// 3. write aacAudioSpecificConfig
			aacAudioSpecificConfig.setTimestamp(content.get(0).getTimestamp());
			byte[] aac = encodeMediaAsFlvTagAndPrevTagSize(aacAudioSpecificConfig);
			channel.writeAndFlush(Unpooled.wrappedBuffer(aac));
		}
		// 4. write content

//        for (RtmpMediaMessage msg : content) {
//            channel.writeAndFlush(Unpooled.wrappedBuffer(encodeMediaAsFlvTagAndPrevTagSize(msg)));
//        }

	}

	int dwtime = 0;

	/**
	 * 增加rtsp订阅
	 *
	 * @param channel
	 */
	public synchronized void addRtspSubscriber(Channel channel) {
		ByteBuf bufStreamFrame = channel.alloc().directBuffer(16, 16);
		StreamFrame streamFrame = new StreamFrame(bufStreamFrame);
		streamFrame.dwTime = this.dwtime;
		ByteBuf buf = streamFrame.content();
		byte type = buf.getByte(4);
		streamFrame.bIsKey = ((type & 0x1f) == 5) || ((type & 0x1f) == 7);

		int taglen = 5 + buf.readableBytes();
		ByteBuf buftag = channel.alloc().directBuffer(16, 16);
		// 11 bytes
		buftag.writeByte(0x9).writeMedium(taglen).writeMedium(streamFrame.dwTime);
		buftag.writeByte((streamFrame.dwTime >> 24) & 0xff).writeZero(3);

		// 5 bytes
		buftag.writeByte(streamFrame.bIsKey ? 0x17 : 0x27);
		buftag.writeByte(1).writeZero(3);

		// list.add(buftag);
//		list.add(WrappedFlvBuf(buftag));
//
//		ReferenceCountUtil.retain(streamFrame.content());
//		// list.add(streamFrame.content());
//		list.add(WrappedFlvBuf(streamFrame.content()));
//
//		ByteBuf bufend = ctx.alloc().directBuffer(4, 4).writeInt(taglen + 11);
//		// list.add(bufend);
//		list.add(WrappedFlvBuf(bufend));
		this.dwtime += 30;
//		return true;
	}

	private synchronized void broadCastToSubscribers(RtmpMediaMessage msg) {
		Iterator<Channel> iterator = subscribers.iterator();
		while (iterator.hasNext()) {
			Channel next = iterator.next();
			if (next.isActive()) {
				next.writeAndFlush(msg);
			} else {
				iterator.remove();
			}
		}

		if (!httpFLvSubscribers.isEmpty()) {
			byte[] encoded = encodeMediaAsFlvTagAndPrevTagSize(msg);

			Iterator<Channel> httpIte = httpFLvSubscribers.iterator();
			while (httpIte.hasNext()) {
				Channel next = httpIte.next();
				if (next.isActive()) {
					next.writeAndFlush(Unpooled.wrappedBuffer(encoded));
				} else {
					log.info("http channel :{} is not active remove", next);
					httpIte.remove();
				}
			}
		}

	}

	public synchronized void 	sendEofToAllSubscriberAndClose() {
//		if (MyLiveConfig.getInstance().isSaveFlvFile() && flvout != null) {
//			try {
//				flvout.close();
////				stopTask();
//			} catch (IOException e) {
//				log.error("close file:{} failed", flvout);
//			}
//		}
		/***************
		 * 2022-01-11
		 ******************/
		if (MyLiveConfig.getInstance().isSaveFlvFile()) {
			// objWriteFlvHelper.closeFileStream();
		}
		for (Channel sc : subscribers) {
			sc.writeAndFlush(UserControlMessageEvent.streamEOF(Constants.DEFAULT_STREAM_ID))
					.addListener(ChannelFutureListener.CLOSE);

		}

		for (Channel sc : httpFLvSubscribers) {
			sc.writeAndFlush(DefaultLastHttpContent.EMPTY_LAST_CONTENT).addListener(ChannelFutureListener.CLOSE);

		}

	}

}
