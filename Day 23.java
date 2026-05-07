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
     * Complete the 'downToZero' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER n as parameter.
     */

    static int MAX = 1000000;
    static int[] dp = new int[MAX + 1];
    static boolean precomputed = false;

    public static void precompute() {
        Arrays.fill(dp, Integer.MAX_VALUE);

        dp[0] = 0;

        for (int i = 1; i <= MAX; i++) {

            // Operation 2: decrease by 1
            dp[i] = Math.min(dp[i], dp[i - 1] + 1);

            // Operation 1: replace by max(a, b)
            for (int j = 2; j * j <= i; j++) {
                if (i % j == 0) {
                    int next = Math.max(j, i / j);
                    dp[i] = Math.min(dp[i], dp[next] + 1);
                }
            }
        }

        precomputed = true;
    }

    public static int downToZero(int n) {
        // Write your code here

        if (!precomputed) {
            precompute();
        }

        return dp[n];
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                int result = Result.downToZero(n);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
