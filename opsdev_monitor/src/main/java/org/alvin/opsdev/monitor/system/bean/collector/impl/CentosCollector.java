package org.alvin.opsdev.monitor.system.bean.collector.impl;

import com.google.common.collect.Lists;
import org.alvin.opsdev.monitor.system.bean.CollectorTicket;
import org.alvin.opsdev.monitor.system.bean.annotation.Collector;
import org.alvin.opsdev.monitor.system.bean.cache.CpuItem;
import org.alvin.opsdev.monitor.system.domain.Device;
import org.alvin.opsdev.monitor.system.domain.DeviceGroup;
import org.alvin.opsdev.monitor.system.domain.Performance;
import org.alvin.opsdev.monitor.system.service.MetricService;
import org.alvin.opsdev.monitor.system.service.PerformanceService;
import org.alvin.opsdev.monitor.system.service.ThresholdService;
import org.alvin.opsdev.monitor.system.service.cache.AlertCacheService;
import org.alvin.opsdev.monitor.system.service.cache.CpuCacheService;
import org.alvin.opsdev.monitor.system.service.cache.DeviceMetricValueCacheService;
import org.alvin.opsdev.monitor.system.service.cache.DeviceStatusCacheService;
import org.alvin.opsdev.monitor.system.utils.PropertiesUtils;
import org.alvin.opsdev.monitor.system.utils.SSHCollectTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tangzhichao on 2017/4/24.
 */
@Component
@Collector(1)
public class CentosCollector extends AbstractCollector {

    String[] cmds = {
            "cat /proc/net/tcp",
            "cat /proc/net/dev",
            "cat /proc/meminfo",
            "cat /proc/stat",
            "cat /proc/loadavg",
            "cat /proc/sys/fs/file-nr",
            // "cat /proc/swaps",
            "df -Pkl",
            "iostat -d",
    };

    private CpuCacheService cpuCacheService;

    @Autowired
    public CentosCollector(SSHCollectTool sshCollectTool,
                           MetricService metricService,
                           ThresholdService thresholdService,
                           PerformanceService performanceService,
                           DeviceStatusCacheService deviceStatusCacheService,
                           DeviceMetricValueCacheService deviceMetricValueCacheService,
                           AlertCacheService alertCacheService,
                           @NotNull(message = "cpuCacheService must not be null") CpuCacheService cpuCacheService
    ) {
        super(sshCollectTool, metricService,
                thresholdService, performanceService,
                deviceStatusCacheService, deviceMetricValueCacheService, alertCacheService);
        this.cpuCacheService = cpuCacheService;
    }


    @Override
    protected String[] getCmds() {
        return cmds;
    }

    @Override
    protected List<Performance> resolve(CollectorTicket ticket, DeviceGroup group, Device device, String result) throws Exception {
        List<Performance> list = Lists.newArrayList();
        String[] array = result.split(separator);
        setHttpConnect(array[0], device, ticket.getTime(), list);
        setNetWorkInfo(array[1], device, ticket.getTime(), list);
        setMemoryAndSwapInfo(array[2], device, ticket.getTime(), list);
        setCpusInfo(array[3], device, ticket.getTime(), list);
        setLoadAvgInfo(array[4], device, ticket.getTime(), list);
        setFileHandleInfo(array[5], device, ticket.getTime(), list);
        setDiskInfo(array[6], device, ticket.getTime(), list);
        setIoLoadInfo(array[7], device, ticket.getTime(), list);
        return list;
    }

    private void setIoLoadInfo(String data, Device device, Date time, List<Performance> list) {
        String[] lines = data.trim().split("\n");
        double sum_r = 0;
        double sum_w = 0;
        for (int i = 3; i < lines.length; i++) {
            String[] array = lines[i].trim().split("\\s+");
            double read = Double.parseDouble(array[2]);
            double write = Double.parseDouble(array[3]);

            sum_r += read;
            sum_w += write;
        }

        list.add(super.createPerformance(device, metricService.findOneByLabel("centos io read"), time, sum_r));
        list.add(super.createPerformance(device, metricService.findOneByLabel("centos io write"), time, sum_w));
    }

    private void setDiskInfo(String data, Device device, Date time, List<Performance> list) {
        String[] lines = data.trim().split("\n");
        long diskTotal = 0;
        long diskUsage = 0;
        for (int i = 1; i < lines.length; i++) {
            String[] array = lines[i].split("\\s+");
            long total = Long.parseLong(array[1]);
            long usage = Long.parseLong(array[2]);
            diskTotal += total;
            diskUsage += usage;
        }
        //总数计算平均,其实这样的算法不太准确
        double usageValue = (diskUsage * 1.0 / diskTotal) * 100;

        list.add(super.createPerformance(device, metricService.findOneByLabel("centos disk total"), time, diskTotal));
        list.add(super.createPerformance(device, metricService.findOneByLabel("centos disk uasge"), time, usageValue));
          }

    private void setFileHandleInfo(String data, Device device, Date time, List<Performance> list) {
        String[] array = data.trim().split("\\s+");
        long count = Long.parseLong(array[0]);
        long limit = Long.parseLong(array[2]);

        list.add(super.createPerformance(device, metricService.findOneByLabel("centos file handle count"), time, count));
        list.add(super.createPerformance(device, metricService.findOneByLabel("centos file handle limit"), time, limit));
    }

    private void setLoadAvgInfo(String data,Device device, Date time, List<Performance> list) {
        String[] array = data.trim().split("\\s+");
        double loadavg_1 = Double.parseDouble(array[0]);
        //double loadavg_10 = Double.parseDouble(array[1]);
        //double loadavg_15 = Double.parseDouble(array[2]);
        list.add(super.createPerformance(device, metricService.findOneByLabel("centos load avg"), time, loadavg_1));
    }

    private void setCpusInfo(String data, Device device, Date time, List<Performance> list) {
        String[] lines = data.split("\n");
//        String key = managedObject.getName() + "_" + CPU_KEY;
        List<CpuItem> oldCpus = this.cpuCacheService.get(device.getId());
        List<CpuItem> newCpus = Lists.newArrayList();
        for (String line : lines) {
            String[] array = line.trim().split("\\s+");
            if (array.length < 5) {
                continue;
            }
            if (!line.trim().startsWith("cpu")) {
                continue;
            }
            long total = 0;
            for (int i = 1; i < array.length; i++) {
                total += Long.parseLong(array[i]);
            }
            long idle = Long.parseLong(array[4]);
            String name = array[0];
            newCpus.add(new CpuItem(name, idle, total));
            break; //只要第一个求总数就可以
        }
        this.cpuCacheService.put(device.getId(), newCpus);
        if (oldCpus == null || oldCpus.isEmpty()) {
            return;
        }
        //计算使用率
        double sum_usage = 0;
        for (CpuItem cpuItem : newCpus) {
            for (CpuItem old : oldCpus) {
                if (cpuItem.getName().equals(old.getName())) {
                    double total = cpuItem.getTotal() - old.getTotal();
                    double idle = cpuItem.getIdle() - old.getIdle();
                    //double usage = 100 - (idle * 100 / total);
                    double usage = 100 * (total - idle) / total;
                    sum_usage += usage;
                    break;
                }
            }
        }
        double value = sum_usage / newCpus.size();
        list.add(super.createPerformance(device, metricService.findOneByLabel("centos load avg"), time, value));
    }

    private void setMemoryAndSwapInfo(String data, Device device, Date time, List<Performance> list) {
        Properties prop = PropertiesUtils.getProperties(data);
        String regex = "(\\d+)";
        Pattern p = Pattern.compile(regex);
        long memoryTotal = getValue(prop.getProperty("MemTotal"), p);
        long memFree = getValue(prop.getProperty("MemFree"), p);
        long bufferes = getValue(prop.getProperty("Buffers"), p);
        long cached = getValue(prop.getProperty("Cached"), p);
        //可用内存的计算方式 之前计算有问题
        memFree = memFree + bufferes + cached;
        double memoryUsage = ((double) (memoryTotal - memFree) / memoryTotal) * 100;

        list.add(super.createPerformance(device, metricService.findOneByLabel("centos memory total"), time, memoryTotal));
        list.add(super.createPerformance(device, metricService.findOneByLabel("centos memory usage"), time, memoryUsage));

        long swapTotal = getValue(prop.getProperty("SwapTotal"), p);
        long swapFree = getValue(prop.getProperty("SwapFree"), p);
        double swapUsage = ((double) (swapTotal - swapFree) / swapTotal) * 100;

        list.add(super.createPerformance(device, metricService.findOneByLabel("centos swap total"), time, swapTotal));
        list.add(super.createPerformance(device, metricService.findOneByLabel("centos swao usage"), time, swapUsage));
    }

    private long getValue(String data, Pattern p) {
        Matcher m = p.matcher(data);
        if (m.find()) {
            return Long.parseLong(m.group(1));
        }
        return 0;
    }

    private void setNetWorkInfo(String data, Device device, Date time, List<Performance> list) {
        String[] lines = data.split("\n");
        //计算总量
        long sum_r = 0;
        long sum_w = 0;
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (!line.matches("((eth)|(em))\\d+")) {
                continue;
            }
            String[] array = line.split("\\s+");
            String[] con = array[0].split(":");
            //String name = con[0];
            sum_r += Long.parseLong(con[1]);
            sum_w += Long.parseLong(array[9]);
        }

        list.add(super.createPerformance(device, metricService.findOneByLabel("centos network read"), time, sum_r));
        list.add(super.createPerformance(device, metricService.findOneByLabel("centos network write"), time, sum_w));
    }

    private void setHttpConnect(String data, Device device, Date time, List<Performance> list) {
        String[] lines = data.split("\n");
        long count = 0;
        for (String line : lines) {
            String port = line.substring(15, 19);
            String state = line.substring(34, 36);
            if ((port.equals("0050") || port.equals("01B1")) && state.equals("OA")) {
                count++;
            }
        }
        String label = "centos http connect";
        list.add(super.createPerformance(device, metricService.findOneByLabel(label), time, count));
    }

}
