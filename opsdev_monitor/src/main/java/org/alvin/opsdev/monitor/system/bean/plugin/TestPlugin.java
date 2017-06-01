package org.alvin.opsdev.monitor.system.bean.plugin;

/**
 * Created by tangzhichao on 2017/5/31.
 */
public class TestPlugin {

    /**
     * 插件唯一编号
     * @return
     */
    public Long getId(){
        return 1L;
    }

    /**
     * 插件名称
     * @return
     */
    public String getName(){
        return "测试插件";
    }

    /**
     * 是否启用
     * @return
     */
    public boolean isEnabled(){return false;}

    public boolean getStatus(){
        return false;
    }
}
