// Ryan Bieg
// Class name: CS1450 (M/W)
// April 26, 2023 
// Assignment 10
// This assignment creates a binary search tree with parrots read in from a file
// It then traverses the BST printing their song by level order, and the leaf nodes

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

public class BiegRyanAssignment10 {
	public static void main(String[] args) throws IOException{
		//Create and populate binary tree
		BinaryTree tree = new BinaryTree();
		File parrotFile = new File("parrots.txt");
		Scanner parrotScnr = new Scanner(parrotFile);
		while(parrotScnr.hasNext())
		{
			int id = parrotScnr.nextInt();
			String name = parrotScnr.next();
			String song = parrotScnr.next();
			Parrot parrot = new Parrot(id, name, song);
			if(!tree.insert(parrot))
				System.out.println("Parrot could not be added");
		}//end while add parrot
		
		//print BST by level order
		System.out.println("Parrot's Song");
		System.out.println("-------------------------------");
		tree.levelOrder();
		
		//print BST leaf nodes
		System.out.println("\n\nParrots on Leaf Nodes");
		System.out.println("-------------------------------");
		tree.visitLeaves();
	}//end main
}//end main class


// The Parrot class has 3 instsance variables, an int id, and two strings
// name and songWord. The Constructor takes in an id, name, and songWord
// The class has two get methods for Name and songWord (sing).
// This class implements the comparable class on Parrot objects
// The are compared by their id numbers
class Parrot implements Comparable<Parrot>{
	private int id;
	private String name;
	private String songWord;
	
	Parrot(int id, String name, String songWord){
		this.id = id;
		this.name = name;
		this.songWord = songWord;
	}//constructor
	
	//getters
	public String getName() { return name;  }
	public String sing() { return songWord; }
	
	public int compareTo(Parrot otherParrot){
		if(this.id > otherParrot.id)
			return 1;
		if(this.id < otherParrot.id)
			return -1;
		return 0;
	}//end compareTo
}//end Parrot


// The class Binary Tree has one instance variable, root, which is
// A TreeNode object. TreeNode is a private class inside the 
// Binary Tree class. 
// TreeNode has three instance variables, right and left, which
// are TreeNode objects, and parrot, which is a Parrot object
// The constructor takes a Parrot object, and sets the left
// and right nodes to null.
// 
// BinaryTree has 4 methods. 
// Insert, which inserts new node into the tree.
// levelOrder which prints the levelOrder of the tree
// public visitLeaves which is a helper method for
// private visitLeaves, which prints the leaves
// of the BST
class BinaryTree{
	private TreeNode root;
	
	BinaryTree(){
		root = null;
	}//constructor
	
	public boolean insert(Parrot parrotToAdd) {
		if(root == null) 
			root = new TreeNode(parrotToAdd);
		else {
			TreeNode curr = root;
			TreeNode prev = null;
			while(curr != null) {
				//goes right
				if(parrotToAdd.compareTo(curr.parrot) > 0) {
					prev = curr;
					curr = curr.right;
				}
				else if(parrotToAdd.compareTo(curr.parrot) < 0) {
					prev = curr;
					curr = curr.left;
				}
				else
					return false;
			}//end while
			if(parrotToAdd.compareTo(prev.parrot) < 0)
				prev.left = new TreeNode(parrotToAdd);
			else
				prev.right = new TreeNode(parrotToAdd);
		}//end else
		return true;
	}//end insert
	
	public void levelOrder() {
		if(root == null)
			return;
		Queue<TreeNode> nodesVisited = new LinkedList<TreeNode>();
		nodesVisited.offer(root);
		while(!nodesVisited.isEmpty())
		{
			TreeNode tempNode = nodesVisited.remove();
			System.out.print(tempNode.parrot.sing() + " "); 
			if(tempNode.left != null) {
				nodesVisited.offer(tempNode.left);
			}
			if(tempNode.right != null) {
				nodesVisited.offer(tempNode.right);
			}
		}//end while
	}//end levelOrder
	
	public void visitLeaves() {
		visitLeaves(root);
	}//end public visitLeaves
	
	private void visitLeaves(TreeNode aNode) {
		if(aNode != null) {
			//visit left
			visitLeaves(aNode.left);
			if(aNode.left == null && aNode.right == null)
				System.out.println(aNode.parrot.getName());
			//visit right
			visitLeaves(aNode.right);
		}//end while
	}//end private visitLeaves
	
	private class TreeNode{
		private Parrot parrot;
		private TreeNode left;
		private TreeNode right;
		
		TreeNode(Parrot parrot){
			this.parrot = parrot;
			left = null;
			right = null;
		}
	}//end TreeNode
}//end BinaryTree