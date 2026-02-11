import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
  static FastReader scan = new FastReader();
  static int[] indegree;
  static int[] time;
  static ArrayList<Integer>[] graph;
  static Queue<Integer> queue = new LinkedList<>();

  public static void main(String[] args) {
    int n = scan.nextInt();
    indegree = new int[n + 1];
    time = new int[n + 1];
    graph = new ArrayList[n + 1];
    for (int i = 1; i <= n; i++) {
      graph[i] = new ArrayList<>();
    }

    // 건물짓는 시간 기록하기
    for (int now = 1; now <= n; now++) {
      int temp = scan.nextInt();
      time[now] = temp;
      while (true) {
        int prev = scan.nextInt();
        if (prev == -1) {
          break;
        }
        graph[prev].add(now);
        indegree[now]++;
      }
    }

    int[] answer = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      answer[i] = time[i];
      if (indegree[i] == 0) {
        queue.offer(i);
      }
    }

    while (!queue.isEmpty()) {
      int now = queue.poll();
      for (int next : graph[now]) {
        indegree[next]--;
        answer[next] = Math.max(answer[next], answer[now] + time[next]);
        if (indegree[next] == 0) {
          queue.add(next);
        }
      }
    }

    for (int i = 1; i <= n; i++) {
      System.out.println(answer[i] + " ");
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
