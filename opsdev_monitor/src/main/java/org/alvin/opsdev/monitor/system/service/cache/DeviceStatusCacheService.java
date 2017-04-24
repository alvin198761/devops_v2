package org.alvin.opsdev.monitor.system.service.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.alvin.opsdev.monitor.system.bean.cache.StatusCacheBean;
import org.alvin.opsdev.monitor.system.bean.enums.ObjectStatus;
import org.alvin.opsdev.monitor.system.domain.Device;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Component
public class DeviceStatusCacheService {

    private Cache<Long, StatusCacheBean> deviceCache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.MINUTES).build();

    public void put(Device dev, ObjectStatus status) {
        StatusCacheBean statusCacheBean = this.get(dev);
        if (statusCacheBean == null) {
            statusCacheBean = new StatusCacheBean();
            statusCacheBean.setCount(0);
            statusCacheBean.setStatus(status);
        }
        statusCacheBean.setCount(statusCacheBean.getCount() + 1);
        this.deviceCache.put(dev.getId(), statusCacheBean);
    }

    public StatusCacheBean get(Device dev) {
        return deviceCache.getIfPresent(dev.getId());
    }

    public void remove(Device dev) {
        deviceCache.invalidate(dev.getId());
    }

    public void clear() {
        deviceCache.invalidateAll();
    }
}
