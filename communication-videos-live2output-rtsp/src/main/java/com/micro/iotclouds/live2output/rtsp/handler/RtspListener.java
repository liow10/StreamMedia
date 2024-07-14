package com.micro.iotclouds.live2output.rtsp.handler;

import com.micro.iotclouds.communication.video.core.entities.Stream2;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public interface RtspListener {

	public void onRtspRequest(HttpRequest request, Stream2 stream);

	public void onRtspResponse(HttpResponse response);
}
