package com.micro.iotclouds.communication.video.core.manager;

/**
@author longyubo
2020年1月2日 下午3:32:04
**/
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.micro.iotclouds.communication.video.core.domain.MyLiveConfig;
import com.micro.iotclouds.communication.video.core.entities.Stream2;
import com.micro.iotclouds.communication.video.core.entities.StreamName;
import com.micro.iotclouds.communication.video.core.utils.JedisUtil;
import com.micro.iotclouds.communication.video.core.utils.SerializeObjectTool;
import com.micro.iotclouds.communication.video.core.utils.SpringUtils;

/**
 * all stream info store here,including publisher and subscriber and their live
 * type video & audio
 *
 * @author longyubo
 *
 */
public class StreamManager {

	private ConcurrentHashMap<StreamName, Stream2> streams = new ConcurrentHashMap();

	/** 注入SpringBoot自动配置好的redisTemplate **/
	// 注意：spring boot帮我们注入的redisTemplate类，泛型里面只能写 <String, String>、<Object,
	// Object>，不能一个String一个Object！
//    @Autowired
//    private RedisTemplate<Object,Object> redisTemplate = SpringUtils.getBean(RedisTemplate.class);

	private static int LIVE_STREAM = 0;

	public void newStream(StreamName streamName, Stream2 s) {
		if (MyLiveConfig.getInstance().isHaproxy()) {
			streams.put(streamName, s);
//			Jedis jedis = JedisUtil.getJedis();
//			jedis.set(SerializeObjectTool.serialize(streamName), SerializeObjectTool.serialize(s));
		} else {
			streams.put(streamName, s);
		}
	}

	public boolean exist(StreamName streamName) {
		return streams.containsKey(streamName);
	}

	public Stream2 getStream(StreamName streamName) {
		if (MyLiveConfig.getInstance().isHaproxy()) {
			Stream2 objStream2 = streams.get(streamName);
			return objStream2;
		} else {
			return streams.get(streamName);
		}
	}

	public void remove(StreamName streamName) {
		if (MyLiveConfig.getInstance().isHaproxy()) {
			JedisUtil.del(SerializeObjectTool.serialize2String(streamName));
		}
		streams.remove(streamName);
	}

}
