//Israel Alcantara

package edu.njit.cs114;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.*;

/**
 * Author: Ravi Varadrajan
 * Date created: 11/13/2023
 */
public class HuffmanTreeCoder
{

    private static final char INTERNAL_NODE_CHAR = (char) 0;
    private HuffmanTreeNode root;
    private final Comparator<HuffmanTreeNode> nodeComparator;
    private Map<Character,String> charCodes = new HashMap<>();

    public static class HuffmanTreeNode implements BinTreeNode<Double,Character>
    {

        private final double weight; /* stores frequency */
        private final char ch;
        private final HuffmanTreeNode left, right;
        private final int height;

        public HuffmanTreeNode(double weight, char ch, HuffmanTreeNode left,
                               HuffmanTreeNode right)
        {
            this.weight = weight;
            this.ch = ch;
            this.left = left;
            this.right = right;
            height = 1 + Math.max(left == null ? 0 : left.height, right == null ? 0 :  right.height);
        }

        // used by leaf node which represents a character
        public HuffmanTreeNode(double weight, char ch)
        {
            this(weight,ch,null,null);
        }

        // used by internal node
        public HuffmanTreeNode(double weight,HuffmanTreeNode left, HuffmanTreeNode right)
        {
            /** Complete code for lab assignment **/
            this(weight, INTERNAL_NODE_CHAR, left, right);
        }

        @Override
        public Double getKey()
        {
            return weight;
        }

        @Override
        public Character getValue()
        {
            return ch;
        }

        @Override
        public BinTreeNode<Double, Character> leftChild()
        {
            return left;
        }

        @Override
        public BinTreeNode<Double, Character> rightChild()
        {
            return right;
        }

        @Override
        public boolean isLeaf()
        {
            return left == null && right == null;
        }

        @Override
        public int balanceFactor()
        {
            return 0;
        }
    }

    public HuffmanTreeCoder(Comparator<HuffmanTreeNode> comp, Map<Character,Double> freqMap)
    {
        this.nodeComparator = comp;
        buildTree(freqMap);
    }

    public HuffmanTreeCoder(Map<Character,Double> freqMap)
    {
        this(new HuffmanNodeComparator(), freqMap);
    }

    public static class HuffmanNodeComparator implements Comparator<HuffmanTreeNode>
    {
        @Override
        public int compare(HuffmanTreeNode t1, HuffmanTreeNode t2)
        {
            if (t1 == null || t2 == null)
            {
                throw new IllegalArgumentException("Nodes to be compared cannot be null");
            }
            return Double.compare(t1.weight, t2.weight);
        }
    }

    /**
     * This procedure must be recursive to get full credit
     * Extract codes for the characters in the Huffman subtree
     * rooted at node and add them to the charcodes map
     * @param node
     * @param prefix to be added to before every char code constructed from the subtree
     */

    private void encodeChars(HuffmanTreeNode node, String prefix)
    {
        if (node == null)
        {
            return;
        }

        /**
         * Complete code for lab assignment
         */

        if(node.isLeaf())
        {
            charCodes.put(node.ch,prefix);
        }
        else
        {
            encodeChars(node.right, prefix + "1");
            encodeChars(node.left, prefix + "0");
        }

    }

    public void buildTree(Map<Character,Double> freqMap)
    {
        PriorityQueue<HuffmanTreeNode> queue = new PriorityQueue<HuffmanTreeNode>(this.nodeComparator);

        // insert all leaf nodes
        for (Map.Entry<Character,Double> entry : freqMap.entrySet())
        {
            /**
             * Complete code here for lab assignment
             */

            queue.add(new HuffmanTreeNode(entry.getValue(), entry.getKey()));
        }
        while(true)
        {
            HuffmanTreeNode node = queue.poll();
            HuffmanTreeNode temp = queue.poll();

            if(temp == null)
            {
                root = node;
                break;
            }

            double weight = node.weight + temp.weight;

            HuffmanTreeNode newNode = new HuffmanTreeNode(weight, node, temp);
            queue.add(newNode);
        }
        encodeChars(root,"");
    }

    public String encodeBitString(String str)
    {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i < str.length(); i++)
        {
            builder.append(charCodes.get(str.charAt(i)));
        }
        return builder.toString();
    }

    /**
     * Uses a reader to fetch characters from a stream
     * @param rdr
     * @return
     * @throws Exception
     */


    /**
     * Decode the binary string encoded using this tree
     * @param code
     * @return
     */
    public String decodeBitString(String code)
    {

        // StringBuffer to store the decoded characters
        StringBuffer decodedBuffer = new StringBuffer();

        // Start decoding from the root of the Huffman tree
        HuffmanTreeNode rootNode = root;

        // Iterate through each bit in the provided code
        for (int i = 0 ; i < code.length(); i++)
        {


            // Check if the current bit is '0' (left traversal) or '1' (right traversal).
            if (code.charAt(i) == '0')
            {
                rootNode = rootNode.left;
            }

            // Otherwise traverse right.
            else
            {
                rootNode = rootNode.right;
            }

            // If a leaf node is reached, append its value to the decodedBuffer and reset to the root for the next character.
            if (rootNode.isLeaf())
            {
                decodedBuffer.append(rootNode.getValue());
                rootNode = root;
            }
        }
        /**
         * Complete code for homework assignment
         */

        // Return the decoded string.
        return decodedBuffer.toString();
    }
    public String decodeBitString(Reader rdr) throws Exception
    {
        // StringBuilder to store the decoded characters.
        StringBuilder decodedBuilder = new StringBuilder();

        // Buffer to read characters from the Reader.
        char [] charBuffer = new char[1024];

        // Start decoding from the root of the Huffman tree.
        HuffmanTreeNode currentNode = root;

        // Variable created to store the number of characters read
        int numCharsRead = 0;

        // Read characters from the rdr to the charBuffer.
        while ((numCharsRead = rdr.read(charBuffer)) > 0)
        {

            // Iterate through the characters read
            for (int i = 0; i < numCharsRead ; i++)
            {
                // Check if the current character is '0' or '1' and traverse the Huffman tree accordingly.
                if (charBuffer[i] == '0')
                {
                    currentNode = currentNode.left;
                }

                else
                {
                    currentNode = currentNode.right;
                }

                // If a leaf node is reached, append its value to the decodedBuilder and reset to the root for the next character.
                if (currentNode.isLeaf())
                {
                    decodedBuilder.append(currentNode.getValue());
                    currentNode = root;
                }
            }

        }
        // Return the decoded string.
        return decodedBuilder.toString();
    }

    /**
     * Compress a text file
     * @param fileName
     * @param compressedFileName
     */
    public void compress(String fileName, String compressedFileName)
    {
        FileReader rdr = null;
        FileWriter writer = null;
        try
        {
            char[] charBuf = new char[1024];
            rdr = new FileReader(fileName);
            int nCharsRead = 0;
            writer = new FileWriter(compressedFileName);
            while ((nCharsRead = rdr.read(charBuf)) >= 0)
            {
                writer.write(encodeBitString(new String(charBuf, 0, nCharsRead)));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (rdr != null)
            {
                try
                {
                    rdr.close();
                }
                catch (Exception e)
                {}
            }
            if (writer != null)
            {
                try
                {
                    writer.close();
                }
                catch (Exception e)
                {

                }
            }
        }
    }

    private static long getFileSize(String fileName)
    {
        return new File(fileName).length();
    }

    private static long getSizeForCompressedFile(String fileName)
    {
        return (long) Math.ceil(new File(fileName).length()/8);
    }


    /**
     * Get frequency map for characters in a file
     * @param fileName
     * @return
     */
    public static Map<Character,Double> getFreqMap(String fileName)
    {
        Map<Character,Double> freqMap = new HashMap<>();
        FileReader rdr = null;
        try
        {
            char[] charBuf = new char[1024];
            rdr = new FileReader(fileName);
            int nCharsRead = 0;
            while ((nCharsRead = rdr.read(charBuf)) >= 0)
            {
                for (int i=0; i < nCharsRead; i++)
                {
                    Double freq = freqMap.get(charBuf[i]);
                    if (freq == null)
                    {
                        freq = 0d;
                    }
                    freqMap.put(charBuf[i],++freq);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (rdr != null)
            {
                try
                {
                    rdr.close();
                }
                catch (Exception e)
                {
                }
            }
        }
        return freqMap;
    }


    public String toString()
    {
        HuffmanTreeNavigator navigator = new HuffmanTreeNavigator();
        navigator.visit(root);
        return navigator.toString();
    }


    public static void main(String [] args) throws Exception
    {
        // Uncomment lines for checking the homework soluion
        Map<Character,Double> freqMap = new HashMap<>();
        freqMap.put('C', 32d);
        freqMap.put('D', 42d);
        freqMap.put('E', 120d);
        freqMap.put('K', 7d);
        freqMap.put('L', 42d);
        freqMap.put('M', 24d);
        freqMap.put('U', 37d);
        freqMap.put('Z', 2d);
        HuffmanTreeCoder coder = new HuffmanTreeCoder(freqMap);
        System.out.println(coder.toString());
        String msg = "MUZZ";
        String bitStr = coder.encodeBitString(msg);
        float compressionRatio = ((float) msg.length()*8) / bitStr.length();
        System.out.println("compression ratio for msg: "+msg+" = "+compressionRatio);
      String val = coder.decodeBitString(bitStr);
      System.out.println("Decoded message = " +val);
        freqMap = new HashMap<>();
        int [] freqArr = new int [] {64, 13, 22, 32, 103, 21, 15, 47, 57, 1, 5, 32, 20, 57, 63, 15,
                1, 48, 51, 80, 23, 8, 18, 1, 16, 1, 186};
        for (int i = (int) 'a'; i < (int) 'z'; i++) {
            freqMap.put((char) i, (double) freqArr[i - (int) 'a']);
        }
        freqMap.put(' ', 186d);
        coder = new HuffmanTreeCoder(freqMap);
        System.out.println(coder.toString());
        msg = "this program builds a custom huffman tree for a particular file";
        bitStr = coder.encodeBitString(msg);
        compressionRatio = ((float) msg.length()*8) / bitStr.length();
        System.out.println("compression ratio for msg: "+msg+" = "+compressionRatio);
        val = coder.decodeBitString(bitStr);
        System.out.println("Decoded message = " +val);
        String textFile = "testHuffman.txt";
        String codeFile = "testHuffman_code.txt";
        Map<Character,Double> freqMap1 = getFreqMap(textFile);
        coder = new HuffmanTreeCoder(freqMap1);
        coder.compress(textFile,codeFile);
        compressionRatio = ((float) getFileSize(textFile)) / getSizeForCompressedFile(codeFile);
        System.out.println("compression ratio for file: "+ textFile +" = "+compressionRatio);
        val = coder.decodeBitString(new FileReader(codeFile));
        System.out.println("Decoded message = " +val);
    }

}

