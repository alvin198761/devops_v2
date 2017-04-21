package org.alvin.opsdev.monitor.system.domain;

import org.alvin.opsdev.monitor.system.bean.enums.AlertLevel;

/**
 * Created by tangzhichao on 2017/4/21.
 */
public class Rule {
    private Long id;
    private String name;
    private String description;
    private AlertLevel level;
    private Integer count;
    private Long interval;
}
