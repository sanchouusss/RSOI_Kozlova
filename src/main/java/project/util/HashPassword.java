package project.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

    public static byte[] getHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        var digest = MessageDigest.getInstance("SHA-512");
        digest.reset();

        return digest.digest(password.getBytes(StandardCharsets.UTF_8));
    }
}
