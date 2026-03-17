import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/* BOJ 13271 스파이 */
/**
 * [풀이 흐름]
 * Step 1. 입력 조건을 차분 제약식(difference constraints)으로 바꾼다.
 * Step 2. 각 제약식을 Bellman-Ford가 처리할 수 있는 간선 형태로 바꾼다.
 * Step 3. 점수 범위 0 <= score[i] <= 100 도 가상 정점 0을 이용해 간선으로 추가한다.
 * Step 4. Bellman-Ford로 각 점수의 가능한 최소 상한값(dist)을 구한다.
 * Step 5. 한 번 더 완화가 가능하면 음수 사이클이므로, 제약식이 서로 모순된 상태다.
 * Step 6. 모순이 없으면 가장 작은 점수를 찾아 점수 폭(100 - minValue)을 출력한다.
 *
 * --------------------------------------------------
 * [제약식 -> 간선 변환]
 *
 * 간선 u -> v (cost)는 아래 뜻으로 해석한다.
 * score[v] <= score[u] + cost
 *
 * [type 1]
 * 1 a b c : score[a] - score[b] >= c
 * : score[b] - score[a] <= -c
 * : score[b] <= score[a] + (-c)
 * : 간선 a -> b, cost = -c
 *
 * [type 2]
 * 2 a b c : score[a] - score[b] <= c
 * : score[a] <= score[b] + c
 * : 간선 b -> a, cost = c
 *
 * [type 3]
 * 3 a b c : score[a] - score[b] = c
 * : score[a] - score[b] <= c
 * : score[a] - score[b] >= c
 * : 간선 b -> a, cost = c
 * : 간선 a -> b, cost = -c
 *
 * --------------------------------------------------
 * [점수 범위 처리]
 *
 * 각 제품 평점은 0 <= score[i] <= 100 이다.
 * 가상 정점 0을 두고 score[0] = 0 이라고 보면:
 *
 * score[i] <= 100
 * -> score[i] - score[0] <= 100
 * -> 간선 0 -> i, cost = 100
 *
 * score[i] >= 0
 * -> score[0] - score[i] <= 0
 * -> 간선 i -> 0, cost = 0
 *
 * 즉 0번은 실제 제품이 아니라, 각 점수의 범위를 그래프에 연결하기 위한 가상 기준점이다.
 */
public class Main {
  static FastReader scan = new FastReader();

  public static void main(String[] args) {
    int n = scan.nextInt();
    int k = scan.nextInt();

    ArrayList<Edge> edges = new ArrayList<>();

    // Step 1. 점수 범위 0 <= score[i] <= 100 을 간선으로 추가한다.
    // 0번은 가상 정점이며, 각 점수의 초기 상한 100 / 하한 0을 연결하는 기준점이다.
    for (int i = 1; i <= n; i++) {
      edges.add(new Edge(0, i, 100));
      edges.add(new Edge(i, 0, 0));
    }

    // Step 2. 입력 제약식을 Bellman-Ford용 간선으로 변환한다.
    for (int i = 0; i < k; i++) {
      int type = scan.nextInt();
      int a = scan.nextInt();
      int b = scan.nextInt();
      int cost = scan.nextInt();

      if (type == 1) {
        edges.add(new Edge(a, b, -cost));
      } else if (type == 2) {
        edges.add(new Edge(b, a, cost));
      } else {
        edges.add(new Edge(b, a, cost));
        edges.add(new Edge(a, b, -cost));
      }
    }

    long INF = Long.MAX_VALUE / 4;

    // Step 3. Bellman-Ford 초기화
    // dist[i]는 i번 점수가 넘을 수 없는 현재까지의 최소 상한값이다.
    // dist[0] = 0 에서 시작해야 0 -> i (100) 간선을 통해 각 점수의 초기 상한 100이 퍼진다.
    long[] dist = new long[n + 1];
    Arrays.fill(dist, INF);
    dist[0] = 0;

    // Step 4. Bellman-Ford 수행
    // 노드는 0 ~ n 이므로 총 n+1개이고, 완화는 (정점 수 - 1) = n번 수행하면 충분하다.
    // 매 라운드마다 모든 간선을 보면서 상한값을 더 작은 값으로 갱신한다.
    // 어떤 라운드에서 갱신이 한 번도 없으면 이후에도 결과가 바뀌지 않으므로 조기 종료한다.
    for (int i = 0; i < n; i++) {
      boolean updated = false;

      for (Edge edge : edges) {
        if (dist[edge.from] == INF) {
          continue;
        }

        long next = dist[edge.from] + edge.cost;
        if (dist[edge.to] > next) {
          dist[edge.to] = next;
          updated = true;
        }
      }

      if (!updated) {
        break;
      }
    }

    // Step 5. 음수 사이클 검사
    // Bellman-Ford를 끝낸 뒤에도 더 줄어드는 간선이 있다면 음수 사이클이 존재한다.
    // 이 문제에서는 음수 사이클 = 제약식끼리 서로 모순된다는 뜻이므로 -1 출력.
    for (Edge edge : edges) {
      if (dist[edge.from] == INF) {
        continue;
      }

      long next = dist[edge.from] + edge.cost;
      if (next < dist[edge.to]) {
        System.out.println(-1);
        return;
      }
    }

    // Step 6. 정답 계산
    // dist[i]를 최종 점수값처럼 보면, 그중 가장 작은 점수를 찾는다.
    // 이 문제는 점수 차이만 중요하므로 가능한 해를 위로 평행이동해 최고점을 100에 맞춰 볼 수 있다.
    // 따라서 점수 폭은 100 - minValue 이다.
    long minValue = 100;
    for (int i = 1; i <= n; i++) {
      minValue = Math.min(minValue, dist[i]);
    }

    System.out.println(100 - minValue);
  }

  static class Edge {
    int from;
    int to;
    int cost;

    Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
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
          throw new RuntimeException(e);
        }
      }
      return st.nextToken();
    }

    int nextInt() {
      return Integer.parseInt(next());
    }
  }
}