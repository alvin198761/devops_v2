package org.alvin.opsdev.monitor.system.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.alvin.opsdev.monitor.system.bean.dto.GroupBean;
import org.alvin.opsdev.monitor.system.bean.dto.GroupListBean;
import org.alvin.opsdev.monitor.system.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tangzhichao on 2017/4/21.
 */
@RestController
@RequestMapping("group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @ResponseBody
    @RequestMapping(value = "list/{page}",method = RequestMethod.GET)
    @ApiOperation(value = "设备组列表" ,notes = "分页显示设备组")
    public GroupListBean list(@ApiParam(name = "page" ,required = true) @PathVariable("page") int page){
        return null;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "添加/修改 设备组")
    public void save(@ApiParam(name = "id" ) Long id,@ApiParam(name = "name" ,required = true) String name ,@ApiParam(name = "remark") String remark){

    }

    @ResponseBody
    @RequestMapping(value = "{id}" ,method = RequestMethod.GET)
    @ApiOperation(value = "获取一个设备组")
    public GroupBean find(@ApiParam(name = "id" ,required = true ) @PathVariable("id")  Long id){
        return null;
    }

    @RequestMapping(value = "{id}" ,method = RequestMethod.DELETE)
    public void del(@ApiParam(name = "id" ,required = true) @PathVariable("id") Long id){

    }

}
