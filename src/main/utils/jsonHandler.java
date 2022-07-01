package main.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class jsonHandler {

    private static jsonHandler instance;

    public final Gson gson;

    private jsonHandler(){
        gson = new Gson();
    }

    public static jsonHandler getInstance() {
        if (instance == null){
            instance = new jsonHandler();
        }
        return instance;
    }






}
