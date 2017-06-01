package org.alvin.opsdev.monitor.system.service.cache;

import org.alvin.opsdev.monitor.system.bean.enums.ObjectStatus;
import org.alvin.opsdev.monitor.system.domain.Device;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Component
public class DeviceStatusCacheService extends AbstractCacheService<Long, StatusCacheBean> {

    public DeviceStatusCacheService() {
        super(1, TimeUnit.MINUTES);
    }

    public void put(Device dev, ObjectStatus status) {
        StatusCacheBean statusCacheBean = this.get(dev.getId());
        if (statusCacheBean == null) {
            statusCacheBean = new StatusCacheBean();
            statusCacheBean.setCount(0);
            statusCacheBean.setStatus(status);
        }
        statusCacheBean.setCount(statusCacheBean.getCount() + 1);
        super.put(dev.getId(), statusCacheBean);
    }

}
