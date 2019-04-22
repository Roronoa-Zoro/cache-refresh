package com.illegalaccess.cache.refresh;

/**
 * 缓存生命周期
 */
public interface CacheLifecycle {

    CacheLifecycleKey getCacheLifecycleKey();

    boolean reloadCache(String param);

    boolean invalidateCache(String param);
}
