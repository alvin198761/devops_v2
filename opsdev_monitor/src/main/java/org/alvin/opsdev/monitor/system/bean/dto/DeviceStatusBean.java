package org.alvin.opsdev.monitor.system.bean.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by tangzhichao on 2017/4/21.
 */
public class DeviceStatusBean {

    @ApiModelProperty(value = "成功状态比率", required = true, dataType = "Double")
    private double success;
    @ApiModelProperty(value = "失败状态比率", required = true, dataType = "Double")
    private double fail;
    @ApiModelProperty(value = "未知状态比率", required = true, dataType = "Double")
    private double unknown;


    public double getSuccess() {
        return success;
    }

    public void setSuccess(double success) {
        this.success = success;
    }

    public double getFail() {
        return fail;
    }

    public void setFail(double fail) {
        this.fail = fail;
    }


    public double getUnknown() {
        return unknown;
    }

    public void setUnknown(double unknown) {
        this.unknown = unknown;
    }
}
