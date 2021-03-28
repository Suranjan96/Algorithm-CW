public class Graph {

    //adjacency matrix
    private int addMatrix[][];
    private int numOfNode;

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
}
