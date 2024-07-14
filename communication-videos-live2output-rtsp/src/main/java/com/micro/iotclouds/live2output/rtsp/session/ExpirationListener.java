package com.micro.iotclouds.live2output.rtsp.session;

/**
 * A listener for expired object events.
 */
public interface ExpirationListener<E> {
  void expired(E expiredObject);
}
