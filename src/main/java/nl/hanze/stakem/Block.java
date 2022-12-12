package nl.hanze.stakem;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.security.MessageDigest;

public class Block {

    private Integer index;
    private String hash;
    private final String previousHash;
    private final Long timeStamp;
    private List<Transaction> transactions;

    public Block(int index, String previousHash, Long timeStamp, List<Transaction> transactions) {
        this.index = index;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.transactions = transactions;
        this.hash = generateHash();
    }

    public String getPreviousHash() {
        return this.previousHash;
    }

    public String generateHash() {
        String dataToHash = Integer.toString(index)
                + previousHash
                + Long.toString(timeStamp)
                + transactions.toString();

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

    public String getHash() {
        return this.hash;
    }
}
