package org.alvin.opsdev.monitor.system.utils;

import com.google.common.collect.Maps;
import org.alvin.opsdev.monitor.system.bean.annotation.Collector;
import org.alvin.opsdev.monitor.system.bean.collector.ICollector;
import org.alvin.opsdev.monitor.system.bean.plugin.PluginBean;
import org.alvin.opsdev.monitor.system.bean.plugin.PluginClassLoader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by tangzhichao on 2017/4/24.
 */
@Component
public class InjectTool implements InitializingBean {

    private Map<Long, ICollector> collectorInjectMap = Maps.newHashMap();

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PluginClassLoader pluginClassLoader;

    @Override
    public void afterPropertiesSet() throws Exception {
        //加载采集器
        try {
            Map<String, Object> map = applicationContext.getBeansWithAnnotation(Collector.class);
            map.forEach((k, v) -> {
                Collector c = v.getClass().getAnnotation(Collector.class);
                collectorInjectMap.put(c.value(), (ICollector) v);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 加载插件
        collectorInjectMap.putAll(pluginClassLoader.loadPlugins());
    }

    public ICollector getCollector(Long type) {
        ICollector collector = this.collectorInjectMap.get(type);
        Assert.notNull(collector, MessageFormat.format("collector {0} not impl", type));
        return collector;
    }
}
