package org.alvin.opsdev.monitor.system.bean.collector.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.schmizz.sshj.connection.channel.direct.Session;
import org.alvin.opsdev.monitor.system.bean.CollectorTicket;
import org.alvin.opsdev.monitor.system.bean.enums.ObjectStatus;
import org.alvin.opsdev.monitor.system.bean.plugin.PluginBean;
import org.alvin.opsdev.monitor.system.domain.Device;
import org.alvin.opsdev.monitor.system.domain.DeviceGroup;
import org.alvin.opsdev.monitor.system.domain.Metric;
import org.alvin.opsdev.monitor.system.domain.Performance;
import org.alvin.opsdev.monitor.system.service.MetricService;
import org.alvin.opsdev.monitor.system.service.PerformanceService;
import org.alvin.opsdev.monitor.system.service.ThresholdService;
import org.alvin.opsdev.monitor.system.service.cache.AlertCacheService;
import org.alvin.opsdev.monitor.system.service.cache.DeviceMetricValueCacheService;
import org.alvin.opsdev.monitor.system.service.cache.DeviceStatusCacheService;
import org.alvin.opsdev.monitor.system.utils.SSHCollectTool;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by tangzhichao on 2017/6/1.
 */
public class PluginCollector extends AbstractCollector {

    private PluginBean plugin;

    public PluginCollector(SSHCollectTool sshCollectTool,
                           MetricService metricService,
                           ThresholdService thresholdService,
                           PerformanceService performanceService,
                           DeviceStatusCacheService deviceStatusCacheService,
                           DeviceMetricValueCacheService deviceMetricValueCacheService,
                           AlertCacheService alertCacheService,
                           PluginBean plugin) {
        super(sshCollectTool, metricService,
                thresholdService, performanceService,
                deviceStatusCacheService, deviceMetricValueCacheService, alertCacheService);
        this.plugin = plugin;
    }


    @Override
    protected void doCollect(final Session session, DeviceGroup group, final Device device, final CollectorTicket ticket) {
        //传入 session ,deviceId ,time
        /* 返回 map [metric_label = value]         */
        Map<String, Double> performanceMap = AccessController.doPrivileged(new PrivilegedAction<Map<String, Double>>() {

            @Override
            public Map<String, Double> run() {
                Class c = plugin.getPluginInstance().getClass();
                Method method = null;
                try {
                    method = c.getDeclaredMethod(plugin.getCollectorMethod(), Session.class, Long.class, Date.class);
                } catch (NoSuchMethodException e) {
                    deviceStatusCacheService.put(device, ObjectStatus.UNKNOWN);
                    e.printStackTrace();
                    Logger.getLogger(getClass()).error(e);
                }
                if (method == null) {
                    return Maps.newHashMap();
                }
                boolean flag = method.isAccessible();
                method.setAccessible(true);
                try {
                    return (Map<String, Double>) method.invoke(plugin.getPluginInstance(), session, device.getId(), ticket.getTime());
                } catch (IllegalAccessException e) {
                    deviceStatusCacheService.put(device, ObjectStatus.UNKNOWN);
                    e.printStackTrace();
                    Logger.getLogger(getClass()).error(e);
                } catch (InvocationTargetException e) {
                    deviceStatusCacheService.put(device, ObjectStatus.UNKNOWN);
                    e.printStackTrace();
                    Logger.getLogger(getClass()).error(e);
                }
                method.setAccessible(flag);
                return null;
            }

        });
        List<Performance> performances = parsePerformance(performanceMap, device, ticket);
        super.checkMetrics(ticket, device, performances);
    }

    private List<Performance> parsePerformance(Map<String, Double> performanceMap, Device device, CollectorTicket ticket) {
        List<Performance> list = Lists.newArrayList();
        if(performanceMap == null){
            return list;
        }
        performanceMap.forEach((label, value) -> {
            Metric metric = this.metricService.findOneByLabel(label);
            Assert.notNull(metric, "metric must not be null");
            list.add(createPerformance(device, metric, ticket.getTime(), value));
        });
        return list;
    }

    @Override
    public boolean isEnabled() {
        //检查是否可用，如果可用才能使用，目前为可用
        return true;
    }

    @Override
    protected String[] getCmds() {
        return null;
    }

    @Override
    protected List<Performance> resolve(CollectorTicket ticket, DeviceGroup group, Device device, String result) throws Exception {
        return null;
    }
}
