package org.alvin.opsdev.monitor.controller;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.alvin.opsdev.monitor.bean.ObjBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by Administrator on 2017/3/16.
 */
@RestController
@RequestMapping("api/obj")

public class ObjController {

    @ApiOperation(value="查询监控对象列表", notes="根据页面分页显示监控对象列表")
    @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "Long")
    @RequestMapping(value = "list/{page}" ,method = RequestMethod.GET)
    public List<ObjBean> list(@PathVariable("page") int page){
//        Pageable pageable = new PageRequest(page,10);
        return Lists.newArrayList();
    }


}
