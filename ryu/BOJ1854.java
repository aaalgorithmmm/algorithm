// BOJ1854 k번째 최단경로 찾기

import java.util.*;
import java.io.*;

class Node implemegnts Comparable<Node> {
    int nodeNum;
    int distance;

    public Node(){}
    public Node(int n, int d){
        this.nodeNum = n;
        this.distance = d;
    }

    public int compareTo(Node node){
       return this.distance - node.distance;
    }
}

class Main{
    public static void main(String[] args) throws IOException{
        // buff str sys
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken()); // 노드 수
        int m = Integer.parseInt(st.nextToken()); // 엣지 수
        int k = Integer.parseInt(st.nextToken()); // k번째 최단경로를 출력해야 함
        PriorityQueue<Integer>[] dist = new PriorityQueue[n+1];
        List<Node>[] graph = new ArrayList[n+1]; // 경로

        for(int i=1; i<n+1; i++){
            graph[i] = new ArrayList<>();
            dist[i] = new PriorityQueue<>(Collections.reverseOrder()); // 가장 큰 최단거리가 우선순위에 오도록
        }

        for(int i=1; i<m+1; i++){
            st = new StringTokenizer(br.readLine());

            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int distance = Integer.parseInt(st.nextToken());

            graph[from].add(new Node(to, distance)); // 경로 입력
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(1, 0));
        dist[1].add(0); // 시작점(1번 노드)의 최단 거리 0 추가

        while(!pq.isEmpty()){
            Node current = pq.poll();

            for(Node next : graph[current.nodeNum]){
            int newDist = current.distance + next.distance;

                if(dist[next.nodeNum].size() < k){
                    dist[next.nodeNum].add(newDist);
                    pq.add(new Node(next.nodeNum, newDist));
                // newDist가 새로운 최단거리라면!
                }else if(dist[next.nodeNum].peek() > newDist){
                    dist[next.nodeNum].poll(); // 기존 k번째로 길었던 경로 제거
                    dist[next.nodeNum].add(newDist); // 더 짧은 새로운 경로 추가
                    pq.add(new Node(next.nodeNum, newDist));
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i=1; i<n+1; i++){
            if(dist[i].size() == k){
                sb.append(dist[i].peek());
            }else{
                sb.append("-1");
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
