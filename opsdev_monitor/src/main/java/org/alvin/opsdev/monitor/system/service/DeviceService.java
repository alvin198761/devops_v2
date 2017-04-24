package org.alvin.opsdev.monitor.system.service;

import com.google.common.collect.Lists;
import org.alvin.opsdev.monitor.system.bean.dto.DeviceStatusBean;
import org.alvin.opsdev.monitor.system.domain.Device;
import org.alvin.opsdev.monitor.system.domain.DeviceGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangzhichao on 2017/4/21.
 */

@Service
@Transactional(readOnly = true)
public class DeviceService {

    public DeviceStatusBean getDevStatus() {
        return new DeviceStatusBean();
    }

    /**
     * 有组的设备
     * @param group
     * @return
     */
    public List<Device> findByGroupAndEnabled(DeviceGroup group) {
        return Lists.newArrayList();
    }

    /**
     * 没有组的设备
     * @return
     */
    public List<Device> findGroupIsNullAndEnabled() {
        return Lists.newArrayList();
    }
}
