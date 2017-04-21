package org.alvin.opsdev.monitor.system.service;

import org.alvin.opsdev.monitor.system.bean.dto.DeviceStatusBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tangzhichao on 2017/4/21.
 */

@Service
@Transactional(readOnly = true)
public class DeviceService {

    public DeviceStatusBean getDevStatus(){
        return new DeviceStatusBean();
    }
}
