package com.javarush.task.task33.task3310.strategy;


import java.util.Objects;

public class FileStorageStrategy implements StorageStrategy{
    private FileBucket [] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;
    private int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    private long maxBucketSize;

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public FileStorageStrategy() {
        for (int i = 0; i < table.length; i++) {
            table[i] = new FileBucket();
        }
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    public int getSize() {
        return size;
    }

    public long getMaxBucketSize() {
        return maxBucketSize;
    }

    public int hash(Long k) {
        final int h;
        return (k == null) ? 0 : (h = k.hashCode()) ^ (h >>> 16);
    }
    public int indexFor(int hash, int length) {
        return hash & (length - 1);
    }
    public Entry getEntry(Long key) {
        int hash = (key == null) ? 0 : hash((long)key.hashCode());
        for(Entry e = table[indexFor(hash, table.length)].getEntry(); e != null; e = e.next) {
            long k;
            if(e.hash == hash && (k = e.key) == key)
                return e;
        }
        return null;
    }
    public void resize(int newCapacity) {
        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
        table = newTable;
        maxBucketSize = 0;
        for(FileBucket bucket: table) {
            long currentBucketSize = bucket.getFileSize();
            maxBucketSize = Math.max(currentBucketSize, maxBucketSize);
        }
    }
    public void transfer(FileBucket[] newTable) {
        FileBucket[] src = table;
        int newCapacity = newTable.length;
        for (int i = 0; i < src.length; i++) {
            Entry e = src[i].getEntry();
            if(e != null) {
                src[i] = null;
                do {
                    Entry next = e.next;
                    int j = indexFor(e.hash, newCapacity);
                    e.next = newTable[j].getEntry();
                    newTable [j].putEntry(e);
                    e = next;
                } while (e != null);
            }
        }
    }
    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        long currentBucketSize = table[bucketIndex].getFileSize();
        maxBucketSize = Math.max(currentBucketSize, maxBucketSize);
        if (maxBucketSize > bucketSizeLimit) resize(2 * table.length);
    }
    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        size++;
        long currentBucketSize = table[bucketIndex].getFileSize();
        maxBucketSize = Math.max(currentBucketSize, maxBucketSize);
    }
    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        if (table != null && size > 0) {
            for ( FileBucket bucket : table) {
                for (Entry e = bucket.getEntry(); e != null; e = e.next) {
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

        if (table[index].getEntry() != null) {
            for (Entry e = table[index].getEntry(); e != null; e = e.next) {
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
            for (final FileBucket bucket : table) {
                for (Entry e = bucket.getEntry(); e != null; e = e.next) {
                    if (Objects.equals(e.value, value)) {
                        return e.key;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String getValue(final Long key) {
        final Entry e = getEntry(key);
        return e == null ? null : e.value;
    }
}
