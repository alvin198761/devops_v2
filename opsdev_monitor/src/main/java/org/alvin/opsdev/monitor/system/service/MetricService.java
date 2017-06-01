package org.alvin.opsdev.monitor.system.service;

import com.google.common.collect.Lists;
import org.alvin.opsdev.monitor.system.domain.CollectorType;
import org.alvin.opsdev.monitor.system.domain.Metric;
import org.alvin.opsdev.monitor.system.repository.MetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangzhichao on 2017/4/24.
 */
@Service
@Transactional(readOnly = true)
public class MetricService {

    @Autowired
    private MetricsRepository metricsRepository;

    public List<Metric> findByObjectType(CollectorType collectorType) {
        return this.metricsRepository.findByObjectType(collectorType);
    }

    public Metric findOne(Long id) {
        return this.metricsRepository.findOne(id);
    }

    public Metric findOneByLabel(String label) {
        return this.metricsRepository.findByLabel(label);
    }
}
