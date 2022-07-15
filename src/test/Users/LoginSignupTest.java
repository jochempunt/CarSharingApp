package Users;

import main.Users.LoginSignup;
import main.utils.jsonHandler;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;



class LoginSignupTest {

    static LoginSignup ls = LoginSignup.getInstance();

    @BeforeAll
    static void setUp() {
        ls.SignUp("Hans","1234");
    }

    @AfterAll
    static void tearDown() {
        jsonHandler.getInstance().deleteFile("src/data/clients/Hans.json");
        jsonHandler.getInstance().deleteFile("src/data/clients/Franzi66.json");
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
        assertTrue(ls.SignUp("Franzi66","abc").isSuccess());

    }
}