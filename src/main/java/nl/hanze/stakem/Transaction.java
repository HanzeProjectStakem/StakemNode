package nl.hanze.stakem;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

public class Transaction {

    private final Integer version;
    private final String sender;
    private final String recipient;
    private final Integer amount;
    private final Long timeStamp;
    private final Signature signature;
    private final String hash;

    public Transaction(Integer version, String sender, String recipient, Integer amount, Long timeStamp, Signature signature) {
        this.version = version;
        this.timeStamp = timeStamp;
        this.signature = signature;
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.hash = generateHash();
    }

    public String getHash() {
        return this.hash;
    }

    private String generateHash() {
        String dataToHash = version
                + sender
                + recipient
                + amount
                + timeStamp
                + signature;

        byte[] bytes = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        assert bytes != null;
        return bytesToHex(bytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xff & aByte);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}