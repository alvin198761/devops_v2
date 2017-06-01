package org.alvin.opsdev.monitor.system.domain;

import org.alvin.opsdev.monitor.system.bean.enums.MetricType;

import javax.persistence.*;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Entity(name = "metric")
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String label;
    private Double warn;
    @Column(name = "`limit`")
    private Double limit;
    @ManyToOne
    @JoinColumn(name = "t_id", referencedColumnName = "id")
    private CollectorType objectType;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "metric_type")
    private MetricType metricType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public CollectorType getObjectType() {
        return objectType;
    }

    public void setObjectType(CollectorType objectType) {
        this.objectType = objectType;
    }

    public MetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }
}
