package org.alvin.opsdev.monitor.system.service;

import com.google.common.collect.Lists;
import org.alvin.opsdev.monitor.system.bean.enums.CollectorType;
import org.alvin.opsdev.monitor.system.domain.Metric;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangzhichao on 2017/4/24.
 */
@Service
@Transactional(readOnly = true)
public class MetricService {
    public List<Metric> findByObjectType(CollectorType collectorType) {
        return Lists.newArrayList();
    }
}
