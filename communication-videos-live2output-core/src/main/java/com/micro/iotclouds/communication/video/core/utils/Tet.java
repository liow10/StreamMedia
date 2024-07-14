package com.micro.iotclouds.communication.video.core.utils;

import java.io.File;
import java.io.IOException;

public class Tet {

	static String[] files = new String[] {"D:\\resources\\iot\\rtmp\\flv\\test\\live_ppp20220112122400.flv","D:\\resources\\iot\\rtmp\\flv\\test\\live_ppp20220112122401.flv"};
	public static void main(String[] args) {
		FlvMerge objFlvMerge = new FlvMerge();
		File f1 = new File(files[0]);
		File f2 = new File(files[1]);
		File[] fs = new File[] {f1,f2};
		
		try {
			objFlvMerge.merge(fs, new File("D:\\resources\\iot\\rtmp\\flv\\test\\live\\1234567.flv"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			objFlvMerge.merge(f1, f2, new File("D:\\resources\\iot\\rtmp\\flv\\test\\live_123456.flv"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
