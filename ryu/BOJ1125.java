import java.util.*;

public class Main {
     // A, B, C의 최대 용량 저장
    static int[] max = new int[3];
    // 방문 여부 체크 (물통 최대용량은 200이고 물의 총량을 알기 때문에 2차원 배열만으로도 모든 상태 체크 가능)
    static boolean[][] visited = new boolean[201][201]; 
    // 정답 저장 (C의 양) TreeSet은 자동 정렬, 중복 제거를 해줌
    static TreeSet<Integer> answer = new TreeSet<>(); 

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        max[0] = sc.nextInt(); //8
        max[1] = sc.nextInt(); //9
        max[2] = sc.nextInt(); //10

        bfs();

        for (int amount : answer) {
            System.out.print(amount + " ");
        }
    }

    static void bfs() {
        Queue<int[]> queue = new LinkedList<>();
        // 초기 상태: A, B는 비어 있고 C는 가득 참
        queue.add(new int[]{0, 0, max[2]});
        visited[0][0] = true;

        // queue가 빌때까지 계속 물을 옮김 
        while (!queue.isEmpty()) {

            // 큐에서 조합 꺼내
            int[] curr = queue.poll();
            int a = curr[0];
            int b = curr[1];
            int c = curr[2];
            
            // a물통이 0리터일 땐 정답
            if (a == 0) answer.add(c);

            // 6가지 이동 케이스 (0:A, 1:B, 2:C)
            // from -> to
            int[] from = {0, 0, 1, 1, 2, 2};
            int[] to = {1, 2, 0, 2, 0, 1};

            for (int i = 0; i < 6; i++) {
                int[] next = {a, b, c}; // 현재 물의 양을 복사해서 '다음 상태' 준비
                
                // 물 옮기기: 받는 쪽의 남은 공간과 주는 쪽의 물 양 중 최소값
                // 둘 중 최소값을 옮기는 이유가 뭐냐면 남은 공간이 없으면 물을 줄 수도 없고 공간이 있더라도 남은 물이 없으면 줄 수 없잖아
                // amount = 옮겨지는 물의 양
                // max[to[i]] - next[to[i]] = 받을 물통의 최대 한도 - 받을 물통에 들어있는 물의 양
                int amount = Math.min(next[from[i]], max[to[i]] - next[to[i]]);
                next[from[i]] -= amount; // 준만큼 물양 -
                next[to[i]] += amount; // 받은만큼 물양 +
                // 이렇게 다음 상태의 물통을 만든 것

                // 이렇게 물을 옮긴 결과(A, B의 양)이 이전에 본적 없는 새로운 조합이라면?
                if (!visited[next[0]][next[1]]) {
                    visited[next[0]][next[1]] = true; // 이 조합 만들어본 적 있는 조합이라고 표시
                    queue.add(new int[]{next[0], next[1], next[2]}); // 조합을 큐에 넣기
                }
            }
        }
    }
}