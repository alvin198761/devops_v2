package org.alvin.opsdev.monitor.system.service.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.alvin.opsdev.monitor.system.domain.Device;
import org.alvin.opsdev.monitor.system.domain.Metric;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by tangzhichao on 2017/4/24.
 */
@Component
public class DeviceMetricValueCacheService {

    private Cache<String, Double> metricValueCache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.MINUTES).build();

    public void put(Device device, Metric metric ,Double value){
        String key = device.getId() + "_" + metric.getId();
        this.metricValueCache.put(key,value);
    }

    public double get(Device device ,Metric metric){
        String key = device.getId() + "_" + metric.getId();
        return this.metricValueCache.getIfPresent(key);
    }

    public void remove(Device device ,Metric metric){
        String key = device.getId() + "_" + metric.getId();
        this.metricValueCache.invalidate(key);
    }
}
