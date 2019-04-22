package com.illegalaccess.cache.refresh;

import lombok.Data;

/**
 * 缓存生命周期命令, 即发送到topic的数据结构
 */
@Data
public class CacheLifecycleCommand {

    private String command;
    private String lifeCycleKey;
    private String param; //json格式的参数

}
