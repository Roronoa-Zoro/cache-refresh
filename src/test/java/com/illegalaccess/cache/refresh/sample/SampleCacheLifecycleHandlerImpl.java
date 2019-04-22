package com.illegalaccess.cache.refresh.sample;

import com.illegalaccess.cache.refresh.CacheLifecycleCommand;
import com.illegalaccess.cache.refresh.CacheLifecycleHandler;
import com.illegalaccess.cache.refresh.CacheLifecycleKey;
import org.springframework.stereotype.Component;

/**
 * 统一缓存处理器实现类 路由器
 */
@Component
public class SampleCacheLifecycleHandlerImpl extends CacheLifecycleHandler {

    @Override
    protected CacheLifecycleKey getCacheLifecycleKey(CacheLifecycleCommand command) {
        CacheLifecycleKey clk = SampleCacheLifecycleKey.ProductCacheKey.getCacheLifecycleKeyWithVal(command.getLifeCycleKey());
        return clk;
    }
}
