package main.Users;

import main.utils.Response;

public class LoginSignup {

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

        //TODO PasswordEncription
        return  new Response(true,"created new user");
    }

    public static boolean validNewUsername(String username){
        return username.matches("^[a-zA-Z/d]+");
    }

    private boolean uniqueUsername(String username){
        //TODO write script

        return false;
    }

    private String hashPassword(){
    return "";
    }





}
