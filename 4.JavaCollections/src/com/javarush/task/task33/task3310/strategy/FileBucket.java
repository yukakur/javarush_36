package com.javarush.task.task33.task3310.strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket implements Serializable {
    private Path path;

    public FileBucket() {
        try {
            path = Files.createTempFile(null, null);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        path.toFile().deleteOnExit();
    }
    public long getFileSize() {
        try {
            return Files.size(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }
    public void putEntry(Entry entry) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path));
            oos.writeObject(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Entry getEntry() {
        if(getFileSize() == 0) return null;
        try {
            ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path));
            return (Entry) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
            return null;
    }
    public void remove() {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
