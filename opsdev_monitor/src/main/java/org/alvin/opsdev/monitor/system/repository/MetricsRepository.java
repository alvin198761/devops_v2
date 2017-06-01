package org.alvin.opsdev.monitor.system.repository;

import org.alvin.opsdev.monitor.system.domain.CollectorType;
import org.alvin.opsdev.monitor.system.domain.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tangzhichao on 2017/6/1.
 */
@Repository
public interface MetricsRepository extends JpaRepository<Metric, Long> {

    List<Metric> findByObjectType(CollectorType collectorType);

    Metric findByLabel(String label);
}
