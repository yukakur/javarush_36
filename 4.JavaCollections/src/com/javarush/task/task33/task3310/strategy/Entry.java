package com.javarush.task.task33.task3310.strategy;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class Entry implements Serializable {
    int hash;
    Long key;
    String value;
    Entry next;

    public Entry(int hash, Long key, String value, Entry next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public Long getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Entry entry = (Entry) obj;
        return Objects.equals(key, entry.key) && Objects.equals(value, entry.value);
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
