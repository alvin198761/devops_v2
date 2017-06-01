package org.alvin.opsdev.monitor.system.service.cache;

import org.alvin.opsdev.monitor.system.domain.Device;
import org.alvin.opsdev.monitor.system.domain.Metric;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by tangzhichao on 2017/4/24.
 */
@Component
public class DeviceMetricValueCacheService extends AbstractCacheService<String, Double> {

    public DeviceMetricValueCacheService() {
        super(2, TimeUnit.MINUTES);
    }

    public void put(Device device, Metric metric, Double value) {
        String key = device.getId() + "_" + metric.getId();
        super.put(key, value);
    }

    public double get(Device device, Metric metric) {
        String key = device.getId() + "_" + metric.getId();
        return super.get(key);
    }

    public void del(Device device, Metric metric) {
        String key = device.getId() + "_" + metric.getId();
        super.del(key);
    }
}
