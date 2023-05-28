package org.example.cache.simple;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.example.cache.CacheEntry;

public class SimpleCacheService<K> {

    private final LinkedHashMap<K, CacheEntry> cache = new LinkedHashMap();
    private final Map<K, Instant> keyLastAccessMap = new HashMap<>();
    private final SimpleCacheRemovalListener<K> removalListener = new SimpleCacheRemovalListener();
    private final Map<K, Integer> keyFrequencyMap = new HashMap();
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
        keyLastAccessMap.remove(key);
        removalListener.onRemoval(key);
    }

    private void evictCache(K key) {
        keyLastAccessMap.put(key, Instant.now());
        keyFrequencyMap.putIfAbsent(key, 0);
        keyFrequencyMap.merge(key, 1, Integer::sum);
        Set<Entry<K, Instant>> keysSet = Set.copyOf(keyLastAccessMap.entrySet());
        keysSet.forEach(item -> {
            var lastAccessDuration = Duration.between(keyLastAccessMap.get(item.getKey()),
                Instant.now());
            if (lastAccessDuration.getSeconds() >= expireTimeout) {
                removeCacheEntry(item.getKey());
            }
        });
        if (cache.size() >= capacity) {
            K keyToRemove = keyFrequencyMap.entrySet().stream().sorted(Entry.comparingByValue())
                .findFirst().get().getKey();

            keyFrequencyMap.remove(keyToRemove);
            removeCacheEntry(keyToRemove);
        }
    }
}
