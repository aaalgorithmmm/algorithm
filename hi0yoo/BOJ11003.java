import java.io.*;
import java.util.*;

/**
 * 핵심 : 자료구조
 * <p>Deque 사용법 익히기</p>
 */
public class BOJ11003 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        // N, L 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());

        // N개의 수 입력
        st = new StringTokenizer(br.readLine());

        // (i - L + 1) ~ i 범위의 최소값 구하기
        // 덱 사용
        Deque<int[]> deque = new LinkedList<>();

        // N 반복 : i
        for (int i = 0; i < N; i++) {
            int k = Integer.parseInt(st.nextToken());
            // 덱의 맨 뒤에서부터 i번째 숫자(k)가 더 클 때까지 꺼낸다.
            while (!deque.isEmpty() && deque.peekLast()[1] > k) {
                deque.removeLast();
            }

            deque.addLast(new int[]{i, k});

            // 덱의 맨 앞 수가 (i - L + 1) ~ i 범위 이내가 아니면 제거한다.
            int[] first = deque.peekFirst();
            if (first != null && (first[0] < (i - L + 1) || first[0] > i)) deque.removeFirst();

            // 덱의 맨 앞 수를 출력한다.
            sb.append(deque.peekFirst()[1]).append(" ");
        }

        System.out.println(sb);
    }
}
