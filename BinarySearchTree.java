//Israel Alcantara

package edu.njit.cs114;

import java.util.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 11/6/2023
 */
public class BinarySearchTree<K extends Comparable<K>,V>
{

    private BSTNode<K,V> root;
    private int size;

    public static class BSTNode<K extends Comparable<K>,V> implements BinTreeNode<K,V>
    {

        private K key;
        private V value;
        private int height;
        // number of keys (including the key in this node) in the subtree rooted at this node
        private int size;
        private BSTNode<K, V> left, right, parent;

        public BSTNode(K key, V value, BSTNode<K, V> left, BSTNode<K, V> right)
        {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;

            setAugmentInformation(left, right);

            /**
             * Complete code to store height and size  (for the lab)
             */
        }

        public void setAugmentInformation(BSTNode<K,V> left, BSTNode<K,V> right)
        {
            int leftHt = 0; int rightHt = 0;
            int leftSz = 0; int rightSz = 0;

            if(left !=null)
            {
                leftHt = left.height;
                leftSz = left.size;
            }

            if(right != null)
            {
                rightSz = right.size;
                rightHt = right.height;
            }

            int treeHeight = 1+ Math.max(leftHt, rightHt);
            int treeSize = 1 + Math.max(leftSz, rightSz);

            this.height = treeHeight;
            this.size = treeSize;
        }

        public BSTNode(K key, V value)
        {
            this(key, value, null, null);
        }

        @Override
        public K getKey()
        {
            return key;
        }

        @Override
        public V getValue()
        {
            return value;
        }

        @Override
        public BinTreeNode<K, V> leftChild()
        {
            return left;
        }

        @Override
        public BinTreeNode<K, V> rightChild()
        {
            return right;
        }

        @Override
        public boolean isLeaf()
        {
            return (left == null && right == null);
        }

        private void setLeftChild(BinTreeNode<K, V> node)
        {
            this.left = (BSTNode<K, V>) node;
        }

        private void setRightChild(BinTreeNode<K, V> node)
        {
            this.right = (BSTNode<K, V>) node;
        }

        private void setValue(V value)
        {
            this.value = value;
        };

        /**
         * Returns height of right subtree - height of left subtree
         * @return
         */
        @Override
        public int balanceFactor()
        {

            int leftHt = 0;
            int rightHt = 0;

            if(left != null)
                leftHt = left.height;
            if(right != null)
                rightHt = right.height;

            return rightHt - leftHt;

            /**
             * Complete code for the lab
             *
             */

        }


        private void copy(BSTNode<K,V> node)
        {
            this.key = node.key;
            this.value = value;
        }
    }


    public BSTNode<K,V> getRoot()
    {
        return root;
    }


    public BSTNode<K,V> insertAux(BSTNode<K,V> localRoot, K key, V value)
    {
        if (localRoot == null)
        {
            return new BSTNode<K,V>(key, value);
        }
        int rslt = key.compareTo(localRoot.key);
        if (rslt < 0)
        {
            localRoot.setLeftChild(insertAux(localRoot.left, key, value));
        } else if (rslt > 0)
        {
            localRoot.setRightChild(insertAux(localRoot.right, key, value));
        } else
        {
            localRoot.setValue(value);
        }

        localRoot.height = heightAid(localRoot);
        localRoot.size = sizeAid(localRoot);

        return localRoot;

        /**
         * Complete code to set height and size of localRoot for the lab
         */
    }

    public int heightAid(BSTNode<K, V> node)
    {
        if(node == null)
            return 0;
        if(node.isLeaf())
            return 1;
        return 1 + Math.max(heightAid(node.left), heightAid(node.right));
    }

    public int sizeAid(BSTNode<K, V> node)
    {
        if(node == null)
            return 0;
        if(node.isLeaf())
            return 1;
        return 1 + sizeAid(node.left) + sizeAid(node.right);
    }

    /**
     * Insert/Replace (key,value) association or mapping in the tree
     * @param key
     * @param value value to insert or replace
     */
    public void insert(K key, V value)
    {
        this.root = insertAux(root, key, value);
    }

    // Extra credit problem for homework
    /**
     * Delete the value associated with the key if it exists
     * Note you need to set height and size properly in the nodes of the subtrees affected
     * @param key
     * @return value deleted if key exists else null
     */
    public V delete(K key)
    {
        return null;
    }

    public int height()
    {
        return (root == null ? 0 : root.height);
    }

    public int size()
    {
        return (root == null ? 0 : root.size);
    }

    private boolean isBalanced(BSTNode<K,V> localRoot)
    {

        if( localRoot.balanceFactor() < -1|| localRoot.balanceFactor() > 1)
            return false;
        return true;

        /**
         * Complete code here for the lab
         */

    }

    /**
     * Is the tree balanced ?
     * For every node, height of left and right subtrees differ by at most 1
     * @return
     */
    public boolean isBalanced()
    {
        return isBalanced(root);
    }

    /**
     * Get level ordering of nodes; keys in a level must be in descending order
     * @return a map which associates a level with list of nodes at that level
     */
    public Map<Integer, List<BSTNode<K,V>>> getNodeLevels()
    {
        Map<Integer, List<BSTNode<K,V>>> nodeLevels = new HashMap<>();


        if(root == null)
            return new HashMap<>();

        List<BSTNode<K,V>> leveledList = new ArrayList<>();
        leveledList.add(root);
        nodeLevels.put(0, leveledList);

        int level = 1;
        while(leveledList.size() > 0)

        {
            List<BSTNode<K,V>> nextLeveledList = new ArrayList<>();
            for(BSTNode<K,V> node : leveledList)
            
            {
                if(node.right != null)
                    nextLeveledList.add(node.right);
                if(node.left != null)
                    nextLeveledList.add(node.left);
            }

            if(nextLeveledList.size() == 0)
                break;
            nodeLevels.put(level, nextLeveledList);
            leveledList = nextLeveledList;
            level++;
        }

        return nodeLevels;

        /**
         * Complete code for the lab
         */

    }

    /**
     * Return list of nodes whose keys are greater than or equal to key1
     *   and smaller than or equal to key2
     * @param key1
     * @param key2
     * @return
     */
    public List<BSTNode<K,V>> getRange(K key1, K key2)
    {
        List<BSTNode<K, V>> result = new ArrayList<>();
        getRange(root, key1, key2, result);
        return result;
        /**
         * Complete code for homework (define a recursive aux function to be called from here)
         */
    }
    private void getRange(BSTNode<K, V> node, K key1, K key2, List<BSTNode<K, V>> result)
    {
        if (node == null)
        {
            return;
        }

        int cmp1 = key1.compareTo(node.key);
        int cmp2 = key2.compareTo(node.key);

        if (cmp1 < 0)
        {
            getRange(node.left, key1, key2, result);
        }

        if (cmp1 <= 0 && cmp2 >= 0)
        {
            result.add(node);
        }

        if (cmp2 > 0)
        {
            getRange(node.right, key1, key2, result);
        }
    }



    /**
     * Find number of keys smaller than or equal to the specified key
     * @param key
     * @return
     */
    public int rank(K key)
    {
        return rank(root, key);
    }

    private int rank(BSTNode<K, V> node, K key)
    {
        if (node == null)
        {
            return 0;
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0)
        {
            return rank(node.left, key);
        }
        else if (cmp > 0)
        {
            return 1 + size(node.left) + rank(node.right, key);
        }
        else
        {
            return size(node.left) + 1;
        }
    }

    private int size(BSTNode<K, V> node)
    {
        return (node == null) ? 0 : node.size;
    }
    /**
     * Find the k-th smallest element in the binary search tree
     *
     * @param k
     * @return
     */
    public K kthSmallest(int k)
    {
        if (k < 1 || k > size())
        {
            return null; // Invalid k
        }

        return kthSmallest(root, k);
    }

    private K kthSmallest(BSTNode<K, V> node, int k)
    {
        int leftSize = sizeAid(node.left);

        if (k <= leftSize)
        {
            return kthSmallest(node.left, k);
        }
        else if (k == leftSize + 1)
        {
            return node.key;
        }
        else
        {
            return kthSmallest(node.right, k - leftSize - 1);
        }
    }


    public static void main(String [] args)
    {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst.insert(25,"a");
        bst.insert(15,"b");
        bst.insert(30,"c");
        bst.insert(5,"d");
        bst.insert(27,"e");
        bst.insert(36,"f");
        bst.insert(40,"g");
        bst.insert(10,"k");
        bst.insert(52,"l");
        System.out.println("Printing tree bst..");
        new BinTreeInOrderNavigator<Integer,String>().visit(bst.root);
        bst.insert(40,"m");
        System.out.println("Printing tree bst..");
        new BinTreeInOrderNavigator<Integer,String>().visit(bst.root);System.out.println("Value for deleted key 5 = " + bst.delete(5));
        System.out.println("Printing tree bst..");
        new BinTreeInOrderNavigator<Integer,String>().visit(bst.root);
        System.out.println("Value for deleted key 30 = " + bst.delete(30));
        System.out.println("Printing tree bst..");
        new BinTreeInOrderNavigator<Integer,String>().visit(bst.root);
        System.out.println("size of bst=" + bst.size());
        System.out.println("height of bst=" + bst.height());
        System.out.println("Is bst an AVL tree ? " + bst.isBalanced());
        Map<Integer, List<BSTNode<Integer,String>>> nodeLevels = bst.getNodeLevels();
        for (int level : nodeLevels.keySet())
        {
            System.out.print("Keys at level " + level + " :");
            for (BSTNode<Integer,String> node : nodeLevels.get(level))
            {
                System.out.print(" " + node.getKey());
            }
            System.out.println("");
        }
        BinarySearchTree<Integer, Integer> bst1 = new BinarySearchTree<>();
        bst1.insert(44,1);
        bst1.insert(17,2);
        bst1.insert(78,3);
        bst1.insert(50,4);
        bst1.insert(62,5);
        bst1.insert(88,6);
        bst1.insert(48,7);
        bst1.insert(32,8);
        System.out.println("Printing tree bst..");
        new BinTreeInOrderNavigator<Integer,Integer>().visit(bst1.root);
        System.out.println("size of bst1=" + bst1.size());
        System.out.println("height of bst1=" + bst1.height());
        System.out.println("Is bst1 an AVL tree ? " + bst1.isBalanced());
        Map<Integer, List<BSTNode<Integer,Integer>>> nodeLevels1 = bst1.getNodeLevels();
        for (int level : nodeLevels1.keySet()) {
            System.out.print("Keys at level " + level + " :");
            for (BSTNode<Integer,Integer> node : nodeLevels1.get(level)) {
                System.out.print(" " + node.getKey());
            }
            System.out.println("");
        }
        System.out.println("rank of key 10 in bst=" + bst.rank(10)); // should be 2
        System.out.println("rank of key 30 in bst=" + bst.rank(30)); // should be 6
        System.out.println("rank of key 3 in bst=" + bst.rank(3)); // should be 0
        System.out.println("rank of key 55 in bst=" + bst.rank(55)); // should be 9
        List<BSTNode<Integer,Integer>> rangeNodes = bst1.getRange(32,62);
        System.out.print("Keys in the range : [32,62] are:");
        // should get 32,44,48,50,62,
        for (BSTNode<Integer,Integer> node : rangeNodes)
        {
            System.out.print(node.key + ",");
        }
        System.out.println("");
                rangeNodes = bst1.getRange(10,50);
        System.out.print("Keys in the range : [10,50] are:");
        // should get 17,32,44,48,50,
        for (BSTNode<Integer,Integer> node : rangeNodes)
        {
            System.out.print(node.key + ",");
        }
        System.out.println("");
        rangeNodes = bst1.getRange(90,100);
        System.out.print("Keys in the range : [90,100] are:");
        // should get empty list
        for (BSTNode<Integer,Integer> node : rangeNodes)
        {
            System.out.print(node.key + ",");
        }
        System.out.println("");
    }

}
