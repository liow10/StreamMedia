package com.mkst.iotclouds.communication.protocol.websocket.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * camera相机
 * @author ZJ
 *
 */
@Getter
@Setter
public class Camera implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5575352151805386129L;
	
//	private String id;
	private String url;
	private String streamName;
	private String appName;
}