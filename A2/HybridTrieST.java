// CS 1501 Summer 2019
// HybridTrieST<V> class

package TriePackage;

import java.util.*;
import java.io.*;

public class HybridTrieST<V> {

  private int R = 26;
  private TrieNodeInt<V> root;
  private int treeType;
	// treeType = 0 --> multiway trie
	// treeType = 1 --> DLB
	// treeType = 2 --> hybrid

	// You must supply the methods for this class.  See test program
	// HybridTrieTest.java for details on the methods and their
	// functionality.  Also see handout TrieSTMT.java for a partial
	// implementation.

  // Constructors
  public HybridTrieSt(){
    this(0);
  }
  public HybridTrieST(int type){
    treetype = type;
    root = null;
  }

  public int searchPrefix(String key)


  // Put a key value pair into the trie
  public void put(String key, V val){

  }

  // return the value of the node at
  // this key in the trie, null if not there
  public V get(String key) {
    char c;
    int i = 0;
    TrieNodeInt<V> node = root;
    while (node != null && i < key.length()){
      c = key.charAt(i++);
      node = node.getNextNode(c);
    }

    if (node == null){
      return null;
    }else{
      return node.getData();
    }
  }

  // return the approximate size of this Trie
  // as the sum of each node within the trie
  public int getSize() {
    int size;
    for (TrieNodeInt<V> node: root.children()){}
      size+=node.getSize();
    }
  }

  // traverse the trie  and return the
  // frequency of each degree in an int[]
  // indexed from 0 to (K is max degree == 26)
  public int[] degreeDistribution(){
    int [] distribution = new int[R+1];
    for (TrieNodeInt<V> node: root.children()){}
      distribution[node.getDegree()]++;
    }
    return distribution;
  }

  // return the number of nodes of a given type,
  // 1 = MTAlphaNode
  // 2 = DLBNode
  public int countNodes(int type){
    int count = 0;
    for (TrieNodeInt<V> node: root.children()){}
      if (node instanceof DLBNode && type==2){
          count++;
      }else if(type == 1){
          count++;
      }
    }
    return count;
  }

  // Save the trie in order to the filename fn
  public save(String fn){
    BufferedWriter write = new BufferedWriter(new FileWriter(fn));
    for (TrieNodeInt<V> node: root.children()){
      writer.println(node.getData() +'\r\n');
    }
  }

}
