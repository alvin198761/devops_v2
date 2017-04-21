package org.alvin.opsdev.monitor.system.domain;

import org.alvin.opsdev.monitor.system.bean.enums.ObjectStatus;
import org.alvin.opsdev.monitor.system.bean.enums.ObjectType;

import javax.persistence.*;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Entity(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Enumerated(EnumType.ORDINAL)
    private ObjectStatus status;
    private Long parentId;
    @Enumerated(EnumType.ORDINAL)
    private ObjectType type;
    @JoinColumn(name = "g_id" ,referencedColumnName = "id")
    private DeviceGroup group;


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

    public ObjectStatus getStatus() {
        return status;
    }

    public void setStatus(ObjectStatus status) {
        this.status = status;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

    public DeviceGroup getGroup() {
        return group;
    }

    public void setGroup(DeviceGroup group) {
        this.group = group;
    }
}
