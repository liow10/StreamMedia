package com.micro.iotclouds.live2output.rtsp.utils;
//package com.darkmi.server.util;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//import com.darkmi.server.config.ServerConfig;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.Channel;
//import io.netty.channel.socket.DatagramPacket;
//
//public class PlayUtils {
//
//	public static ExecutorService EXECUTOR; // 处理的线程池
//
//	private static int fps = 25; // 默认帧率
//	
//	public static void init(ServerConfig serverConfig) {
//		EXECUTOR = new ThreadPoolExecutor(5, serverConfig.getExecutorThreadpool(), 600, TimeUnit.SECONDS,
//				new LinkedBlockingQueue<Runnable>());
//	}
//
//	public static void playH264(File f, Channel channel) {
////	    	int time = rtpTimestamp;
//		int time = 0;
//		// 播放h264视频文件
//		BufferedInputStream in = null;
//		try {
//			in = new BufferedInputStream(new FileInputStream(f));
//			int buf_size = 64 * 1024;
//			byte[] buffer = new byte[buf_size]; // 从文件读的字节存入的地方
//			byte[] nalu; // 临时存储一个nalu单元内容
//			byte[] firstHalfNalu = null; // nalu前半段
//			byte[] secondHalfNalu = null; // nalu后半段
//			int len = 0; // 每次从文件读的字节数
//			int state = 0; // 状态机，值范围：0、1、2、3、4
//			int first = 1; // 是否是第一个起始码
//			int cross = 0; // 某个nalu是否跨buffer
//			RtpUtils rtpUtils = new RtpUtils();
//
//			while (-1 != (len = in.read(buffer, 0, buf_size))) {
////				if (isRtspAlive == 0) { // 如果rtsp连接中断，则停止发送udp
////					break;
////				}
//
//				int start = 0; // 第一个nalu的起始位置
//				int offset = 0; // 当前循环中的偏移量
//				while (offset <= len - 4) {
//					if (state == 0) { // 没有遗留状态
//						if (buffer[offset] == 0x00 && buffer[offset + 1] == 0x00 && buffer[offset + 2] == 0x00
//								&& buffer[offset + 3] == 0x01) {
//							if (cross == 1) { // 跨buffer
//								if (first == 0) { // 不是第一个起始码
//									secondHalfNalu = new byte[offset]; // 拿到后半段内容
//									System.arraycopy(buffer, start, secondHalfNalu, 0, secondHalfNalu.length);
//
//									// 拼接前半段与后半段内容, 拷贝到新的数组中
//									int naluSize = firstHalfNalu.length + secondHalfNalu.length;
//									nalu = new byte[naluSize];
//									System.arraycopy(firstHalfNalu, 0, nalu, 0, firstHalfNalu.length);
//									System.arraycopy(secondHalfNalu, 0, nalu, firstHalfNalu.length,
//											secondHalfNalu.length);
//
////	    								List<byte[]> rtpList = rtpUtils.naluToRtpPack(nalu, videoSsrc, fps, time);
//									Map<Integer, byte[]> rtpMap = rtpUtils.naluToRtpPack(nalu, videoSsrc, fps, time);
//									for (Entry<Integer, byte[]> entry : rtpMap.entrySet()) {
//										ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//										byteBuf.writeBytes(entry.getValue());
////										RtspNettyServer.rtpChannel
////												.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//										channel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//										// 存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
////										if (rtpCacheQueue.size() > 800) {
////											rtpCacheQueue.pollFirstEntry();
////										}
////										rtpCacheQueue.put(entry.getKey(), entry.getValue());
//									}
//									time += (90000 / fps); // rtp时间戳刻度递增
//								}
//
//								offset += 4;
//								state = 0;
//								first = 0;
//								start = offset; // 当前位置变成新的起始位置
//								cross = 0; // 跨buffer标志位重置成0
//							} else { // 没有跨buffer
//								if (first == 0) { // 不是第一个起始码
//									int naluSize = offset - start;
//									nalu = new byte[naluSize];
//									System.arraycopy(buffer, start, nalu, 0, naluSize);
//
//									Map<Integer, byte[]> rtpMap = rtpUtils.naluToRtpPack(nalu, videoSsrc, fps, time);
//									for (Entry<Integer, byte[]> entry : rtpMap.entrySet()) {
//										ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//										byteBuf.writeBytes(entry.getValue());
//										channel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//
//										// 存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
////										if (rtpCacheQueue.size() > 800) {
////											rtpCacheQueue.pollFirstEntry();
////										}
////										rtpCacheQueue.put(entry.getKey(), entry.getValue());
//									}
//									time += (90000 / fps); // rtp时间戳刻度递增
//								}
//
//								offset += 4;
//								state = 0;
//								first = 0;
//								start = offset; // 当前位置变成新的起始位置
//							}
//
//						} else {
//							state = 0;
//							offset++;
//						}
//					} else if (state == 1) {
//						if (buffer[offset] == 0x00 && buffer[offset + 1] == 0x00 && buffer[offset + 2] == 0x01) {
//
//							// 拿到两个起始码之间的一个nalu的数据
//							int naluSize = firstHalfNalu.length - 1;
//							nalu = new byte[naluSize];
//							System.arraycopy(firstHalfNalu, 0, nalu, 0, naluSize);
//
//							Map<Integer, byte[]> rtpMap = rtpUtils.naluToRtpPack(nalu, videoSsrc, fps, time);
//							for (Entry<Integer, byte[]> entry : rtpMap.entrySet()) {
//								ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//								byteBuf.writeBytes(entry.getValue());
//								channel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//
//								// 存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
////								if (rtpCacheQueue.size() > 800) {
////									rtpCacheQueue.pollFirstEntry();
////								}
////								rtpCacheQueue.put(entry.getKey(), entry.getValue());
//							}
//							time += (90000 / fps); // rtp时间戳刻度递增
//
//							offset += 3;
//							state = 0;
//							start = offset; // 当前位置变成新的起始位置
//						} else {
//							state = 0;
//							offset++;
//						}
//					} else if (state == 2) {
//						if (buffer[offset] == 0x00 && buffer[offset + 1] == 0x01) {
//
//							// 拿到两个起始码之间的一个nalu的数据
//							int naluSize = firstHalfNalu.length - 2;
//							nalu = new byte[naluSize];
//							System.arraycopy(firstHalfNalu, 0, nalu, 0, naluSize);
//
//							Map<Integer, byte[]> rtpMap = rtpUtils.naluToRtpPack(nalu, videoSsrc, fps, time);
//							for (Entry<Integer, byte[]> entry : rtpMap.entrySet()) {
//								ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//								byteBuf.writeBytes(entry.getValue());
//								channel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//
//								// 存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
////								if (rtpCacheQueue.size() > 800) {
////									rtpCacheQueue.pollFirstEntry();
////								}
////								rtpCacheQueue.put(entry.getKey(), entry.getValue());
//							}
//							time += (90000 / fps); // rtp时间戳刻度递增
//
//							offset += 2;
//							state = 0;
//							start = offset; // 当前位置变成新的起始位置
//						} else {
//							state = 0;
//							offset++;
//						}
//					} else if (state == 3) {
//						if (buffer[offset] == 0x01) {
//							// 拿到两个起始码之间的一个nalu的数据
//							int naluSize = firstHalfNalu.length - 3;
//							nalu = new byte[naluSize];
//							System.arraycopy(firstHalfNalu, 0, nalu, 0, naluSize);
//
//							Map<Integer, byte[]> rtpMap = rtpUtils.naluToRtpPack(nalu, videoSsrc, fps, time);
//							for (Entry<Integer, byte[]> entry : rtpMap.entrySet()) {
//								ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//								byteBuf.writeBytes(entry.getValue());
//								channel.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
//
//								// 存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
////								if (rtpCacheQueue.size() > 800) {
////									rtpCacheQueue.pollFirstEntry();
////								}
////								rtpCacheQueue.put(entry.getKey(), entry.getValue());
//							}
//							time += (90000 / fps); // rtp时间戳刻度递增
//
//							offset += 1;
//							state = 0;
//							start = offset; // 当前位置变成新的起始位置
//						} else {
//							state = 0;
//							offset++;
//						}
//					}
//				}
//
//				// 指针指向最后3位时
//				if (offset == len - 3) {
//					if (buffer[offset] == 0x00 && buffer[offset + 1] == 0x00 && buffer[offset + 2] == 0x00) {
//						state = 3;
//					} else if (buffer[offset + 1] == 0x00 && buffer[offset + 2] == 0x00) {
//						state = 2;
//					} else if (buffer[offset + 2] == 0x00) {
//						state = 1;
//					}
//					cross = 1; // 一定会跨buffer
//					firstHalfNalu = new byte[offset + 3 - start]; // 初始化前半段nalu数组，将前半段内容放进去
//					System.arraycopy(buffer, start, firstHalfNalu, 0, firstHalfNalu.length);
//				}
//
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				in.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}
