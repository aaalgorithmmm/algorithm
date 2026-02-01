import java.io.*;
import java.util.*;
import java.util.stream.*;

// https://www.acmicpc.net/problem/2251
public class BOJ2251 {

    private static int[] capacity;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        capacity = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int max = Math.max(Math.max(capacity[0], capacity[1]), capacity[2]);

        // A 뭁통이 비어있을 때, C 물통에 담길 수 있는 물의 양
        Set<Integer> results = new HashSet<>();

        // 0 0 10 -> 8 0 2, 0 9 1
        // 8 0 2 -> 0 8 2, 0 0 10(방문하지 않음)
        boolean[][] visited = new boolean[max + 1][max + 1];

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});
        visited[0][0] = true;

        results.add(capacity[2]);

        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            int curA = poll[0];
            int curB = poll[1];
            int curC = capacity[2] - curA - curB;

            // A -> B
            int[] m1 = move(new int[]{curA, curB, curC}, 0, 1);
            if (!visited[m1[0]][m1[1]]) {
                visited[m1[0]][m1[1]] = true;
                queue.add(new int[]{m1[0], m1[1]});
                if (m1[0] == 0) results.add(m1[2]);
            }

            // A -> C
            int[] m2 = move(new int[]{curA, curB, curC}, 0, 2);
            if (!visited[m2[0]][m2[1]]) {
                visited[m2[0]][m2[1]] = true;
                queue.add(new int[]{m2[0], m2[1]});
                if (m2[0] == 0) results.add(m2[2]);
            }

            // B -> A
            int[] m3 = move(new int[]{curA, curB, curC}, 1, 0);
            if (!visited[m3[0]][m3[1]]) {
                visited[m3[0]][m3[1]] = true;
                queue.add(new int[]{m3[0], m3[1]});
                if (m3[0] == 0) results.add(m3[2]);
            }

            // B -> C
            int[] m4 = move(new int[]{curA, curB, curC}, 1, 2);
            if (!visited[m4[0]][m4[1]]) {
                visited[m4[0]][m4[1]] = true;
                queue.add(new int[]{m4[0], m4[1]});
                if (m4[0] == 0) results.add(m4[2]);
            }

            // C -> A
            int[] m5 = move(new int[]{curA, curB, curC}, 2, 0);
            if (!visited[m5[0]][m5[1]]) {
                visited[m5[0]][m5[1]] = true;
                queue.add(new int[]{m5[0], m5[1]});
                if (m5[0] == 0) results.add(m5[2]);
            }

            // C -> B
            int[] m6 = move(new int[]{curA, curB, curC}, 2, 1);
            if (!visited[m6[0]][m6[1]]) {
                visited[m6[0]][m6[1]] = true;
                queue.add(new int[]{m6[0], m6[1]});
                if (m6[0] == 0) results.add(m6[2]);
            }
        }


        System.out.println(results.stream().sorted().map(String::valueOf).collect(Collectors.joining(" ")));
    }


    private static int[] move(int[] bottles, int from, int to) {
        bottles[to] += bottles[from];
        bottles[from] = 0;

        // 넘치면 초과분 만큼 반환
        if (bottles[to] > capacity[to]) {
            bottles[from] = bottles[to] - capacity[to];
            bottles[to] = capacity[to];
        }

        return bottles;
    }
}
