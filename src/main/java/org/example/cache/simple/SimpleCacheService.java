package org.example.cache.simple;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.example.cache.CacheEntry;

public class SimpleCacheService<K> {

    private final LinkedHashMap<K, CacheEntry> cache = new LinkedHashMap();
    private final Map<K, Instant> keyLastAccessMap = new HashMap<>();
    private final SimpleCacheRemovalListener<K> removalListener = new SimpleCacheRemovalListener();
    private int capacity = 100000;
    private int expireTimeout = 5;

    public SimpleCacheService() {
    }

    public SimpleCacheService(int capacity) {
        this.capacity = capacity;
    }

    public void put(K key, CacheEntry value) {
        evictCache(key);
        cache.put(key, value);
    }

    public CacheEntry get(K key) {
        evictCache(key);
        return cache.get(key);
    }

    private void removeCacheEntry(K key) {
        cache.remove(key);
        removalListener.onRemoval(key);
    }

    private void evictCache(K key) {
        if (keyLastAccessMap.get(key) == null) {
            keyLastAccessMap.put(key, Instant.now());
        }
        keyLastAccessMap.entrySet().forEach(item -> {
            var lastAccessDuration = Duration.between(keyLastAccessMap.get(item.getKey()), Instant.now());
            if (lastAccessDuration.getSeconds() >= expireTimeout) {
                removeCacheEntry(item.getKey());
            }
        });
        if (cache.size() >= capacity) {
            removeCacheEntry(cache.entrySet().iterator().next().getKey());
        }
    }
}
