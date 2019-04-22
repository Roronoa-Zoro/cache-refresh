### 使用说明
1.需要进行缓存更新的操作实现CacheLifecycle  
2.不同的CacheLifecycle的实现类需要使用不同的CacheLifecycleKey进行区分,需要实现接口CacheLifecycleKey,统一管理所以的key, 建议使用枚举  
3.示例程序见Junit  
4.发送的数据结构   
```json
{
	"command": "reload",
	"lifeCycleKey": "productCache",
	"param": "aaaa"
}
```
### 参数说明
1.command取值为有2种  
reload: 重新加载      
invalidate: 删除   
2.lifeCycleKey为使用方配置的tag名称  
3.param为具体传递的参数,根据不同情况分析,由对应的实现方确定   



### 需要的配置参数
1.cacheLifecycle.enable  是否启用   
2.cacheLifecycle.topic  使用的topic, 建议在一个体系内使用一个topic,并且分区数量使用1    
3.cacheLifecycle.tag  使用的tag, <strong>在一个体系内使用一个topic,通过tag进行消息过滤</strong>    
4.cacheLifecycle.consumerMinThread  消费者最小线程   建议1   
5.cacheLifecycle.consumerMaxThread  消费者最大线程   建议1   


### 流程概述
发送消息    
--> 进入listener    
--> filter by tag  
--> CacheLifecycleHandler的实现类根据参数对象的lifeCycleKey找到对应的CacheLifecycleKey    
--> 根据找到的具体的CacheLifecycleKey, 找到CacheLifecycle对应的实现类   
--> 根据参数对象的command进行操作
