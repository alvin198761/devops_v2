package org.alvin.opsdev.monitor.system.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Entity(name="performance")
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "mid" ,referencedColumnName = "id")
    private Metric metric;
    private Double value;
    private Date time;
    @JoinColumn(name = "did" ,referencedColumnName = "id")
    @ManyToOne
    private Device device;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
