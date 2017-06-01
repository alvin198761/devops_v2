package org.alvin.opsdev.monitor.system.bean.plugin;

import org.alvin.opsdev.monitor.system.domain.CollectorType;

/**
 * Created by tangzhichao on 2017/5/31.
 */
public class PluginBean {

    private CollectorType type;
    private Object pluginInstance;
    private String collectorMethod;


    public CollectorType getType() {
        return type;
    }

    public void setType(CollectorType type) {
        this.type = type;
    }

    public Object getPluginInstance() {
        return pluginInstance;
    }

    public void setPluginInstance(Object pluginInstance) {
        this.pluginInstance = pluginInstance;
    }

    public String getCollectorMethod() {
        return collectorMethod;
    }

    public void setCollectorMethod(String collectorMethod) {
        this.collectorMethod = collectorMethod;
    }
}
