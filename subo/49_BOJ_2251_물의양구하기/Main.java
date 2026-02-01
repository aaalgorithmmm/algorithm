//49_BOJ_2251_물의양구하기

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/*
입력 8 9 10
출력 1 2 8 9 10
특정무게상태를 1개의 노드로 가정하고 그래프를 역으로 그리기
*/
public class Main {
  static FastReader scan = new FastReader();
  // 용량(최대로 담을 수 있는 양)
  static int capA, capB, capC;

  static boolean[][] visited; // visited[a][b]
  static boolean[] possibleC; // possibleC[c] (A가 0일 때 C가 c 가능?)

  public static void main(String[] args) {
    capA = scan.nextInt();
    capB = scan.nextInt();
    capC = scan.nextInt();

    visited = new boolean[capA + 1][capB + 1];
    possibleC = new boolean[capC + 1];

    bfs();

    StringBuilder sb = new StringBuilder();
    for (int c = 0; c <= capC; c++) {
      if (possibleC[c])
        sb.append(c).append(' ');
    }
    System.out.print(sb.toString().trim());
  }

  static void bfs() {
    ArrayDeque<StateAB> q = new ArrayDeque<>();

    // A=0, B=0, C는 가득채운상태로 BFS시작
    visited[0][0] = true;
    q.add(new StateAB(0, 0));

    while (!q.isEmpty()) {
      StateAB cur = q.poll();
      int a = cur.a;
      int b = cur.b;
      int c = capC - a - b; // 총 물은 항상 capC

      // 문제 요구: A가 비어있을 때 C의 물양 기록
      if (a == 0) {
        possibleC[c] = true;
      }

      // 현재 물양 배열 (0:A, 1:B, 2:C)
      int[] now = { a, b, c };
      int[] cap = { capA, capB, capC };

      // 6가지 붓기: from -> to (from != to)
      for (int from = 0; from < 3; from++) {
        for (int to = 0; to < 3; to++) {
          if (from == to)
            continue;

          int[] next = { now[0], now[1], now[2] };

          // from 물통에서 to 물통으로 물을 붓는데, from이 빌 때까지 또는 to가 가득 찰 때까지 붓는다.
          int move = Math.min(next[from], cap[to] - next[to]);

          next[from] -= move;
          next[to] += move;

          int nextA = next[0];
          int nextB = next[1];

          if (!visited[nextA][nextB]) {
            visited[nextA][nextB] = true;
            q.add(new StateAB(nextA, nextB));
          }
        }
      }
    }
  }

  static class StateAB {
    int a;
    int b;

    StateAB(int a, int b) {
      this.a = a;
      this.b = b;
    }

  }

  static class FastReader {
    BufferedReader br;
    StringTokenizer st;

    FastReader() {
      br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
      while (st == null || !st.hasMoreElements()) {
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
