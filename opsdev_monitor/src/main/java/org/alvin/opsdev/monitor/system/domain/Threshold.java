package org.alvin.opsdev.monitor.system.domain;

import javax.persistence.*;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Entity(name = "threshold")
public class Threshold {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "did" ,referencedColumnName = "id")
    private Device device;
    @ManyToOne
    @JoinColumn(name = "mid" ,referencedColumnName = "id")
    private Metric metric;
    private Double warn;
    @Column(name = "`limit`")
    private Double limit;
    private Boolean enabled;


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

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public Double getWarn() {
        return warn;
    }

    public void setWarn(Double warn) {
        this.warn = warn;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }


    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
