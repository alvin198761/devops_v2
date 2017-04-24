package org.alvin.opsdev.monitor.system.bean.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by tangzhichao on 2017/4/24.
 */
@Component
public class DataJob {

    /**
     * 清理数据库
     */
    @Scheduled(cron = "0 0 0 ? * SUN")
    public void clearDB(){
        System.out.println("清理数据库过期数据");
    }

    /**
     * 清理日志文件
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearLog(){
        System.out.println("清理日志文件");
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void addAlert(){

    }
}
