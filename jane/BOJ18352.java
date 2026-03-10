import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();
        int x = sc.nextInt();

        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<Integer>());
        }

        boolean[] chk = new boolean[n + 1]; // 방문 기록
        int[] distance = new int[n + 1]; // 이동 거리 배열

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph.get(a).add(b);
        }

        ArrayList<Integer> result = new ArrayList<>(); // 결과를 담을 리스트

        // BFS 함수
        bfs(graph, chk, distance, x, k, result);

        if (result.isEmpty()) {
            System.out.println(-1);
        } else {
            Collections.sort(result);
            for (int i : result) {
                System.out.println(i);
            }
        }
    }

    public static void bfs(ArrayList<ArrayList<Integer>> graph, boolean[] chk, int[] distance, int start, int k, ArrayList<Integer> result) {
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        chk[start] = true;

        while (!q.isEmpty()) {
            int now = q.poll();
            for (int i : graph.get(now)) {
                if (!chk[i]) {
                    chk[i] = true;
                    distance[i] = distance[now] + 1;
                    q.add(i);
                    if (distance[i] == k) {
                        result.add(i);
                    }
                }
            }
        }
    }
}