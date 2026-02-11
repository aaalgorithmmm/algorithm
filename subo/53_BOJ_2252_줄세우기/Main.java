import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  // 차수배열 하나
  static int[] indegree;
  // 그래프배열 하나
  static ArrayList<Integer>[] nums;
  // 그래프관계를 풀어낼 큐 하나
  static Queue<Integer> queue = new LinkedList<>();

  public static void main(String[] args) throws IOException {
    StringTokenizer st = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(st.nextToken());
    int m = Integer.parseInt(st.nextToken());
    indegree = new int[n + 1];
    ArrayList<Integer>[] nums = new ArrayList[n + 1];

    for (int i = 1; i <= n; i++) {
      nums[i] = new ArrayList<>();
    }

    for (int i = 0; i < m; i++) {
      st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());
      nums[a].add(b);
      indegree[a]++;
    }

    for (int i = 1; i <= n; i++) {
      if (indegree[i] == 0) {
        queue.add(i);
      }
    }

    while (!queue.isEmpty()) {
      int now = queue.poll();
      System.out.print(now + " ");
      for (int next : nums[now]) {
        indegree[next]--;
        if (indegree[next] == 0) {
          queue.offer(next);
        }
      }
    }
  }
}
