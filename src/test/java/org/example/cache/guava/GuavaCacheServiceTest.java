package org.example.cache.guava;

import java.util.logging.Logger;
import org.example.cache.CacheEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GuavaCacheServiceTest {

    private static final Logger LOGGER = Logger.getLogger(GuavaCacheServiceTest.class.getName());
    private final String cacheValue = "default value";
    private final String cacheKey = "default key";
    private final String newCacheKey = "new key";

    @Test
    @DisplayName("Cache was added successfully")
    void checkGuavaCacheServiceStoreEntry() {
        GuavaCacheService<String> guavaCacheService = new GuavaCacheService();
        guavaCacheService.put(cacheKey, new CacheEntry(cacheValue));
        Assertions.assertEquals(guavaCacheService.get(cacheKey).value(), cacheValue,
            String.format("Cannot get cache entry value by key! Expecting: %s, but actual: %s",
                guavaCacheService.get(cacheKey).value(), cacheValue));
        LOGGER.info("Statistic: " + guavaCacheService.getStatistic());
    }

    @Test
    @DisplayName("Cache was removed after 5 seconds successfully")
    void checkCacheEntryWasRemovedAfterExpiredTimeoutTest() throws InterruptedException {
        GuavaCacheService<String> guavaCacheService = new GuavaCacheService();
        guavaCacheService.put(cacheKey, new CacheEntry(cacheValue));
        Thread.sleep(5000);
        guavaCacheService.put(newCacheKey, new CacheEntry(cacheValue));

        Assertions.assertNull(guavaCacheService.get(cacheKey),
            String.format("Cannot get cache entry value by key! Expecting: null, but actual: %s",
                guavaCacheService.get(cacheKey)));
        LOGGER.info("Statistic: " + guavaCacheService.getStatistic());
    }

    @Test
    @DisplayName("Cache was removed after size excess successfully")
    void checkCacheEntryWasRemovedAfterSizeExcessTest() {
        GuavaCacheService<String> guavaCacheService = new GuavaCacheService(1);
        guavaCacheService.put(cacheKey, new CacheEntry(cacheValue));
        guavaCacheService.put(newCacheKey, new CacheEntry(cacheValue));
        Assertions.assertNull(guavaCacheService.get(cacheKey));
        LOGGER.info("Statistic: " + guavaCacheService.getStatistic());
    }
}
