// CS 1501 Summer 2019
// HybridTrieST<V> class

package TriePackage;

import java.util.*;
import java.io.*;

public class HybridTrieST<V> {
  private int R = 256;
  private TrieNodeInt<V> root;
  private int treeType;
  private static int NODE_THRESHOLD = 12;
	// treeType = 0 --> multiway trie
	// treeType = 1 --> DLB
	// treeType = 2 --> hybrid

	// You must supply the methods for this class.  See test program
	// HybridTrieTest.java for details on the methods and their
	// functionality.  Also see handout TrieSTMT.java for a partial
	// implementation.

  // Constructors
  public HybridTrieST(){
    this(0);
  }
  public HybridTrieST(int type){
    treeType = type;
    root = null;
  }

  // 0 -- prefix not in trie
  // 1 -- prefix but not terminal value
  // 2 -- leaf, end of a word but not a prefix
  // 3 -- terminal value and a prefix
  public int searchPrefix(StringBuilder key){
    TrieNodeInt<V> node = root;
    int i = 0;
    while (node != null && i < key.length())
    {
      node = node.getNextNode(key.charAt(i++));
    }
    if (node != null && i == key.length()){
      int a = 0;
      if (node.getData() != null) a+=2;
      if (node.getDegree() > 0) a++;
      return a;
    }
    return 0;
  }


  // Put a key value pair into the trie
  public void put(StringBuilder key, V val){
    root = put(root,key,val,0);
  }

  private TrieNodeInt<V> put(TrieNodeInt<V> node, StringBuilder key, V val, int d){
    if (node == null){
      if (treeType == 0){
          node = new MTAlphaNode<V>();
      }
      else{
        node = new DLBNode<V>();
      }
    }
    if (d == key.length()){
      node.setData(val);
      return node;
    }
    char c = key.charAt(d);
    node.setNextNode(c,put(node.getNextNode(c),key,val,d+1));

    // Hybrid-conversion check
    if (node.getDegree() >= NODE_THRESHOLD && treeType == 2 && node instanceof DLBNode){
      node = new MTAlphaNode<V>((DLBNode<V>)node);
    }
    return node;
  }

  // return the value of the node at
  // this key in the trie, null if not there
  public V get(StringBuilder key) {
    char c;
    int i = 0;
    TrieNodeInt<V> node = root;
    while (node != null && i < key.length()){
      c = key.charAt(i++);
      node = node.getNextNode(c);
    }
    if (node == null) return null;
    return node.getData();
  }

  // return the approximate size of this Trie
  // as the sum of each node within the trie
  public int getSize() {
    int size = root.getSize();
    for (TrieNodeInt<V> node: root.children()){
      size+=node.getSize();
    }
    return size;
  }

  // traverse the trie  and return the
  // frequency of each degree in an int[]
  // indexed from 0 to (K is max degree == 26)
  public int[] degreeDistribution(){
    int [] distribution = new int[R+1];
    distribution[root.getDegree()]++;
    for (TrieNodeInt<V> node: root.children()){
      distribution[node.getDegree()]++;
    }
    return distribution;
  }

  // return the number of nodes of a given type,
  // 1 = MTAlphaNode
  // 2 = DLBNode
  public int countNodes(int type){
    int count = 0;
    if (root instanceof DLBNode<?> && type==2){
        count++;
    }else if(root instanceof MTAlphaNode<?> && type == 1){
        count++;
    }
    for (TrieNodeInt<V> node: root.children()){
      if (node instanceof DLBNode<?> && type==2){
          count++;
      }else if(node instanceof MTAlphaNode<?> && type == 1){
          count++;
      }
    }
    return count;
  }

  // Save the trie in order to the filename fn
  public void save(String fn) throws IOException{
    BufferedWriter writer = new BufferedWriter(new FileWriter(fn));
    for (TrieNodeInt<V> node: root.children()){
      if (node.getData() != null) writer.write(node.getData() +"\r\n");
    }
    writer.close();
  }

}
