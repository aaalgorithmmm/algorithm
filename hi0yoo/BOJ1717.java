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

        /**
         * <h3>union</h3>
         * a 노드가 속한 그룹과 b 노드가 속한 그룹을 합친다.</br>
         * b 노드의 대표 노드가 a 노드의 대표 노드를 가리키도록 한다.
         *
         * @param a 노드 번호
         * @param b 노드 번호
         */
        public void union(int a, int b) {
            map[find(b)] = map[find(a)];
        }

        /**
         * <h3>find</h3>
         * 특정 노드가 속한 그룹의 대표 노드 번호를 찾는다.
         *
         * @param k 대표노드를 찾을 노드 번호
         * @return k 노드가 속한 그룹의 대표 노드 번호
         */
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
