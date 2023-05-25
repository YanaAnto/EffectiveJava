package org.example.cache;

public interface Cache<K> {

    void put(K key, CacheEntry value);
    CacheEntry get(K key);
}
