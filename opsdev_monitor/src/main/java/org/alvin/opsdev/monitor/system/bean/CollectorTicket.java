package org.alvin.opsdev.monitor.system.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tangzhichao on 2017/4/24.
 */
public class CollectorTicket {

    private String id;
    private Date time;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("*********\n" +
                "{id:%s,time:%s }\n" +
                "******", this.id,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.time));
    }
}
