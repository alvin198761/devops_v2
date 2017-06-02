package org.alvin.opsdev.monitor.system.service;

import org.alvin.opsdev.monitor.system.bean.CollectorTicket;
import org.alvin.opsdev.monitor.system.bean.action.NotifyActionExecutor;
import org.alvin.opsdev.monitor.system.bean.enums.AlertLevel;
import org.alvin.opsdev.monitor.system.bean.enums.AlertStatus;
import org.alvin.opsdev.monitor.system.bean.enums.MetricType;
import org.alvin.opsdev.monitor.system.bean.enums.ObjectStatus;
import org.alvin.opsdev.monitor.system.domain.*;
import org.alvin.opsdev.monitor.system.service.cache.AlertCacheService;
import org.alvin.opsdev.monitor.system.service.cache.DeviceMetricValueCacheService;
import org.alvin.opsdev.monitor.system.service.cache.DeviceStatusCacheService;
import org.alvin.opsdev.monitor.system.service.cache.StatusCacheBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
        AtomicInteger count = new AtomicInteger(0);
        //先检查所有状态出错的，或者恢复的
        checkStatus(groups, devices, ticket,count);
        //检查指标是否有出错的，或者有恢复的
        checkMetrics(groups, devices, ticket,count);
        // 没有产生告警，直接退出
        if(count.get() == 0){
            return ;
        }
        // TODO: 2017/4/25 如果有新的严重级别的问题，并且超过了三次，立即发送一封邮件
        this.notifyActionExecutor.checkAlert();
    }

    //_______________________________status__________________________________________________
    private void checkStatus(List<DeviceGroup> groups, List<Device> devices, CollectorTicket ticket,AtomicInteger count) {
        groups.forEach(group -> {
            List<Device> devList = deviceService.findByGroupAndEnabled(group);
            Assert.notNull(devList, "checkStatus : device must not be null");
            devList.forEach(device -> checkDevStatus(device, ticket,count));
        });
        devices.forEach(device -> checkDevStatus(device, ticket,count));
    }

    private void checkDevStatus(Device device, CollectorTicket ticket,AtomicInteger count) {
        StatusCacheBean statusCacheBean = this.deviceStatusCacheService.get(device.getId());
        ObjectStatus status = statusCacheBean.getStatus();
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
                //产生新的告警，需要计数器记录一下
                count.incrementAndGet();
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
            //产生新的告警，需要计数器记录一下
            count.incrementAndGet();
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
    private void checkMetrics(List<DeviceGroup> groups, List<Device> devices, CollectorTicket ticket,AtomicInteger count) {
        //设备组检查
        groups.forEach(group -> {
            List<Device> devList = this.deviceService.findByGroupAndEnabled(group);
            Assert.notNull(devList, "checkStatus : device must not be null");
            devList.forEach(device -> checkDevMetric(device, ticket,count));
        });
        //无组设备检查
        devices.forEach(device -> checkDevMetric(device, ticket,count));
    }

    private void checkDevMetric(Device device, CollectorTicket ticket,AtomicInteger count) {
        List<Metric> metrics = this.metricService.findByObjectType(device.getCollectorType());
        metrics.forEach(metric -> {
            Threshold threshold = this.thresholdService.findByDeviceAndMetric(device, metric);
            //属性值用来查看，不用来比较 ，比如cpu 个数,内存总量
            if (metric.getMetricType().equals(MetricType.ATTR)) {
                return;
            }
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
            Alert alert = this.alertCacheService.get(device, metric);
            if (alert == null) {
                alert = new Alert();
                alert.setStatus(AlertStatus.ACTIVE);
                alert.setCount(0);
                alert.setLevel(AlertLevel.NORMAL);
                //产生新的告警，需要计数器记录一下
                count.incrementAndGet();
            }
            Double value = this.deviceMetricValueCacheService.get(device, metric);
            if(value == null){
                //Todo 严重错误 没有获取到属性值
                alert.setTime(ticket.getTime());
                alert.setDevice(device);
                alert.setMetric(metric);
                alert.setLevel(AlertLevel.CRITICAL);
                alert.setStatus(AlertStatus.UNKNOWN);
                alert.setCount(alert.getCount() + 1);
                this.alertCacheService.put(device, metric, alert);
                return;
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
