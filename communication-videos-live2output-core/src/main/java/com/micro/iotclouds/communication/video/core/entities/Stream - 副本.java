//package com.micro.iotclouds.communication.video.core.entities;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//
///**
// * @author longyubo
// * 2020年1月2日 下午3:36:21
// **/
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.TimerTask;
//
//import com.micro.iotclouds.communication.video.core.amf.AMF0;
//import com.micro.iotclouds.communication.video.core.domain.Constants;
//import com.micro.iotclouds.communication.video.core.domain.MyLiveConfig;
//import com.micro.iotclouds.communication.video.core.messages.AudioMessage;
//import com.micro.iotclouds.communication.video.core.messages.RtmpMediaMessage;
//import com.micro.iotclouds.communication.video.core.messages.RtmpMessage;
//import com.micro.iotclouds.communication.video.core.messages.UserControlMessageEvent;
//import com.micro.iotclouds.communication.video.core.messages.VideoMessage;
//
//import cn.hutool.core.date.DateTime;
//import cn.hutool.core.date.DateUtil;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFutureListener;
//import io.netty.handler.codec.http.DefaultLastHttpContent;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//
//@Data
//@Slf4j
//public class Stream {
//
//	static byte[] flvHeader = new byte[] { 0x46, 0x4C, 0x56, 0x01, 0x05, 00, 00, 00, 0x09 };
//
////	@Autowired
////	private static MyLiveConfig INSTANCE;// = SpringUtil.getBean(MyLiveConfig.class);
//
//	Map<String, Object> metadata;
//
//	Channel publisher;
//
//	VideoMessage avcDecoderConfigurationRecord;
//
//	AudioMessage aacAudioSpecificConfig;
//	Set<Channel> subscribers;
//
//	List<RtmpMediaMessage> content;
//
//	StreamName streamName;
//	Map<String, String> map;
//	int videoTimestamp;
//	int audioTimestamp;
//	static TimerTask saveTask;
//	static TimerTask deleteTask;
//
//	FileOutputStream flvout;
//	boolean flvHeadAndMetadataWritten = false;
//
//	Set<Channel> httpFLvSubscribers;
//
//	public Stream(StreamName streamName) {
//		subscribers = new LinkedHashSet<>();
//		httpFLvSubscribers = new LinkedHashSet<>();
//		content = new ArrayList<>();
//		map = new HashMap<>();
//		this.streamName = streamName;
////		if (MyLiveConfig.getInstance().isSaveFlvFile()) {
////			// TODO
////			// createFile();
////		}
//	}
//
//	public synchronized void addContent(RtmpMediaMessage msg) {
//
//		if (msg instanceof VideoMessage) {
//			VideoMessage vm = (VideoMessage) msg;
//			if (vm.getTimestamp() != null) {
//				// we may encode as FMT1 ,so we need timestamp delta
//				vm.setTimestampDelta(vm.getTimestamp() - videoTimestamp);
//				videoTimestamp = vm.getTimestamp();
//			} else if (vm.getTimestampDelta() != null) {
//				videoTimestamp += vm.getTimestampDelta();
//				vm.setTimestamp(videoTimestamp);
//			}
//
//			if (vm.isAVCDecoderConfigurationRecord()) {
//				log.info("avcDecoderConfigurationRecord  ok");
//				avcDecoderConfigurationRecord = vm;
//			}
//
//			if (vm.isH264KeyFrame()) {
//				log.debug("video key frame in stream :{}", streamName);
//				content.clear();
//			}
//		}
//
//		if (msg instanceof AudioMessage) {
//
//			AudioMessage am = (AudioMessage) msg;
//			if (am.getTimestamp() != null) {
//				am.setTimestampDelta(am.getTimestamp() - audioTimestamp);
//				audioTimestamp = am.getTimestamp();
//			} else if (am.getTimestampDelta() != null) {
//				audioTimestamp += am.getTimestampDelta();
//				am.setTimestamp(audioTimestamp);
//			}
//
//			if (am.isAACAudioSpecificConfig()) {
//				aacAudioSpecificConfig = am;
//			}
//		}
//
//		content.add(msg);
//		// if (MyLiveConfig.getInstance().isSaveFlvFile()) {
//		// 需要保存文件则持续写入文件
//		// WriteFlvHelper.writeFlv4Redis(msg, encodeFlvHeaderAndMetadata(), streamName);
//		writeFlv4Local(msg);
//		// }
//		broadCastToSubscribers(msg);
//	}
//
////	public static byte[] encodeMediaAsFlvTagAndPrevTagSize(RtmpMediaMessage msg) {
////		int tagType = msg.getMsgType();
////		byte[] data = msg.raw();
////		int dataSize = data.length;
////		int timestamp = msg.getTimestamp() & 0xffffff;
////		int timestampExtended = ((msg.getTimestamp() & 0xff000000) >> 24);
////
////		ByteBuf buffer = Unpooled.buffer();
////
////		buffer.writeByte(tagType);
////		buffer.writeMedium(dataSize);
////		buffer.writeMedium(timestamp);
////		buffer.writeByte(timestampExtended);// timestampExtended
////		buffer.writeMedium(0);// streamid
////		buffer.writeBytes(data);
////		buffer.writeInt(data.length + 11); // prevousTagSize
////
////		byte[] r = new byte[buffer.readableBytes()];
////		buffer.readBytes(r);
////
////		return r;
////	}
//
//	// 写入flv文件
////	private void writeFlv(RtmpMediaMessage msg) {
////		if (flvout == null) {
////			log.error("no flv file existed for stream : {}", streamName);
////			return;
////		}
////		try {
////			if (!flvHeadAndMetadataWritten) {
////				writeFlvHeaderAndMetadata();
////				flvHeadAndMetadataWritten = true;
////			}
////			byte[] encodeMediaAsFlv = WriteFlvHelper.encodeMediaAsFlvTagAndPrevTagSize(msg);
////
////			// 写入flv数据
////			flvout.write(encodeMediaAsFlv);
////			// 缓存同步系统
////			flvout.flush();
////			// 同步硬盘
////			flvout.getFD().sync();
////
////		} catch (IOException e) {
////			log.error("writting flv file failed , stream is :{}", streamName, e);
////		}
////	}
//	private static long startDateNum = System.currentTimeMillis();
//	private static boolean isFirst = true;
//
//	/**
//	 * 实例化flvout
//	 */
//	private void createFlvOut() {
//		// 文件名
//		String fileName = streamName.getApp() + "_" + streamName.getName() + "_"
//				+ DateUtil.format(new DateTime(startDateNum), "yyyyMMddHHmmss");
//		// 文件路径
//		String fileUrl = MyLiveConfig.getInstance().getSaveFlVFilePath() + fileName + ".flv";
//		// 保存视频文件
//		File f = new File(fileUrl);
//		try {
//			if (flvout != null) {
//				try {
//					flvout.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			flvout = new FileOutputStream(f);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 把flv写入本地磁盘
//	 * 
//	 * @param msg
//	 */
//	private void writeFlv4Local(RtmpMediaMessage msg) {
////		if (flvout == null) {
////			log.error("no flv file existed for stream : {}", streamName);
////			return;
//		if (isFirst) {
//			createFlvOut();
//			try {
//				writeFlvHeaderAndMetadata();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			isFirst = false;
//		}
//		if (System.currentTimeMillis() - startDateNum >= 60000) {
//			if (msg instanceof VideoMessage) {
//				VideoMessage vm = (VideoMessage) msg;
//				if (vm.isH264KeyFrame()) {
//					flvHeadAndMetadataWritten = false;
//					startDateNum = System.currentTimeMillis();
//					createFlvOut();
//				}
//			}
//		}
////		}
//		try {
////			if (!flvHeadAndMetadataWritten) {
////				writeFlvHeaderAndMetadata();
////				flvHeadAndMetadataWritten = true;
////			}
//			byte[] encodeMediaAsFlv = WriteFlvHelper.encodeMediaAsFlvTagAndPrevTagSize(msg);
//			flvout.write(encodeMediaAsFlv);
//			flvout.flush();
//			// 同步硬盘
//			flvout.getFD().sync();
//
//		} catch (IOException e) {
//			log.error("writting flv file failed , stream is :{}", streamName, e);
//		}
//	}
////	private static long startDateNum = System.currentTimeMillis();
////	private ByteBuf buffer = Unpooled.buffer();
////	private List<Byte> bufferList = Lists.newArrayList();
////	ByteBuffer bb = ByteBuffer.allocate(128);
////	
////	/**
////	 * @param msg
////	 */
////	private void writeFlv4Redis(RtmpMediaMessage msg) {
////		byte[] encodeMediaAsFlv = encodeMediaAsFlvTagAndPrevTagSize(msg);
////		//buffer.writeBytes(encodeMediaAsFlv, 0, encodeMediaAsFlv.length);
////		//bufferList.add(Bytes);
////		bb = bb.put(encodeMediaAsFlv);
////		if (System.currentTimeMillis() - startDateNum >= 60000) {
////			System.out.println("当前时间："+new Date(System.currentTimeMillis())+  "-------------->"  + new Date(startDateNum) );
////			startDateNum = System.currentTimeMillis();
////			//ByteBuf bufferSave = Unpooled.buffer();
////			//byte[] bufferSave = buffer.array();
////			//byte[] bufferSave = new byte[buffer.readableBytes()];
////			//buffer.readBytes(bufferSave);
////			//buffer.discardReadBytes();
////			byte[] bufferSave = bb.array();
////			bb.clear();
////			//bufferSave =buffer.duplicate();
//////			buffer = null;
//////			buffer = ByteBuf.w;
////			//buffer = Unpooled.buffer();
////			//buffer= buffer.clear();
////			//buffer.writeBytes(encodeMediaAsFlv, 0, encodeMediaAsFlv.length);
////			String saveFlVFilePath = MyLiveConfig.getInstance().getSaveFlVFilePath();
////			byte[] encodeFlvHeaderAndMetadata = encodeFlvHeaderAndMetadata();
////			StreamOutputThread objStreamOutputThread = new StreamOutputThread(encodeFlvHeaderAndMetadata, bufferSave,
////					saveFlVFilePath, streamName, DateUtil.format(new DateTime(startDateNum),"yyyyMMddHHmmss"));
////			new Thread(objStreamOutputThread).start();
////		}
//////			// 写入flv数据
//////			flvout.write(encodeMediaAsFlv);
//////			// 缓存同步系统
//////			flvout.flush();
//////			// 同步硬盘
//////			flvout.getFD().sync();
////	}
//
//	public byte[] encodeFlvHeaderAndMetadata() {
//		ByteBuf encodeMetaData = encodeMetaData();
//		ByteBuf buf = Unpooled.buffer();
//
//		RtmpMediaMessage msg = content.get(0);
//		int timestamp = msg.getTimestamp() & 0xffffff;
//		int timestampExtended = ((msg.getTimestamp() & 0xff000000) >> 24);
//
//		buf.writeBytes(flvHeader);
//		buf.writeInt(0); // previousTagSize0
//
//		int readableBytes = encodeMetaData.readableBytes();
//		buf.writeByte(0x12); // script
//		buf.writeMedium(readableBytes);
//		// make the first script tag timestamp same as the keyframe
//		buf.writeMedium(timestamp);
//		buf.writeByte(timestampExtended);
////		buf.writeInt(0); // timestamp + timestampExtended
//		buf.writeMedium(0);// streamid
//		buf.writeBytes(encodeMetaData);
//		buf.writeInt(readableBytes + 11);
//
//		byte[] result = new byte[buf.readableBytes()];
//		buf.readBytes(result);
//
//		return result;
//
//	}
//
//	/**
//	 * @throws IOException
//	 */
//	private void writeFlvHeaderAndMetadata() throws IOException {
//		byte[] encodeFlvHeaderAndMetadata = encodeFlvHeaderAndMetadata();
//		flvout.write(encodeFlvHeaderAndMetadata);
//		flvout.flush();
//
//	}
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
//
////	private void createFileStream() {
////
////	}
////
////	private synchronized void createFile() {
////
////		System.out.println("execute");
////		// 保存数据时间间隔(小时)
////		Double Isava = MyLiveConfig.getInstance().getSaveDataTimeInterval() == null ? 1
////				: MyLiveConfig.getInstance().getSaveDataTimeInterval();
////		long saveDelay = (long) (Isava * 30 * 1000);
////		// 删除文件时间间隔(小时)
////		Double Idelete = MyLiveConfig.getInstance().getSaveDataTimeInterval() == null ? 1
////				: MyLiveConfig.getInstance().getSaveDataTimeInterval();
////		long deleteDelay = (long) (Idelete * 30 * 1000);
////		// 删除过期文件(小时)
////		Double deleteDataHour = MyLiveConfig.getInstance().getDeleteDataHour() == null ? 60
////				: MyLiveConfig.getInstance().getDeleteDataHour();
////		long lHour = (long) (deleteDataHour * 300 * 1000);
////
////		String deviceId = JedisUtil.get("MKST:VIDEO:TOKEN:" + streamName.getName());
////
//////        saveTask = new TimerTask() {
//////            @Override
//////            public void run() {
//////                // TODO Auto-generated method stub
////		try {
////			if (flvout != null) {
////				flvout.flush();
////				flvout.close();
////			}
////		} catch (IOException e) {
////			log.error("close file:{} failed", flvout);
////		}
////		// 创建文件接收视频流
////		String strTime = formatDateTime(new DateTime());
////		// 文件名
////		String fileName = streamName.getApp() + "_" + streamName.getName() + "_" + strTime;
////		// 文件路径
////		String fileUrl = MyLiveConfig.getInstance().getSaveFlVFilePath() + "/" + fileName + ".flv";
////		// 保存视频文件
////		File f = new File(fileUrl);
////
////		map.put(strTime, fileUrl);
////
//////                String deviceId = JedisUtil.get("MKST:VIDEO:TOKEN:"+streamName.getName());
//////        log.info("视频文件信息写入redis,KEY为:"+"MKST:VIDEOS:"+deviceId+",map为:"+strTime+":"+fileUrl);
////
////		JedisUtil.hmset("MKST:VIDEOS:" + deviceId, map);
////
////		try {
////			FileOutputStream fos = new FileOutputStream(f);
////			flvout = fos;
////			flvHeadAndMetadataWritten = false;
////
////		} catch (IOException e) {
////			log.error("create file : {} failed", e);
////		}
//////
//////            }
//////
//////        };
//////
//////        deleteTask=new TimerTask() {
//////
//////            @Override
//////            public void run() {
//////                //删除文件代码
//////                String key = "MKST:VIDEOS:"+deviceId;
//////                HashMap<String,String> map = JedisUtil.queryHashMapByVal(key,lHour);
//////
//////                if(map!=null){
//////                    for (Map.Entry<String, String> entry : map.entrySet()) {
//////                        String strFileUrl = entry.getValue().substring(1,entry.getValue().length()-1);
//////                        File file = new File(strFileUrl);
//////                        if(file.exists()){
//////                            file.delete();
//////                            JedisUtil.hdel(key,entry.getKey());
//////                            log.info("删除过期视频文件:"+strFileUrl);
//////                        }
//////                    }
//////                }
//////            }
//////
//////        };
//////
//////        //立即执行，每隔delay秒循环执行
//////        new Timer().schedule(saveTask, 0,saveDelay);
//////        new Timer().schedule(deleteTask, 0,deleteDelay);
////	}
////
////	public static void stopTask() {
////		if (saveTask != null) {
////			saveTask.cancel();
////		}
////		if (deleteTask != null) {
////			deleteTask.cancel();
////		}
////	}
////
////	public static String formatDateTime(Date date) {
////		return null == date ? null : DatePattern.PURE_DATETIME_FORMAT.format(date);
////	}
////    private void createFileStream(String flvName) {
////        String strTime =  formatDateTime(new DateTime());
////        //文件名
////        String fileName = StringUtils.isNotEmpty(flvName)?flvName:streamName.getApp() + "_" + streamName.getName()+"_" + strTime;
////        //文件路径
////        String fileUrl = MyLiveConfig.getInstance().getSaveFlVFilePath() + "/" + fileName + ".flv";
////        //保存视频文件
////        File f = new File(fileUrl);
////
////        map.put(strTime,fileUrl);
////
////        log.info("视频文件信息写入redis,KEY为:"+"MKST:VIDEOS:"+streamName.getName()+",map为:"+strTime+":"+fileUrl);
////
////        JedisUtil.hmset("MKST:VIDEOS:"+streamName.getName(),map);
////
////        try {
////            FileOutputStream fos = new FileOutputStream(f);
////            flvout = fos;
////
////        } catch (IOException e) {
////            log.error("create file : {} failed", e);
////        }
////
////
////    }
//
////    private void createFile(){
////
////        //保存数据时间间隔(小时)
////        Double Isava = MyLiveConfig.getInstance().getSaveDataTimeInterval()==null?1:MyLiveConfig.getInstance().getSaveDataTimeInterval();
////        long saveDelay = (long) (Isava *3600*1000);
////        //删除文件时间间隔(小时)
////        Double Idelete = MyLiveConfig.getInstance().getSaveDataTimeInterval()==null?1:MyLiveConfig.getInstance().getSaveDataTimeInterval();
////        long deleteDelay = (long) (Idelete *3600*1000);
////        //删除过期文件(小时)
////        Double deleteDataHour = MyLiveConfig.getInstance().getDeleteDataHour()==null?60:MyLiveConfig.getInstance().getDeleteDataHour();
////        long lHour = (long) (deleteDataHour *3600*1000);
////
////        TimerTask saveTask=new TimerTask() {
////            @Override
////            public void run() {
////                // TODO Auto-generated method stub
////                String strTime =  formatDateTime(new DateTime());
////                String fileName = streamName.getApp() + "_" + streamName.getName()+"_" + strTime;
////
////                try {
////                    if(flvout!=null) {
////                        flvout.flush();
////                        flvout.close();
////                    }
////                } catch (IOException e) {
////                    log.error("close file:{} failed", flvout);
////                }
////                //创建文件接收视频流
////                createFileStream(fileName);
////
////            }
////
////        };
////
////        TimerTask deleteTask=new TimerTask() {
////
////            @Override
////            public void run() {
////                //删除文件代码
////                String key = "MKST:VIDEOS:"+streamName.getName();
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
////    }
//
//	/*
//	 * public static String formatDateTime(Date date) { return null == date ? null :
//	 * DatePattern.PURE_DATETIME_FORMAT.format(date); }
//	 */
//
//	public synchronized void addSubscriber(Channel channel) {
//		subscribers.add(channel);
//		log.info("subscriber : {} is added to stream :{}", channel, streamName);
//		avcDecoderConfigurationRecord.setTimestamp(content.get(0).getTimestamp());
//		log.info("avcDecoderConfigurationRecord:{}", avcDecoderConfigurationRecord);
//		channel.writeAndFlush(avcDecoderConfigurationRecord);
//		for (RtmpMessage msg : content) {
//			channel.writeAndFlush(msg);
//		}
//
//	}
//
//	public synchronized void addHttpFlvSubscriber(Channel channel) {
//		httpFLvSubscribers.add(channel);
//		log.info("http flv subscriber : {} is added to stream :{}", channel, streamName);
//
//		// 1. write flv header and metaData
//		byte[] meta = encodeFlvHeaderAndMetadata();
//		channel.writeAndFlush(Unpooled.wrappedBuffer(meta));
//
//		// 2. write avcDecoderConfigurationRecord
//		if (avcDecoderConfigurationRecord != null) {
//			avcDecoderConfigurationRecord.setTimestamp(content.get(0).getTimestamp());
//			byte[] config = WriteFlvHelper.encodeMediaAsFlvTagAndPrevTagSize(avcDecoderConfigurationRecord);
//			channel.writeAndFlush(Unpooled.wrappedBuffer(config));
//		}
//
//		if (aacAudioSpecificConfig != null) {
//			// 3. write aacAudioSpecificConfig
//			aacAudioSpecificConfig.setTimestamp(content.get(0).getTimestamp());
//			byte[] aac = WriteFlvHelper.encodeMediaAsFlvTagAndPrevTagSize(aacAudioSpecificConfig);
//			channel.writeAndFlush(Unpooled.wrappedBuffer(aac));
//		}
//		// 4. write content
//
//		for (RtmpMediaMessage msg : content) {
//			channel.writeAndFlush(Unpooled.wrappedBuffer(WriteFlvHelper.encodeMediaAsFlvTagAndPrevTagSize(msg)));
//		}
//
//	}
//
//	private synchronized void broadCastToSubscribers(RtmpMediaMessage msg) {
//		Iterator<Channel> iterator = subscribers.iterator();
//		while (iterator.hasNext()) {
//			Channel next = iterator.next();
//			if (next.isActive()) {
//				next.writeAndFlush(msg);
//			} else {
//				iterator.remove();
//			}
//		}
//
//		if (!httpFLvSubscribers.isEmpty()) {
//			byte[] encoded = WriteFlvHelper.encodeMediaAsFlvTagAndPrevTagSize(msg);
//
//			Iterator<Channel> httpIte = httpFLvSubscribers.iterator();
//			while (httpIte.hasNext()) {
//				Channel next = httpIte.next();
//				if (next.isActive()) {
//					next.writeAndFlush(Unpooled.wrappedBuffer(encoded));
//				} else {
//					log.info("http channel :{} is not active remove", next);
//					httpIte.remove();
//				}
//			}
//		}
//
//	}
//
//	public synchronized void sendEofToAllSubscriberAndClose() {
//		if (MyLiveConfig.getInstance().isSaveFlvFile() && flvout != null) {
//			try {
//				flvout.close();
//			} catch (IOException e) {
//				log.error("close file:{} failed", flvout);
//			}
//		}
//		for (Channel sc : subscribers) {
//			sc.writeAndFlush(UserControlMessageEvent.streamEOF(Constants.DEFAULT_STREAM_ID))
//					.addListener(ChannelFutureListener.CLOSE);
//
//		}
//
//		for (Channel sc : httpFLvSubscribers) {
//			sc.writeAndFlush(DefaultLastHttpContent.EMPTY_LAST_CONTENT).addListener(ChannelFutureListener.CLOSE);
//
//		}
//
//	}
//
//}
