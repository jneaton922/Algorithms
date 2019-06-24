// CS 1501 Summer 2019
// MultiWay Trie Node implemented as an external class which
// implements the TrieNodeInt InterfaceAddress.  For this
// class it is assumed that all characters in any key will
// be letters between 'a' and 'z'.

package TriePackage;

import java.util.*;
import java.lang.*;

public class MTAlphaNode<V> implements TrieNodeInt<V>
{
	private static final int R = 256;						// 26 letters in
																	  					// alphabet
	private static final int INDEX_OFFSET = 0; // integer value for 'a', offset
																							// to map a-z into 0-25 indices
  protected V val;
  protected TrieNodeInt<V> [] next;
	protected int degree;

	public MTAlphaNode(DLBNode<V> dlb_node){
			val = dlb_node.val;
			degree = dlb_node.degree;
			next = (TrieNodeInt<V> []) new TrieNodeInt<?>[R];

 			DLBNode.Nodelet nodelet = dlb_node.front;
			while (nodelet != null){
				if (nodelet.child != null ){
					next[nodelet.cval - INDEX_OFFSET] = nodelet.child;
				}
				nodelet = nodelet.rightSib;
			}
	}
	public MTAlphaNode(V data){
		val = data;
		degree = 0;
		next = (TrieNodeInt<V> []) new TrieNodeInt<?>[R];
	}
	public MTAlphaNode(){
		val = null;
		degree = 0;
		next = (TrieNodeInt<V> []) new TrieNodeInt<?>[R];
	}

	// Return the reference corresponding to character c
	public TrieNodeInt<V> getNextNode(char c)	{
		return next[c-INDEX_OFFSET];
	}

	// Assign the argument node to the location corresponding
	// to character c.  If the reference had been null we
	// increase the degree of the current node by one.
	// IndexOutOfBoundsException if c not in a-z
	public void setNextNode(char c, TrieNodeInt<V> node) throws IndexOutOfBoundsException	{
		if (next[c-INDEX_OFFSET] == null){
			degree++;
		}
		next[c-INDEX_OFFSET] = node;
	}

	// Return the value of this node
	public V getData(){
		return val;
	}

	// Set the value of this node
	public void setData(V data){
		val = data;
	}

	// Return the degree of this node
	public int getDegree(){
		return degree;
	}

	// Return the approximate size in bytes of the current node.
	//TODO this doesn't match the given output
	public int getSize(){
		int reference_bytes = 4*R;
		int int_bytes = 4;

		// Each node has the 26-reference array
		// and an int for degree
		return reference_bytes;
	}

	// Return an Iterable collection of the references to all of the children
  // of this node.  Do not put any null references into this result.  The
  // order of the children as stored in the TrieNodeInt<V> node must be
  // maintained in the returned Iterable.  The easiest way to do this is to
  // put all of the references into a Queue and to return the Queue (since a
  // Queue implements Iterable and maintains the order of the children).
  // This method will allow us to access all of the children of a node without
  // having to know how the node is actually implemented.
	public Iterable<TrieNodeInt<V>> children(){
		LinkedList<TrieNodeInt<V>> q =  new LinkedList<TrieNodeInt<V>>();
		for (int i = 0; i < R; i++){
			if (next[i] != null){
				q.add(next[i]);
				for (TrieNodeInt<V> node: next[i].children()){
					q.add(node);
				}
			}
		}
		return q;
	}

}
