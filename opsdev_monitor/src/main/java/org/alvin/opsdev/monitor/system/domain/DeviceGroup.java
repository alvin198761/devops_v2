package org.alvin.opsdev.monitor.system.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Entity(name = "group")
public class DeviceGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(length = 500)
    private String remark;


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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
