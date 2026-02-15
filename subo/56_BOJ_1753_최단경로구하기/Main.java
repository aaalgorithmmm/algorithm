import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static FastReader scan = new FastReader();
    static int V,E,K;
    static int[] dist;
    static boolean[] visited;
    static ArrayList<Info>[] from;
    static void input(){
        V = scan.nextInt();
        E = scan.nextInt();
        K = scan.nextInt();
        from = new ArrayList[V+1];
        dist = new int[V+1];

        for (int i = 1; i <= V; i++){
            from[i] = new ArrayList<>();
            dist[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < E; i++){
            from[scan.nextInt()].add(new Info(scan.nextInt(), scan.nextInt()));
        }

    }
    static void pro(){

        PriorityQueue<Info> queue = new PriorityQueue<>(((o1, o2) -> o1.weight-o2.weight));
        queue.add(new Info(K,0));
        dist[K] = 0;
        while (!queue.isEmpty()){
            Info x = queue.poll();
            //to, weight
            for (Info y : from[x.to]){
                if (dist[y.to] > dist[x.to] + y.weight){
                    dist[y.to] = dist[x.to]+y.weight;
                    queue.add(new Info(y.to,dist[y.to]));
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= V; i++){
            if (dist[i] == Integer.MAX_VALUE) sb.append("INF").append("\n");
            else sb.append(dist[i]).append("\n");
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        input();
        pro();
    }
    static class Info{
        int to;
        int weight;

        Info(int to, int weight){
            this.to = to;
            this.weight = weight;
        }
    }
    static class FastReader{
        BufferedReader br;
        StringTokenizer st;

        FastReader(){
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next(){
            while (st == null || !st.hasMoreTokens()){
                try {
                    st = new StringTokenizer(br.readLine());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt(){
            return Integer.parseInt(next());
        }
    }
}