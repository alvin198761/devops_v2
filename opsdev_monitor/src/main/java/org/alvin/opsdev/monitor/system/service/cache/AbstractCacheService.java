package org.alvin.opsdev.monitor.system.service.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Created by tangzhichao on 2017/6/1.
 */
public class AbstractCacheService<K, V> {

    private Cache<K, V> cache;

    public AbstractCacheService(long time, TimeUnit unit) {
        this.cache = CacheBuilder.newBuilder().expireAfterWrite(time, unit).build();
    }

    public void put(K k, V v) {
        this.cache.put(k, v);
    }


    public void del(K k) {
        this.cache.invalidate(k);
    }

    public void clean() {
        this.cache.invalidateAll();
    }

    public V get(K k) {
        return this.cache.getIfPresent(k);
    }

    public Collection<V> getAll() {
        return this.cache.asMap().values();
    }

}
