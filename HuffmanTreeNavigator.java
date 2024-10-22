package edu.njit.cs114;

/**
 * Author: Ravi Varadarajan
 * Date created: 11/13/2023
 */
public class HuffmanTreeNavigator implements BinTreeNavigator<Double,Character> {

    private int tabs = 0;
    public static final String newLine = System.getProperty("line.separator");

    private StringBuilder builder = new StringBuilder();

    @Override
    public void visit(BinTreeNode<Double, Character> node) {
        if (node != null) {
            tabs += 1;
            visit(node.rightChild());
            tabs -= 1;
            process(node);
            tabs += 1;
            visit(node.leftChild());
            tabs -= 1;
        }
    }

    @Override
    public void process(BinTreeNode<Double, Character> node) {
        for (int i=0; i < tabs; i++) {
            builder.append("    ");
        }
        builder.append(node.getKey());
        if (node.isLeaf()) {
            builder.append("[" + node.getValue() + "]");
        }
        builder.append(newLine);
    }

    public String toString() {
        return builder.toString();
    }
}
