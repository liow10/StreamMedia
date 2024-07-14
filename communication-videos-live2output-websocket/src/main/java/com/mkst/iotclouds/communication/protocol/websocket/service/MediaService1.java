package com.mkst.iotclouds.communication.protocol.websocket.service;
//package com.mkst.iotclouds.communication.protocol.websocket.service;
//
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.springframework.stereotype.Service;
//
//import com.micro.iotclouds.communication.video.core.manager.StreamManager;
//import com.mkst.iotclouds.communication.protocol.websocket.entity.Camera;
//import com.mkst.iotclouds.communication.protocol.websocket.thread.MediaConvert;
//
//import cn.hutool.core.thread.ThreadUtil;
//import cn.hutool.crypto.digest.MD5;
//import io.netty.channel.ChannelHandlerContext;
//
///**
// * 媒体服务
// * 
// * @author ZJ
// *
// */
//@Service
//public class MediaService {
//
//	// 缓存流转换线程
//	public static ConcurrentHashMap<String, MediaConvert> cameras = new ConcurrentHashMap<>();
//
//	/**
//	 * 
//	 * @param url 源地址
//	 */
//	public void playForHttp(Camera camera, ChannelHandlerContext ctx,StreamManager streamManager) {
//
//		// 区分不同媒体
////		String mediaKey = MD5.create().digestHex(camera.getUrl());
////
////		if (cameras.containsKey(mediaKey)) {
////			MediaConvert mediaConvert = cameras.get(mediaKey);
////			cameras.put(mediaKey, mediaConvert);
////			mediaConvert.addHttpClient(ctx);
////		} else {
////			MediaConvert mediaConvert = new MediaConvert(camera,streamManager,ctx);
////			cameras.put(mediaKey, mediaConvert);
////			ThreadUtil.execute(mediaConvert);
////			mediaConvert.addHttpClient(ctx);
////		}
//	}
//
//	public void playForWs(Camera camera, ChannelHandlerContext ctx,StreamManager streamManager) throws InterruptedException {
//
//		// 区分不同媒体
// 		String mediaKey = MD5.create().digestHex(camera.getUrl());
//
//		if (cameras.containsKey(mediaKey)) {
//			MediaConvert mediaConvert = cameras.get(mediaKey);
//			cameras.put(mediaKey, mediaConvert);
//			mediaConvert.addWsClient(ctx);
//		} else {
//			MediaConvert mediaConvert = new MediaConvert(camera,streamManager,ctx);
//			cameras.put(mediaKey, mediaConvert);
//			ThreadUtil.execute(mediaConvert);
//		//	Thread.sleep(500);
//		//	mediaConvert.run();
//			mediaConvert.addWsClient(ctx);
//		}
//	}
//
//}