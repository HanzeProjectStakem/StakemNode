package nl.hanze.stakem;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.security.MessageDigest;

public class Block {

    private final Integer index;
    private final String hash;
    private final String previousHash;
    private final Long timeStamp;
    private final List<Transaction> transactions;

    public Block(int index, String previousHash, Long timeStamp, List<Transaction> transactions) {
        this.index = index;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.transactions = transactions;
        this.hash = generateHash();
    }

    private String generateHash() {
        String dataToHash = index
                + previousHash
                + timeStamp
                + transactions; // TODO: merkle tree gebruiken om hash te krijgen van transactions

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

    public String getPreviousHash() {
        return this.previousHash;
    }

    public String getHash() {
        return this.hash;
    }
}