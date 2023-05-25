package org.example.cache.simple;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import org.example.cache.Cache;
import org.example.cache.CacheEntry;

public class SimpleCacheService<K> implements Cache<K> {

    private final LinkedHashMap<K, CacheEntry> cache = new LinkedHashMap();
    private final Map<K, Integer> usageCounts = new HashMap<>();
    private final Map<Integer, LinkedHashSet<K>> frequencyLists = new HashMap<>();
    private int minFrequency = 0;
    private SimpleCacheRemovalListener<K> removalListener = new SimpleCacheRemovalListener();

    @Override
    public void put(K key, CacheEntry value) {
        if (cache.containsKey(key)) {
            cache.put(key, value);
            increaseUsageCount(key);
        } else {
            if (cache.size() >= CAPACITY) {
                evictLFUEntry();
            }
            cache.put(key, value);
            usageCounts.put(key, 1);
            addToFrequencyList(1, key);
            minFrequency = 1;
        }
    }

    public void remove(K key) {
        if (!cache.containsKey(key)) {
            return;
        }
        int frequency = usageCounts.get(key);
        usageCounts.remove(key);
        frequencyLists.get(frequency).remove(key);
        removalListener.onRemoval(key, cache.get(key));
        cache.remove(key);
    }

    @Override
    public CacheEntry get(K key) {
        increaseUsageCount(key);
        return cache.get(key);
    }

    private void increaseUsageCount(K key) {
        int frequency = usageCounts.get(key);
        usageCounts.put(key, frequency + 1);

        frequencyLists.get(frequency).remove(key);
        addToFrequencyList(frequency + 1, key);
        if (frequency == minFrequency && frequencyLists.get(frequency).isEmpty()) {
            minFrequency++;
        }
    }

    private void addToFrequencyList(int frequency, K key) {
        frequencyLists.putIfAbsent(frequency, new LinkedHashSet<>());
        frequencyLists.get(frequency).add(key);
    }

    private void evictLFUEntry() {
        LinkedHashSet<K> minFrequencyList = frequencyLists.get(minFrequency);
        K evictKey = minFrequencyList.iterator().next();
        minFrequencyList.remove(evictKey);
        remove(evictKey);
        usageCounts.remove(evictKey);
    }
}
