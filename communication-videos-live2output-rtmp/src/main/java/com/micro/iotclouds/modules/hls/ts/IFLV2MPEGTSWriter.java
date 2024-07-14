package com.micro.iotclouds.modules.hls.ts;

/**
 * FLV TO MPEGTS TS Writer Interface
 * @author pengliren
 * flv转mpegts流写入接口
 */
public interface IFLV2MPEGTSWriter {

	public void nextBlock(long ts, byte[] block);
}
