package com.septeo.ulyses.technical.test.cache;

import com.septeo.ulyses.technical.test.entity.Brand;

import java.util.List;
import java.util.function.Supplier;

public class BrandCache {

    private static final long EXPIRATION_TIME_MS = 5 * 60 * 1000;

    private volatile List<Brand> cachedBrands = null;
    private volatile long lastUpdate = 0;

    public synchronized List<Brand> getOrLoad(Supplier<List<Brand>> supplier) {
        long now = System.currentTimeMillis();

        if (cachedBrands == null || now - lastUpdate > EXPIRATION_TIME_MS) {
            List<Brand> loaded = supplier.get();
            if (loaded != null) {
                cachedBrands = loaded;
                lastUpdate = now;
            }
        }
        return cachedBrands;
    }

    public synchronized void clear() {
        cachedBrands = null;
        lastUpdate = 0;
    }

    public synchronized void refresh(Supplier<List<Brand>> supplier) {
        List<Brand> loaded = supplier.get();
        if (loaded != null) {
            cachedBrands = loaded;
            lastUpdate = System.currentTimeMillis();
        }
    }
}
