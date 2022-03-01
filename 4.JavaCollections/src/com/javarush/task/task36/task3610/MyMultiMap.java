package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        //напишите тут ваш код
        int count = 0;
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            count += entry.getValue().size();
        }
        return count;
    }

    @Override
    public V put(K key, V value) {
        //напишите тут ваш код
        List<V> list;
        if (map.get(key) == null) {
            list = new ArrayList<>();
        } else list = map.get(key);
        if (map.containsKey(key) && list.size() == repeatCount) {
            list.remove(0);
            list.add(list.size(), value);
            if (list.size() > 2) {
                return list.get(list.size() - 2);
            } else return list.get(0);
        } else if (map.containsKey(key) && list.size() < repeatCount) {
            list.add(list.size(), value);
            if (list.size() > 2) {
                return list.get(list.size() - 2);
            } else return list.get(0);
        } else {
            list.add(value);
            map.put(key, list);
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        //напишите тут ваш код
        List<V> list = map.get(key);
        if (list == null) return null;

            V v = list.get(0);
            list.remove(0);
            if (list.size() == 0) {
                map.remove(key);
            }
        return v;
    }



    @Override
    public Set<K> keySet() {
        //напишите тут ваш код
        Set<K> set = new HashSet<>();
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            set.add(entry.getKey());
        }
        return set;
    }


    @Override
    public Collection<V> values() {
        //напишите тут ваш код
        List<V> list = new ArrayList();
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            for (V v :
                    entry.getValue()) {
                list.add(v);
            }
        }
        return list;
    }

    @Override
    public boolean containsKey(Object key) {
        //напишите тут ваш код
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        //напишите тут ваш код
        boolean hasValue = false;
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            for (V v : entry.getValue()) {
                if(entry.getValue().contains(value)) hasValue = true;
            }
        }
        return hasValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}