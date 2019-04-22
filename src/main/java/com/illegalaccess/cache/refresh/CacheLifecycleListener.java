package com.illegalaccess.cache.refresh;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 从mq监听 指定的topic
 * 发送到topic的数据格式{command:reload/invalidate, lifeCycleKey:CacheLifecycleKey}
 * 然后根据key进行分发找到具体的实现类, 根据method调用对应的方法
 */
@Slf4j
@ConditionalOnProperty(value = "cacheLifecycle.enable", havingValue = "true")
@Service
@RocketMQMessageListener(topic = "${cacheLifecycle.topic}", selectorType = SelectorType.TAG,
        selectorExpression = "${cacheLifecycle.tag}", consumerGroup = "${cacheLifecycle.listener.group}")
public class CacheLifecycleListener implements RocketMQListener<CacheLifecycleCommand>, RocketMQPushConsumerLifecycleListener {

    @Autowired
    private List<CacheLifecycle> cacheLifecycleList;
    @Autowired
    private CacheLifecycleHandler cacheLifecycleHandler;
    @Value("${cacheLifecycle.consumerMinThread}")
    private int minThread;
    @Value("${cacheLifecycle.consumerMaxThread}")
    private int maxThread;
    @Value("${cacheLifecycle.topic}")
    private String topic;
    @Value("${cacheLifecycle.tag}")
    private String tag;

    private ConcurrentMap<CacheLifecycleKey, CacheLifecycle> lifecycleMap;

    @PostConstruct
    public void init() {
        lifecycleMap = new ConcurrentHashMap<>(cacheLifecycleList.size());
        cacheLifecycleList.forEach(c -> lifecycleMap.putIfAbsent(c.getCacheLifecycleKey(), c));
        cacheLifecycleHandler.setLifecycleMap(lifecycleMap);
        log.info("CacheLifecycleListener init...............");
    }

    @Override
    public void onMessage(CacheLifecycleCommand command) {
        log.info("incoming cache lifecycle command:{}", command);
        cacheLifecycleHandler.doInCacheLifecycle(command);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setConsumeThreadMax(maxThread);
        defaultMQPushConsumer.setConsumeThreadMin(minThread);
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        try {
            defaultMQPushConsumer.unsubscribe(topic);
            defaultMQPushConsumer.subscribe(topic, tag);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        log.info("CacheLifecycleListener defaultMQPushConsumer>>>>{}", defaultMQPushConsumer.hashCode());
    }
}
