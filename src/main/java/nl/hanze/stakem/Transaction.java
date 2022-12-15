package nl.hanze.stakem;

public class Transaction {
    private final String sender;
    private final String receiver;
    private final double amount;
    private Long timeStamp;

    public Transaction(String sender, String receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
}
