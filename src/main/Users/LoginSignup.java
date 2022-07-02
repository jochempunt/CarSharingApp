package main.Users;

import main.utils.Encryptor;
import main.utils.Response;
import main.utils.jsonHandler;

public class LoginSignup {

    private String clientPath = "src/data/clients/";

    private static LoginSignup instance;
    private LoginSignup() {}

    public static LoginSignup getInstance(){
        if(instance==null){
            instance = new LoginSignup();
        }
        return instance;
    }

    public Response SignUp(String username, String password){
        if (!validNewUsername(username)){
            return new Response(false,"usename can only contain alphanumeric values");
        }
        if (!uniqueUsername(username)){
            return new Response(false,"username is already taken");
        }

        if (password.length()<3){
            return new Response(false,"password has to be longer then 3 characters");
        }

        //password encryption

        String userSalt = Encryptor.getNewSalt(12);

        String hashedPw = Encryptor.hash(password,userSalt);

        Client newClient = new Client(username,hashedPw,userSalt);

        jsonHandler j = jsonHandler.getInstance();

        j.writeJsonfile(clientPath,username,newClient.toJson());

        //TODO write in json file

        return  new Response(true,"created new user");
    }

    public static boolean validNewUsername(String username){
        return username.matches("^[a-zA-Z/d]+");
    }

    private boolean uniqueUsername(String username){
        //TODO write script

        return true;
    }







}
