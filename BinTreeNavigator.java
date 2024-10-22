package edu.njit.cs114;

/**
 * Author: Ravi Varadarajan
 * Date created: 11/6/2023
 */
public interface BinTreeNavigator<K extends Comparable<K>,V> {

    public void visit(BinTreeNode<K,V> node);

    public void process(BinTreeNode<K, V> node);

}
