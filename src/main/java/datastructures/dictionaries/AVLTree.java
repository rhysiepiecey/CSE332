package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.exceptions.NotYetImplementedException;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {

    private class AVLNode extends BSTNode {

        public int height;
        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;
        }
    }

    public AVLTree() {
        super();
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V oldVal = find(key);
        this.root = insert(key, value, makeAVL(this.root));
        return oldVal;
    }

    private AVLNode insert(K key, V value, AVLNode current) {

        // reached bottom of tree so add node
        if (current == null) {
            current = new AVLNode(key, value);
            this.size++;
        } else {
            int direction = Integer.signum(key.compareTo(current.key));
            if (direction == 0) {
                // found node location and it already exists
                // replace value no need to fix anything
                current.value = value;
            } else {
                // determine to go left or right
                int child = Integer.signum(direction + 1);
                current.children[child] = insert(key, value, makeAVL(current.children[child]));
                current = rebalance(key, current);
            }
        }
        updateHeight(current);
        return current;
    }

    private AVLNode rebalance(K key, AVLNode node) {
        int bf = balanceFactor(node);
        if (bf > 1) {
            if (key.compareTo(node.children[0].key) < 0) {
                // left left
                return rotateRight(node);
            } else {
                node.children[0] = rotateLeft(makeAVL(node.children[0]));
                return rotateRight(node);
            }
        } else if (bf < -1) {
            if (key.compareTo(node.children[1].key) > 0) {
                return rotateLeft(node);
            } else {
                node.children[1] = rotateRight(makeAVL(node.children[1]));
                return rotateLeft(node);
            }
        }
        return node;
    }

    // typecast to AVLNode
    private AVLNode makeAVL(BSTNode node) {
        return (AVLNode) node;
    }

    // returns bf at node
    private int balanceFactor(AVLNode node) {
        return height(makeAVL(node.children[0])) - height(makeAVL(node.children[1]));
    }

    // get height
    private int height(AVLNode node) {
        return node == null ? -1 : node.height;
    }

    private void updateHeight(AVLNode node) {
        int leftSubTreeHeight = height(makeAVL(node.children[0]));
        int rightSubTreeHeight = height(makeAVL(node.children[1]));
        // take tallest subtree and add 1 to account for current node
        node.height = Math.max(leftSubTreeHeight, rightSubTreeHeight) + 1;
    }

    private AVLNode rotateRight(AVLNode node) {
        // get left child of root
        AVLNode left = makeAVL(node.children[0]);
        AVLNode temp = makeAVL(left.children[1]);

        // make root the left child's right child
        left.children[1] = node;

        // make root left child be cur left child's right child
        node.children[0] = temp;

        updateHeight(node);
        updateHeight(left);

        return left;
    }

    private AVLNode rotateLeft(AVLNode node) {
        // get right child fo root
        AVLNode right = makeAVL(node.children[1]);
        AVLNode temp = makeAVL(right.children[0]);

        // make root the right childs left child
        right.children[0] = node;

        // make root right child be cur right child's left child
        node.children[1] = temp;

        updateHeight(node);
        updateHeight(right);

        return right;
    }

}
