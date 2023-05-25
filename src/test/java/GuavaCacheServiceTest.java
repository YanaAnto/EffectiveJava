import java.util.logging.Logger;
import org.example.cache.CacheEntry;
import org.example.cache.guava.GuavaCacheService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GuavaCacheServiceTest {

    private static final Logger LOGGER = Logger.getLogger(GuavaCacheServiceTest.class.getName());
    private final String cacheValue = "default value";
    private final String cacheKey = "default key";
    private final String newCacheKey = "new key";
    private final GuavaCacheService<String> guavaCacheService = new GuavaCacheService();

    @Test
    @DisplayName("Cache was added successfully")
    void checkGuavaCacheServiceStoreEntry() {
        guavaCacheService.put(cacheKey, new CacheEntry(cacheValue));
        Assertions.assertEquals(guavaCacheService.get(cacheKey).value(), cacheValue,
            String.format("Cannot get cache entry value by key! Expecting: %s, but actual: %s",
                guavaCacheService.get(cacheKey).value(), cacheValue));
    }

    @Test
    @DisplayName("Cache was removed after 5 seconds successfully")
    void checkCacheEntryWasRemovedAfterExpiredTimeoutTest() throws InterruptedException {
        guavaCacheService.put(cacheKey, new CacheEntry(cacheValue));
        Thread.sleep(5000);
        guavaCacheService.put(newCacheKey, new CacheEntry(cacheValue));

        Assertions.assertNull(guavaCacheService.get(cacheKey),
            String.format("Cannot get cache entry value by key! Expecting: null, but actual: %s",
                guavaCacheService.get(cacheKey)));
        Assertions.assertEquals(guavaCacheService.get(newCacheKey).value(), cacheValue,
            String.format("Cannot get cache entry value by key! Expecting: %s, but actual: %s",
                guavaCacheService.get(newCacheKey).value(), cacheValue));
    }

    @AfterEach
    public void getStatistic() {
        LOGGER.info("Statistic: " + guavaCacheService.getStatistic());
    }
}
