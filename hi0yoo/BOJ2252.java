import java.io.*;
import java.util.*;

public class BOJ2252 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] nm = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = nm[0];
        int m = nm[1];

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        int[] degree = new int[n + 1];

        for (int i = 0; i < m; i++) {
            int[] ab = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int a = ab[0];
            int b = ab[1];

            adj.get(a).add(b);
            degree[b]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (degree[i] == 0) queue.add(i);
        }

        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            sb.append(poll).append(" ");

            for (int k : adj.get(poll)) {
                degree[k]--;

                if (degree[k] == 0) queue.add(k);
            }
        }

        System.out.println(sb);
    }
}
