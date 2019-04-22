package com.illegalaccess.cache.refresh.sample;

import com.alibaba.fastjson.JSON;
import com.illegalaccess.cache.refresh.CacheLifecycleCommand;
import org.junit.Test;

public class DataTest {

    @Test
    public void test() {
        CacheLifecycleCommand cc = new CacheLifecycleCommand();
        cc.setCommand("reload");
        cc.setLifeCycleKey("productCache");
        cc.setParam("aaaa");
        System.out.println(JSON.toJSONString(cc));
    }
}
