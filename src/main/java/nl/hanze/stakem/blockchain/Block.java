package nl.hanze.stakem.blockchain;

import com.google.gson.JsonObject;

public class Block {

    private int index;
    private String hash;
    private String previousHash;
    private JsonObject[] transactions;
    private Long timeStamp;

    public Block(JsonObject transaction, String previousHash) {
//        this.data = data;
//        this.previousHash = previousHash;
//        this.timeStamp = System.currentTimeMillis();
//        this.hash = calculateHash();
    }

    public String getPreviousHash() {
        return this.previousHash;
    }

    public String getHash() {
        return this.hash;
    }
}
