// BOJ1956 운동 (플로이드-워셜)

import java.util.*;
import java.io.*;

class Main {
    // V * distance 최댓값보다 큰 값으로 설정
    static final int INF = 400 * 10000 + 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int v = Integer.parseInt(st.nextToken()); // 노드 수
        int e = Integer.parseInt(st.nextToken()); // 간선 수

        int[][] dist = new int[v + 1][v + 1];

        // 1. 초기화: 모든 경로를 INF로 설정
        // -1로 하면 나중에 더할 때 -1 체크해야됨
        for (int i = 1; i <= v; i++) {
            for (int j = 1; j <= v; j++) {
                dist[i][j] = INF;
            }
        }

        for (int i = 0; i < e; i++) { 
            st = new StringTokenizer(br.readLine()); 
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int distance = Integer.parseInt(st.nextToken());
            
            dist[from][to] = distance;
        }

        // 2. 플로이드-워셜 알고리즘 수행
        // k: 거쳐가는 노드, i: 출발 노드, j: 도착 노드
        for (int k = 1; k <= v; k++) {
            for (int i = 1; i <= v; i++) {
                for (int j = 1; j <= v; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        // 3. 최단 사이클 찾기 (dist[i][i]가 i에서 출발해 i로 돌아오는 최단 거리)
        int minCycle = INF;
        for (int i = 1; i <= v; i++) {
            minCycle = Math.min(minCycle, dist[i][i]);
        }

        // 결과 출력 (찾지 못했으면 -1)
        System.out.println(minCycle >= INF ? -1 : minCycle);
    }
}