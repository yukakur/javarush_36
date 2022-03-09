package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;

public class FunctionalTest {



    public void testStorage(Shortener shortener) {
        String str1 = Helper.generateRandomString();
        String str2 = Helper.generateRandomString();
        String str3 = new String(str1);
        Long l1 = shortener.getId(str1);
        Long l2 = shortener.getId(str2);
        Long l3 = shortener.getId(str3);
        Assert.assertNotEquals(l2, l1);
        Assert.assertNotEquals(l2, l3);
        Assert.assertEquals(l1, l3);
        String str11 = shortener.getString(l1);
        String str22 = shortener.getString(l2);
        String str33 = shortener.getString(l3);
        Assert.assertEquals(str1, str11);
        Assert.assertEquals(str2, str22);
        Assert.assertEquals(str3, str33);
    }
    @Test
    public void testHashMapStorageStrategy() {
        Shortener shortener = new Shortener(new HashMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testOurHashMapStorageStrategy() {
        Shortener shortener = new Shortener(new OurHashMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testFileStorageStrategy() {
        Shortener shortener = new Shortener(new FileStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testHashBiMapStorageStrategy() {
        Shortener shortener = new Shortener(new HashBiMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testDualHashBidiMapStorageStrategy() {
        Shortener shortener = new Shortener(new DualHashBidiMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testOurHashBiMapStorageStrategy() {
        Shortener shortener = new Shortener(new HashBiMapStorageStrategy());
        testStorage(shortener);
    }
}
