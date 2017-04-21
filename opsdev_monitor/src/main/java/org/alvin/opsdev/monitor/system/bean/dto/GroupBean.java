package org.alvin.opsdev.monitor.system.bean.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by tangzhichao on 2017/4/21.
 */
public class GroupBean {

    @ApiModelProperty(value = "组编号")
    private Long id;
    @ApiModelProperty(value = "组名称")
    private String name;
    @ApiModelProperty(value = "组备注")
    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
