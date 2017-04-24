package org.alvin.opsdev.monitor.system.service;

import org.alvin.opsdev.monitor.system.domain.Device;
import org.alvin.opsdev.monitor.system.domain.Metric;
import org.alvin.opsdev.monitor.system.domain.Threshold;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tangzhichao on 2017/4/24.
 */
@Service
@Transactional(readOnly = true)
public class ThresholdService {


    public Threshold findByDeviceAndMetric(Device device, Metric metric) {
        return null;
    }
}
