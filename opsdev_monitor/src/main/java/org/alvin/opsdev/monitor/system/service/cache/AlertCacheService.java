package org.alvin.opsdev.monitor.system.service.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.alvin.opsdev.monitor.system.domain.Alert;
import org.alvin.opsdev.monitor.system.domain.Device;
import org.alvin.opsdev.monitor.system.domain.Metric;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by tangzhichao on 2017/4/24.
 */
@Component
public class AlertCacheService {

    private Cache<String, Alert> deviceCache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.MINUTES).build();

    public void put(Device dev, Alert alert) {
        this.deviceCache.put(dev.getId() + "_", alert);
    }

    public void put(Device dev, Metric metric, Alert alert) {
        String key = dev.getId() + "_" + metric.getId();
        Alert old = this.get(dev, metric);
        if(old != null){
            alert.setCount(old.getCount() +1);
        }else {
            alert.setCount(1);
        }
        this.deviceCache.put(key, alert);
    }

    public Alert get(Device dev) {
        return deviceCache.getIfPresent(dev.getId());
    }

    public Alert get(Device dev, Metric metric) {
        String key = dev.getId() + "_" + metric.getId();
        return this.deviceCache.getIfPresent(key);
    }

    public Alert remove(Device dev, Metric metric) {
        String key = dev.getId() + "_" + metric.getId();
        return this.deviceCache.getIfPresent(key);
    }

    public void clear() {
        deviceCache.invalidateAll();
    }

    public List<Alert> getAll() {
        return deviceCache.asMap().values().stream().collect(Collectors.toList());
    }

    public void add(Alert alert) {
        this.put(alert.getDevice(),alert.getMetric(),alert);
    }
}
