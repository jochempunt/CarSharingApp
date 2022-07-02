package main.Users;

import main.utils.jsonHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class LoginSignupTest {

    LoginSignup ls = LoginSignup.getInstance();

    @BeforeEach
    void setUp() {
        ls.SignUp("Hans","1234");
    }

    @AfterEach
    void tearDown() {
        jsonHandler.getInstance().deleteFile("src/data/clients/Hans.json");
    }

    @Test
    void logIn() {
        assertTrue(ls.LogIn("Hans","1234").isSuccess());
        assertFalse(ls.LogIn("sHans","1234").isSuccess());
        assertEquals("incorrect password",ls.LogIn("Hans","4321").getMessage());
    }

    @Test
    void signUp() {
        assertFalse(ls.SignUp("Hans","1234").isSuccess());
    }
}