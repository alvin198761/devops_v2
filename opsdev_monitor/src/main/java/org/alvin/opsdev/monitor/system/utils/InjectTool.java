package org.alvin.opsdev.monitor.system.utils;

import org.alvin.opsdev.monitor.system.bean.annotation.Collector;
import org.alvin.opsdev.monitor.system.bean.collector.ICollector;
import org.alvin.opsdev.monitor.system.bean.enums.CollectorType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangzhichao on 2017/4/24.
 */
@Component
public class InjectTool implements InitializingBean {

    private Map<CollectorType, ICollector> collectorInjectMap = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            Map<String, Object> map = applicationContext.getBeansWithAnnotation(Collector.class);
            map.forEach((k, v) -> {
                Collector c = v.getClass().getAnnotation(Collector.class);
                collectorInjectMap.put(c.value(), (ICollector) v);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ICollector getCollector(CollectorType type) {
        return this.collectorInjectMap.get(type);
    }
}
