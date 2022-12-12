package nl.hanze.stakem.blockchain;

public class Block {

    private int index;
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
        // TODO: Generate hash
        return "";
    }


    public String getHash() {
        return this.hash;
    }
}
