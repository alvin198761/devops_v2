package org.alvin.opsdev.monitor.system.bean.collector.impl;

import org.alvin.opsdev.monitor.system.bean.CollectorTicket;
import org.alvin.opsdev.monitor.system.bean.annotation.Collector;
import org.alvin.opsdev.monitor.system.bean.collector.ICollector;
import org.alvin.opsdev.monitor.system.bean.enums.CollectorType;
import org.alvin.opsdev.monitor.system.bean.enums.ObjectStatus;
import org.alvin.opsdev.monitor.system.domain.Device;
import org.alvin.opsdev.monitor.system.domain.DeviceGroup;
import org.springframework.stereotype.Component;

/**
 * Created by tangzhichao on 2017/4/24.
 */
@Component
@Collector(CollectorType.OS)
public class CentosCollector implements ICollector {

    @Override
    public void collect(CollectorTicket ticket, DeviceGroup group, Device device) {

    }

    @Override
    public ObjectStatus getStatus() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
