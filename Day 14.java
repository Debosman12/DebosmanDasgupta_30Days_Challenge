import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int q = sc.nextInt();

        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        while (q-- > 0) {
            int type = sc.nextInt();

            // Enqueue
            if (type == 1) {
                int x = sc.nextInt();
                stack1.push(x);
            }

            // Dequeue
            else if (type == 2) {
                if (stack2.isEmpty()) {
                    while (!stack1.isEmpty()) {
                        stack2.push(stack1.pop());
                    }
                }
                stack2.pop();
            }

            // Print front element
            else if (type == 3) {
                if (stack2.isEmpty()) {
                    while (!stack1.isEmpty()) {
                        stack2.push(stack1.pop());
                    }
                }
                System.out.println(stack2.peek());
            }
        }

        sc.close();
    }
}
