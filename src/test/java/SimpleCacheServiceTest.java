import java.util.logging.Logger;
import org.example.cache.CacheEntry;
import org.example.cache.simple.SimpleCacheService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleCacheServiceTest {

    private final String cacheValue = "default value";
    private final String cacheKey = "default key";
    private final String newCacheKey = "new key";
    private final SimpleCacheService<String> simpleCacheService = new SimpleCacheService<>();

    @Test
    @DisplayName("Cache was added successfully")
    void checkCacheServiceStoreEntry() {
        simpleCacheService.put(cacheKey, new CacheEntry(cacheValue));
        Assertions.assertEquals(simpleCacheService.get(cacheKey).value(), cacheValue,
            String.format("Cannot get cache entry value by key! Expecting: %s, but actual: %s",
                simpleCacheService.get(cacheKey).value(), cacheValue));
    }
}
