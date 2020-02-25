//package com.gxf.his.config;
//
//import org.apache.shiro.cache.Cache;
//import org.apache.shiro.cache.CacheException;
//import org.apache.shiro.cache.CacheManager;
//import org.springframework.data.redis.core.RedisTemplate;
//
///**
// * 重写Shiro缓存管理器
// * @author GXF
// */
//public class CustomCacheManager implements CacheManager {
//
//    private RedisTemplate redisTemplate;
//
//    public CustomCacheManager(RedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    @Override
//    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
//        return new CustomCache<>(redisTemplate);
//    }
//}
