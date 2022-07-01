package main.Users;

import com.google.gson.*;
import main.utils.jsonHandler;

import java.util.UUID;

public class Client {


    private String username;
    private String encryptedPassword;
    private String salt;

    public Client(String username, String encryptedPassword,String salt) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.salt = salt;


    }



    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return encryptedPassword;
    }



    public JsonObject toJson(){
        jsonHandler j = jsonHandler.getInstance();
        return j.gson.toJsonTree(this,this.getClass()).getAsJsonObject();
    }



}
