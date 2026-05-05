import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    static class Node {
        int data;
        Node left, right;

        Node(int data) {
            this.data = data;
        }
    }

    public static List<List<Integer>> swapNodes(List<List<Integer>> indexes, List<Integer> queries) {
        
        int n = indexes.size();

        // Create nodes (1-based indexing)
        Node[] nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }

        // Build tree
        for (int i = 1; i <= n; i++) {
            int left = indexes.get(i - 1).get(0);
            int right = indexes.get(i - 1).get(1);

            if (left != -1) nodes[i].left = nodes[left];
            if (right != -1) nodes[i].right = nodes[right];
        }

        Node root = nodes[1];
        List<List<Integer>> result = new ArrayList<>();

        // Process each query
        for (int k : queries) {
            swap(root, 1, k);

            List<Integer> traversal = new ArrayList<>();
            inorder(root, traversal);

            result.add(traversal);
        }

        return result;
    }

    // Swap nodes at depth multiples of k
    private static void swap(Node node, int depth, int k) {
        if (node == null) return;

        if (depth % k == 0) {
            Node temp = node.left;
            node.left = node.right;
            node.right = temp;
        }

        swap(node.left, depth + 1, k);
        swap(node.right, depth + 1, k);
    }

    // In-order traversal
    private static void inorder(Node node, List<Integer> res) {
        if (node == null) return;

        inorder(node.left, res);
        res.add(node.data);
        inorder(node.right, res);
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> indexes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String[] indexesRowTempItems = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            List<Integer> indexesRowItems = new ArrayList<>();

            for (int j = 0; j < 2; j++) {
                int indexesItem = Integer.parseInt(indexesRowTempItems[j]);
                indexesRowItems.add(indexesItem);
            }

            indexes.add(indexesRowItems);
        }

        int queriesCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> queries = new ArrayList<>();

        for (int i = 0; i < queriesCount; i++) {
            int queriesItem = Integer.parseInt(bufferedReader.readLine().trim());
            queries.add(queriesItem);
        }

        List<List<Integer>> result = Result.swapNodes(indexes, queries);

        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < result.get(i).size(); j++) {
                bufferedWriter.write(String.valueOf(result.get(i).get(j)));

                if (j != result.get(i).size() - 1) {
                    bufferedWriter.write(" ");
                }
            }

            if (i != result.size() - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
