package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {
    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
        Date date = new Date();
        for (String str : strings) {
            ids.add(shortener.getId(str));
        }
        return new Date().getTime() - date.getTime();
    }
    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Date date = new Date();
        for (Long l : ids) {
            strings.add(shortener.getString(l));
        }
        return new Date().getTime() - date.getTime();
    }
    @Test
    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origStrings = new HashSet<>();
        Set<Long> ids = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            origStrings.add(Helper.generateRandomString());
        }
        long time1 = getTimeToGetIds(shortener1, origStrings, ids);
        long time2 = getTimeToGetIds(shortener2, origStrings, ids);
        Assert.assertTrue(time1 > time2);
        long time3 = getTimeToGetStrings(shortener1, ids, origStrings);
        long time4 = getTimeToGetStrings(shortener2, ids, origStrings);
        Assert.assertEquals(time3, time4, 30);


    }
}
