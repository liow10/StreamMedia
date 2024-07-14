/**
 * 
 */
package com.micro.iotclouds.live2output.rtsp.helper;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.micro.iotclouds.communication.video.core.entities.Stream2;
import com.micro.iotclouds.communication.video.core.messages.RtmpMediaMessage;
import com.micro.iotclouds.live2output.rtsp.start.RtspServerStart;
import com.micro.iotclouds.live2output.rtsp.streamhub.StreamFrame;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

/**
 * rtsp播放逻辑辅助类
 * 
 * @author Joe
 *
 */
public class RtspPlayHelper {

	public static ExecutorService EXECUTOR; // 处理的线程池

	private int first = 0;

	private Channel rtpchn;

	private Channel rtcpchn;

	private Channel channel;

	private static int localrtpport = 54000;

	static {
		init();
	}

	/**
	 * @param channel
	 */
	public RtspPlayHelper(Channel channel) {
		this.channel = channel;
		creatUdp();
	}

	public static void init() {
		EXECUTOR = new ThreadPoolExecutor(5, 5, 600, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	}

	protected  void playH264(Stream2 stream, Channel channel, InetSocketAddress dstaddr2) {
		List<RtmpMediaMessage> lstRtmpMediaMessage = stream.getContent();
//		for (RtmpMediaMessage m : lstRtmpMediaMessage) {
//			channel.writeAndFlush(m.raw());
//		}
		// TODO Auto-generated method stub
		ByteBuf h24frame = Unpooled.buffer();
		//for (RtmpMediaMessage rr : lst) {
			h24frame.writeBytes(lstRtmpMediaMessage.get(0).raw());
		//}
		this.dstaddr = dstaddr2;
		StreamFrame frame = new StreamFrame(h24frame);
		WriteFrame(frame);
	}

	/**
	 * 打开udp端口
	 */
	private void creatUdp() {
		rtpchn = RtspServerStart.createUdp(((InetSocketAddress) channel.localAddress()).getHostString(), localrtpport);
		rtcpchn = RtspServerStart.createUdp(((InetSocketAddress) channel.localAddress()).getHostString(),
				localrtpport + 1);
		localrtpport += 2;
	}

	private int timestamp = 0;
	private int sequence = 0;
	private int rtpssrc = 0x13;
	private InetSocketAddress dstaddr = null;

	private void writeRtpHeader(ByteBuf header, byte bmarker) {
		header.writeByte(0x80);
		header.writeByte(96 | (bmarker << 7));
		header.writeShort(this.sequence++);
		header.writeInt(this.timestamp);
		header.writeInt(this.rtpssrc);
	}

	String app = "live";
	String streamName;

	public boolean WriteFrame(StreamFrame frame) {
		final int MAX_RTP_PKT_LENGTH = 1400;
		int nretains = 0;
		if (this.channel.isActive() && null != this.dstaddr && rtpchn.isActive()) {
//			streamName = strDeviceid;
//			StreamName sn = new StreamName(app, streamName);
//			// log.info("http stream :{} requested", sn);
//			Stream2 stream = streamManager.getStream(sn);
//			List<RtmpMediaMessage> lst = stream.getContent();
//			ByteBuf h24frame = Unpooled.buffer();
//			for (RtmpMediaMessage r : lst) {
//				h24frame.writeBytes(r.raw());
//			}

			ByteBuf h24frame = frame.content();

			ByteBuf buf = h24frame.slice();
			int dwtotallen = buf.readableBytes();
			int dwindex = 0;
			byte bmarker = 0;

			while (buf.isReadable()) {
				final int nalsize = buf.readInt();
				dwindex += 4;
				if (dwindex + nalsize == dwtotallen) {
					bmarker = 1;
				}

				if ((nalsize - 1) <= MAX_RTP_PKT_LENGTH) {
					// new datagrampacket header 12
					// body
					ByteBuf header = channel.alloc().directBuffer(12);
					writeRtpHeader(header, bmarker);

					ByteBuf data = h24frame.retainedSlice(dwindex, nalsize);
					CompositeByteBuf combuf = channel.alloc().compositeBuffer(2).addComponent(true, header)
							.addComponent(true, data);
					System.out.println(combuf.readableBytes());

					rtpchn.write(new DatagramPacket(combuf, this.dstaddr));
					nretains += 1;

				} else {
					final byte bynaltype = buf.getByte(dwindex);
					byte blastfu = 0;
					int npacket = (nalsize - 1 + MAX_RTP_PKT_LENGTH - 1) / MAX_RTP_PKT_LENGTH;

					for (int inxpack = 0; inxpack < npacket; inxpack++) {
						int npacketsize = MAX_RTP_PKT_LENGTH;
						int packetinx = dwindex + 1 + inxpack * MAX_RTP_PKT_LENGTH;
						if (inxpack == npacket - 1) {
							npacketsize = (nalsize - 1) - inxpack * MAX_RTP_PKT_LENGTH; // 2801 nallen
							blastfu = 1;
						}
						ByteBuf header = channel.alloc().directBuffer(14);
						writeRtpHeader(header, (byte) (bmarker & blastfu));

						header.writeByte((bynaltype & 0xe0) | 28);
						if (inxpack == 0) {
							header.writeByte((bynaltype & 0x1f) | 0x80);// S
						}
						// last pack
						else if (inxpack == npacket - 1) {
							header.writeByte((bynaltype & 0x1f) | 0x40);// E
						} else {
							header.writeByte(bynaltype & 0x1f);
						}
						ByteBuf data = h24frame.retainedSlice(packetinx, npacketsize);
						CompositeByteBuf combuf = channel.alloc().compositeBuffer(2).addComponent(true, header)
								.addComponent(true, data);

						rtpchn.write(new DatagramPacket(combuf, this.dstaddr));

						nretains += 1;
					}
				}
				dwindex += nalsize;
				// except the type byte
				buf.skipBytes(nalsize);
			}
			rtpchn.flush();
			this.timestamp += 30 * 90;
		}
		return true;
	}
}
