package org.alvin.opsdev.monitor.system.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.alvin.opsdev.monitor.system.bean.dto.DeviceBean;
import org.alvin.opsdev.monitor.system.bean.dto.DeviceListBean;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@RestController
@RequestMapping("device")
public class DeviceController {

    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "添加设备")
    @ApiImplicitParam(name = "device", required = true, dataType = "DeviceBean")
    public void save(@RequestBody DeviceBean device) {
    }

    @ResponseBody
    @RequestMapping(value = "{page}" ,method = RequestMethod.GET)
    @ApiOperation(value = "分页显示设备列表")
    public DeviceListBean list(@CookieValue("groupId") long groupId ,@ApiParam(name = "page" ,required = true) @PathVariable("page") Integer page){
        return null;
    }
}
