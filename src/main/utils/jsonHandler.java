package main.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class jsonHandler {

    private static jsonHandler instance;

    public final Gson gson;

    public FileWriter writer;

    public FileReader reader;

    private jsonHandler() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public static jsonHandler getInstance() {
        if (instance == null) {
            instance = new jsonHandler();
        }
        return instance;
    }

    public void writeJsonfile(String filePath, String fileName, JsonObject json) {
        try {
            writer = new FileWriter(filePath + fileName + ".json");
            gson.toJson(json, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // this method always returns an Object of the Wanted type from the Given Class;
    public <T> T readJsonFileToObject(String filePath, String fileName, Class c) {
        try {

            reader = new FileReader(filePath + fileName + ".json");
            T object = (T) gson.fromJson(reader, c);
            reader.close();
            return object;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String filepath) {
        try {
            File file = new File(String.valueOf(Path.of(filepath)));
            System.out.println();
            Files.deleteIfExists(Paths.get(file.getAbsolutePath()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
