package com.illegalaccess.cache.refresh;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentMap;

/**
 * 任务处理分发
 * 根据入参对象的command值进度分发调用不同的接口方法
 */
@Slf4j
public abstract class CacheLifecycleHandler {

    private final String reload_command = "reload";
    private final String invalidate_command = "invalidate";

    private ConcurrentMap<CacheLifecycleKey, CacheLifecycle> lifecycleMap;

    public void setLifecycleMap(ConcurrentMap<CacheLifecycleKey, CacheLifecycle> lifecycleMap) {
        this.lifecycleMap = lifecycleMap;
    }

    protected abstract CacheLifecycleKey getCacheLifecycleKey(CacheLifecycleCommand command);

    public final void doInCacheLifecycle(CacheLifecycleCommand command) {

        CacheLifecycleKey cl = getCacheLifecycleKey(command);
        if (cl == null) {
            log.info("does not get lifecycle key with command:{}", command.getLifeCycleKey());
            return;
        }

        CacheLifecycle clc = lifecycleMap.get(cl);
        if (clc == null) {
            log.info("there is not implementation for CacheLifecycle:{}", clc);
            return;
        }

        log.info("CacheLifecycle:{} will {}", clc, command.getCommand());
        if (reload_command.equals(command.getCommand())) {
            clc.reloadCache(command.getParam());
            return;
        }

        clc.invalidateCache(command.getParam());
    }
}
