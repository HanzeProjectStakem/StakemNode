package nl.hanze.stakem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MerkleTree {
    public static Node generateTree(ArrayList<String> datablocks) {
        ArrayList<Node> childNodes = new ArrayList<>();

        for (String message : datablocks) {
            childNodes.add(new Node(null, null, HashAlgorithm.generateHash(message)));
        }

        return buildTree(childNodes);
    }

    private static Node buildTree(ArrayList<Node> children) {
        ArrayList<Node> parents = new ArrayList<>();

        while (children.size() > 1) {
            int i = 0, length = children.size();
            while (i < length) {
                Node leftChild = children.get(i);
                Node rightChild = null;

                if ((i + 1) < length) {
                    rightChild = children.get(i + 1);
                } else {
                    rightChild = new Node(null, null, leftChild.getHash());
                }

                String parentHash = HashAlgorithm.generateHash(leftChild.getHash() + rightChild.getHash());
                parents.add(new Node(leftChild, rightChild, parentHash));
                i += 2;
            }

            children = parents;
            parents = new ArrayList<>();
        }
        return children.get(0);
    }

    private static void printLevelOrderTraversal(Node root) {
        if (root == null) {
            return;
        }

        if (root.getLeft() == null && root.getRight() == null) {
            System.out.println(root.getHash());
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        queue.add(null);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node != null) {
                System.out.println(node.getHash());
            } else {
                System.out.println();
                if (!queue.isEmpty()) {
                    queue.add(null);
                }
            }

            if (node != null && node.getLeft() != null) {
                queue.add(node.getLeft());
            }

            if (node != null && node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
    }
}
