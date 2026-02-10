import java.io.*;
import java.util.*;

// https://www.acmicpc.net/problem/1516
public class BOJ1516 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        // 해당 건물 짓는데 소요되는 시간
        int[] buildTimes = new int[N + 1];

        // 해당 건물을 짓기 위해 필요한 건물 연결 리스트 (단방향)
        List<Integer>[] buildMap = new List[N + 1];
        for (int i = 1; i <= N; i++) buildMap[i] = new ArrayList<>();

        // 위상정렬 차수
        int[] degrees = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());
            buildTimes[i] = time;

            while (true) {
                int k = Integer.parseInt(st.nextToken());
                if (k == -1) break;

                // 연결리스트 추가
                buildMap[k].add(i);
                // 차수 추가
                degrees[i]++;
            }
        }

        // 위상정렬
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (degrees[i] == 0) queue.add(i);
        }

        // 건물별 총 소요 시간
        int[] results = Arrays.copyOf(buildTimes, buildTimes.length);
        while (!queue.isEmpty()) {
            int node = queue.poll();

            // 현재 노드에서 갈 수 있는 다음 노드를 방문한다.
            for (Integer nextNode : buildMap[node]) {
                degrees[nextNode]--;

                // 현재 건물의 소요 시간을 반영한다.
                results[nextNode] = Math.max(results[nextNode], results[node] + buildTimes[nextNode]);

                // 차수가 0이 되었으면 다음 방문으로 넣는다.
                if (degrees[nextNode] == 0) queue.add(nextNode);
            }
        }

        for (int i = 1; i <= N; i++) System.out.println(results[i]);
    }
}
