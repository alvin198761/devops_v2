package org.alvin.opsdev.monitor.system.service;

import org.alvin.opsdev.monitor.system.bean.CollectorTicket;
import org.alvin.opsdev.monitor.system.bean.action.NotifyActionExecutor;
import org.alvin.opsdev.monitor.system.service.cache.StatusCacheBean;
import org.alvin.opsdev.monitor.system.bean.enums.AlertLevel;
import org.alvin.opsdev.monitor.system.bean.enums.AlertStatus;
import org.alvin.opsdev.monitor.system.bean.enums.ObjectStatus;
import org.alvin.opsdev.monitor.system.domain.*;
import org.alvin.opsdev.monitor.system.service.cache.AlertCacheService;
import org.alvin.opsdev.monitor.system.service.cache.DeviceMetricValueCacheService;
import org.alvin.opsdev.monitor.system.service.cache.DeviceStatusCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by tangzhichao on 2017/4/24.
 */
@Component
public class CheckMetricService {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceStatusCacheService deviceStatusCacheService;
    @Autowired
    private AlertService alertService;
    @Autowired
    private MetricService metricService;
    @Autowired
    private ThresholdService thresholdService;
    @Autowired
    private DeviceMetricValueCacheService deviceMetricValueCacheService;
    @Autowired
    private AlertCacheService alertCacheService;
    @Autowired
    private NotifyActionExecutor notifyActionExecutor;

    /**
     * 对每一轮监控和采集结果进行检查，审核，并产生报表数据
     *
     * @param groups
     * @param devices
     * @param ticket
     */
    public void check(List<DeviceGroup> groups, List<Device> devices, CollectorTicket ticket) {
        //先检查所有状态出错的，或者恢复的
        checkStatus(groups, devices, ticket);
        //检查指标是否有出错的，或者有恢复的
        checkMetrics(groups, devices, ticket);
        // TODO: 2017/4/25 如果有新的严重级别的问题，并且超过了三次，立即发送一封邮件
        this.notifyActionExecutor.checkAlert();
    }

    //_______________________________status__________________________________________________
    private void checkStatus(List<DeviceGroup> groups, List<Device> devices, CollectorTicket ticket) {
        groups.forEach(group -> {
            List<Device> devList = deviceService.findByGroupAndEnabled(group);
            Assert.notNull(devList, "checkStatus : device must not be null");
            devList.forEach(device -> checkDevStatus(device, ticket));
        });
        devices.forEach(device -> checkDevStatus(device, ticket));
    }

    private void checkDevStatus(Device device, CollectorTicket ticket) {
        StatusCacheBean statusCacheBean = this.deviceStatusCacheService.get(device.getId());
        ObjectStatus status = device.getStatus();
        if (statusCacheBean == null) {
            if (status != ObjectStatus.SUCCESS) {
                //状态缓存
                this.deviceStatusCacheService.put(device, status);
                Alert alert = new Alert();
                alert.setDevice(device);
                //状态问题，如果不是正常，一律视为严重
                alert.setLevel(AlertLevel.CRITICAL);
                alert.setCount(1);
                alert.setTime(ticket.getTime());
                alert.setStatus(AlertStatus.ACTIVE);
                //产生一个告警信息
                this.alertService.save(alert);
            }
            return;
        }
        Alert alert = this.alertService.findByDevice(device);
        if (alert == null) {
            alert = new Alert();
            alert.setDevice(device);
            alert.setLevel(AlertLevel.CRITICAL);
            alert.setCount(1);
            alert.setTime(ticket.getTime());
            alert.setStatus(AlertStatus.ACTIVE);
        }
        if (status == ObjectStatus.SUCCESS && status != statusCacheBean.getStatus()) {
            //产生改为清除，次数清0 ,清缓存
            alert.setStatus(AlertStatus.CLEAN);
            alert.setTime(ticket.getTime());
            this.alertService.save(alert);
            this.deviceStatusCacheService.del(device.getId());
            return;
        }
        //增加警告次数
        if (statusCacheBean.getStatus() == status) {
            alert.setCount(alert.getCount() + 1);
            alert.setTime(ticket.getTime());
            this.alertService.save(alert);
            alert.setStatus(AlertStatus.ACTIVE);
            return;
        }
    }

    //_______________________________metric__________________________________________________
    private void checkMetrics(List<DeviceGroup> groups, List<Device> devices, CollectorTicket ticket) {
        groups.forEach(group -> {
            List<Device> devList = this.deviceService.findByGroupAndEnabled(group);
            Assert.notNull(devList, "checkStatus : device must not be null");
            devList.forEach(device -> checkDevMetric(device, ticket));
        });
        devices.forEach(device -> checkDevMetric(device, ticket));
    }

    private void checkDevMetric(Device device, CollectorTicket ticket) {
        List<Metric> metrics = this.metricService.findByObjectType(device.getCollectorType());
        metrics.forEach(metric -> {
            Threshold threshold = this.thresholdService.findByDeviceAndMetric(device, metric);
            if (threshold == null) {
                //Todo  清除缓存
                this.alertCacheService.remove(device, metric);
                return;
            }
            if (threshold.getEnabled()) {
                //Todo  清除缓存
                this.alertCacheService.remove(device, metric);
                return;
            }
            double value = this.deviceMetricValueCacheService.get(device, metric);
            Alert alert = this.alertCacheService.get(device, metric);
            if (alert == null) {
                alert = new Alert();
                alert.setStatus(AlertStatus.ACTIVE);
                alert.setCount(0);
                alert.setLevel(AlertLevel.NORMAL);
            }
            if (value >= threshold.getLimit()) {
                //Todo 严重错误
                alert.setTime(ticket.getTime());
                alert.setDevice(device);
                alert.setMetric(metric);
                alert.setLevel(AlertLevel.CRITICAL);
                alert.setStatus(AlertStatus.ACTIVE);
                alert.setCount(alert.getCount() + 1);
                this.alertCacheService.put(device, metric, alert);
                return;
            }
            if (value >= threshold.getWarn()) {
                //Todo 警告错误
                alert.setTime(ticket.getTime());
                alert.setDevice(device);
                alert.setMetric(metric);
                alert.setLevel(AlertLevel.WARNING);
                alert.setStatus(AlertStatus.ACTIVE);
                alert.setCount(alert.getCount() + 1);
                this.alertCacheService.put(device, metric, alert);
                return;
            }
            //Todo 根据状态判断是不是要产生清除信息
            if (alert.getCount() > 0 && alert.getLevel() != AlertLevel.NORMAL) {
                alert.setLevel(AlertLevel.NORMAL);
                alert.setStatus(AlertStatus.CLEAN);
                alert.setCount(1);
                alert.setDevice(device);
                alert.setMetric(metric);
                this.alertCacheService.put(device, metric, alert);
                return;
            }
        });
    }
}
