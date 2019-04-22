package com.illegalaccess.cache.refresh.sample;

import com.illegalaccess.cache.refresh.CacheLifecycle;
import com.illegalaccess.cache.refresh.CacheLifecycleKey;
import org.springframework.stereotype.Service;

/**
 * 实际进行缓存操作的实现类
 */
@Service
public class SampleCacheLifecycle implements CacheLifecycle {

    @Override
    public CacheLifecycleKey getCacheLifecycleKey() {
        return SampleCacheLifecycleKey.MerchantCacheKey;
    }

    @Override
    public boolean reloadCache(String param) {
        //把param转成对应的bean  进行缓存操作
        return false;
    }

    @Override
    public boolean invalidateCache(String param) {
        //把param转成对应的bean  进行缓存操作
        return false;
    }
}
