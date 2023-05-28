package org.example.cache.simple;

import java.util.logging.Logger;

public class SimpleCacheRemovalListener<K> {

    private static final Logger LOGGER = Logger.getLogger(SimpleCacheRemovalListener.class.getName());

    public void onRemoval(K key) {
        LOGGER.info(key + " entry was removed from cache");
    }
}
