// CS 1501 Summer 2019
// DLB Trie Node implemented as an external class which
// implements the TrieNodeInt<V> Interface

package TriePackage;

import java.util.*;
public class DLBNode<V> implements TrieNodeInt<V>
{
    protected Nodelet front;
    protected int degree;
	  protected V val;

    protected class Nodelet
    {
    	protected char cval;
    	protected Nodelet rightSib;
    	protected TrieNodeInt<V> child;
      protected Nodelet(){};
    }

	// You must supply the methods for this class.  See TrieNodeInt.java for the
	// interface specifications.  You will also need a constructor or two.

  public DLBNode(V data){
    degree = 0;
    val = data;
    front = new Nodelet();
  }
  public DLBNode(){
    val = null;
    degree = 0;
    front = new Nodelet();

  }


  // Return the next node in the trie corresponding to character
  // c in the current node, or null if there is not next node for
  // that character.
  public TrieNodeInt<V> getNextNode(char c){
    Nodelet nodelet = front;
    while ((nodelet.cval != c) && (nodelet != null)){
      nodelet = nodelet.rightSib
    }
    if (nodelet == null) return null;
    return nodelet.child

  }

  // Set the next node in the trie corresponding to character char
  // to the argument node.  If the node at that position was previously
  // null, increase the degree of this node by one (since it is now
  // branching by one more link).
  public void setNextNode(char c, TrieNodeInt<V> node){
    Nodelet nodelet = front;

    // search for sorted insertion nodelet by
    // comparision to key c
    while (nodelet.rightSib != null && nodelet.rightSib.cval < c){
      nodelet = nodelet.rightSib;
    }
    if (nodelet.rightSib == null){
      nodelet.rightSib = new Nodelet();
      nodelet = nodelet.rightSib;
      nodelet.cval = c;
      nodelet.child = node;
      degree++;
    }
    else {

    }
  }

  // Return the data at the current node (or null if there is no data)
  public V getData(){
    return val;
  }

  // Set the data at the current node to the data argument
  public void setData(V data){
    val = data;
  }

  // Return the degree of the current node.  This corresponds to the
  // number of children that this node has.
  public int getDegree(){
    return degree;
  }

  // Return the approximate size in bytes of the current node.  This is
  // a very rough approximation based on the following:
  // 1) Assume each reference in a node will use 4 bytes (whether it is
  //    used or it is null)
  // 2) Assume any primitive type is its specified size (see Java reference
  //    for primitive type sizes in bytes)
  // Note that the actual size of the node is implementation dependent and
  // is not specified in the Java language.  There are tools to give a better
  // approximation of this value but for our purposes, this approximation is
  // fine.
  public int getSize(){
    // each node has (degree) nodelets
    // and an int for degree
    // each nodelet has a char and 2 references
  }

  // Return an Iterable collsection of the references to all of the children
  // of this node.  Do not put any null references into this result.  The
  // order of the children as stored in the TrieNodeInt<V> node must be
  // maintained in the returned Iterable.  The easiest way to do this is to
  // put all of the references into a Queue and to return the Queue (since a
  // Queue implements Iterable and maintains the order of the children).
  // This method will allow us to access all of the children of a node without
  // having to know how the node is actually implemented.
  public Iterable<TrieNodeInt<V>> children(){

  }
}
