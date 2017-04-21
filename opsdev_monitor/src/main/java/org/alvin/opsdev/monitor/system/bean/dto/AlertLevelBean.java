package org.alvin.opsdev.monitor.system.bean.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by tangzhichao on 2017/4/21.
 */
public class AlertLevelBean {

    @ApiModelProperty(value = "严重等级比率", required = true, dataType = "Double")
    private double critical;
    @ApiModelProperty(value = "警告等级比率", required = true, dataType = "Double")
    private double warning;
    @ApiModelProperty(value = "正常等级比率", required = true, dataType = "Double")
    private double normal;


    public double getCritical() {
        return critical;
    }

    public void setCritical(double critical) {
        this.critical = critical;
    }

    public double getWarning() {
        return warning;
    }

    public void setWarning(double warning) {
        this.warning = warning;
    }

    public double getNormal() {
        return normal;
    }

    public void setNormal(double normal) {
        this.normal = normal;
    }
}
