package main.Users;

public class LoginSignup {

    private static LoginSignup instance;
    private LoginSignup() {}

    public static LoginSignup getInstance(){
        if(instance==null){
            instance = new LoginSignup();
        }
        return instance;
    }

    public void signUp(String username, String password){

    }



}
