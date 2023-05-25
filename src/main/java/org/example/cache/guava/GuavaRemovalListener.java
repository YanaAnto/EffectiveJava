package org.example.cache.guava;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import java.util.logging.Logger;
import org.example.cache.CacheEntry;

public class GuavaRemovalListener<K> implements RemovalListener<K, CacheEntry> {

    private static final Logger LOGGER = Logger.getLogger(GuavaCacheService.class.getName());

    @Override
    public void onRemoval(RemovalNotification<K, CacheEntry> removalNotification) {
        LOGGER.info("remove cache entry: " + removalNotification.getValue() + " for reason: "
            + removalNotification.getCause());
    }
}
