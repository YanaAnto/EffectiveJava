package org.example.cache;


import com.google.common.base.MoreObjects;

public record CacheEntry(String value) {

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .omitNullValues()
            .add("value", value)
            .toString();
    }
}
