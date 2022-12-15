package nl.hanze.stakem;

import java.util.HashMap;
import java.util.Map;

public class ProofOfStake {

    private final Map<String, Integer> validators;
    private final int minStake;
    private final int maxStake;

    public ProofOfStake() {
        this.validators = new HashMap<>();
        this.minStake = 250;
        this.maxStake = 500;
    }

    public void addValidator(String address, int stake) {
        if (stake >= this.minStake && stake <= this.maxStake) {
            // Add the validator to the list with their stake
            this.validators.put(address, stake);
        }
        validators.put(address, stake);
    }

    public void processBlock() {
        // TODO: create method to process a block (validate transactions in block)
    }

    public void chooseValidator() {
        // TODO: create algorithm to choose a validator to process the next block
    }
}
