package main.Users;

import com.google.gson.*;
import main.utils.jsonHandler;

public class Client {


    private String username;
    private String hashedPassword;
    private String salt;



    public Client(String username, String hashedPassword, String salt) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.salt = salt;


    }



    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public JsonObject toJson(){
        jsonHandler j = jsonHandler.getInstance();
        return j.gson.toJsonTree(this,this.getClass()).getAsJsonObject();
    }



}
