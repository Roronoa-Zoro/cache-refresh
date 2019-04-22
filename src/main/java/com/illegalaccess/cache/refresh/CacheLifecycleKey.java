package com.illegalaccess.cache.refresh;

/**
 * 标识接口
 * 有多个CacheLifecycle处理器时, 用于区分, 子类建议使用枚举实现
 */
public interface CacheLifecycleKey {

    CacheLifecycleKey getCacheLifecycleKeyWithVal(String val);
}
