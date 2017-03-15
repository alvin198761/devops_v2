package org.alvin.opsdev.monitor.controller;

import org.alvin.opsdev.monitor.bean.ObjBean;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;

/**
 * Created by Administrator on 2017/3/16.
 */
@RestController
@RequestMapping("api/obj")
public class ObjController {

    @RequestMapping("list/{page}")
    public Page<ObjBean> list(@PathVariable("page") int page){
        return null;
    }


}
