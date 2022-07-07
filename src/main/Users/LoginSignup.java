package main.Users;

import main.utils.Encryptor;
import main.utils.Response;
import main.utils.jsonHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class LoginSignup {






    private static LoginSignup instance;
    private final String clientPath = "src/data/clients/";

    private Client currentClient = null;
    private Map<String, Client> clients = getAllClients();

    private LoginSignup() {
    }

    public static LoginSignup getInstance() {
        if (instance == null) {
            instance = new LoginSignup();
        }
        return instance;
    }

    public static boolean validNewUsername(String username) {
        return username.matches("^[a-zA-Z0-9]+");
    }


    public Response LogIn(String username, String password) {
        if (username.length() < 1) {
            return new Response(false, "empty username");
        }
        if (password.length() < 1) {
            return new Response(false, "empty password");
        }
        if (uniqueUsername(username)) {
            return new Response(false, "unknown username");
        }

        Client tempClient = getAllClients().get(username);


        if (!Encryptor.correctPassword(password, tempClient.getSalt(), tempClient.getHashedPassword())) {
            return new Response(false, "incorrect password");
        } else {
            currentClient = tempClient;
            return new Response(true, "Welcome: " + username);
        }
    }


    public Response SignUp(String username, String password) {
        if (!validNewUsername(username)) {
            return new Response(false, "username can only contain alphanumeric values");
        }
        if (!uniqueUsername(username)) {
            return new Response(false, "username is already taken");
        }

        if (password.length() < 3) {
            return new Response(false, "password has to be longer then 3 characters");
        }


        //password encryption

        String userSalt = Encryptor.createNewSalt(12);

        String hashedPw = Encryptor.hash(password, userSalt);

        Client newClient = new Client(username, hashedPw, userSalt);

        jsonHandler j = jsonHandler.getInstance();

        j.writeJsonfile(clientPath, username, newClient.toJson());

        clients.put(newClient.getUsername(), newClient);


        return new Response(true, "created new user");
    }

    private TreeMap<String, Client> getAllClients() {
        File folder = new File(clientPath);
        File[] files = folder.listFiles();
        TreeMap<String, Client> allClients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        if (files.length > 0) {
            ArrayList<String> usernames = new ArrayList<>();
            for (File file : files) {
                // replace with regex which  removes everything that comes after the dot
                if (file.getName().endsWith(".json")) {
                    usernames.add(file.getName().replaceFirst("[.][.json]+$", ""));
                }
            }
            for (String username : usernames) {
                Client tempClient = jsonHandler.getInstance().readJsonFileToObject(clientPath, username, Client.class);
                allClients.put(username, tempClient);
            }
            return allClients;
        } else {
            return allClients;
        }
    }


    private boolean uniqueUsername(String username) {
        return !clients.containsKey(username);
    }


    public Client getCurrentClient() {
        return currentClient;
    }
}
