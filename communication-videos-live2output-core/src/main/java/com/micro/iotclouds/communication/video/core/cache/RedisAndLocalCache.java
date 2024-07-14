//package com.micro.iotclouds.communication.video.core.cache;
//
//import java.util.concurrent.Callable;
//import java.util.concurrent.ConcurrentHashMap;
//import org.springframework.cache.Cache;
//import org.springframework.data.redis.cache.RedisCache;
//
///**
// * 基于ConcurrentHashMap和Redis的两级缓存器
// * 
// * @author dyw
// * @date 2019年9月30日
// */
//public class RedisAndLocalCache implements Cache {
//	private ConcurrentHashMap<Object, Object> local = new ConcurrentHashMap<>();
//	private RedisCache redisCache;
//	private TwoLevelCacheManager cacheManager;
//
//	public RedisAndLocalCache(TwoLevelCacheManager redisCacheManager, RedisCache cache) {
//		this.redisCache = cache;
//		this.cacheManager = redisCacheManager;
//	}
//
//	@Override
//	public String getName() {
//		return redisCache.getName();
//	}
//
//	@Override
//	public Object getNativeCache() {
//		return redisCache.getNativeCache();
//	}
//
//	@Override
//	public ValueWrapper get(Object key) {
//		ValueWrapper wrapper = (ValueWrapper) local.get(key);
//		if (wrapper == null) {
//			return wrapper;
//		} else {
//			wrapper = redisCache.get(key);
//			if (wrapper != null) {
//				local.put(key, wrapper);
//			}
//			return wrapper;
//		}
//	}
//
//	@Override
//	public void put(Object key, Object value) {
//		redisCache.put(key, value);
//		clearOtherJVM();
//	}
//
//	@Override
//	public void evict(Object key) {
//		redisCache.evict(key);
//		clearOtherJVM();
//	}
//
//	@Override
//	public <T> T get(Object key, Class<T> type) {
//		return redisCache.get(key, type);
//	}
//
//	@Override
//	public <T> T get(Object key, Callable<T> valueLoader) {
//		return redisCache.get(key, valueLoader);
//	}
//
//	@Override
//	public ValueWrapper putIfAbsent(Object key, Object value) {
//		return redisCache.putIfAbsent(key, value);
//	}
//
//	@Override
//	public void clear() {
//		redisCache.clear();
//	}
//
//	protected void clearOtherJVM() {
//		cacheManager.publishMessage(redisCache.getName());
//	}
//
//	public void clearLocal() {
//		this.local.clear();
//	}
//}