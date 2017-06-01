package org.alvin.opsdev.monitor.system.bean.cache;

import org.alvin.opsdev.monitor.system.bean.enums.AlertLevel;

/**
 * Created by tangzhichao on 2017/6/1.
 */
public class MetricCacheBean {

    private Double value;
    private AlertLevel level;


    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public AlertLevel getLevel() {
        return level;
    }

    public void setLevel(AlertLevel level) {
        this.level = level;
    }
}
