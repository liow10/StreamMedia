package com.micro.iotclouds.communication.video.core.domain;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;

public class StreamFrame extends DefaultByteBufHolder {
	
	public int dwTime;
	public boolean bIsKey;
	public int streamType;

	public StreamFrame(ByteBuf buf) {
		super(buf);
	}
}
