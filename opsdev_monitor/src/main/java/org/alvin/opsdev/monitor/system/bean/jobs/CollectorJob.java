package org.alvin.opsdev.monitor.system.bean.jobs;

import com.google.common.collect.Lists;
import org.alvin.opsdev.monitor.system.bean.CollectorTicket;
import org.alvin.opsdev.monitor.system.bean.collector.ICollector;
import org.alvin.opsdev.monitor.system.bean.enums.ObjectStatus;
import org.alvin.opsdev.monitor.system.domain.Device;
import org.alvin.opsdev.monitor.system.domain.DeviceGroup;
import org.alvin.opsdev.monitor.system.service.CheckMetricService;
import org.alvin.opsdev.monitor.system.service.DeviceService;
import org.alvin.opsdev.monitor.system.service.GroupService;
import org.alvin.opsdev.monitor.system.utils.InjectTool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Component
@EnableScheduling
public class CollectorJob implements InitializingBean {

    private BlockingQueue<CollectorTicket> blockingQueue = new SynchronousQueue<CollectorTicket>();
    private static Logger logger = Logger.getLogger(CollectorJob.class);
    @Autowired
    private GroupService groupService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private InjectTool injectTool;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private CheckMetricService checkMetricsService;

    @Scheduled(cron = "0/60 * * * * *")
    public void producer() {
        CollectorTicket ticket = new CollectorTicket();
        ticket.setId(UUID.randomUUID().toString());
        ticket.setTime(new Date());
        try {
            this.blockingQueue.put(ticket);
            logger.info("create ticket :" + ticket);
        } catch (InterruptedException e) {
            logger.error(e);
            logger.info("create ticket failed");
        }
    }

    /**
     * 紧循环来获取任务，并处理
     */
    public void consumer() {
        while (true) {
            try {
                CollectorTicket ticket = blockingQueue.take();
                logger.info("consumer: " + ticket);
                //处理组
                List<DeviceGroup> groups = this.groupService.getAllGroup();
                List<Future> futures = Lists.newArrayList();
                groups.forEach(group -> doCollect(group, ticket, futures));
                //处理没有组的设备
                List<Device> devices = this.deviceService.findGroupIsNullAndEnabled();
                devices.forEach(dev -> this.deviceCollector(null, ticket, futures, dev));
                //统一处理等待结束
                waitFor(futures);
                //统一计算处理逻辑
                checkMetricsService.check(groups,devices,ticket);
            } catch (InterruptedException e) {
                logger.error(e);
                logger.info("take ticket failed");
            }
        }
    }

    private void waitFor(List<Future> futures) throws InterruptedException {
        for (Future f : futures) {
            try {
                f.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private void doCollect(DeviceGroup item, CollectorTicket ticket, List<Future> futures) {
        List<Device> devices = this.deviceService.findByGroupAndEnabled(item);
        Assert.notNull(devices, "devices must not be null");
        devices.forEach(dev -> deviceCollector(item, ticket, futures, dev));
    }

    private void deviceCollector(DeviceGroup item, CollectorTicket ticket, List<Future> futures, Device dev) {
        ICollector collector = injectTool.getCollector(dev.getCollectorType());
        Assert.notNull(collector, dev.getCollectorType() + " collector not impl");
        //获取状态
        dev.setStatus(collector.getStatus());
        //是否连接成功
        if(dev.getStatus() != ObjectStatus.SUCCESS){
            return ;
        }
        //是否开启采集
        if (!collector.isEnabled()) {
            return;
        }
        //提交采集任务
        futures.add(this.threadPoolTaskExecutor.submit(() ->
                collector.collect(ticket, item, dev)
        ));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(this::consumer).start();
    }
}
