package org.alvin.opsdev.monitor.system.domain;

import org.alvin.opsdev.monitor.system.bean.enums.ActionType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@Entity(name = "action")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private ActionType type;
    private Date time;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
