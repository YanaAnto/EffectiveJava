package org.example.cache;

public interface Cache<K> {
     int CAPACITY = 3;
     int EXPIRE_TIMEOUT = 5;

    void put(K key, CacheEntry value);
    CacheEntry get(K key);
}
