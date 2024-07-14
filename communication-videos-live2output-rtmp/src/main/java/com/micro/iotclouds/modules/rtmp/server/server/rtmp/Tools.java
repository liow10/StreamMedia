package com.micro.iotclouds.modules.rtmp.server.server.rtmp;

import java.util.Random;

import cn.hutool.core.lang.UUID;

/**
@author longyubo
2019年12月10日 下午8:49:19
**/
public class Tools {
	private static Random random = new Random();;
	public static byte[] generateRandomData(int size) {
		byte[] bytes = new byte[size];
		random.nextBytes(bytes);
		return bytes;
	}
	public static String uuid() {
		return UUID.randomUUID().toString();
	}
}

