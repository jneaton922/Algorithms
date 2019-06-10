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
	private static final int R = 26;						// 26 letters in
																	  					// alphabet
	private static final int INDEX_OFFSET = 65; // integer value for 'a', offset
																							// to map a-z into 0-25 indices
  protected V val;
  protected TrieNodeInt<V> [] next;
	protected int degree;

	// conversion constructor
	// create an MTAlphaNode from an existing DLBNode
	public MTAlphaNode(DLBNode<V> dlb_node){
			val = dlb_node.val;
			degree = dlb_node.degree;
			next = (TrieNodeInt<V> []) new TrieNodeInt<?>[R];

 			Nodelet nodelet = dlb_node.front;
			while (nodelet != null){
				next[nodelet.cval - INDEX_OFFSET] = nodelet.child
				nodelet = nodelet.rightSib
			}
	}

	public MTAlphaNode(V data)
	{
		val = data;
		degree = 0;
		next = (TrieNodeInt<V> []) new TrieNodeInt<?>[R];
	}
	public MTAlphaNode()
	{
		val = null;
		degree = 0;
		next = (TrieNodeInt<V> []) new TrieNodeInt<?>[R];
	}

	// Return the reference corresponding to character c
	public TrieNodeInt<V> getNextNode(char c)
	{
		return next[c-INDEX_OFFSET];
	}

	// Assign the argument node to the location corresponding
	// to character c.  If the reference had been null we
	// increase the degree of the current node by one.
	// IndexOutOfBoundsException if c not in a-z
	public void setNextNode(char c, TrieNodeInt<V> node) throws IndexOutOfBoundsException
	{
		if (next[c-INDEX_OFFSET] == null){
			degree++;
		}
		next[c-INDEX_OFFSET] = node;
	}

	// Return the value of this node
	public V getData()
	{
		return val;
	}

	// Set the value of this node
	public void setData(V data)
	{
		val = data;
	}

	// Return the degree of this node
	public int getDegree()
	{
		return degree;
	}

	// Return the approximate size in bytes of the current node.
	public int getSize(){
		int reference_bytes = 4;
		int int_bytes = 4;
		int refs = R*reference_bytes

		// Each node has the 26-reference array
		// and an int for degree
		return refs + int_bytes;
	}

	public Iterable<TrieNodeInt<V>> children(){

	}
}
