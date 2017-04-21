package org.alvin.opsdev.monitor.system.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.alvin.opsdev.monitor.system.bean.dto.AlertLevelBean;
import org.alvin.opsdev.monitor.system.bean.dto.AlertLevelTrendItem;
import org.alvin.opsdev.monitor.system.bean.dto.AlertStatusBean;
import org.alvin.opsdev.monitor.system.bean.dto.DeviceStatusBean;
import org.alvin.opsdev.monitor.system.service.AlertService;
import org.alvin.opsdev.monitor.system.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@RestController
@RequestMapping("/console")
public class MainConsoleController {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private AlertService alertService;

    @ApiOperation(value = "设备状态分布", notes = "设备状态的整体比例")
    @ResponseBody
    @RequestMapping(value = "dev/status", method = RequestMethod.GET)
    public DeviceStatusBean deviceStatus() {
        return this.deviceService.getDevStatus();
    }

    @ApiOperation(value = "告警级别分布图", notes = "告警级别的整体比例")
    @ResponseBody
    @RequestMapping(value = "alert/level", method = RequestMethod.GET)
    public AlertLevelBean alertLevel() {
        return this.alertService.getAlertLevel();
    }

    @ApiOperation(value = "告警状态分布图", notes = "告警状态的整体比例")
    @ResponseBody
    @RequestMapping(value = "alert/status", method = RequestMethod.GET)
    public AlertStatusBean alertStatus() {
        return this.alertService.getAlertStatus();
    }

    @ApiOperation(value = "告警级别分布趋势图", notes = "某个时间段以内的告警级别趋势")
    @ResponseBody
    @RequestMapping(value = "alert/level/trend", method = RequestMethod.GET)
    public List<AlertLevelTrendItem> getLevelTrends(
            @ApiParam(name = "begin", required = true, example = "2017-04-21 10:10:01") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date begin,
            @ApiParam(name = "end", required = true, example = "2017-04-21 10:10:01") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end
    ) {
        return this.alertService.getAlertLevelTrends();
    }


}
