package main.Users;

import com.google.gson.*;

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

    public String fromJSON(){

        Gson g = new Gson();
        String  gg=  g.toJson(this,this.getClass());
        JsonObject jsonObject = JsonParser.parseString(gg).getAsJsonObject();

        Client c = g.fromJson(jsonObject,Client.class);


        return c.toString();
    }

}
