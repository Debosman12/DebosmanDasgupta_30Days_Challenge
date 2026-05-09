/* Class node is defined as :
class Node
    int val;    // Value
    int ht;     // Height
    Node left;  // Left child
    Node right; // Right child
*/

static Node insert(Node root, int val)
{
    // Standard BST insertion
    if (root == null) {
        Node node = new Node();
        node.val = val;
        node.ht = 0;
        return node;
    }

    if (val < root.val) {
        root.left = insert(root.left, val);
    } else if (val > root.val) {
        root.right = insert(root.right, val);
    } else {
        // Duplicate values are not allowed
        return root;
    }

    // Update height of current node
    root.ht = 1 + Math.max(height(root.left), height(root.right));

    // Get balance factor
    int balance = height(root.left) - height(root.right);

    // Left Left Case
    if (balance > 1 && val < root.left.val) {
        return rotateRight(root);
    }

    // Right Right Case
    if (balance < -1 && val > root.right.val) {
        return rotateLeft(root);
    }

    // Left Right Case
    if (balance > 1 && val > root.left.val) {
        root.left = rotateLeft(root.left);
        return rotateRight(root);
    }

    // Right Left Case
    if (balance < -1 && val < root.right.val) {
        root.right = rotateRight(root.right);
        return rotateLeft(root);
    }

    return root;
}

// Returns height of a node (-1 if null)
static int height(Node node) {
    return (node == null) ? -1 : node.ht;
}

// Right rotation
static Node rotateRight(Node y) {
    Node x = y.left;
    Node T2 = x.right;

    // Perform rotation
    x.right = y;
    y.left = T2;

    // Update heights
    y.ht = 1 + Math.max(height(y.left), height(y.right));
    x.ht = 1 + Math.max(height(x.left), height(x.right));

    // New root
    return x;
}

// Left rotation
static Node rotateLeft(Node x) {
    Node y = x.right;
    Node T2 = y.left;

    // Perform rotation
    y.left = x;
    x.right = T2;

    // Update heights
    x.ht = 1 + Math.max(height(x.left), height(x.right));
    y.ht = 1 + Math.max(height(y.left), height(y.right));

    // New root
    return y;
}
