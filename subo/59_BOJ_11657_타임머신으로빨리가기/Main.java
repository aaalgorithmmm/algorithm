import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
  static FastReader scan = new FastReader();

  public static void main(String[] args) {
    int n = scan.nextInt(); // 도시 수
    int m = scan.nextInt(); // 버스 수

    ArrayList<Edge> edges = new ArrayList<>(m);
    for (int i = 0; i < m; i++) {
      int from = scan.nextInt();
      int to = scan.nextInt();
      int weight = scan.nextInt();
      edges.add(new Edge(from, to, weight));
    }

    final long INF = Long.MAX_VALUE / 4;
    long[] dist = new long[n + 1];
    Arrays.fill(dist, INF);
    dist[1] = 0;

    // 1) N-1번 완화
    for (int i = 1; i <= n - 1; i++) {
      boolean updated = false;
      for (Edge e : edges) {
        if (dist[e.from] == INF)
          continue; // 1에서 e.from까지 도달 불가
        long cand = dist[e.from] + e.w;
        if (cand < dist[e.to]) {
          dist[e.to] = cand;
          updated = true;
        }
      }
      if (!updated)
        break;
    }

    // 2) 음수 사이클 체크 (시작점에서 도달 가능한 경우만)
    for (Edge e : edges) {
      if (dist[e.from] == INF)
        continue;
      long cand = dist[e.from] + e.w;
      if (cand < dist[e.to]) {
        System.out.print("-1");
        return;
      }
    }

    // 3) 출력
    StringBuilder sb = new StringBuilder();
    for (int i = 2; i <= n; i++) {
      sb.append(dist[i] == INF ? -1 : dist[i]).append('\n');
    }
    System.out.print(sb);
  }

  static class Edge {
    int from, to;
    int weight;

    Edge(int from, int to, int weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }
  }

  static class FastReader {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st;

    String next() {
      while (st == null || !st.hasMoreElements()) {
        try {
          String line = br.readLine();
          if (line == null)
            return null;
          st = new StringTokenizer(line);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      return st.nextToken();
    }

    int nextInt() {
      String s = next();
      return Integer.parseInt(s);
    }
  }
}