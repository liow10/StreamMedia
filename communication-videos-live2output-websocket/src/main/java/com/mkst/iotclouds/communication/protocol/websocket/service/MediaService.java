package com.mkst.iotclouds.communication.protocol.websocket.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.micro.iotclouds.communication.video.core.manager.StreamManager;
import com.mkst.iotclouds.communication.protocol.websocket.entity.Camera;
import com.mkst.iotclouds.communication.protocol.websocket.thread.MediaConvert;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.crypto.digest.MD5;
import io.netty.channel.ChannelHandlerContext;

/**
 * 媒体服务
 *
 * @author ZJ
 *
 */
@Service
public class MediaService {

	// 缓存流转换线程
	public static ConcurrentHashMap<String, MediaConvert> cameras = new ConcurrentHashMap();

	/**
	 *
	 * @param url 源地址
	 */
	public void playForHttp(Camera camera, ChannelHandlerContext ctx, boolean autoClose,StreamManager streamManager) {

		// 区分不同媒体
		String mediaKey = MD5.create().digestHex(camera.getUrl());

		if (cameras.containsKey(mediaKey)) {
			MediaConvert mediaConvert = cameras.get(mediaKey);
			cameras.put(mediaKey, mediaConvert);
			mediaConvert.addHttpClient(ctx);
		} else {
			MediaConvert mediaConvert = new MediaConvert(camera, autoClose,streamManager);
			cameras.put(mediaKey, mediaConvert);
			ThreadUtil.execute(mediaConvert);
			mediaConvert.addHttpClient(ctx);
		}
	}

	public void playForWs(Camera camera, ChannelHandlerContext ctx, boolean autoClose,StreamManager streamManager) {

		// 区分不同媒体
		String mediaKey = MD5.create().digestHex(camera.getUrl());

		if (cameras.containsKey(mediaKey)) {
			MediaConvert mediaConvert = cameras.get(mediaKey);
			cameras.put(mediaKey, mediaConvert);
			mediaConvert.addWsClient(ctx);
		} else {
			MediaConvert mediaConvert = new MediaConvert(camera, autoClose,streamManager);
			cameras.put(mediaKey, mediaConvert);
			ThreadUtil.execute(mediaConvert);
			mediaConvert.addWsClient(ctx);
		}
	}

	/**
	 * api拉流
	 * @param camera
	 */
	public void playForApi(Camera camera,StreamManager streamManager) {

		// 区分不同媒体
		String mediaKey = MD5.create().digestHex(camera.getUrl());

		if (!cameras.containsKey(mediaKey)) {
			MediaConvert mediaConvert = new MediaConvert(camera, false,streamManager);
			cameras.put(mediaKey, mediaConvert);
			ThreadUtil.execute(mediaConvert);
		}
	}

	/**
	 * 关闭流
	 * @param camera
	 */
	public void closeForApi(Camera camera) {

		// 区分不同媒体
		String mediaKey = MD5.create().digestHex(camera.getUrl());

		if (cameras.containsKey(mediaKey)) {
			MediaConvert mediaConvert = cameras.get(mediaKey);
			mediaConvert.setRuning(false);
			cameras.remove(mediaKey);
		}
	}


}
