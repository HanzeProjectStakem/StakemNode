package nl.hanze.stakem;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HashAlgorithm {
    public static final Charset UTF_8 = StandardCharsets.UTF_8;
    public static final String OUTPUT_FORMAT = "%-20s:%s";


    public static byte[] digest(byte[] input, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String generateHash(String input) {
        try {
            byte[] hash = digest(input.getBytes(UTF_8), "SHA-256");
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

