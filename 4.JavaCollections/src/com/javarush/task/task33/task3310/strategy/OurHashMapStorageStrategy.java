package com.javarush.task.task33.task3310.strategy;

import java.util.Objects;

public class OurHashMapStorageStrategy implements StorageStrategy{
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private int threshold = (int)(DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    private float loadFactor = DEFAULT_LOAD_FACTOR;

    public int hash(Long k) {
        final int h;
        return (k == null) ? 0 : (h = k.hashCode()) ^ (h >>> 16);
    }
    public int indexFor(int hash, int length) {
        return hash & (length - 1);
    }
    public Entry getEntry(Long key) {
        int hash = (key == null) ? 0 : hash((long)key.hashCode());
        for(Entry e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
            long k;
            if(e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }
        return null;
    }
    public void resize(int newCapacity) {
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int)(newCapacity * loadFactor);
    }
    public void transfer(Entry[] newTable) {
        Entry[] src = table;
        int newCapacity = newTable.length;
        for (int i = 0; i < src.length; i++) {
            Entry e = src[i];
            if(e != null) {
                src[i] = null;
                do {
                    Entry next = e.next;
                    int j = indexFor(e.hash, newCapacity);
                    e.next = newTable[j];
                    newTable [j] = e;
                    e = next;
                } while (e != null);
            }
        }
    }
    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
        if(size ++ >= threshold)
            resize(2 * table.length);
    }
    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
        size++;
    }
    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        if (table != null && size > 0) {
            for (final Entry bucket : table) {
                for (Entry e = bucket; e != null; e = e.next) {
                    if (Objects.equals(e.value, value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void put(Long key, String value) {
        final int hash = (key == null) ? 0 : hash((long) key.hashCode());
        final int index = indexFor(hash, table.length);

        if (table[index] != null) {
            for (Entry e = table[index]; e != null; e = e.next) {
                if (e.hash == hash && Objects.equals(e.key, key)) {
                    e.value = value;
                    return;
                }
            }
            addEntry(hash, key, value, index);
        } else {
            createEntry(hash, key, value, index);
        }
    }

    @Override
    public Long getKey(String value) {
        if (table != null && size > 0) {
            for (final Entry bucket : table) {
                for (Entry e = bucket; e != null; e = e.next) {
                    if (Objects.equals(e.value, value)) {
                        return e.key;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        return getEntry(key).getValue();
    }
}
