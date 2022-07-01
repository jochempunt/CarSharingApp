package main.Users;

import com.google.gson.*;
import main.utils.jsonHandler;

import java.util.UUID;

public class Client {

    private UUID id;
    private String username;
    private String hashedPassword;

    public Client(String username, String hashedpassword) {
        this.username = username;
        this.hashedPassword = hashedpassword;
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                '}';
    }

    public JsonObject toJson(){
        jsonHandler j = jsonHandler.getInstance();
        return j.gson.toJsonTree(this,this.getClass()).getAsJsonObject();
    }



}
