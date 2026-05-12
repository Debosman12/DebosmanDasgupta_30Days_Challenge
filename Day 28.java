/*  
    class Node
        public int frequency; // the frequency of this tree
        public char data;
        public Node left, right;
*/ 

    void decode(String s, Node root) {
        StringBuilder decoded = new StringBuilder();
        Node current = root;

        // Traverse the encoded string bit by bit
        for (int i = 0; i < s.length(); i++) {
            char bit = s.charAt(i);

            // Move left for '0' and right for '1'
            if (bit == '0') {
                current = current.left;
            } else {
                current = current.right;
            }

            // If we reach a leaf node, append the character
            if (current.left == null && current.right == null) {
                decoded.append(current.data);

                // Reset to root for decoding the next character
                current = root;
            }
        }

        // Print the decoded string
        System.out.print(decoded.toString());
    }
