package utils;

import com.ning.http.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Simple utility class for password salts and hashing
 *
 * @author Kristian Haugene
 */
public class PasswordHelper {

    public static final int SALT_LENGTH = 16;

    /**
     * Generates a random salt value
     *
     * @return a byte array of random bytes
     */
    public static String nextSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return Base64.encode(salt);
    }

    /**
     * Does a SHA-256 hash of the given input
     *
     * @param input message to hash
     * @return hashed message
     */
    public static String hash(String input) {
        // Initialize string for final hash
        String hash;

        try {
            // Get a MessageDigest from java.security, set algorithm SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Fill up the message digest with bytes from hashed input string
            md.update(input.getBytes("UTF-8"));

            // Get the hash, base64 encode the bytes
            hash = Base64.encode(md.digest());

        } catch (NoSuchAlgorithmException nsae) {
            throw new RuntimeException("Tried to hash password with non-existing algorithm", nsae);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Tried to hash password with unsupported encoding", uee);
        }

        // Return it
        return hash;
    }
}