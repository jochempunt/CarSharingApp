package main.Users;

import main.utils.Encryptor;
import main.utils.Response;
import main.utils.jsonHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LoginSignup {

    private static LoginSignup instance;
    private final String clientPath = "src/data/clients/";
    private Map<String, Client> clients;

    private LoginSignup() {
        clients = getAllClients();


    }

    public static LoginSignup getInstance() {
        if (instance == null) {
            instance = new LoginSignup();
        }
        return instance;
    }

    public static boolean validNewUsername(String username) {
        return username.matches("^[a-zA-Z/d]+");
    }

    public Response SignUp(String username, String password) {
        if (!validNewUsername(username)) {
            return new Response(false, "usename can only contain alphanumeric values");
        }
        if (!uniqueUsername(username)) {
            return new Response(false, "username is already taken");
        }

        if (password.length() < 3) {
            return new Response(false, "password has to be longer then 3 characters");
        }

        //password encryption

        String userSalt = Encryptor.getNewSalt(12);

        String hashedPw = Encryptor.hash(password, userSalt);

        Client newClient = new Client(username, hashedPw, userSalt);

        jsonHandler j = jsonHandler.getInstance();

        j.writeJsonfile(clientPath, username, newClient.toJson());

        clients = getAllClients();


        return new Response(true, "created new user");
    }

    private TreeMap<String, Client> getAllClients() {
        File folder = new File(clientPath);
        File[] files = folder.listFiles();
        ArrayList<String> usernames = new ArrayList<>();
        TreeMap<String, Client> allClients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (File file : files) {
            // replace with regex which  removes everything that comes after the dot
            if (file.getName().endsWith(".json")) {
                usernames.add(file.getName().replaceFirst("[.][.json]+$", ""));
            }

        }
        for (int i = 0; i < usernames.size(); i++) {
            Client tempClient = jsonHandler.getInstance().readJsonFileToObject(clientPath, usernames.get(i), Client.class);
            allClients.put(usernames.get(i), tempClient);
        }
        return allClients;
    }

    private boolean uniqueUsername(String username) {

        if (clients.containsKey(username)){
            return false;
        }

        return true;
    }


}
