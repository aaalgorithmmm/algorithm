import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
  [챕터 1. 문제 핵심]
  다익스트라는 음수 없는 가중치 그래프에서 단일 시작점 최단거리를 구하는 알고리즘이다.
  이 문제는 최단거리뿐 아니라 실제 경로까지 출력해야 하므로 prev 배열이 추가로 필요하다.
*/
public class Main {
  static FastReader scan = new FastReader();
  static int n; // 도시의 수
  static int m; // 버스의 수
  static ArrayList<Edge>[] edges;
  static final int INF = Integer.MAX_VALUE;

  public static void process() {
    /*
     * [챕터 2. 입력 및 그래프 생성]
     * 인접 리스트로 그래프를 구성하여 각 도시에서 이동 가능한 다음 도시와 비용을 저장한다.
     * edges[from]에는 from 도시에서 출발하는 모든 버스 정보가 들어간다.
     */
    n = scan.nextInt();
    m = scan.nextInt();

    edges = new ArrayList[n + 1];
    for (int i = 1; i <= n; i++) {
      edges[i] = new ArrayList<>();
    }

    for (int i = 1; i <= m; i++) {
      int from = scan.nextInt();
      int to = scan.nextInt();
      int weight = scan.nextInt();

      edges[from].add(new Edge(to, weight));
    }

    /*
     * [챕터 3. 다익스트라 준비]
     * dist는 시작점에서 각 도시까지의 최소 비용이고, prev는 최단 경로에서 직전 도시를 저장한다.
     * PQ에는 현재 도시와 누적 비용을 넣고, 비용이 가장 작은 상태부터 먼저 탐색한다.
     */
    int start = scan.nextInt();
    int end = scan.nextInt();

    int[] dist = new int[n + 1];
    int[] prev = new int[n + 1];
    Arrays.fill(dist, INF);

    PriorityQueue<Node> pq = new PriorityQueue<>();
    dist[start] = 0;
    pq.offer(new Node(start, 0));

    /*
     * [챕터 4. 다익스트라 수행]
     * 현재까지 비용이 가장 작은 도시를 꺼내고, 더 짧은 경로가 발견되면 dist와 prev를 갱신한다.
     * PQ에 남아 있던 오래된 값은 current.cost > dist[current.city] 조건으로 버린다.
     */
    while (!pq.isEmpty()) {
      Node current = pq.poll();

      if (current.cost > dist[current.city]) {
        continue;
      }

      for (Edge edge : edges[current.city]) {
        int nextCity = edge.to;
        int nextCost = current.cost + edge.weight;

        if (dist[nextCity] > nextCost) {
          dist[nextCity] = nextCost;
          prev[nextCity] = current.city;
          pq.offer(new Node(nextCity, nextCost));
        }
      }
    }

    /*
     * [챕터 5. 경로 복원]
     * prev는 '이전 도시'를 저장하므로 도착점에서 시작점까지 거꾸로 추적한 뒤 reverse 한다.
     * 이렇게 하면 최단 경로를 시작점 -> 도착점 순서로 복원할 수 있다.
     */
    ArrayList<Integer> path = new ArrayList<>();
    int currentCity = end;
    while (currentCity != 0) {
      path.add(currentCity);
      if (currentCity == start) {
        break;
      }
      currentCity = prev[currentCity];
    }
    Collections.reverse(path);

    StringBuilder sb = new StringBuilder();
    sb.append(dist[end]).append('\n');
    sb.append(path.size()).append('\n');
    for (int city : path) {
      sb.append(city).append(' ');
    }

    System.out.println(sb);
  }

  static class Node implements Comparable<Node> {
    int city;
    int cost;

    Node(int city, int cost) {
      this.city = city;
      this.cost = cost;
    }

    @Override
    public int compareTo(Node other) {
      return Integer.compare(this.cost, other.cost);
    }
  }

  static class Edge {
    int to;
    int weight;

    Edge(int to, int weight) {
      this.to = to;
      this.weight = weight;
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

  public static void main(String[] args) {
    process();
  }
}