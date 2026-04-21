// BOJ1517 버블 소트 (세그먼트 트리)

import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static long[] tree;
    static Element[] arr;
    static int[] rank;

    // 숫자의 값과 원래 위치를 저장하기 위한 클래스
    static class Element implements Comparable<Element> {
        int val, originalIdx;

        public Element(int val, int originalIdx) {
            this.val = val;
            this.originalIdx = originalIdx;
        }

        @Override
        public int compareTo(Element o) {
            if (this.val == o.val) {
                return this.originalIdx - o.originalIdx;
            }
            return this.val - o.val;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        arr = new Element[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = new Element(Integer.parseInt(st.nextToken()), i);
        }

        // 1. 좌표 압축: 값들을 정렬하여 순위를 매김
        Arrays.sort(arr);
        rank = new int[N];
        for (int i = 0; i < N; i++) {
            rank[arr[i].originalIdx] = i + 1; // 1번부터 N번까지 순위 부여
        }

        // 2. 세그먼트 트리 초기화 (트리 크기는 N의 4배 정도면 충분)
        tree = new long[N * 4];
        long inversionCount = 0;

        // 원래 배열 순서대로 확인하면서 트리에 기록
        for (int i = 0; i < N; i++) {
            int currentRank = rank[i];
            
            // 3. 내 앞에 나보다 큰 숫자가 몇 개 있는지 확인 (Query)
            // 현재 순위 + 1 부터 전체 개수 N 사이의 합을 구함
            inversionCount += query(1, 1, N, currentRank + 1, N);
            
            // 4. 현재 숫자의 순위 위치에 1을 더해 등장했음을 알림 (Update)
            update(1, 1, N, currentRank, 1);
        }

        System.out.println(inversionCount);
    }

    // 세그먼트 트리 업데이트: 특정 위치(target)에 가중치(diff)를 더함
    static void update(int node, int start, int end, int target, int diff) {
        if (target < start || target > end) return;
        tree[node] += diff;
        if (start != end) {
            int mid = (start + end) / 2;
            update(node * 2, start, mid, target, diff);
            update(node * 2 + 1, mid + 1, end, target, diff);
        }
    }

    // 세그먼트 트리 쿼리: 특정 구간 [left, right]의 합을 구함
    static long query(int node, int start, int end, int left, int right) {
        // 구간에 없음
        if (left > end || right < start) return 0;
        // 구간 사이에 있음
        if (left <= start && end <= right) return tree[node];
        
        // 일부만 있음
        int mid = (start + end) / 2;
        return query(node * 2, start, mid, left, right) +
               query(node * 2 + 1, mid + 1, end, left, right);
    }
}