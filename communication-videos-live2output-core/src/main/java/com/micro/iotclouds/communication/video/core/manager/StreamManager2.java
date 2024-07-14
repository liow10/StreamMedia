//package com.micro.iotclouds.communication.video.core.manager;
//
///**
//@author longyubo
//2020年1月2日 下午3:32:04
//**/
//import java.util.concurrent.ConcurrentHashMap;
//
//import com.micro.iotclouds.communication.video.core.domain.MyLiveConfig;
//import com.micro.iotclouds.communication.video.core.entities.Stream2;
//import com.micro.iotclouds.communication.video.core.entities.StreamName;
//import com.micro.iotclouds.communication.video.core.utils.JedisUtil;
//import com.micro.iotclouds.communication.video.core.utils.SerializeObjectTool;
//
///**
// * all stream info store here,including publisher and subscriber and their live
// * type video & audio
// * 
// * @author longyubo
// *
// */
//public class StreamManager2 {
//
//	private ConcurrentHashMap<StreamName, Stream2> streams = new ConcurrentHashMap<>();
//
//	private static int LIVE_STREAM = 0;
//
//	public void newStream(StreamName streamName, Stream2 s) {
//		streams.put(streamName, s);
//		if (MyLiveConfig.getInstance().isHaproxy()) {
//			JedisUtil.set(SerializeObjectTool.serialize2String(streamName), SerializeObjectTool.serialize2String(s));
//		}
//	}
//
//	public boolean exist(StreamName streamName) {
//		return streams.containsKey(streamName);
//	}
//
//	public Stream2 getStream(StreamName streamName) {
//		if (MyLiveConfig.getInstance().isHaproxy()) {
//			Stream2 objStream2 = streams.get(streamName);
//			if (objStream2 == null) {
//				objStream2 = (Stream2) SerializeObjectTool.unserizlize(JedisUtil.get(SerializeObjectTool.serialize2String(streamName)).getBytes());
//			}
//			return objStream2;
//		} else {
//			return streams.get(streamName);
//		}
//	}
//
//	public void remove(StreamName streamName) {
//		if (MyLiveConfig.getInstance().isHaproxy()) {
//			JedisUtil.del(SerializeObjectTool.serialize2String(streamName));
//		}
//		streams.remove(streamName);
//	}
//
//}
