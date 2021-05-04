import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.LinkedList;

class MaxFlow {                                                 //https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
   static Graph graph;

   boolean bfs(int rGraph[][], int s, int t, int parent[]) {
        boolean visited[] = new boolean[graph.getNumOfNode()];              // Create a visited array and mark all vertices as not visited
        for (int i = 0; i < graph.getNumOfNode(); ++i)
            visited[i] = false;

        LinkedList<Integer> queue = new LinkedList<Integer>();              // Create a queue, enqueue source vertex and mark
        queue.add(s);                                                       // source vertex as visited
        visited[s] = true;
        //parent[s] = -1;    //TODO

        while (queue.size() != 0) {                                         // Standard BFS Loop
            int u = queue.poll();
            for (int v = 0; v < graph.getNumOfNode(); v++) {
                if (visited[v] == false && rGraph[u][v] > 0) {              // If we find a connection to the sink node, then there is no point in BFS anymore We just have to set its parent and can return true
                    if (v == t) {                                           //check is there path
                        parent[v] = u;                                      //store the path
                        return true;
                    }
                    queue.add(v);                                           //add traveled path
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return false;                                                    // We didn't reach sink in BFS starting from source, so return false
    }                                                                     // Returns tne maximum flow from s to t in the given graph

    int fordFulkerson(int graph[][], int s, int t) {
        int u, v;                                                       // Create a residual graph and fill the residual graph with given capacities in the original graph as residual capacities in residual graph
        int rGraph[][] = new int[MaxFlow.graph.getNumOfNode()][MaxFlow.graph.getNumOfNode()];           // Residual graph where rGraph[i][j] indicates residual capacity of edge from i to j (if there is an edge. If rGraph[i][j] is 0, then there is not)

        for (u = 0; u < MaxFlow.graph.getNumOfNode(); u++)
            for (v = 0; v < MaxFlow.graph.getNumOfNode(); v++)
                rGraph[u][v] = graph[u][v];                 //store the graph capacities

            int parent[] = new int[MaxFlow.graph.getNumOfNode()];           // This array is filled by BFS and to store path
        int max_flow = 0;                                                   // There is no flow initially

        while (bfs(rGraph, s, t, parent)) {                                 // Augment the flow while there is path from source to sink
            int path_flow = Integer.MAX_VALUE;                      // Find minimum residual capacity of the edges along the path filled by BFS. Or we can say find the maximum flow through the path found.
            for (v = t; v != s; v = parent[v]) {                    //when v=0 stop the loop
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            for (v = t; v != s; v = parent[v]) {                // update residual capacities of the edges and reverse edges along the path
                u = parent[v];
                rGraph[u][v] -= path_flow;                      //min the path cap
                rGraph[v][u] += path_flow;                      //add the path cap

            }
            System.out.println("Augmenting Path "+ path_flow);
            max_flow += path_flow;                  // Add path flow to overall flow
        }
        return max_flow;            // Return the overall flow
    }

    public static void getDataset(){
       try{
           File file = new File("Data set/ladder_1.txt");          //read the file
           Scanner scanner = new Scanner(file);
           String[] capArray = scanner.nextLine().split(" ");          //add all data in to string array and check the first element
           int numOfNode = Integer.parseInt(capArray[0]);
           graph = new Graph(numOfNode);                       //pass the variable to the constructor

           while (scanner.hasNextLine()){
               String[] array = scanner.nextLine().split(" ");         //add all data from file and check all elements to the order
               int u = Integer.parseInt(array[0]);                 //source
               int v = Integer.parseInt(array[1]);                 //target
               int cap = Integer.parseInt(array[2]);               //capacity
               graph.addEdges(u,v,cap);
           }
       }catch (FileNotFoundException e){
           System.out.println("File not Found");

       }
   }

    public static void main(String[] args) throws java.lang.Exception {             // Driver program to test above functions
        double []averageTime = new double[3];           //double array to get average time
        MaxFlow m = new MaxFlow();
        Stopwatch stopwatch = new Stopwatch();

        getDataset();
        System.out.println("\n"+"The maximum possible flow is " + m.fordFulkerson(graph.getAddMatrix(), 0, graph.getNumOfNode()-1));
        for(int i =1; i<=3;i++){
            System.out.println("Time "+i+" : "+stopwatch.elapsedTime());
            averageTime[i-1]= stopwatch.elapsedTime();
        }
        double a = averageTime[0];                  //calculate the average time
        double b = averageTime[1];
        double c = averageTime[2];
        double average = (a+b+c)/3;
        System.out.println("\nAverage Time: "+average);

        graph.printArray();
    }
}