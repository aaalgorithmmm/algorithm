import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/* BOJ 6497 전력난 */
/**
 * [핵심]
 * 모든 길 유지비 합(totalCost) - 최소 스패닝 트리 비용(mstCost) = 절약 가능한 금액
 *
 * [방법]
 * Kruskal + Union-Find
 * 1) 간선을 가중치 오름차순 정렬
 * 2) 사이클이 생기지 않는 간선만 선택
 * 3) 선택된 간선들의 합이 MST 비용
 */
public class Main {
  static FastReader scan = new FastReader();

  // 유니온 파인드 부모 배열
  static int[] parent;

  public static void main(String[] args) {
    StringBuilder output = new StringBuilder();

    while (true) {
      int n = scan.nextInt(); // 집(정점) 수
      int m = scan.nextInt(); // 길(간선) 수

      // 종료 조건
      if (n == 0 && m == 0) {
        break;
      }

      List<Edge> edges = new ArrayList<>();
      int totalCost = 0;

      // 전체 길 정보와 총 유지비 입력
      for (int i = 0; i < m; i++) {
        int u = scan.nextInt();
        int v = scan.nextInt();
        int w = scan.nextInt();

        edges.add(new Edge(u, v, w));
        totalCost += w;
      }

      // 유지비가 작은 길부터 선택하기 위해 정렬
      Collections.sort(edges);

      // 유니온 파인드 초기화
      parent = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
      }

      // 크루스칼로 MST 비용 계산
      int mstCost = 0;
      for (Edge edge : edges) {
        if (union(edge.u, edge.v)) {
          mstCost += edge.w;
        }
      }

      // 절약 가능한 금액 저장
      output.append(totalCost - mstCost).append('\n');
    }

    System.out.print(output);
  }

  static int find(int x) {
    // 루트 정점이면 그대로 반환
    if (parent[x] == x) {
      return x;
    }
    // 경로 압축
    return parent[x] = find(parent[x]);
  }

  static boolean union(int a, int b) {
    int rootA = find(a);
    int rootB = find(b);

    // 이미 같은 집합이면 간선 선택 불가
    if (rootA == rootB) {
      return false;
    }

    // 두 집합 합치기
    parent[rootB] = rootA;
    return true;
  }

  static class Edge implements Comparable<Edge> {
    final int u;
    final int v;
    final int w;

    Edge(int u, int v, int w) {
      this.u = u;
      this.v = v;
      this.w = w;
    }

    @Override
    public int compareTo(Edge other) {
      return Integer.compare(this.w, other.w);
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
