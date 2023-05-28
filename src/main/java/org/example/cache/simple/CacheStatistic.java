package org.example.cache.simple;


public class CacheStatistic {

    private int cacheEvictionNumber;
    private long avgMillisLoadCacheEntry;

    public CacheStatistic() {
        cacheEvictionNumber = 0;
        avgMillisLoadCacheEntry = 0;
    }

    public void refreshAvgTimeLoadCacheEntry(long loadTime) {
        avgMillisLoadCacheEntry = (avgMillisLoadCacheEntry + loadTime) / 2;
    }

    public void increaseCacheEvictionNumber() {
        this.cacheEvictionNumber++;
    }

    public int getCacheEvictionNumber() {
        return cacheEvictionNumber;
    }

    public long getAvgTimeLoadCacheEntry() {
        return avgMillisLoadCacheEntry;
    }
}
