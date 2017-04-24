package org.alvin.opsdev.monitor.system.bean.annotation;

import org.alvin.opsdev.monitor.system.bean.enums.CollectorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2017/3/15.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Collector {

    CollectorType value();

}
