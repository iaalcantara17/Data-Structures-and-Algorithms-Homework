package edu.njit.cs114;

/**
 * Author: Ravi Varadarajan
 * Date created: 11/6/2023
 */
public class BinTreeInOrderNavigator<K extends Comparable<K>,V> implements
                        BinTreeNavigator<K,V> {
    private int tabs = 0;
    @Override
    public void visit(BinTreeNode<K, V> node) {
          if (node != null) {
              tabs += 1;
              visit(node.leftChild());
              tabs -= 1;
              process(node);
              tabs += 1;
              visit(node.rightChild());
              tabs -= 1;
          }
    }

    @Override
    public void process(BinTreeNode<K, V> node) {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i < tabs; i++) {
            builder.append("      ");
        }
        builder.append("("+node.getKey()+","+node.getValue()+")");
        builder.append("(BF="+node.balanceFactor()+")");
        System.out.println(builder.toString());
    }
}
