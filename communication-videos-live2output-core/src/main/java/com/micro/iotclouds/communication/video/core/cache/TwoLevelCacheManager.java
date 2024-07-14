//package com.micro.iotclouds.communication.video.core.cache;
//
//import org.springframework.cache.Cache;
//import org.springframework.data.redis.cache.RedisCache;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCacheWriter;
//import org.springframework.data.redis.core.RedisTemplate;
//
///**
// * 基于ConcurrentHashMap和Redis的两级缓存管理器
// * 
// * @author Joe
// * @date 2019年9月30日
// */
//@SuppressWarnings("rawtypes")
//public class TwoLevelCacheManager extends RedisCacheManager {
////	@Value("springext.cache.redis.channel")
//	private String topicName = "topicName123456";
//	/**
//	 * Redis缓存数据库访问工具
//	 */
//	private RedisTemplate redisTemplate;
//
//	/**
//	 * 
//	 * @param redisTemplate
//	 * @param cacheWriter
//	 * @param defaultCacheConfiguration
//	 */
//	public TwoLevelCacheManager(RedisTemplate redisTemplate, RedisCacheWriter cacheWriter,
//			RedisCacheConfiguration defaultCacheConfiguration) {
//		super(cacheWriter, defaultCacheConfiguration);
//		this.redisTemplate = redisTemplate;
//	}
//
//	@Override
//	protected Cache decorateCache(Cache cache) {
//		return new RedisAndLocalCache(this, (RedisCache) cache);
//	}
//
//	/**
//	 * 
//	 * @param channel
//	 * @param cacheName
//	 */
//	public void publishMessage(String cacheName) {
//		this.redisTemplate.convertAndSend(topicName, cacheName);
//
//	}
//
//	/**
//	 * 
//	 * @param name
//	 */
//	public void receiver(String name) {
//		RedisAndLocalCache cache = (RedisAndLocalCache) this.getCache(name);
//		if (cache != null) {
//			cache.clearLocal();
//		}
//	}
//}