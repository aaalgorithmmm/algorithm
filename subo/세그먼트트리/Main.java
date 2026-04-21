package subo.세그먼트트리;

import java.io.*;
import java.util.*;

public class Main {
  static int n, q;
  static long[] numbers;
  static long[] tree;

  public static void main(String[] args) {
    FastReader scan = new FastReader();
    StringBuilder sb = new StringBuilder();

    n = scan.nextInt();
    q = scan.nextInt();

    numbers = new long[n + 1];
    tree = new long[n * 4];

    for (int i = 1; i <= n; i++) {
      numbers[i] = scan.nextLong();
    }

    init(1, 1, n);

    for (int i = 0; i < q; i++) {
      int x = scan.nextInt();
      int y = scan.nextInt();
      int a = scan.nextInt();
      long b = scan.nextLong();

      int left = Math.min(x, y);
      int right = Math.max(x, y);

      // 1) 구간 합 출력
      sb.append(query(1, 1, n, left, right)).append('\n');

      // 2) 값 변경
      long diff = b - numbers[a];
      numbers[a] = b;
      update(1, 1, n, a, diff);
    }

    System.out.print(sb);
  }

  // 세그먼트 트리 초기화
  //                   tree[1]
  //                  [1~5] = 10
  //              /                 \
  //         tree[2]                tree[3]
  //        [1~3] = 4              [4~5] = 6
  //       /        \             /         \
  //    tree[4]     tree[5]     tree[6]     tree[7]
  //   [1~2] = 3   [3] = 1     [4] = 1      [5] = 5
  //    /      \
  // tree[8]  tree[9]
  // [1] = 1  [2] = 2
  static long init(int node, int start, int end) {
    if (start == end) {
      return tree[node] = numbers[start];
    }

    int mid = (start + end) / 2;
    long leftSum = init(node * 2, start, mid);
    long rightSum = init(node * 2 + 1, mid + 1, end);

    return tree[node] = leftSum + rightSum;
  }

  // 구간 합 조회
  static long query(int node, int start, int end, int left, int right) {
    // 현재 구간이 완전히 범위 밖
    if (right < start || end < left) {
      return 0L;
    }

    // 현재 구간이 완전히 범위 안
    if (left <= start && end <= right) {
      return tree[node];
    }

    int mid = (start + end) / 2;
    long leftSum = query(node * 2, start, mid, left, right);
    long rightSum = query(node * 2 + 1, mid + 1, end, left, right);

    return leftSum + rightSum;
  }

  // 한 점 값 변경 반영
  static void update(int node, int start, int end, int index, long diff) {
    // 수정할 인덱스가 현재 구간 밖이면 종료
    if (index < start || end < index) {
      return;
    }

    // 현재 구간 합 갱신
    tree[node] += diff;

    // 리프가 아니면 자식도 갱신
    if (start != end) {
      int mid = (start + end) / 2;
      update(node * 2, start, mid, index, diff);
      update(node * 2 + 1, mid + 1, end, index, diff);
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

    long nextLong() {
      return Long.parseLong(next());
    }
  }
}