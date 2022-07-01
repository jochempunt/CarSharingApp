package main.utils;

import main.Users.LoginSignup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EncryptorTest {

    @Test
    void correctPassword() {
        String userPw = "1234abcd";
        String salt = Encryptor.getNewSalt(12);
        String hashedPw = Encryptor.hash(userPw, salt);
        assertTrue(Encryptor.correctPassword("1234abcd", salt, hashedPw));
    }



    @Test
    void getNewSalt() {
        String salt = Encryptor.getNewSalt(12);
        // test if salt characters are indeed printable ascii characters
        assertTrue(salt.matches("^[\\u0000-\\u007F]*$"));

    }

}