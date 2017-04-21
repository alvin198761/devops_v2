package org.alvin.opsdev.monitor.system.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import org.alvin.opsdev.monitor.system.bean.enums.ObjectStatus;

/**
 * Created by tangzhichao on 2017/4/21.
 */
public class DeviceBean {

    @ApiModelProperty(value = "设备编号")
    private Long id;
    @ApiModelProperty(value = "设备名称" ,required = true)
    private String name;
    @ApiModelProperty(value = "设备状态")
    private ObjectStatus status;
    @ApiModelProperty(value = "设备ip地址")
    private String ip;
    @ApiModelProperty(value = "设备组名称/ip")
    private String group;
    @ApiModelProperty(value = "插件数量")
    private Integer pluginCount;
    @ApiModelProperty(value = "指标数量")
    private Integer metricCount;
    @ApiModelProperty(value = "规则数量")
    private Integer ruleCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectStatus getStatus() {
        return status;
    }

    public void setStatus(ObjectStatus status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getPluginCount() {
        return pluginCount;
    }

    public void setPluginCount(Integer pluginCount) {
        this.pluginCount = pluginCount;
    }

    public Integer getMetricCount() {
        return metricCount;
    }

    public void setMetricCount(Integer metricCount) {
        this.metricCount = metricCount;
    }

    public Integer getRuleCount() {
        return ruleCount;
    }

    public void setRuleCount(Integer ruleCount) {
        this.ruleCount = ruleCount;
    }
}
