import java.io.*;
import java.util.*;

public class Main {

    static int n, m, k, squareNum;
    static long[] segmentTree;
    static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken()); // 숫자 개수
        m = Integer.parseInt(st.nextToken()); // 바꾸는 횟수
        k = Integer.parseInt(st.nextToken()); // 구간 곱 구하는 횟수

        int height = 0;
        while (Math.pow(2, height) < n) height++;
        squareNum = (int) Math.pow(2, height);

        segmentTree = new long[squareNum * 2];
        Arrays.fill(segmentTree, 1);

        // 리프에 값 채우기
        for (int i = squareNum; i < squareNum + n; i++) {
            segmentTree[i] = Long.parseLong(br.readLine());
        }

        // 부모 노드 채우기
        for (int i = squareNum - 1; i > 0; i--) {
            segmentTree[i] = (segmentTree[i * 2] * segmentTree[i * 2 + 1]) % MOD;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m + k; i++) {
            st = new StringTokenizer(br.readLine());
            int op = Integer.parseInt(st.nextToken());

            if (op == 1) {
                int idx = Integer.parseInt(st.nextToken());
                long value = Long.parseLong(st.nextToken());
                update(idx, value);
            } else {
                int left = Integer.parseInt(st.nextToken());
                int right = Integer.parseInt(st.nextToken());
                sb.append(query(left, right)).append("\n");
            }
        }

        System.out.print(sb);
    }

    static void update(int idx, long value) {
        int node = squareNum + idx - 1;
        segmentTree[node] = value;
        node /= 2;
        while (node > 0) {
            segmentTree[node] = (segmentTree[node * 2] * segmentTree[node * 2 + 1]) % MOD;
            node /= 2;
        }
    }

    static long query(int left, int right) {
        left += squareNum - 1;
        right += squareNum - 1;
        long result = 1;

        while (left <= right) {
            if (left % 2 == 1) result = (result * segmentTree[left++]) % MOD;
            if (right % 2 == 0) result = (result * segmentTree[right--]) % MOD;
            left /= 2;
            right /= 2;
        }

        return result;
    }
}