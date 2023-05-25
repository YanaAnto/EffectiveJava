package org.example.cache.simple;

import java.util.Map;
import org.example.cache.Cache;
import org.example.cache.CacheEntry;

public class SimpleCacheService<K> implements Cache<K> {

    private Map<K, CacheEntry> cacheMap;

    @Override
    public void put(K key, CacheEntry value) {
        cacheMap.put(key, value);
    }

    @Override
    public CacheEntry get(K key) {
        return cacheMap.getOrDefault(key, null);
    }
}
