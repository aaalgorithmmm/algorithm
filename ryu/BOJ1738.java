// BOJ1738 골목길 (벨만-포드)

import java.util.*;
import java.io.*;

class Node {
    int fNodeNum;
    int tNodeNum;
    int distance;
    
    public Node(){}
    public Node(int f, int t, int d) {
        this.fNodeNum = f;
        this.tNodeNum = t;
        this.distance = d;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 1. 최댓값을 구해야 하므로 아주 작은 값으로 초기화 (오버플로우/언더플로우 방지 위해 long 사용)
        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MIN_VALUE);
        dist[1] = 0;
        
        Node[] graph = new Node[m];
        
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            
            graph[i] = new Node(u, v, w);
        }
        
        // 경로 추적을 위한 배열
        int[] pre = new int[n + 1];
        boolean hasPositiveCycle = false;
        
        // 2. 벨만-포드 로직 (n번 반복)
        for(int i = 1; i <= n; i++) {
            for(int j = 0; j < m; j++) {
                int fNodeNum = graph[j].fNodeNum;
                int tNodeNum = graph[j].tNodeNum;
                int distance = graph[j].distance;
                    
                // 현재 노드가 도달 가능하고, 새 경로가 기존 경로보다 금품을 더 많이 준다면 갱신
                if(dist[fNodeNum] != Long.MIN_VALUE 
                      && dist[fNodeNum] + distance > dist[tNodeNum]) {
                    
                    dist[tNodeNum] = dist[fNodeNum] + distance;
                    pre[tNodeNum] = fNodeNum; // 어디서 왔는지 기록
                    
                    // n번째 순회에서 갱신이 일어났다면 사이클이 존재한다는 의미
                    if(i == n) {
                        // 3. 해당 사이클이 목적지 n에 도달할 수 있는지 확인
                        if (canReachDest(tNodeNum, n, graph, n)) {
                            hasPositiveCycle = true;
                        }
                    }
                }
            }
        }
        
        // 도착할 수 없거나, 목적지로 가는 길에 양의 사이클이 존재하면 -1 출력
        if(dist[n] == Long.MIN_VALUE || hasPositiveCycle) {
            System.out.println("-1");
        } else {
            // 역추적 로직
            List<Integer> path = new ArrayList<>();
            int curr = n;
            while(curr != 0) {
                path.add(curr);
                curr = pre[curr];
            }
            
            // 목적지부터 역으로 담았으므로 뒤집어서 출력
            Collections.reverse(path);
            for(int node : path) {
                System.out.print(node + " ");
            }
        }
    }
    
    // BFS를 이용해 현재 노드에서 도착점까지 갈 수 있는지 검사
    static boolean canReachDest(int start, int dest, Node[] graph, int n) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];
        
        q.add(start);
        visited[start] = true;
        
        while(!q.isEmpty()) {
            int curr = q.poll();
            if(curr == dest) return true;
            
            for(Node node : graph) {
                if(node.fNodeNum == curr && !visited[node.tNodeNum]) {
                    visited[node.tNodeNum] = true;
                    q.add(node.tNodeNum);
                }
            }
        }
        return false;
    }
}