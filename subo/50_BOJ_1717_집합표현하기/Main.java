//50_BOJ_1717_집합표현하기

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  static FastReader scan = new FastReader();
  static int[] parent;

  public static void main(String[] args) {
    StringBuilder sb = new StringBuilder();
    int n = scan.nextInt();
    int m = scan.nextInt();

    parent = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      parent[i] = i;
    }

    for (int i = 0; i < m; i++) {
      int command = scan.nextInt();
      int a = scan.nextInt();
      int b = scan.nextInt();
      if (command == 0) {
        union(a, b);
      } else {
        if (checkSame(a, b)) {
          sb.append("YES\n");
        } else {
          sb.append("NO\n");
        }
      }
    }
    System.out.println(sb.toString());
  }

  static void union(int a, int b) {
    a = find(a);
    b = find(b);
    if (a != b) {
      parent[b] = a;
    }
  }

  static int find(int a) {
    if (a == parent[a]) {
      return a;
    } else {
      return parent[a] = find(parent[a]);
    }
  }

  static boolean checkSame(int a, int b) {
    a = find(a);
    b = find(b);
    return a == b;
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
