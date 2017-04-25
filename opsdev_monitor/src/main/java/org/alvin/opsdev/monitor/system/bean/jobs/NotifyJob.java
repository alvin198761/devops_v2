package org.alvin.opsdev.monitor.system.bean.jobs;

import org.alvin.opsdev.monitor.system.bean.action.NotifyActionExecutor;
import org.alvin.opsdev.monitor.system.service.cache.AlertCacheService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by tangzhichao on 2017/4/24.
 */
@Component
public class NotifyJob {

    @Autowired
    private NotifyActionExecutor notifyActionExecutor;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void doNotify(){
        Logger.getLogger(this.getClass()).info("doNotify ..");
        // 首先检查是否已经从别处触发了通知
        // 如果没有，就去查看是否需要产生一个通知
        // 产生通知，并发送到所有的action 中
        // 记录通知发送信息，并报告产生了一次发送动作
        this.notifyActionExecutor.checkCanExecute();
    }
}
