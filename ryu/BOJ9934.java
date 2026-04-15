// BOJ9934 완전이진트리

import java.util.*;
import java.io.*;

class Main {
    static int height;
    static int[] nodeList;
    static List<Integer>[] result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String input = br.readLine();
        if (input == null) return;
        
        height = Integer.parseInt(input);
        int nodeCnt = (int) Math.pow(2, height) - 1;
        nodeList = new int[nodeCnt];
        
        result = new ArrayList[height];
        for (int i = 0; i < height; i++) {
            result[i] = new ArrayList<>();
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < nodeCnt; i++) {
            nodeList[i] = Integer.parseInt(st.nextToken());
        }

        solve(0, nodeCnt - 1, 0);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int val : result[i]) {
                sb.append(val).append(" ");
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }

    static void solve(int sIdx, int eIdx, int depth) {
        if (depth == height) return;

        int mid = (sIdx + eIdx) / 2;
        result[depth].add(nodeList[mid]);

        // 왼쪽
        solve(sIdx, mid - 1, depth + 1);
        // 오른쪽
        solve(mid + 1, eIdx, depth + 1);
    }
}