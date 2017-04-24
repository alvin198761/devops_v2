package org.alvin.opsdev.monitor.system.bean.collector;

import org.alvin.opsdev.monitor.system.bean.CollectorTicket;
import org.alvin.opsdev.monitor.system.bean.enums.ObjectStatus;
import org.alvin.opsdev.monitor.system.domain.Device;
import org.alvin.opsdev.monitor.system.domain.DeviceGroup;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface ICollector {

    void collect(CollectorTicket ticket, DeviceGroup group, Device device);

    ObjectStatus getStatus();

    boolean isEnabled();

}
