// BOJ2252 줄세우기
import java.util.*;
import java.io.*;

class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        ArrayList<ArrayList<Integer>> edgeArr = new ArrayList<>();
        int [] visited = new int [n+1];
        Queue<Integer> queue = new LinkedList<>();
        
        // 그래프 배열 초기화
        for(int i=0; i<n+1; i++){
            edgeArr.add(new ArrayList<>());
        }
        
        // 그래프 배열 만들기
        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            int edge = Integer.parseInt(st.nextToken());
            
            edgeArr.get(node).add(edge);
            visited[edge]++;
        }
        
        // visited 0인 곳 찾아서 queue에 집어넣기
        for(int i=1; i<n+1; i++){
            if (visited[i] == 0){
                queue.offer(i);
            }
        }
        
        while(!queue.isEmpty()){
            int node = queue.poll();
            System.out.print(node + " ");
            
            for(int next : edgeArr.get(node)){
                visited[next]--;
                if(visited[next] == 0){
                    queue.offer(next);
                }
            }
        }
        
    }
    
}