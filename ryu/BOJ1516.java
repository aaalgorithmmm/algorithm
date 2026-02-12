import java.util.*;
import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        
        List<List<Integer>> graph = new ArrayList<>();
        
        int[] selfBuild = new int[n + 1];
        int[] indegree = new int[n + 1];
        int[] result = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            selfBuild[i] = Integer.parseInt(st.nextToken());
            
            while (st.hasMoreTokens()) {
                int prev = Integer.parseInt(st.nextToken());
                if (prev == -1) break; 
                
                graph.get(prev).add(i);
                indegree[i]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
                result[i] = selfBuild[i];
            }
        }

        while (!queue.isEmpty()) {
            int now = queue.poll();
            
            for (int next : graph.get(now)) {
                result[next] = Math.max(result[next], result[now] + selfBuild[next]);
                indegree[next]--;
                
                if (indegree[next] == 0) {
                    queue.add(next);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(result[i]).append("\n");
        }
        System.out.print(sb);
    }
}