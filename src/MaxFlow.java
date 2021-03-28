// Java program for implementation of Ford Fulkerson
// algorithm
import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.LinkedList;

class MaxFlow {
   static Graph graph;

    /* Returns true if there is a path from source 's' to
    sink 't' in residual graph. Also fills parent[] to
    store the path */
    boolean bfs(int rGraph[][], int s, int t, int parent[])
    {
        // Create a visited array and mark all vertices as
        // not visited
        boolean visited[] = new boolean[graph.getNumOfNode()];
        for (int i = 0; i < graph.getNumOfNode(); ++i)
            visited[i] = false;

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        // Standard BFS Loop
        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < graph.getNumOfNode(); v++) {
                if (visited[v] == false
                        && rGraph[u][v] > 0) {
                    // If we find a connection to the sink
                    // node, then there is no point in BFS
                    // anymore We just have to set its parent
                    // and can return true
                    if (v == t) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        // We didn't reach sink in BFS starting from source,
        // so return false
        return false;
    }

    // Returns tne maximum flow from s to t in the given
    // graph
    int fordFulkerson(int graph[][], int s, int t)
    {
        int u, v;

        // Create a residual graph and fill the residual
        // graph with given capacities in the original graph
        // as residual capacities in residual graph

        // Residual graph where rGraph[i][j] indicates
        // residual capacity of edge from i to j (if there
        // is an edge. If rGraph[i][j] is 0, then there is
        // not)
        int rGraph[][] = new int[MaxFlow.graph.getNumOfNode()][MaxFlow.graph.getNumOfNode()];

        for (u = 0; u < MaxFlow.graph.getNumOfNode(); u++)
            for (v = 0; v < MaxFlow.graph.getNumOfNode(); v++)
                rGraph[u][v] = graph[u][v];

        // This array is filled by BFS and to store path
        int parent[] = new int[MaxFlow.graph.getNumOfNode()];

        int max_flow = 0; // There is no flow initially

        // Augment the flow while tere is path from source
        // to sink
        while (bfs(rGraph, s, t, parent)) {
            // Find minimum residual capacity of the edhes
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            // update residual capacities of the edges and
            // reverse edges along the path
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            // Add path flow to overall flow
            max_flow += path_flow;
        }

        // Return the overall flow
        return max_flow;
    }

    public static void getDataset() throws FileNotFoundException{
        File file = new File("Data set/ladder_9.txt");
        Scanner scanner = new Scanner(file);
        String[] capArray = scanner.nextLine().split(" ");
        int numOfNode = Integer.parseInt(capArray[0]);
        graph = new Graph(numOfNode);

        while (scanner.hasNextLine()){
            String[] array = scanner.nextLine().split(" ");
            int u = Integer.parseInt(array[0]);
            int v = Integer.parseInt(array[1]);
            int cap = Integer.parseInt(array[2]);
           graph.addEdges(u,v,cap);
        }
    }

    // Driver program to test above functions
    public static void main(String[] args) throws java.lang.Exception
    {
        double []averageTime = new double[3];
        MaxFlow m = new MaxFlow();
        Stopwatch stopwatch = new Stopwatch();

        getDataset();
        System.out.println("The maximum possible flow is " + m.fordFulkerson(graph.getAddMatrix(), 0, graph.getNumOfNode()-1));
        for(int i =1; i<=3;i++){
            System.out.println("Time "+i+" : "+stopwatch.elapsedTime());
            averageTime[i-1]= stopwatch.elapsedTime();
        }
        double a = averageTime[0];
        double b = averageTime[1];
        double c = averageTime[2];
        double average = (a+b+c)/3;
        System.out.println("\nAverage Time: "+average);

    }
}
