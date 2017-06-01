package org.alvin.opsdev.monitor.system.service.cache;

import org.alvin.opsdev.monitor.system.bean.cache.CpuItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by tangzhichao on 2017/6/1.
 */
@Component
public class CpuCacheService extends AbstractCacheService<Long,List<CpuItem>>{

    private int interva = 1;
    private int fixedRate = 3;

    public CpuCacheService() {
        super(3,  TimeUnit.MILLISECONDS);
    }

}
