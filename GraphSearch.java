//Israel Alcantara

package edu.njit.cs114;

import java.util.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 11/4/2023
 */
public class GraphSearch
{

    public static final int VISITED = 1;
    public static final int UNVISITED = 0;

    public static void printDfsTreeEdges(Graph g, List<Graph.Edge> treeEdges)
    {
        int lastVertex = -1;
        for (Graph.Edge edge : treeEdges)
        {
            if (edge.from != lastVertex)
            {
                if (lastVertex >= 0)
                {
                    System.out.println();
                }
                System.out.print(edge.from + " ---> " + edge.to);
            }
            else
            {
                System.out.print(" ---> " + edge.to);
            }
            lastVertex = edge.to;
        }
        System.out.println();
    }

    public static void printBfsTreeEdges(Graph g, List<Graph.Edge> treeEdges)
    {
        int fromVertex = -1;
        for (Graph.Edge edge : treeEdges)
        {
            if (edge.from != fromVertex)
            {
                if (fromVertex >= 0)
                {
                    System.out.println();
                }
                System.out.print(edge.from + "(" + g.getMark(edge.from) +
                        ")" + " ---> " +
                        edge.to + "(" + g.getMark(edge.to) + ")");
            }
            else
            {
                System.out.print("," + edge.to+ "(" + g.getMark(edge.to) + ")");
            }
            fromVertex = edge.from;
        }
        System.out.println();
    }


    public static void graphTraverseBFS(Graph g)
    {
        System.out.println("breadth-first search of graph..");
        for (int v = 0; v < g.numVertices(); v++)
        {
            g.setMark(v, UNVISITED);
        }
        for (int v = 0; v < g.numVertices(); v++)
        {
            if (g.getMark(v) == UNVISITED)
            {
                System.out.println("Start vertex : " + v);
                List<Graph.Edge> treeEdges = bfs(g, v);
                printBfsTreeEdges(g, treeEdges);
            }
        }
    }

    public static void graphTraverseDFS(Graph g)
    {
        System.out.println("depth-first search of graph..");
        for (int v = 0; v < g.numVertices(); v++)
        {
            g.setMark(v, UNVISITED);
        }
        for (int v = 0; v < g.numVertices(); v++)
        {
            if (g.getMark(v) == UNVISITED)
            {
                System.out.println("Start vertex : " + v);
                List<Graph.Edge> treeEdges = dfs(g, v);
                printDfsTreeEdges(g, treeEdges);
            }
        }
    }

    public static List<Graph.Edge> dfs(Graph g, int v)
    {
        g.setMark(v,VISITED);
        Iterator<Graph.Edge> outEdgeIter = g.getOutgoingEdges(v);
        // search-tree edges rooted at v
        List<Graph.Edge> subTreeEdges = new ArrayList<>();

        /**
         * Complete code here for lab assignment
         */

        while (outEdgeIter.hasNext())
        {
            Graph.Edge edge = outEdgeIter.next();
            int neighbor = edge.to;

            if (g.getMark(neighbor) == UNVISITED)
            {
                subTreeEdges.add(edge);
                subTreeEdges.addAll(dfs(g, neighbor));
            }
        }

        return subTreeEdges;
    }

    public static List<Graph.Edge> bfs(Graph g, int start)
    {
        Queue<Integer> vertexQueue = new LinkedList<Integer>();
        vertexQueue.add(start);
        int level = 1;
        g.setMark(start,1);
        List<Graph.Edge> treeEdges = new ArrayList<>();

        while (!vertexQueue.isEmpty())
        {
            /**
             * Complete code here lab assignment
             */

            int currentVertex = vertexQueue.poll();
            Iterator<Graph.Edge> iter = g.getOutgoingEdges(currentVertex);

            while (iter.hasNext())
            {
                Graph.Edge edge = iter.next();
                int neighbor = edge.to;
                if (g.getMark(neighbor) == UNVISITED)
                {
                    vertexQueue.add(neighbor);
                    g.setMark(neighbor, g.getMark(currentVertex) + 1);
                    treeEdges.add(edge);
                }
            }
        }
        return treeEdges;
    }

    /**
     * (complete it for homework assignment)
     * Returns true if a cycle exists in undirected graph
     * @param g undirected graph
     * @return
     */
    private static boolean cycleExists(Graph g, int v, int parent)
    {
        g.setMark(v, VISITED);
        Iterator<Graph.Edge> outEdges = g.getOutgoingEdges(v);
        while (outEdges.hasNext())
        {
            Graph.Edge edge = outEdges.next();
            int next = edge.to;
            int w = g.getMark(next);
            if(w == UNVISITED)
            {
                if(cycleExists(g, next, v) == true)
                {
                    return true;
                }
            }
            else if(w != UNVISITED && next != parent)
            {
                return true;
            }
        }
        return false;
    }
    public static boolean cycleExists(Graph g)
    {
        for (int v = 0; v < g.numVertices(); v++)
        {
            g.setMark(v, UNVISITED);
        }
        for (int v = 0; v < g.numVertices(); v++)
        {
            if (g.getMark(v) == UNVISITED)
            {
                if(cycleExists(g, v,-1) == true)
                {
                    return true;
                }
            }
        }
        return false;
    }



    /**
     * (complete it for homework assignment)
     * Returns true if a simple cycle with odd number of edges exists in undirected graph
     * @param g undirected graph
     * @return
     */
    public static boolean oddCycleExists(Graph g, int startVertex)
    {
        Queue<Integer> vertices = new LinkedList<>();

        vertices.add(startVertex);
        g.setMark(startVertex, 1);

        while (!vertices.isEmpty())
        {
            int v = vertices.remove();
            Iterator<Graph.Edge> outEdges = g.getOutgoingEdges(v);
            while(outEdges.hasNext())
            {
                Graph.Edge edge= outEdges.next();
                int next = edge.to;
                int w = g.getMark(next);

                if(w == 0)
                {
                    int mark = g.getMark(v);
                    g.setMark(next, mark + 1);
                    vertices.add(next);
                }
                else if (w == g.getMark(v))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean oddCycleExists(Graph g)
    {
        for (int v = 0; v < g.numVertices(); v++)
        {
            g.setMark(v, UNVISITED);
        }
        for (int v = 0; v < g.numVertices(); v++)
        {
            if (g.getMark(v) == UNVISITED)
            {
                if(oddCycleExists(g, v) == true)
                {
                    return true;
                }
            }
        }
        return false;
    }

    private static int dfsWithLevels(Graph tree, int v, int level)
    {
        tree.setMark(v,VISITED);
        level = 0;
        Iterator<Graph.Edge> outEdges = tree.getOutgoingEdges(v);

        while(outEdges.hasNext())
        {
            Graph.Edge edge = outEdges.next();
            int next = edge.to;
            int w = tree.getMark(next);

            if(w == UNVISITED)
            {
                int result = dfsWithLevels(tree, next, level + 1);
                if( result > level)
                {
                    level = result;

                }
            }

        }
        tree.setMark(v, level);
        return  level + 1;

    }

    /**
     * Find the diameter (length of longest path) in the tree
     * @param tree (undirected graph which is a tree)
     * @return
     */
    public static int diameter(Graph tree)
    {
        /**
         * Complete code for the homework
         */
        for (int v = 0; v < tree.numVertices(); v++)
        {
            tree.setMark(v, UNVISITED);
        }
        dfsWithLevels(tree,0,0);
        int x = Integer.MIN_VALUE;

        for (int v = 0; v < tree.numVertices(); v++)
        {
            if(tree.getMark(v) > x)
            {
                x = tree.getMark(v);
            }
        }
        for (int v = 0; v < tree.numVertices(); v++)
        {
            tree.setMark(v, UNVISITED);
        }
        int maxLevel = dfsWithLevels(tree,x,0);


        return maxLevel;
    }

    /**
     * Does the directed graph have a cycle of directed edges ? (Extra credit)
     * @param g
     * @return
     */
    public static boolean cycleExistsDirect(Graph g)
    {
        for (int v = 0; v < g.numVertices(); v++)
        {
            int[] visited = new int[g.numVertices()];
            if (cycleExistsDirectHelper(g, v, visited))
            {
                return true;
            }
        }
        return false;
    }

    private static boolean cycleExistsDirectHelper(Graph g, int v, int[] visited)
    {
        if (visited[v] == 1)
        {
            return true;
        }
        if (visited[v] == 0)
        {
            visited[v] = 1;
            Iterator<Graph.Edge> outEdges = g.getOutgoingEdges(v);
            while (outEdges.hasNext())
            {
                Graph.Edge edge = outEdges.next();
                int next = edge.to;
                if (cycleExistsDirectHelper(g, next, visited))
                {
                    return true;
                }
            }
        }
        visited[v] = 2;
        return false;
    }


    public static void main(String [] args) throws Exception
    {
        Graph g = new AdjListGraph(8, true);
        g.addEdge(0,1);
        g.addEdge(0,2);
        g.addEdge(0,3);
        g.addEdge(1,2);
        g.addEdge(1,3);
        g.addEdge(2,0);
        g.addEdge(3,2);
        g.addEdge(4,3);
        g.addEdge(4,6);
        g.addEdge(5,7);
        g.addEdge(6,5);
        g.addEdge(7,5);
        g.addEdge(7,6);
        System.out.println(g);
        graphTraverseBFS(g);
        graphTraverseDFS(g);
        System.out.println("Directed cycle exists in g ? " + cycleExistsDirect(g));
        g = new AdjListGraph(8, false);
        g.addEdge(0,1);
        g.addEdge(1,3);
        g.addEdge(3,2);
        g.addEdge(3,4);
        g.addEdge(4,5);
        g.addEdge(5,7);
        g.addEdge(4,6);
        System.out.println(g);
        graphTraverseBFS(g);
        graphTraverseDFS(g);
        System.out.println("Cycle exists in g ? " + cycleExists(g));
        g = new AdjListGraph(8, false);
        g.addEdge(0,1);
        g.addEdge(0,2);
        g.addEdge(1,3);
        g.addEdge(1,4);
        g.addEdge(3,2);
        g.addEdge(3,4);
        g.addEdge(4,5);
        g.addEdge(6,5);
        g.addEdge(5,7);
        g.addEdge(7,6);
        System.out.println(g);
        graphTraverseBFS(g);
        graphTraverseDFS(g);
        System.out.println("Cycle exists in g ? " + cycleExists(g));
        System.out.println("Odd cycle exists in g ? " + oddCycleExists(g));
        g = new AdjListGraph(7, false);
        g.addEdge(0,1);
        g.addEdge(0,2);
        g.addEdge(0,3);
        g.addEdge(2,4);
        g.addEdge(2,5);
        g.addEdge(3,6);
        System.out.println(g);
        System.out.println("Cycle exists in g ? " + cycleExists(g));
        System.out.println("Diameter of g = " + diameter(g));
        assert diameter(g) == 4;
        g = new AdjListGraph(7, false);
        g.addEdge(0,1);
        g.addEdge(1,2);
        g.addEdge(2,3);
        g.addEdge(3,0);
        g.addEdge(3,4);
        g.addEdge(4,5);
        g.addEdge(5,6);
        g.addEdge(6,3);
        System.out.println(g);
        System.out.println("Cycle exists in g ? " + cycleExists(g));
        System.out.println("Odd cycle exists in g ? " + oddCycleExists(g));
    }


}
