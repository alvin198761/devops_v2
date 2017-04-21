package org.alvin.opsdev.monitor.system.service;

import com.google.common.collect.Lists;
import org.alvin.opsdev.monitor.system.bean.dto.AlertLevelBean;
import org.alvin.opsdev.monitor.system.bean.dto.AlertLevelTrendItem;
import org.alvin.opsdev.monitor.system.bean.dto.AlertStatusBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Service
@Transactional(readOnly = true)
public class AlertService {

    public AlertLevelBean getAlertLevel() {
        return new AlertLevelBean();
    }

    public AlertStatusBean getAlertStatus() {
        return new AlertStatusBean();
    }

    public List<AlertLevelTrendItem> getAlertLevelTrends() {
        return Lists.newArrayList();
    }
}
