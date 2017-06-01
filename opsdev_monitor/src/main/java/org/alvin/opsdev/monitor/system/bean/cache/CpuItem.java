package org.alvin.opsdev.monitor.system.bean.cache;

/**
 * Created by tangzhichao on 2016/10/25.
 */
public class CpuItem {

    private String name;
    private Long total;
    private Long idle;

    public CpuItem(String name, long idle, long total) {
        this.name = name;
        this.idle = idle;
        this.total = total;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getIdle() {
        return idle;
    }

    public void setIdle(Long idle) {
        this.idle = idle;
    }
}
