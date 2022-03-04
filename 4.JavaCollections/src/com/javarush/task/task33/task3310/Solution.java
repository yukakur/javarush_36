package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> ids = new HashSet<>();
        for(String str: strings) {
            ids.add(shortener.getId(str));
        }
        return ids;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> strs = new HashSet<>();
        for(Long lng : keys) {
            strs.add(shortener.getString(lng));
        }
        return strs;
    }
    public static void testStrategy(StorageStrategy strategy,long elementNumber) {
        Helper.printMessage(strategy.getClass().getSimpleName());
        Shortener shortener = new Shortener(strategy);
        Set<String> setOfStrings = new HashSet<>();
        for (int i = 0; i < elementNumber; i++) {
            String str = Helper.generateRandomString();
            setOfStrings.add(str);
        }
        Date date = new Date();
        Set<Long> ids = getIds(shortener, setOfStrings);
        long diff = new Date().getTime() - date.getTime();
        Helper.printMessage(diff + "");
        Date date1 = new Date();
        Set<String> collectedStrings = getStrings(shortener, ids);
        diff = new Date().getTime() - date.getTime();
        Helper.printMessage(diff + "");
        
        if(collectedStrings.equals(setOfStrings)) {
            Helper.printMessage("Тест пройден.");
        } else Helper.printMessage("Тест не пройден.");

    }
    public static void main(String[] args) {
        Solution.testStrategy(new HashMapStorageStrategy(), 1000);
        Solution.testStrategy(new OurHashMapStorageStrategy(), 1000);
        Solution.testStrategy(new OurHashBiMapStorageStrategy(), 1000);
        Solution.testStrategy(new FileStorageStrategy(), 100);
        Solution.testStrategy(new HashBiMapStorageStrategy(), 1000);
        Solution.testStrategy(new DualHashBidiMapStorageStrategy(), 1000);


    }
}
