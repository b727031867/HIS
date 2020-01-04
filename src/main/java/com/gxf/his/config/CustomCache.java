package com.gxf.his.config;

import com.gxf.his.Const;
import com.gxf.his.uitls.JwtUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 重写Shiro的Cache保存读取
 *
 * @author GXF
 */
public class CustomCache<K, V> implements Cache<K, V> {

    /*
     redis @Autowired注入失败，因此改为下面采用传参形式 @Autowired
    */
//    private RedisClient redis = new RedisClient();

    /**
     *设置600秒后Shiro缓存过期 @Value("${config.shiro-cache-expireTime}")
     */

    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 使用StringRedisSerializer做序列化
     * redisTemplate.setValueSerializer(new StringRedisSerializer());
     */
    @SuppressWarnings("unchecked")
    public CustomCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 缓存的key名称获取为shiro:cache:account
     *
     * @param key key
     * @return java.lang.String
     * @date 2018/9/4 18:33
     */
    private String getKey(Object key) {
        return Const.REDIS_CONSTANT_SHIRO_CACHE_PREFIX + JwtUtil.getUsername(key.toString());
    }

    /**
     * 获取缓存
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object get(Object key) throws CacheException {
        return redisTemplate.opsForValue().get(this.getKey(key));
    }

    /**
     * 保存缓存
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object put(Object key, Object value) throws CacheException {
        // 设置Redis的Shiro缓存
        try {
            redisTemplate.opsForValue().set(this.getKey(key), value, Integer.parseInt(Const.SHIROCACHEEXPIRETIME), TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除缓存
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object remove(Object key) throws CacheException {
        redisTemplate.delete(this.getKey(key));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
    }

    /**
     * 缓存的个数
     */
    @Override
    public Set<K> keys() {
        return null;
    }

    /**
     * 获取所有的key
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * 获取所有的value
     */
    @Override
    public Collection<V> values() {
        return null;
    }

}