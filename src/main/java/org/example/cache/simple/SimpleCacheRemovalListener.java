package org.example.cache.simple;

import java.util.logging.Logger;
import org.example.cache.Cache;
import org.example.cache.CacheEntry;
import org.example.cache.guava.GuavaCacheService;

public class SimpleCacheRemovalListener<K> {

    private static final Logger LOGGER = Logger.getLogger(GuavaCacheService.class.getName());

    public void onRemoval(K key, CacheEntry value) {
        LOGGER.info(String.format("Following entry with key - '%s' was removed: %s", key, value.toString()));
    }
}
