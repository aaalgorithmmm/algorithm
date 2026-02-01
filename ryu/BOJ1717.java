// BOJ1717 집합표현하기
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    // 부모 노드 배열
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        StringTokenizer st = new StringTokenizer(br.readLine());

        // N: 집합의 개수 (0 ~ N), M: 연산의 개수
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 배열 초기화: 0부터 n까지 필요하므로 크기는 n + 1
        parent = new int[n + 1];

        // 초기 상태: 모든 원소는 자기 자신만을 집합으로 가짐
        // 즉, 자기 자신이 부모(루트)인 상태
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }

        StringBuilder sb = new StringBuilder(); // 결과를 모아서 출력하기 위함

        // M번의 연산 수행
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken()); // 0: 합집합, 1: 확인
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (command == 0) {
                // 0 a b: a가 포함된 집합과 b가 포함된 집합을 합침 (Union)
                union(a, b);
            } else {
                // 1 a b: a와 b가 같은 집합에 포함되어 있는지 확인 (Find)
                if (isSameParent(a, b)) {
                    sb.append("YES\n");
                } else {
                    sb.append("NO\n");
                }
            }
        }

        // 결과 출력
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    public static int find(int x) {
        if (x == parent[x]) {
            return x;
        }

        return parent[x] = find(parent[x]);
    }


    public static void union(int a, int b) {

        a = find(a);
        b = find(b);

        // 루트가 다르다면 (서로 다른 집합이라면)
        if (a != b) {
            parent[b] = a;
        }
    }

    public static boolean isSameParent(int a, int b) {
        // 두 원소의 루트 노드가 같다면 같은 집합
        return find(a) == find(b);
    }
}