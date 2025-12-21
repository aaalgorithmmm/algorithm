import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/* 연결 요소의 개수 (백준11724)
 * 1. 노드의 간선 기록 저장
 * 2. 각노드를 돌면서 인접한 노드에 대해 호출.
 * 2-a. 방문하지 않은 노드라면, 그 노드를 기점으로 다시 인접한노드 호출.
 * 2-b. 방문한 노드라라면, 다른노드 꺼내기
 * 3. 그룹별로 개수 세기
 */

public class 문제23 {
    static FastReader scan = new FastReader();
    static boolean[] isVistied;
    static List<Integer>[] map;
    static int count = 0;

    public static void main(String[] args) {
        int node = scan.nextInt();
        int edge = scan.nextInt();
        isVistied = new boolean[node + 1];
        map = new ArrayList[node + 1];
        for (int i = 1; i <= node; i++) {
            map[i] = new ArrayList<>();
        }

        for (int i = 0; i < edge; i++) {
            int from = scan.nextInt();
            int to = scan.nextInt();
            map[from].add(to);
            map[to].add(from);
        }

        for (int i = 1; i <= node; i++) {
            if (isVistied[i]) {
                continue;
            } else {
                count++;
                dfs(i);
            }
        }
        System.out.println(count);
    }

    static void dfs(int curNode) {
        isVistied[curNode] = true;

        for (Integer nextNode : map[curNode]) {
            if (isVistied[nextNode]) {
                continue;
            } else {
                dfs(nextNode);
            }
        }

    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}