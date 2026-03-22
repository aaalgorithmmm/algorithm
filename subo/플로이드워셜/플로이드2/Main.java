package 플로이드워셜.플로이드2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/* BOJ 11780 플로이드 2
   [플로이드-워셜]
   for k = 1..n
     for i = 1..n
       for j = 1..n
         if dist[i][j] > dist[i][k] + dist[k][j]
           dist[i][j] 갱신

   [이 문제에서 추가되는 부분]
   이 문제는 최단거리만 구하는 게 아니라 경로도 출력해야 한다.
   그래서 dist 외에 next 배열도 같이 두고,
   i -> j가 i -> k -> j로 갱신되면 next[i][j] = next[i][k]로 저장한다.
*/
public class Main {
  static FastReader scan = new FastReader();
  static int n;
  static int m;

  // 도달 불가 표시용 값
  // Integer.MAX_VALUE는 덧셈 시 오버플로우 위험이 있어서 10억으로 둔다.
  static final int INF = 1_000_000_000;

  public static void process() {
    n = scan.nextInt();
    m = scan.nextInt();

    int[][] dist = new int[n + 1][n + 1];
    int[][] next = new int[n + 1][n + 1];

    for (int i = 1; i <= n; i++) {
      Arrays.fill(dist[i], INF);
      dist[i][i] = 0;
    }

    // 같은 구간 버스가 여러 개면 더 싼 것만 반영
    for (int i = 0; i < m; i++) {
      int from = scan.nextInt();
      int to = scan.nextInt();
      int cost = scan.nextInt();

      if (dist[from][to] > cost) {
        dist[from][to] = cost;
        next[from][to] = to;
      }
    }

    // 플로이드-워셜
    // 모든 k에 대해 i -> j 와 i -> k -> j 를 비교
    // 더 짧아지면 거리 갱신, 경로 복원을 위해 next도 같이 갱신
    for (int k = 1; k <= n; k++) {
      for (int i = 1; i <= n; i++) {
        if (dist[i][k] == INF)
          continue;

        for (int j = 1; j <= n; j++) {
          if (dist[k][j] == INF)
            continue;

          int nextCost = dist[i][k] + dist[k][j];
          if (dist[i][j] > nextCost) {
            dist[i][j] = nextCost;
            next[i][j] = next[i][k];
          }
        }
      }
    }

    StringBuilder sb = new StringBuilder();

    // 최소 비용 출력, 갈 수 없으면 0
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        sb.append(dist[i][j] == INF ? 0 : dist[i][j]).append(' ');
      }
      sb.append('\n');
    }

    // 경로 출력, 자기 자신이거나 갈 수 없으면 0
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        if (i == j || dist[i][j] == INF) {
          sb.append(0).append('\n');
          continue;
        }

        ArrayList<Integer> path = buildPath(i, j, next);
        sb.append(path.size()).append(' ');
        for (int city : path) {
          sb.append(city).append(' ');
        }
        sb.append('\n');
      }
    }

    System.out.print(sb);
  }

  // next[start][end]는 start에서 end로 갈 때 가장 먼저 가야 하는 도시다.
  // 그래서 current를 start에서 시작해서 next[current][end]를 계속 따라가면
  // start -> end 최단 경로를 순서대로 복원할 수 있다.
  static ArrayList<Integer> buildPath(int start, int end, int[][] next) {
    ArrayList<Integer> path = new ArrayList<>();
    int current = start;
    path.add(current);

    while (current != end) {
      current = next[current][end];
      path.add(current);
    }

    return path;
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
          throw new RuntimeException(e);
        }
      }
      return st.nextToken();
    }

    int nextInt() {
      return Integer.parseInt(next());
    }
  }

  public static void main(String[] args) {
    process();
  }
}
