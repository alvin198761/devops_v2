package org.alvin.opsdev.monitor.system.domain;

import org.alvin.opsdev.monitor.system.bean.enums.AlertLevel;
import org.alvin.opsdev.monitor.system.bean.enums.AlertStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Entity(name = "`alert`")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "dev_id", referencedColumnName = "id")
    @ManyToOne
    private Device device;
    @Column(name = "`count`")
    private Integer count;
    @Enumerated(EnumType.ORDINAL)
    private AlertLevel level;
    @Enumerated(EnumType.ORDINAL)
    private AlertStatus status;
    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public AlertLevel getLevel() {
        return level;
    }

    public void setLevel(AlertLevel level) {
        this.level = level;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
