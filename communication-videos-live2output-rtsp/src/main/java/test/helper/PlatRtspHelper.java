//package test.helper;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import com.mkst.iotclouds.communication.protocol.rtsp.RtspNettyServer;
//import com.mkst.iotclouds.communication.protocol.rtsp.rtp.RtpUtils;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.socket.DatagramPacket;
//
//public class PlatRtspHelper {
//
//
//
//	private int fps = 25; // 默认帧率
//	
////  public void playH264(File f, int rtpTimestamp){
//	public void playH264(File f,int isRtspAlive) {
////  	int time = rtpTimestamp;
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
//				if (isRtspAlive == 0) { // 如果rtsp连接中断，则停止发送udp
//					break;
//				}
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
////  								List<byte[]> rtpList = rtpUtils.naluToRtpPack(nalu, videoSsrc, fps, time);
//									Map<Integer, byte[]> rtpMap = rtpUtils.naluToRtpPack(nalu, videoSsrc, fps, time);
//									for (Entry<Integer, byte[]> entry : rtpMap.entrySet()) {
//										ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
//										byteBuf.writeBytes(entry.getValue());
//										RtspNettyServer.rtpChannel
//												.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
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
//										RtspNettyServer.rtpChannel
//												.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
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
//								RtspNettyServer.rtpChannel
//										.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
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
//								RtspNettyServer.rtpChannel
//										.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
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
//								RtspNettyServer.rtpChannel
//										.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
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
////
//////  public void playH265(File f, int rtpTimestamp){
////	public void playH265(File f) {
//////  	int time = rtpTimestamp;
////		int time = 0;
////		// 播放h265视频文件
////		BufferedInputStream in = null;
////		try {
////			in = new BufferedInputStream(new FileInputStream(f));
////			int buf_size = 64 * 1024;
////			byte[] buffer = new byte[buf_size]; // 从文件读的字节存入的地方
////			byte[] nalu; // 临时存储一个nalu单元内容
////			byte[] firstHalfNalu = null; // nalu前半段
////			byte[] secondHalfNalu = null; // nalu后半段
////			int len = 0; // 每次从文件读的字节数
////			int state = 0; // 状态机，值范围：0、1、2、3、4
////			int first = 1; // 是否是第一个起始码
////			int cross = 0; // 某个nalu是否跨buffer
////			RtpUtils rtpUtils = new RtpUtils();
////
////			while (-1 != (len = in.read(buffer, 0, buf_size))) {
////				if (isRtspAlive == 0) { // 如果rtsp连接中断，则停止发送udp
////					break;
////				}
////
////				int start = 0; // 第一个nalu的起始位置
////				int offset = 0; // 当前循环中的偏移量
////				while (offset <= len - 4) {
////					if (state == 0) { // 没有遗留状态
////						if (buffer[offset] == 0x00 && buffer[offset + 1] == 0x00 && buffer[offset + 2] == 0x00
////								&& buffer[offset + 3] == 0x01) {
////							if (cross == 1) { // 跨buffer
////								if (first == 0) { // 不是第一个起始码
////									secondHalfNalu = new byte[offset]; // 拿到后半段内容
////									System.arraycopy(buffer, start, secondHalfNalu, 0, secondHalfNalu.length);
////
////									// 拼接前半段与后半段内容, 拷贝到新的数组中
////									int naluSize = firstHalfNalu.length + secondHalfNalu.length;
////									nalu = new byte[naluSize];
////									System.arraycopy(firstHalfNalu, 0, nalu, 0, firstHalfNalu.length);
////									System.arraycopy(secondHalfNalu, 0, nalu, firstHalfNalu.length,
////											secondHalfNalu.length);
////
////									Map<Integer, byte[]> rtpMap = rtpUtils.naluH265ToRtpPack(nalu, videoSsrc, fps,
////											time);
////									for (Entry<Integer, byte[]> entry : rtpMap.entrySet()) {
////										ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
////										byteBuf.writeBytes(entry.getValue());
////										RtspNettyServer.rtpChannel
////												.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
////
////										// 存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
////										if (rtpCacheQueue.size() > 800) {
////											rtpCacheQueue.pollFirstEntry();
////										}
////										rtpCacheQueue.put(entry.getKey(), entry.getValue());
////									}
////									time += (90000 / fps); // rtp时间戳刻度递增
////								}
////
////								offset += 4;
////								state = 0;
////								first = 0;
////								start = offset; // 当前位置变成新的起始位置
////								cross = 0; // 跨buffer标志位重置成0
////							} else { // 没有跨buffer
////								if (first == 0) { // 不是第一个起始码
////									int naluSize = offset - start;
////									nalu = new byte[naluSize];
////									System.arraycopy(buffer, start, nalu, 0, naluSize);
////
////									Map<Integer, byte[]> rtpMap = rtpUtils.naluH265ToRtpPack(nalu, videoSsrc, fps,
////											time);
////									for (Entry<Integer, byte[]> entry : rtpMap.entrySet()) {
////										ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
////										byteBuf.writeBytes(entry.getValue());
////										RtspNettyServer.rtpChannel
////												.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
////
////										// 存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
////										if (rtpCacheQueue.size() > 800) {
////											rtpCacheQueue.pollFirstEntry();
////										}
////										rtpCacheQueue.put(entry.getKey(), entry.getValue());
////									}
////									time += (90000 / fps); // rtp时间戳刻度递增
////								}
////
////								offset += 4;
////								state = 0;
////								first = 0;
////								start = offset; // 当前位置变成新的起始位置
////							}
////
////						} else {
////							state = 0;
////							offset++;
////						}
////					} else if (state == 1) {
////						if (buffer[offset] == 0x00 && buffer[offset + 1] == 0x00 && buffer[offset + 2] == 0x01) {
////
////							// 拿到两个起始码之间的一个nalu的数据
////							int naluSize = firstHalfNalu.length - 1;
////							nalu = new byte[naluSize];
////							System.arraycopy(firstHalfNalu, 0, nalu, 0, naluSize);
////
////							Map<Integer, byte[]> rtpMap = rtpUtils.naluH265ToRtpPack(nalu, videoSsrc, fps, time);
////							for (Entry<Integer, byte[]> entry : rtpMap.entrySet()) {
////								ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
////								byteBuf.writeBytes(entry.getValue());
////								RtspNettyServer.rtpChannel
////										.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
////
////								// 存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
////								if (rtpCacheQueue.size() > 800) {
////									rtpCacheQueue.pollFirstEntry();
////								}
////								rtpCacheQueue.put(entry.getKey(), entry.getValue());
////							}
////							time += (90000 / fps); // rtp时间戳刻度递增
////
////							offset += 3;
////							state = 0;
////							start = offset; // 当前位置变成新的起始位置
////						} else {
////							state = 0;
////							offset++;
////						}
////					} else if (state == 2) {
////						if (buffer[offset] == 0x00 && buffer[offset + 1] == 0x01) {
////
////							// 拿到两个起始码之间的一个nalu的数据
////							int naluSize = firstHalfNalu.length - 2;
////							nalu = new byte[naluSize];
////							System.arraycopy(firstHalfNalu, 0, nalu, 0, naluSize);
////
////							Map<Integer, byte[]> rtpMap = rtpUtils.naluH265ToRtpPack(nalu, videoSsrc, fps, time);
////							for (Entry<Integer, byte[]> entry : rtpMap.entrySet()) {
////								ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
////								byteBuf.writeBytes(entry.getValue());
////								RtspNettyServer.rtpChannel
////										.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
////
//////								//存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
////								if (rtpCacheQueue.size() > 800) {
////									rtpCacheQueue.pollFirstEntry();
////								}
////								rtpCacheQueue.put(entry.getKey(), entry.getValue());
////							}
////							time += (90000 / fps); // rtp时间戳刻度递增
////
////							offset += 2;
////							state = 0;
////							start = offset; // 当前位置变成新的起始位置
////						} else {
////							state = 0;
////							offset++;
////						}
////					} else if (state == 3) {
////						if (buffer[offset] == 0x01) {
////							// 拿到两个起始码之间的一个nalu的数据
////							int naluSize = firstHalfNalu.length - 3;
////							nalu = new byte[naluSize];
////							System.arraycopy(firstHalfNalu, 0, nalu, 0, naluSize);
////
////							Map<Integer, byte[]> rtpMap = rtpUtils.naluH265ToRtpPack(nalu, videoSsrc, fps, time);
////							for (Entry<Integer, byte[]> entry : rtpMap.entrySet()) {
////								ByteBuf byteBuf = Unpooled.buffer(entry.getValue().length);
////								byteBuf.writeBytes(entry.getValue());
////								RtspNettyServer.rtpChannel
////										.writeAndFlush(new DatagramPacket(byteBuf, this.dstVideoRtpAddr));
////
////								// 存入历史记录中，用于NACK重传。超过800时，则先删除旧的，再插入
////								if (rtpCacheQueue.size() > 800) {
////									rtpCacheQueue.pollFirstEntry();
////								}
////								rtpCacheQueue.put(entry.getKey(), entry.getValue());
////							}
////							time += (90000 / fps); // rtp时间戳刻度递增
////
////							offset += 1;
////							state = 0;
////							start = offset; // 当前位置变成新的起始位置
////						} else {
////							state = 0;
////							offset++;
////						}
////					}
////				}
////
////				// 指针指向最后3位时
////				if (offset == len - 3) {
////					if (buffer[offset] == 0x00 && buffer[offset + 1] == 0x00 && buffer[offset + 2] == 0x00) {
////						state = 3;
////					} else if (buffer[offset + 1] == 0x00 && buffer[offset + 2] == 0x00) {
////						state = 2;
////					} else if (buffer[offset + 2] == 0x00) {
////						state = 1;
////					}
////					cross = 1; // 一定会跨buffer
////					firstHalfNalu = new byte[offset + 3 - start]; // 初始化前半段nalu数组，将前半段内容放进去
////					System.arraycopy(buffer, start, firstHalfNalu, 0, firstHalfNalu.length);
////				}
////
////			}
////
////		} catch (Exception e) {
////			e.printStackTrace();
////		} finally {
////			try {
////				in.close();
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
////		}
////	}
////
//////  public void playAac(File f, int rtpTimestamp){
////	public void playAac(File f) {
//////  	int time = rtpTimestamp;
////		int time = 0;
////		BufferedInputStream in = null;
////		try {
////			in = new BufferedInputStream(new FileInputStream(f));
////			int seqNum = 1; // rtp的seqnum
////			int len = 0; // 每次从文件读的实际字节数
////			int aacDataLen = 0; // aac data长度
////			int sampling = 16000; // 采样率，默认值16000
////			byte[] adtsHeaderBuffer = new byte[7]; // 临时存储adts头
////			byte[] aacDataBuffer; // 临时存储aac data
////			RtpUtils rtpUtils = new RtpUtils();
////
////			int isHeader = 1; // 标志位，表示当前读取的是adts头还是aac data
////			len = in.read(adtsHeaderBuffer, 0, 7); // 刚开始读取adts头
////			while (len != -1) {
////				if (isRtspAlive == 0) { // 如果rtsp连接中断，则停止发送udp
////					break;
////				}
////
////				if (isHeader == 1) { // adts头，下一个就是aac data
////					aacDataLen = ((adtsHeaderBuffer[3] & 0x03) << 11) // 获取aac data长度
////							+ (adtsHeaderBuffer[4] << 3) + ((adtsHeaderBuffer[5] & 0xE0) >> 5) - 7;
////					byte samp = (byte) (adtsHeaderBuffer[2] & 0x3C); // 获取采样率
////					sampling = RtpUtils.getSampling(samp);
////					isHeader = 0;
////				} else { // aac data，下一个就是adts头
////					aacDataBuffer = new byte[aacDataLen];
////					len = in.read(aacDataBuffer, 0, aacDataLen); // 读取aac data
////					byte[] rptPackage = rtpUtils.aacToRtpPack(aacDataBuffer, seqNum, audioSsrc, time);
////					ByteBuf byteBuf = Unpooled.buffer(rptPackage.length);
////					byteBuf.writeBytes(rptPackage);
////					RtspNettyServer.rtpChannel.writeAndFlush(new DatagramPacket(byteBuf, this.dstAudioRtpAddr));
////					time += 1024; // rtp时间戳刻度递增, 音频固定递增1024
////					seqNum++;
////
////					Thread.sleep(1024 * 1000 / sampling); // 延时发送帧
////					len = in.read(adtsHeaderBuffer, 0, 7);
////					isHeader = 1;
////				}
////			}
////		} catch (IOException | InterruptedException e) {
////			e.printStackTrace();
////		} finally {
////			try {
////				if (in != null) {
////					in.close();
////				}
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
////		}
////	}
//}
