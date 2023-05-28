package org.example.cache.guava;

import com.google.common.base.MoreObjects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;
import org.example.cache.CacheEntry;

public class GuavaCacheService<K> {

    private Cache<K, CacheEntry> cache;
    private final int capacity = 100000;
    private final int expireTimeout = 5;

    public GuavaCacheService() {
        cache = CacheBuilder.newBuilder()
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .expireAfterAccess(expireTimeout, TimeUnit.SECONDS)
            .maximumSize(capacity)
            .recordStats()
            .removalListener(new GuavaRemovalListener<K>())
            .build();
    }

    public GuavaCacheService(int maxSize) {
        cache = CacheBuilder.newBuilder()
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .expireAfterAccess(expireTimeout, TimeUnit.SECONDS)
            .maximumSize(maxSize)
            .recordStats()
            .removalListener(new GuavaRemovalListener<K>())
            .build();
    }

    public void put(K key, CacheEntry value) {
        cache.put(key, value);
    }

    public CacheEntry get(K key) {
        return cache.getIfPresent(key);
    }

    public String getStatistic() {
        return MoreObjects.toStringHelper(cache.stats())
            .omitNullValues()
            .add("Average load time: ", cache.stats().averageLoadPenalty())
            .add("Number of cache evictions: ", cache.stats().evictionCount())
            .toString();
    }
}
