

import java.io.*;
import java.util.*;

public class NetworkGraph {

  LinkedList<Neighbor>[] adjacency_list;
  int vertices;
  int edges;

  // just a pair of ints to store each Neighbor
  // in the adjacency lista
  static class Neighbor{
    int v;
    int w;
    public Neighbor(int vertex,int weight){
     v = vertex;
     w = weight;
    }
  }

  public NetworkGraph(String fn) throws IOException {
    Scanner fileScan = new Scanner(new FileInputStream(fn));
    vertices = fileScan.nextInt();
    fileScan.nextLine();
    edges = fileScan.nextInt();
    fileScan.nextLine();

    adjacency_list = new LinkedList[vertices];

    // initialize an empty list for each vertex
    for (int i = 0; i < vertices;i++){
      adjacency_list[i] = new LinkedList<Neighbor>();
    }

    while (fileScan.hasNext()){
      addEdge(fileScan.nextInt(),fileScan.nextInt(),fileScan.nextInt());
      fileScan.nextLine();
    }

  }

  // add to the graph an edge between vertices a and b, with
  // weight w
  public void addEdge(int a, int b, int weight){
    adjacency_list[a].add(new Neighbor(b,weight));
    adjacency_list[b].add(new Neighbor(a,weight));
  }

  // perform a traversal to determine if the graph is connected
  public boolean isConnected(){
    boolean[] visited = new boolean[vertices];
      
  }

  public void report(){
  }

  public void mst(){
  }

  public void shortestPath(int i, int j){
  }

  public void pathList(int i, int j, int x){}

  public void down(int i){
  }

  public void up(int i){
  }

  public void changeWeight(int i, int j, int x){}

  public static void printMenu(){
    System.out.println();
    System.out.println("R - report status");
    System.out.println("M - minimum spanning tree");
    System.out.println("S <i> <j> - shortest path from i to j");
    System.out.println("P <i> <j> <x> - list paths i to j, latency less than x");
    System.out.println("D <i> - bring node i down");
    System.out.println("U <i> - bring node i up");
    System.out.println("C <i> <j> <x> - change weight of i<->j to x");
    System.out.println("Q - quit");
    System.out.println();
  }

  public static void main(String[] args) throws IOException{
      if (args.length == 0){
        System.out.println("Error: no input filename\r\nProper Usage: java Assignment5 <filename>");
        System.exit(0);
      }
      NetworkGraph graph = new NetworkGraph(args[0]);

      Scanner inScan = new Scanner(System.in);
      char in;
      int i;
      int j;
      int x;
      while (true){
        printMenu();
        in = inScan.next().charAt(0);
        switch (Character.toLowerCase(in)){

          // display current status
          case 'r':
            graph.report();
            break;

          // minimum spanning tree
          case 'm':
            graph.mst();
            break;

          // shortest path from i to j (by weight)
          case 's':
            i = inScan.nextInt();
            j = inScan.nextInt();
            graph.shortestPath(i,j);
            break;

          // each distinct path i to j total weight < x
          case 'p':
            i = inScan.nextInt();
            j = inScan.nextInt();
            x = inScan.nextInt();
            graph.pathList(i,j,x);
            break;

          // node d down
          case 'd':
            i = inScan.nextInt();
            graph.down(i);
            break;

          // node i up
          case 'u':
            i = inScan.nextInt();
            graph.up(i);
            break;

          // Change weight of edge i<->j to x
          // remove if x<=0,
          // create new edge if it doesn't exist and x > 0
          case 'c':
            i = inScan.nextInt();
            j = inScan.nextInt();
            x = inScan.nextInt();
            graph.changeWeight(i,j,x);
            break;

          // Quit
          case 'q':
            System.exit(0);
            break;
        }
      }
  }

}
