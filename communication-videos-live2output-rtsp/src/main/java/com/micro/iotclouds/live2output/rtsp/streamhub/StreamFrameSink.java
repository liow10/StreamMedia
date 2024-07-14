package com.micro.iotclouds.live2output.rtsp.streamhub;

public interface StreamFrameSink {
    boolean WriteFrame(StreamFrame frame);
    void CloseThisClient();
}
