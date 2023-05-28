package org.example.cache.guava;

import org.example.cache.CacheEntry;
import org.example.cache.simple.SimpleCacheService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleCacheServiceTest {

    private final String cacheValue = "default value";
    private final String cacheKey = "default key";
    private final String newCacheKey = "new key";

    @Test
    @DisplayName("Cache was added successfully")
    void checkCacheServiceStoreEntry() {
        SimpleCacheService<String> simpleCacheService = new SimpleCacheService<>();
        simpleCacheService.put(cacheKey, new CacheEntry(cacheValue));
        Assertions.assertEquals(simpleCacheService.get(cacheKey).value(), cacheValue,
            String.format("Cannot get cache entry value by key! Expecting: %s, but actual: %s",
                simpleCacheService.get(cacheKey).value(), cacheValue));
    }

    @Test
    @DisplayName("Cache was removed after 5 seconds successfully")
    void checkCacheEntryWasRemovedAfterExpiredTimeoutTest() throws InterruptedException {
        SimpleCacheService<String> simpleCacheService = new SimpleCacheService<>();
        simpleCacheService.put(cacheKey, new CacheEntry(cacheValue));
        Thread.sleep(5000);
        simpleCacheService.put(newCacheKey, new CacheEntry(cacheValue));
        Assertions.assertNull(simpleCacheService.get(cacheKey));
    }

    @Test
    @DisplayName("Cache was removed after size excess successfully")
    void checkCacheEntryWasRemovedAfterSizeExcessTest() {
        SimpleCacheService<String> simpleCacheService = new SimpleCacheService<>(1);
        simpleCacheService.put(cacheKey, new CacheEntry(cacheValue));
        simpleCacheService.put(newCacheKey, new CacheEntry(cacheValue));
        Assertions.assertNull(simpleCacheService.get(newCacheKey));
    }
}
