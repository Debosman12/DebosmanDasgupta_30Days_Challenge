import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'cookies' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER k
     *  2. INTEGER_ARRAY A
     */

    public static int cookies(int k, List<Integer> A) {
        // Min-heap to always get the two least sweet cookies
        PriorityQueue<Long> pq = new PriorityQueue<>();
        
        for (int sweetness : A) {
            pq.offer((long) sweetness);
        }

        int operations = 0;

        // Continue until the smallest cookie has sweetness >= k
        while (!pq.isEmpty() && pq.peek() < k) {
            // If fewer than 2 cookies remain, it's impossible
            if (pq.size() < 2) {
                return -1;
            }

            long least = pq.poll();
            long secondLeast = pq.poll();

            // Create new cookie
            long newSweetness = least + 2 * secondLeast;

            // Add back to heap
            pq.offer(newSweetness);

            operations++;
        }

        return operations;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> A = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        int result = Result.cookies(k, A);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
