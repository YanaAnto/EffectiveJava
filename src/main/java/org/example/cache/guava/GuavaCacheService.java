package org.example.cache.guava;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;
import org.example.cache.Cache;
import org.example.cache.CacheEntry;

public class GuavaCacheService<K> implements Cache<K> {

    private com.google.common.cache.Cache<K, CacheEntry> cache;

    public GuavaCacheService() {
        cache = CacheBuilder.newBuilder()
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .expireAfterAccess(EXPIRE_TIMEOUT, TimeUnit.SECONDS)
            .maximumSize(CAPACITY)
            .recordStats()
            .removalListener(new GuavaRemovalListener<K>())
            .build();
    }

    @Override
    public void put(K key, CacheEntry value) {
        cache.put(key, value);
    }

    @Override
    public CacheEntry get(K key) {
        return cache.getIfPresent(key);
    }

    public String getStatistic() {
        return MoreObjects.toStringHelper(cache.stats())
            .omitNullValues()
            .add("average load time:", cache.stats().averageLoadPenalty())
            .add("number of cache evictions", cache.stats().evictionCount())
            .toString();
    }
}
