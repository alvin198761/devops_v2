package org.alvin.opsdev.monitor.system.bean.action;

import org.alvin.opsdev.monitor.system.domain.Alert;

import java.util.List;

/**
 * Created by tangzhichao on 2017/4/24.
 */
public interface IAction {

    void execute(List<Alert> alerts);

}
