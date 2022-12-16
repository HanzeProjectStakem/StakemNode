package nl.hanze.stakem;

import java.util.LinkedList;

public class Ledger {
    LinkedList<Block> blockChain = new LinkedList<>();

    public void add(Block block) {
        blockChain.add(block);
    }

    public Block getLast() {
        return blockChain.getLast();
    }

    public int size() {
        return blockChain.size();
    }
}