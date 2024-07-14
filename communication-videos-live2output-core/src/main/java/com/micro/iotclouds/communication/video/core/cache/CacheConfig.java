//package com.micro.iotclouds.communication.video.core.cache;
//
//import java.io.UnsupportedEncodingException;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheWriter;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.listener.PatternTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
//
///**
// * 缓存配置
// * 
// * @author Joe
// * @date 2019年9月30日
// */
//@Configuration
//@SuppressWarnings("rawtypes")
//public class CacheConfig {
//	@Value("springext.cache.redis.channel")
//	private String topicName;
//
//	public TwoLevelCacheManager cacheManager(StringRedisTemplate redisTemplate) {
//		RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(redisTemplate.getConnectionFactory());
//		SerializationPair pair = SerializationPair
//				.fromSerializer(new JdkSerializationRedisSerializer(this.getClass().getClassLoader()));
//		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
//		return new TwoLevelCacheManager(redisTemplate, writer, config);
//	}
//
//	@Bean
//	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
//			MessageListenerAdapter listenerAdapter) {
//		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		container.addMessageListener(listenerAdapter, new PatternTopic(topicName));
//		return container;
//	}
//
//	@Bean
//	MessageListenerAdapter listenerAdapter(final TwoLevelCacheManager cacheManager) {
//		return new MessageListenerAdapter(new MessageListener() {
//			@Override
//			public void onMessage(Message message, byte[] pattern) {
//				try {
//					String cacheName = new String(message.getBody(), "UTF-8");
//					cacheManager.receiver(cacheName);
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//}