package org.alvin.opsdev.monitor.system.service.cache;

import org.alvin.opsdev.monitor.system.bean.enums.ObjectStatus;

/**
 * Created by tangzhichao on 2017/4/24.
 */
public class StatusCacheBean {

    private ObjectStatus status;
    private int count;


    public ObjectStatus getStatus() {
        return status;
    }

    public void setStatus(ObjectStatus status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
