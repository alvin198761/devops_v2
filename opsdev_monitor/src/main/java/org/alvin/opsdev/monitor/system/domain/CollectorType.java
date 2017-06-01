package org.alvin.opsdev.monitor.system.domain;

import javax.persistence.*;

/**
 * Created by tangzhichao on 2017/5/31.
 */
@Entity(name = "collector_type")
public class CollectorType {

    @Id
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
