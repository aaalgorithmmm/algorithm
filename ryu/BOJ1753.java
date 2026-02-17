// BOJ1753 최단경로

import java.util.*;
import java.io.*;

class Node implements Comparable<Node>{
    int nodeNum;
    int weight;
    
    public Node(int t, int w){
        this.nodeNum = t;
        this.weight = w;
    } 
    
    public int compareTo(Node node){
        return this.weight - node.weight;
    }
}

class Main{
    public static void main(String[] args) throws IOException{
        PriorityQueue<Node> pq = new PriorityQueue<>(); // 우선순위 queue
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int v = Integer.parseInt(st.nextToken()); // 노드 개수
        int e = Integer.parseInt(st.nextToken()); // 엣지 개수
        
        int start = Integer.parseInt(br.readLine()); // 시작노드
        
        ArrayList<Node>[] graph = new ArrayList[v + 1]; // 그래프
        int[] dist = new int[v + 1]; // 최단경로 표시할 배열
        
        // 그래프, 경로 배열 초기화
        for(int i=1; i<=v; i++){
            graph[i] = new ArrayList<>();
            dist[i] = Integer.MAX_VALUE; // 가중치 누적되니 아주 큰 값으로
        }
        dist[start] = 0; // 시작점은 0
        
        // 그래프에 값 넣어주기
        for(int i=0; i<e; i++){
            st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken()); // 시작 노드
            int target = Integer.parseInt(st.nextToken()); // 도착 노드
            int weight = Integer.parseInt(st.nextToken()); // 시작 -> 도착 가중치
            
            graph[node].add(new Node(target, weight));
        }
        
        pq.add(new Node(start, 0));
        
        while(!pq.isEmpty()){
            Node current = pq.poll();

            // !!!현재 꺼낸 거리가 이미 알고 있는 최단거리보다 길면 무시!!!
            if(current.weight > dist[current.nodeNum]) continue;
            
            for(Node next : graph[current.nodeNum]){
                if(dist[current.nodeNum] + next.weight < dist[next.nodeNum]){
                    dist[next.nodeNum] = dist[current.nodeNum] + next.weight;
                    pq.add(new Node(next.nodeNum, dist[next.nodeNum])); // 최단경로값 변경되었으니까
                }
            }
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i=1; i<=v; i++){
            if(dist[i] != Integer.MAX_VALUE){
                sb.append(dist[i]).append("\n");
            } else {
                sb.append("INF\n");
            }
        }
        System.out.print(sb);
    }
}