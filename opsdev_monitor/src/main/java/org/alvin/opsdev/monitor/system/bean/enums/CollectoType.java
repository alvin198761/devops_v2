package org.alvin.opsdev.monitor.system.bean.enums;

/**
 * Created by tangzhichao on 2017/4/21.
 */
public enum CollectoType {

    OS(1), JAVA(2), MYSQL(3), MYCAT(4), KAFKA(5), REDIS(6), ZOOKEEPER(7), TOMCAT(8);

    CollectoType(int id) {
        this.id = id;
    }

    private int id;

    public int getId() {
        return this.id;
    }

}
