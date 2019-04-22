package com.illegalaccess.cache.refresh.sample;

import com.illegalaccess.cache.refresh.CacheLifecycleKey;

/**
 * 不同的缓存实现类的 key
 */
public enum SampleCacheLifecycleKey implements CacheLifecycleKey {

    ProductCacheKey("productCache", "产品缓存"),
    MerchantCacheKey("merchantCache", "商户缓存");

    private String key;
    private String desc;

    SampleCacheLifecycleKey(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public CacheLifecycleKey getCacheLifecycleKeyWithVal(String val) {
        for (SampleCacheLifecycleKey clk : SampleCacheLifecycleKey.values()) {
            if (clk.getKey().equals(val)) {
                return clk;
            }
        }

        return null;
    }
}
