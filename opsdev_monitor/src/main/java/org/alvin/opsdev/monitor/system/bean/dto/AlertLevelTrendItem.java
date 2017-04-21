package org.alvin.opsdev.monitor.system.bean.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by tangzhichao on 2017/4/21.
 */
public class AlertLevelTrendItem {

    @ApiModelProperty(value = "严重次数", required = true, dataType = "Integer")
    private int critical;
    @ApiModelProperty(value = "警告次数", required = true, dataType = "Integer")
    private int warning;
    @ApiModelProperty(value = "正常次数", required = true, dataType = "Integer")
    private int normal;
    @ApiModelProperty(value = "采集时间点", required = true, example = "时间戳", dataType = "Long")
    private long time;

    public int getCritical() {
        return critical;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    public int getWarning() {
        return warning;
    }

    public void setWarning(int warning) {
        this.warning = warning;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
