import java.util.Arrays;

public class Graph {

    //adjacency matrix
    private int addMatrix[][];
    private int numOfNode;

    @Override
    public String toString() {
        return "Graph{" +
                "addMatrix=" + Arrays.toString(addMatrix) +
                ", numOfNode=" + numOfNode +
                '}';
    }

    public Graph(int numOfNode) {
        this.numOfNode = numOfNode;
        addMatrix = new int[numOfNode][numOfNode];
    }

    public int[][] getAddMatrix() {
        return addMatrix;
    }

    public int getNumOfNode() {
        return numOfNode;
    }

    public void addEdges(int startNode, int finishingNode, int capacity){
        addMatrix[startNode][finishingNode] = capacity;
    }

    public int getCapacity(int startNode, int finishingNode){
        return addMatrix[startNode][startNode];
    }

    public  void printArray(){             //display the matrix
        System.out.println("Matrix");
        for (int i =0;i<getNumOfNode();i++){
            for (int j = 0;j<getNumOfNode();j++){
                System.out.print(addMatrix[i][j]+" ");
            }
            System.out.println();
        }
    }
}
