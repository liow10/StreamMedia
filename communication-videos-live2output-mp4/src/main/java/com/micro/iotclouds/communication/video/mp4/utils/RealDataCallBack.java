//package com.micro.iotclouds.communication.video.mp4.utils;
//
//import java.io.IOException;
//import java.io.PipedInputStream;
//import java.io.PipedOutputStream;
//
//import com.junction.sdk.HCNetSDK.FRealDataCallBack_V30;
//import com.sun.jna.NativeLong;
//import com.sun.jna.Pointer;
//import com.sun.jna.ptr.ByteByReference;
//
///**
// * @Title RealDataCallBack.java
// * @description 实时预览回调函数
// * @time 2020年3月17日 下午2:45:08
// * @author banmajio
// **/
//public class RealDataCallBack implements FRealDataCallBack_V30 {
//
//	private PipedOutputStream outputStream;// 管道输出流
//	private PipedOutputStream picOutputStream;// 抓图管道流
//
//	public static boolean playbackcapture = false;// 开始抓图标志 true：开始抓图 false：结束抓图
//
//	public RealDataCallBack(PipedOutputStream outputStream) {
//		this.outputStream = outputStream;
//	}
//
//	public void setPicOutputStream(PipedOutputStream picOutputStream) {
//		this.picOutputStream = picOutputStream;
//	}
//
//	@Override
//	public void invoke(NativeLong lRealHandle, int dwDataType, ByteByReference pBuffer, int dwBufSize, Pointer pUser) {
//		try {
//			if (playbackcapture) {
//				// 将数据同时写入抓图管道流中
//				picOutputStream.write(pBuffer.getPointer().getByteArray(0, dwBufSize));
//			}
//			outputStream.write(pBuffer.getPointer().getByteArray(0, dwBufSize));
//		} catch (IOException e) {
////			logger.error(e.getMessage());
//		}
//	}
//}