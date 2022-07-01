package main.Users;

import java.util.UUID;

public class Client {

    private UUID id;
    private String username;
    private String hashedPassword;

    Client(String username, String password, String hashedpassword) {
        this.username = username;
        this.hashedPassword = hashedpassword;
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
}
