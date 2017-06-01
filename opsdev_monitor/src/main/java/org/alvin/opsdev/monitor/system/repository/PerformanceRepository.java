package org.alvin.opsdev.monitor.system.repository;

import org.alvin.opsdev.monitor.system.domain.Device;
import org.alvin.opsdev.monitor.system.domain.Metric;
import org.alvin.opsdev.monitor.system.domain.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by tangzhichao on 2017/6/1.
 */
@Repository
public interface PerformanceRepository extends JpaRepository<Performance,Long>{

    List<Performance> findAllByDeviceAndMetricAndTimeBetween(Device device, Metric metric , Date begin ,Date end);

    List<Performance> findAllByTimeBefore(Date time);
}
