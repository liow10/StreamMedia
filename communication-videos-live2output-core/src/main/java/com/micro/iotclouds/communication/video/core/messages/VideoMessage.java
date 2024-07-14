package com.micro.iotclouds.communication.video.core.messages;
/**
@author longyubo
2019年12月16日 下午5:38:21
**/

import com.micro.iotclouds.communication.video.core.domain.Constants;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class VideoMessage extends RtmpMediaMessage {
	byte[] videoData;

	@Override
	public ByteBuf encodePayload() {

		return Unpooled.wrappedBuffer(videoData);
	}

	@Override
	public int getOutboundCsid() {

		return 12;
	}

	@Override
	public int getMsgType() {
		return Constants.MSG_TYPE_VIDEO_MESSAGE;
	}

	public boolean isH264KeyFrame() {
		return videoData.length > 1 && videoData[0] == 0x17;
	}


	public boolean isVideoFrameData(byte[] bs) {
		System.out.println(bs[0]);
		return videoData.length > 1 && bs[0] == 0x09;
	}

	
	public boolean isAVCDecoderConfigurationRecord() {
		return isH264KeyFrame() && videoData.length > 2 && videoData[1] == 0x00;
	}

	@Override
	public byte[] raw() {
		return videoData;
	}

}
