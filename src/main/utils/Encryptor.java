package main.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public final class Encryptor {
    private final static int kLength = 160;
    private final static String hashAlgorithm = "PBKDF2WithHmacSHA1";
    private static int iterations = 256;
    private static SecureRandom secureRandom;


    private Encryptor() {
    }

    // the salt is used to make the hash more secure, its a randomly generated string
    public static String getNewSalt(int sLength) {
        if (sLength < 1) {
            System.err.println("error in getNew Salt: length must be atleast 1 or more");
            return "";
        }

        byte[] salt = new byte[sLength];

        try {
            secureRandom = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        secureRandom.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);

    }

    public static String hash(String password,String salt){

        char[] pwChars = password.toCharArray();
        byte[] sBytes = salt.getBytes();

        PBEKeySpec pbeKeySpec = new PBEKeySpec(pwChars,sBytes,iterations,kLength);

        // replacing the Chars with the null character for security reasons
        Arrays.fill(pwChars,Character.MIN_VALUE);

        try {
            // get the specified hash algorithm
            SecretKeyFactory skFactory = SecretKeyFactory.getInstance(hashAlgorithm);
            // use hash algorithm with  the specified "PasswordBasedEncryption-KeySpecifications"
            byte[] hashedPw = skFactory.generateSecret(pbeKeySpec).getEncoded();
            // we encode the hashed password with Based64 ,so that the output will be encoded in ASCII chars
            return Base64.getEncoder().encodeToString(hashedPw);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }finally {
            pbeKeySpec.clearPassword();
        }
    }


    public static  boolean correctPassword(String enteredPassword,String salt,String encryptedPassword){
        String hashedPassword = hash(enteredPassword,salt);
        return hashedPassword.equals(encryptedPassword);
    }



}
