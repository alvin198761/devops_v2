package org.alvin.opsdev.monitor.system.service.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.alvin.opsdev.monitor.system.domain.Device;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Component
public class DeviceCacheService {

    private Cache<Long, Device> deviceCache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.HOURS).build();

//    public void put(Device managedObject ) {
//        this.alarmCache.put(managedObject.getId(),cacheBean);
//    }
//
//    public Device get(ManagedObject managedObject) {
//        return alarmCache.getIfPresent(managedObject.getId());
//    }
//
//    public void remove(Device managedObject) {
//        alarmCache.invalidate(managedObject.getId());
//    }
//
//    public void clear() {
//        alarmCache.invalidateAll();
//    }
}
