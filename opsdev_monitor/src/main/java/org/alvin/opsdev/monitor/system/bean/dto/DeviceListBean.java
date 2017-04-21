package org.alvin.opsdev.monitor.system.bean.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by tangzhichao on 2017/4/21.
 */
public class DeviceListBean {

    @ApiModelProperty("总条数")
    private int totalElement;
    @ApiModelProperty("总页数")
    private int totalPage;
    @ApiModelProperty("当前页")
    private int page;
    @ApiModelProperty("页大小")
    private int pageSize;
    @ApiModelProperty("设备列表")
    private List<DeviceBean> items;


    public int getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(int totalElement) {
        this.totalElement = totalElement;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<DeviceBean> getItems() {
        return items;
    }

    public void setItems(List<DeviceBean> items) {
        this.items = items;
    }
}
