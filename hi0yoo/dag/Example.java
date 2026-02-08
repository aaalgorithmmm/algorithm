package dag;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

// https://www.acmicpc.net/problem/1516
public class Example {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        // 해당 건물 짓는데 소요되는 시간
        int[] buildTimes = new int[N + 1];

        DAG.Builder dagBuilder = new DAG.Builder(N + 1);
        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());
            buildTimes[i] = time;

            while (true) {
                int k = Integer.parseInt(st.nextToken());
                if (k == -1) break;

                // 연결리스트에 추가
                dagBuilder.addEdge(k, i);
            }
        }
        DAG dag = dagBuilder.build();

        // 노드별 순서를 가져온다.
        List<Integer> sortedNodes = dag.getSortedNodes();

        // 노드를 돌면서 하위 노드에 소요 시간을 더해준다.
        int[] results = Arrays.copyOf(buildTimes, buildTimes.length);
        for (Integer node : sortedNodes) {
            for (Integer child : dag.getChildren(node)) {
                results[child] = Math.max(results[child], results[node] + buildTimes[child]);
            }
        }

        for (int i = 1; i <= N; i++) System.out.println(results[i]);
    }
}
