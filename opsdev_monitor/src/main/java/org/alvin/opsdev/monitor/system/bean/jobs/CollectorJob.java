package org.alvin.opsdev.monitor.system.bean.jobs;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Component
@EnableScheduling
public class CollectorJob {

    @Scheduled(cron = "0/60 * * * * *")
    public void collector() {
        System.out.println("start");
    }
}
