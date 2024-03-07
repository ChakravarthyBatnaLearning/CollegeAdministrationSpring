package com.college.student.cache.lru_lhm;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache_LHM<K, V> {
    private  final LinkedHashMap<K, V> lruCache_lhm;

    public LRUCache_LHM(int size) {
        this.lruCache_lhm = new LinkedHashMap<>(size, 0.75f, true) {
            @Override
            public boolean removeEldestEntry(Map.Entry entry) {
                return size() >= size;
            }
        };
    }

    public  LinkedHashMap<K, V> getLruCache_lhm() {
        if (lruCache_lhm != null) {
            synchronized (LRUCache_LHM.class) {
                if (lruCache_lhm != null) {
                    return lruCache_lhm;
                }
            }
        }
        return null;
    }
}