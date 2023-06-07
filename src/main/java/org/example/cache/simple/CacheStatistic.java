package org.example.cache.simple;


public class CacheStatistic {

    private int cacheEvictionNumber;
    private long avgMillisLoadCacheEntry;
    private long totalMilliseconds;
    private long numItemsAdded;

    public CacheStatistic() {
        cacheEvictionNumber = 0;
        avgMillisLoadCacheEntry = 0;
    }

    public void refreshAvgTimeLoadCacheEntry(long loadTime) {
        totalMilliseconds += loadTime;
        numItemsAdded++;
        avgMillisLoadCacheEntry = totalMilliseconds / numItemsAdded;
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
