package org.alvin.opsdev.monitor.system.domain;

import org.alvin.opsdev.monitor.system.bean.enums.AlertLevel;
import org.alvin.opsdev.monitor.system.bean.enums.AlertStatus;

import javax.persistence.*;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Entity(name = "alert")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "dev_id", referencedColumnName = "id")
    private Device device;
    private int count;
    @Enumerated(EnumType.ORDINAL)
    private AlertLevel level;
    @Enumerated(EnumType.ORDINAL)
    private AlertStatus status;

}
