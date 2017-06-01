package org.alvin.opsdev.monitor.system.bean.collector.impl;

import net.schmizz.sshj.connection.channel.direct.Session;
import org.alvin.opsdev.monitor.system.bean.CollectorTicket;
import org.alvin.opsdev.monitor.system.bean.collector.ICollector;
import org.alvin.opsdev.monitor.system.bean.enums.AlertLevel;
import org.alvin.opsdev.monitor.system.bean.enums.AlertStatus;
import org.alvin.opsdev.monitor.system.bean.enums.MetricType;
import org.alvin.opsdev.monitor.system.bean.enums.ObjectStatus;
import org.alvin.opsdev.monitor.system.domain.*;
import org.alvin.opsdev.monitor.system.service.MetricService;
import org.alvin.opsdev.monitor.system.service.PerformanceService;
import org.alvin.opsdev.monitor.system.service.ThresholdService;
import org.alvin.opsdev.monitor.system.service.cache.AlertCacheService;
import org.alvin.opsdev.monitor.system.service.cache.DeviceMetricValueCacheService;
import org.alvin.opsdev.monitor.system.service.cache.DeviceStatusCacheService;
import org.alvin.opsdev.monitor.system.utils.SSHCollectTool;
import org.apache.log4j.Logger;
import org.assertj.core.util.Strings;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tangzhichao on 2017/6/1.
 */
public abstract class AbstractCollector implements ICollector {

    public static final String separator = "@@@@";
    protected SSHCollectTool sshCollectTool;
    protected MetricService metricService;
    protected ThresholdService thresholdService;
    protected PerformanceService performanceService;

    protected DeviceStatusCacheService deviceStatusCacheService;
    protected DeviceMetricValueCacheService deviceMetricValueCacheService;
    protected AlertCacheService alertCacheService;

    public AbstractCollector(
            @NotNull(message = "sshCollectTool must not be null") SSHCollectTool sshCollectTool,
            @NotNull(message = "metricService must not be null") MetricService metricService,
            @NotNull(message = "thresholdService must not be null") ThresholdService thresholdService,
            @NotNull(message = "performanceService must not be null") PerformanceService performanceService,
            @NotNull(message = "deviceStatusCacheService must not be null") DeviceStatusCacheService deviceStatusCacheService,
            @NotNull(message = "deviceMetricValueCacheService must not be null") DeviceMetricValueCacheService deviceMetricValueCacheService,
            @NotNull(message = "alertCacheService must not be null") AlertCacheService alertCacheService) {
        this.sshCollectTool = sshCollectTool;
        this.metricService = metricService;
        this.thresholdService = thresholdService;
        this.performanceService = performanceService;
        //
        this.deviceStatusCacheService = deviceStatusCacheService;
        this.deviceMetricValueCacheService = deviceMetricValueCacheService;
        this.alertCacheService = alertCacheService;
    }

    @Override
    public void collect(CollectorTicket ticket, DeviceGroup group, Device device) {
        Account account = device.getAccount();
        if (account == null) {
            deviceStatusCacheService.put(device, ObjectStatus.UNKNOWN);
            return;
        }
        //连接到服务器
        try (Session session = sshCollectTool.getSession(account.getHost(), account.getMonitorPort(), account.getUser(), account.getPassword())) {
            if (session != null && session.isOpen()) {
                this.doCollect(session,group,device,ticket);
                deviceStatusCacheService.put(device, ObjectStatus.SUCCESS);
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass()).error(ex);
        }
        deviceStatusCacheService.put(device, ObjectStatus.FAIL);
    }

    protected void doCollect(Session session, DeviceGroup group, Device device, CollectorTicket ticket) {
        try {
            String[] cmd = getCmds();
            String result;
            if (cmd.length == 0) {
                //执行命令采集
                result = this.sshCollectTool.exec(session, cmd[0]);
            } else {
                result = this.sshCollectTool.batchExec(session, separator, cmd);
            }
            if (Strings.isNullOrEmpty(result)) {
                deviceStatusCacheService.put(device, ObjectStatus.FAIL);
                Logger.getLogger(this.getClass()).error("采集数据为空: deveiceId = " + device.getId());
                return;
            }
            checkMetrics(ticket, device, this.resolve(ticket, group, device, result));
        } catch (Exception ex) {
            Logger.getLogger(this.getClass()).error(ex);
        }
    }

    protected void checkMetrics(CollectorTicket ticket, Device device, List<Performance> performanceList) {
        performanceList.stream().forEach(per -> {
            Assert.notNull(per.getValue(), MessageFormat.format("performance value must not be null device = {0} , metric = {1}", device.getId(), per.getMetric().getId()));
            Metric metric = this.metricService.findOne(per.getMetric().getId());
            Assert.notNull(metric, "metric must mot be null , id = " + per.getMetric().getId());
            Threshold threshold = this.thresholdService.findByDeviceAndMetric(device, metric);
            Assert.notNull(metric, MessageFormat.format("threshold must mot be null , device = {0} ,metric = {1}", device.getId(), per.getMetric().getId()));
            Assert.isTrue(threshold.getEnabled(), MessageFormat.format("threshold is disabled , device = {0} ,metric = {1}", device.getId(), per.getMetric().getId()));
            //属性值用来查看，不用来比较 ，比如cpu 个数,内存总量
            if (metric.getMetricType().equals(MetricType.ATTR)) {
                performanceList.remove(per);
                return;
            }
            // 超过了严重门限
            if (per.getValue() >= threshold.getLimit()) {
                alertCacheService.add(createAlert(ticket, device, metric, AlertLevel.CRITICAL));
                return;
            }
            // 超过了警告门限
            if (per.getValue() >= threshold.getWarn()) {
                alertCacheService.add(createAlert(ticket, device, metric, AlertLevel.WARNING));
                return;
            }
            //保存当前值
            this.deviceMetricValueCacheService.put(device, metric, per.getValue());
        });
        this.performanceService.save(performanceList);
    }

    protected Alert createAlert(CollectorTicket ticket, Device device, Metric metric, AlertLevel level) {
        Alert alert = new Alert();
        alert.setDevice(device);
        alert.setMetric(metric);
        alert.setStatus(AlertStatus.ACTIVE);
        alert.setLevel(level);
        alert.setCount(0);
        alert.setTime(ticket.getTime());
        return alert;
    }

    protected Performance createPerformance(Device device, Metric metric, Date time, double value) {
        Performance performance = new Performance();
        performance.setTime(time);
        performance.setDevice(device);
        performance.setMetric(metric);
        performance.setValue(value);
        return performance;
    }

    /**
     * 执行相关命令 <br/>
     * 解析结果<br/>
     * 分析结果数据，并产生性能数据<br/>
     * 错误放入缓存<br/>
     * 性能数据入库<br/>
     *
     * @param ticket
     * @param group
     * @param device
     * @param result
     */
    protected abstract List<Performance> resolve(CollectorTicket ticket, DeviceGroup group, Device device, String result) throws Exception;

    /**
     * 获取要执行的命令
     *
     * @return
     */
    protected abstract String[] getCmds();

    @Override
    public boolean isEnabled() {
        return true;
    }
}
