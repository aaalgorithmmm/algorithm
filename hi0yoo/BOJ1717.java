import java.io.*;
import java.util.*;
import java.util.stream.*;

// https://www.acmicpc.net/problem/1717
public class BOJ1717 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] nm = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int n = nm[0];
        int m = nm[1];

        Graph graph = new Graph(n);

        for (int i = 0; i < m; i++) {
            int[] command = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int num = command[0];
            int a = command[1];
            int b = command[2];

            // 합집합 : 0 a b (a 집합과 b 집합을 합친다)
            if (num == 0) graph.union(a, b);

            // 연산 : 1 a b (a, b각 같은 집합에 포함되어 있는지 확인)
            if (num == 1) {
                if (graph.isSameGroup(a, b)) System.out.println("YES");
                else System.out.println("NO");
            }
        }
    }

    public static class Graph {
        private final int[] map;

        public Graph(int size) {
            this.map = IntStream.range(0, size + 1).toArray();
        }

        public void union(int a, int b) {
            map[find(b)] = map[find(a)];
        }

        public int find(int k) {
            int cur = k;
            while (map[cur] != cur) {
                cur = map[cur];
            }

            return cur;
        }

        public boolean isSameGroup(int a, int b) {
            return find(a) == find(b);
        }
    }
}
