// BOJ11657 타임머신으로 빨리 가기 (벨만-포드)
import java.util.*;
import java.io.*;

class Node {
    int from;
    int to;
    int distance;

    Node(){}
    Node(int f, int t, int d){
        this.from = f;
        this.to = t;
        this.distance = d;
    }
}
class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken()); // 노드개수
        int m = Integer.parseInt(st.nextToken()); // 엣지개수
        Node[] graph =  new Node[m];// 경로 (어차피 모든 노드 순회하니까 Queue 필요없음)
        long[] dist = new long[n+1]; // 최단 거리

        for(int i=1; i<n+1; i++){
            dist[i] = Integer.MAX_VALUE;
        }
        dist[1] = 0;
        
        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()); // 노드개수
            int to = Integer.parseInt(st.nextToken()); // 노드개수
            int distance = Integer.parseInt(st.nextToken()); // 노드개수

            graph[i] = new Node(from, to, distance);
        }

        // 최단거리 확정이 한번만에 안됨 -> 이중배열
        for (int i=0; i<n-1; i++){
            for(int j=0; j<m; j++){
                int fNodeNum = graph[j].from;
                int tNodeNum = graph[j].to;
                int distance = graph[j].distance;
                
                // 1번과 연결된 노드가 맞다면
                if(dist[fNodeNum] != Integer.MAX_VALUE &&
                      dist[tNodeNum] > dist[fNodeNum] + distance){
                    dist[tNodeNum] = dist[fNodeNum] + distance;
                }
            }
        }

        boolean cycle = false;

        for(int j=0; j<m; j++){
            int fNodeNum = graph[j].from;
            int tNodeNum = graph[j].to;
            int distance = graph[j].distance;
            
            // 1번과 연결된 노드가 맞다면
            if(dist[fNodeNum] != Integer.MAX_VALUE &&
                    dist[tNodeNum] > dist[fNodeNum] + distance){
                cycle = true;
            }
        }

        StringBuilder sb = new StringBuilder();
        if(cycle){
            sb.append("-1");
        }else{
            for(int i=2; i<n+1; i++){
                if(dist[i] != Integer.MAX_VALUE){
                    sb.append(dist[i]);
                    sb.append('\n');
                }else{
                    sb.append('-1');
                    sb.append('\n');  
                }

            }
        }
        System.out.println(sb);
    }
}