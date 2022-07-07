package main.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public final class Encryptor {
    private final static int K_LENGTH = 160;
    private final static String HASH_ALGORITHM = "PBKDF2WithHmacSHA1";
    private final static int ITERATIONS = 256;


    private Encryptor() {
    }

    // the salt is used to make the hash more secure, it's a randomly generated string
    public static String createNewSalt(int sLength) {
        if (sLength < 1) {
            System.err.println("error in getNew Salt: length must be at least 1 or more");
            return "";
        }

        byte[] salt = new byte[sLength];

        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        secureRandom.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);

    }

    public static String hash(String password, String salt) {

        char[] pwChars = password.toCharArray();
        byte[] sBytes = salt.getBytes();

        PBEKeySpec pbeKeySpec = new PBEKeySpec(pwChars, sBytes, ITERATIONS, K_LENGTH);

        // replacing the Chars with the null character for security reasons
        Arrays.fill(pwChars, Character.MIN_VALUE);

        try {
            // get the specified hash algorithm
            SecretKeyFactory skFactory = SecretKeyFactory.getInstance(HASH_ALGORITHM);
            // use hash algorithm with  the specified "PasswordBasedEncryption-KeySpecifications"
            byte[] hashedPw = skFactory.generateSecret(pbeKeySpec).getEncoded();
            // we encode the hashed password with Based64 ,so that the output will be encoded in ASCII chars
            return Base64.getEncoder().encodeToString(hashedPw);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } finally {
            pbeKeySpec.clearPassword();
        }
    }


    public static boolean correctPassword(String enteredPassword, String salt, String encryptedPassword) {
        String hashedPassword = hash(enteredPassword, salt);
        return hashedPassword.equals(encryptedPassword);
    }


}
