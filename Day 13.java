class Solution {
	/*
    class Node 
    	int data;
    	Node left;
    	Node right;
	*/
	public static Node lca(Node root, int v1, int v2) {
      	// Write your code here.
        
        // If both values are smaller than root,
        // LCA lies in the left subtree
        if(v1 < root.data && v2 < root.data) {
            return lca(root.left, v1, v2);
        }
        
        // If both values are greater than root,
        // LCA lies in the right subtree
        if(v1 > root.data && v2 > root.data) {
            return lca(root.right, v1, v2);
        }
        
        // Otherwise, current root is the LCA
        return root;
    }
