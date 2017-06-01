package org.alvin.opsdev.monitor.system.service;

import org.alvin.opsdev.monitor.system.domain.Performance;
import org.alvin.opsdev.monitor.system.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangzhichao on 2017/6/1.
 */
@Service
@Transactional(readOnly = true)
public class PerformanceService {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Transactional
    public void save(List<Performance> performanceList) {
        this.performanceRepository.save(performanceList);
    }
}
