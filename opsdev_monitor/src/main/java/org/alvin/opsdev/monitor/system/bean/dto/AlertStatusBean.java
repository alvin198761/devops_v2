package org.alvin.opsdev.monitor.system.bean.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by tangzhichao on 2017/4/21.
 */
public class AlertStatusBean {

    @ApiModelProperty(value = "活跃状态比率", required = true, dataType = "Double")
    private double active;
    @ApiModelProperty(value = "关闭状态比率", required = true, dataType = "Double")
    private double closed;
    @ApiModelProperty(value = "未知状态比率", required = true, dataType = "Double")
    private double known;


    public double getActive() {
        return active;
    }

    public void setActive(double active) {
        this.active = active;
    }

    public double getClosed() {
        return closed;
    }

    public void setClosed(double closed) {
        this.closed = closed;
    }

    public double getKnown() {
        return known;
    }

    public void setKnown(double known) {
        this.known = known;
    }
}
