package main.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.Users.Client;

import java.io.*;

public class jsonHandler {

    private static jsonHandler instance;

    public final Gson gson;

    public FileWriter writer;

    public FileReader reader;
    private jsonHandler(){
        gson = new Gson();
    }

    public static jsonHandler getInstance() {
        if (instance == null){
            instance = new jsonHandler();
        }
        return instance;
    }

    public void writeJsonfile(String filePath, String fileName,JsonObject json){
        try {
            writer = new FileWriter(filePath+fileName+".json");
            gson.toJson(json,writer);
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public <T>T readJsonFile(String filePath, String fileName, Class c){
        try {

            reader = new FileReader(filePath+fileName+".json");
            T object = (T) gson.fromJson(reader,c);
            return object;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }









}
