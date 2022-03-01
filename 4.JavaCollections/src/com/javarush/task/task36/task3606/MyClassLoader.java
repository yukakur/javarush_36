package com.javarush.task.task36.task3606;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        FileInputStream inputStream = null;
        byte[] bytes = new byte[0];
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        try {
            inputStream = new FileInputStream(name);
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(null, bytes, 0, bytes.length);
    }
}
