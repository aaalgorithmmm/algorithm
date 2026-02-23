import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        FastReader scan = new FastReader();

        int n = scan.nextInt();
        int m = scan.nextInt();
        int k = scan.nextInt();

        List<Edge>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int a = scan.nextInt();
            int b = scan.nextInt();
            int c = scan.nextInt();
            graph[a].add(new Edge(b, c));
        }

        // 각 노드별 K개 거리 저장 (maxHeap)
        PriorityQueue<Long>[] best = new PriorityQueue[n + 1];
        for (int i = 1; i <= n; i++) {
            best[i] = new PriorityQueue<>(Comparator.reverseOrder());
        }

        // 전역 PQ (dist 작은 순)
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingLong(s -> s.dist));

        // 시작 노드
        best[1].add(0L);
        pq.add(new State(1, 0L));

        while (!pq.isEmpty()) {

            State current = pq.poll();

            int currentNode = current.node;
            long currentDist = current.dist;

            for (Edge edge : graph[currentNode]) {

                int nextNode = edge.to;
                long newDist = currentDist + edge.w;

                if (best[nextNode].size() < k) {
                    best[nextNode].add(newDist);
                    pq.add(new State(nextNode, newDist));

                } else if (best[nextNode].peek() > newDist) {
                    best[nextNode].poll();
                    best[nextNode].add(newDist);
                    pq.add(new State(nextNode, newDist));
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (best[i].size() < k)
                sb.append(-1).append('\n');
            else
                sb.append(best[i].peek()).append('\n');
        }

        System.out.print(sb);
    }

    static class Edge {
        int to, w;

        Edge(int to, int w) {
            this.to = to;
            this.w = w;
        }
    }

    static class State {
        int node;
        long dist;

        State(int node, long dist) {
            this.node = node;
            this.dist = dist;
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}