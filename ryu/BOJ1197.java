import java.util.*;
import java.io.*;

// BOJ1197 최소 스패닝 트리

// 모든 노드를 연결할때 에지 비용의 합을 최소로 하는 트리
class Node implements Comparable<Node>{
    int from;
    int to;
    int cost;

    public Node(int f, int t, int c){
        this.from = f;
        this.to = t;
        this.cost = c;
    }

    public int compareTo(Node node){
        return this.cost - node.cost;
    }
}

class Main{
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        
        parent = new int[v+1];
        // parent 배열 초기화 (처음엔 자기 자신이 대장)
        for (int i = 1; i <= v; i++) {
            parent[i] = i;
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        for(int i=0; i<e; i++){
            st = new StringTokenizer(br.readLine());

            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            
            pq.add(new Node(from, to, cost));
        }
       
        long result = 0;
        int usedEdge = 0;

        while(!pq.isEmpty()){
            Node current = pq.poll();

            // 사이클 없도록 파인드로 체크
            if(find(current.from) != find(current.to)){
                union(current.from, current.to);
                result += current.cost;
                
                usedEdge++;
            }
            // 엣지-1 == 노드 수면 종료 (모든 노드가 연결됐다는 뜻이니까)
            if(usedEdge == v-1) break;
        }
        System.out.print(result);
    }

    public static void union(int a, int b){
        int rootA = find(a);
        int rootB = find(b);
        if(rootA != rootB){
            parent[rootB] = rootA;
        }
    }

    public static int find(int n){
        if(n == parent[n]){
            return n;
        }
        return parent[n] = find(parent[n]);
    }

}