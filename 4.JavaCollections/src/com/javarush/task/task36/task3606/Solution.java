package com.javarush.task.task36.task3606;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* 
Осваиваем ClassLoader и Reflection
*/

public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("secondhiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("firsthiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {
        File [] files = new File(packageName).listFiles();
        for (int i = 0; i < files.length; i++) {
            if(files[i].toString().endsWith(".class")) {
                hiddenClasses.add(new MyClassLoader().loadClass(files[i].toString()));
            }
        }
    }


    public HiddenClass getHiddenClassObjectByKey(String key)  {
        for (Class clazz :
                hiddenClasses) {
            if((clazz.getSimpleName().toLowerCase()).startsWith(key.toLowerCase())) {
                Constructor constructor = null;
                try {
                    constructor = clazz.getDeclaredConstructor();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                constructor.setAccessible(true);
                try {
                    return (HiddenClass)constructor.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

