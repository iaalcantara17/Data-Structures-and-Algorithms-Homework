# Data Structures and Algorithms Homework

My homework and labs for NJIT's CS114 data structures course, in Java.

## About the code

The assignments came as starter code from the instructor, and most files keep the instructor's author header. A few classes (`Maze`, `MazeTest`, `TwoDimGrid`) come from the Koffman and Wolfgang textbook. Most of the JUnit tests in `tests/` carry the instructor's header too. The files I worked in have my name on the first line.

## Topics

| Topic | Files | What's in it |
| --- | --- | --- |
| Binary search trees | `BinarySearchTree.java`, `BinTreeNode.java`, `BinTreeNavigator.java`, `BinTreeInOrderNavigator.java` | Insert, delete, height, size, rank, k-th smallest, range queries, and a balance check |
| Graphs | `AdjListGraph.java`, `GraphSearch.java`, `AbstractGraph.java`, `Graph.java` | Adjacency list graph, BFS and DFS traversals, cycle detection, and odd cycle detection |
| Huffman coding | `HuffmanTreeCoder.java`, `HuffmanTreeNavigator.java` | Builds a Huffman tree from character frequencies, encodes and decodes bit strings, and compresses text |
| Hash tables | `AnagramFinderHashTableImpl.java`, `AnagramFinderListImpl.java` | Finds the words with the most anagrams, once with a hash table and once with a list |
| Polynomials | `ArrayPolynomial.java`, `ListPolynomial.java`, `Polynomial.java` | Add, subtract, multiply, divide, compose, and evaluate, with array and linked list versions |
| Stacks | `ExpressionEvaluator.java` and the token classes | Converts infix expressions to postfix and evaluates them |
| Joins and selection | `RelationalQueryProcessor.java`, `RelationalTable.java` | Nested loop, merge, and index joins on small tables, plus k-th smallest with partitioning |
| Arrays and greedy | `DynamicDoubleArray.java`, `CashRegister.java`, `LabCashRegister.java` | A resizable array of doubles, and making change from a set of coin values |
| Recursion and backtracking | `AtoY.java`, `Maze.java`, `MazeTest.java` | Filling a grid with the letters A to Y in sequence, and finding a path through a maze |

## How to run an example

You need a JDK. All classes are in the `edu.njit.cs114` package, so compile them into an output folder and run a class by its full name:

```sh
javac -d build *.java
java -cp build edu.njit.cs114.BinarySearchTree
```

Many of the files above have a `main` method with a small demo.

The tests in `tests/` use JUnit 5, which isn't included in the repo. They're easiest to run from an IDE such as IntelliJ IDEA with JUnit 5 added.

## Status

Finished coursework. The repo also holds compiled `.class` files and IntelliJ project files from my machine.
