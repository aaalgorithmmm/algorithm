// BOJ13549 숨바꼭질3

// 수빈N 동생K
// 1초에 -1 또는 +1
// 0초에 *2

import java.util.*;
import java.io.*;

class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 출발
        int K = Integer.parseInt(st.nextToken()); // 도착
        int MAX = 100000 + 1; // 범위 + 1

        int[] dist = new int[MAX]; // 뒤로 갔다가 돌아오는 형식으로 도착할 수도 있음
        Arrays.fill(dist, -1);
        dist[N] = 0; // 출발점 가중치

        Deque<Integer> dq = new ArrayDeque();
        dq.add(N); // 출발

        while(!dq.isEmpty()){
            int current = dq.poll();

            if(current == K){
                System.out.print(dist[current]);
                return;
            }

            // 순간이동 시
            if(current * 2 < MAX && dist[current * 2] == -1){
                dist[current * 2] = dist[current];
                dq.addFirst(current * 2);
            }

            // 앞뒤 이동 시
            int[] move = {current - 1, current + 1};
            for(int next : move){
                if( next >= 0 && next < MAX && dist[next] == -1){
                    dist[next] = dist[current] + 1;
                    dq.addLast(next);
                }
            }
        }
    

    }
}