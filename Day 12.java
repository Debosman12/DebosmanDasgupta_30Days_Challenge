	/* 
    
    class Node 
        int data;
        Node left;
        Node right;
    */

    static class Pair {
        Node node;
        int hd;

        Pair(Node node, int hd) {
            this.node = node;
            this.hd = hd;
        }
    }

    public static void topView(Node root) {

        if (root == null)
            return;

        Queue<Pair> queue = new LinkedList<>();
        TreeMap<Integer, Integer> map = new TreeMap<>();

        queue.add(new Pair(root, 0));

        while (!queue.isEmpty()) {

            Pair current = queue.poll();

            // Store first node for every horizontal distance
            if (!map.containsKey(current.hd)) {
                map.put(current.hd, current.node.data);
            }

            if (current.node.left != null) {
                queue.add(new Pair(current.node.left, current.hd - 1));
            }

            if (current.node.right != null) {
                queue.add(new Pair(current.node.right, current.hd + 1));
            }
        }

        for (int value : map.values()) {
            System.out.print(value + " ");
        }
    }
