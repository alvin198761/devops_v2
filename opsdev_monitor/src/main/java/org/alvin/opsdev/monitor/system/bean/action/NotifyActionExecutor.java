package org.alvin.opsdev.monitor.system.bean.action;

import org.alvin.opsdev.monitor.system.bean.enums.AlertLevel;
import org.alvin.opsdev.monitor.system.bean.enums.AlertStatus;
import org.alvin.opsdev.monitor.system.domain.Alert;
import org.alvin.opsdev.monitor.system.service.ActionService;
import org.alvin.opsdev.monitor.system.service.AlertService;
import org.alvin.opsdev.monitor.system.service.cache.AlertCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tangzhichao on 2017/4/25.
 */
@Component
public class NotifyActionExecutor implements IAction {

    @Autowired
    private AlertCacheService alertCacheService;
    @Autowired
    private AlertService alertService;
    @Autowired
    private ActionService actionService;
    @Autowired
    private ApplicationContext applicationContext;
    private Long sendTime;

    @Override
    public void execute(List<Alert> alerts) {
        this.applicationContext.getBeansOfType(IAction.class).values().forEach(action -> {
            action.execute(alerts);
        });
        this.alertCacheService.clear();
        this.sendTime = System.currentTimeMillis();
    }

    /**
     * 检查所有的警告，确认是否发送邮件，
     * <br/>
     * 如果有，就发送，病清空已经发送的警告
     */
    public void checkAlert() {
        List<Alert> alerts = this.alertCacheService.getAll();
        for (Alert alert : alerts) {
            if (alert.getStatus() == AlertStatus.ACTIVE
                    && alert.getLevel() == AlertLevel.CRITICAL
                    && alert.getCount() >= 3) {
                this.execute(alerts);
                return;
            }
        }
    }

    /**
     * 日常发通知
     */
    public void checkCanExecute() {
        long time = 1000 * 60 * 60;
        if (System.currentTimeMillis() - this.sendTime >= time) {
            execute(this.alertCacheService.getAll());
        }
        this.alertCacheService.clear();
    }
}
