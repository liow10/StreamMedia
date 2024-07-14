package com.micro.iotclouds.communication.video.core.messages;

import com.micro.iotclouds.communication.video.core.domain.Constants;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author longyubo 2019年12月16日 下午3:40:03
 **/
@Data
@AllArgsConstructor

@NoArgsConstructor
public class SetChunkSize extends RtmpControlMessage {

	int chunkSize;

	@Override
	public ByteBuf encodePayload() {
		return Unpooled.buffer(4).writeInt(chunkSize);

	}

	@Override
	public int getMsgType() {
		return Constants.MSG_SET_CHUNK_SIZE;
	}
}
