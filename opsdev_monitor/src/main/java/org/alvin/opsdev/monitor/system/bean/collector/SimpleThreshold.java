package org.alvin.opsdev.monitor.system.bean.collector;

/**
 * Created by tangzhichao on 2017/6/1.
 */
public class SimpleThreshold {

    private String name;
    private double warn;
    private double limit;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWarn() {
        return warn;
    }

    public void setWarn(double warn) {
        this.warn = warn;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
