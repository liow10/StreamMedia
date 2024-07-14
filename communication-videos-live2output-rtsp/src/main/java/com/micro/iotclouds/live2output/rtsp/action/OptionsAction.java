package com.micro.iotclouds.live2output.rtsp.action;

import java.util.concurrent.Callable;

import com.micro.iotclouds.live2output.rtsp.config.ServerConfig;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.rtsp.RtspHeaderNames;
import io.netty.handler.codec.rtsp.RtspHeaderValues;
import io.netty.handler.codec.rtsp.RtspResponseStatuses;
import io.netty.handler.codec.rtsp.RtspVersions;

public class OptionsAction implements Callable<FullHttpResponse> {
  private HttpRequest request = null;
  private ServerConfig config;

  public OptionsAction(ServerConfig serverConfig, HttpRequest request) {
    this.request = request;
    this.config = serverConfig;
  }

  @Override
  public FullHttpResponse call() throws Exception {
	  
//	  FullHttpResponse response = null;
//    response = new DefaultHttpResponse(RtspVersions.RTSP_1_0, RtspResponseStatuses.OK);
	FullHttpResponse response = new DefaultFullHttpResponse(RtspVersions.RTSP_1_0, RtspResponseStatuses.OK);
	response.headers().add(RtspHeaderNames.PUBLIC, "DESCRIBE,SETUP,PLAY,PAUSE,TEARDOWN,GET_PARAMETER,OPTION");
	response.headers().add(RtspHeaderNames.CSEQ, this.request.headers().get(RtspHeaderNames.CSEQ));
	response.headers().add(RtspHeaderNames.CONNECTION, RtspHeaderValues.KEEP_ALIVE);
    response.headers().add(RtspHeaderNames.SERVER, "RtspServer");
    
    
    return response;
  }
}
