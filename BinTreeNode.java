package edu.njit.cs114;

/**
 * Author: Ravi Varadarajan
 * Date created: 11/6/2023
 */
public interface BinTreeNode<K extends Comparable<K>,V> {

    /**
     * Get key stored in the node
     * @return
     */
    public K getKey();

    /**
     * Get value associated with the key stored in the node
     * @return
     */
    public V getValue();

    /**
     * Get the left child node
     * @return
     */
    public BinTreeNode<K,V> leftChild();

    /**
     * Get the right child node
     * @return
     */
    public BinTreeNode<K,V> rightChild();

    /**
     * Is the node a leaf (external node) ?
     * @return
     */
    public boolean isLeaf();

    /**
     * Returns balance factor (height of left sub tree - height of right sub tree)
     * @return
     */
    public int balanceFactor();
}
