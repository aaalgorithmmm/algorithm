import java.io.*;
import java.util.*;

/**
 * 버블소트
 * <p>버블소트는 인접 요소끼리 swap 연산으로 정렬한다. 시간복잡도는 O(n^2)</p>
 * <p>오름차순 정렬일때, 맨 앞 요소부터 인접 요소를 비교하여 정렬 수행.</p>
 * <p>문제에서 프로그램이 출력하고 종료하는 조건은 루프에세 swap이 일어나지 않았을 경우</p>
 * <p>왼쪽으로 가장 많이 이동한 길이 만큼이 루프 횟수</p>
 */
public class BOJ1377 {

    public static void main(String[] args) throws Exception {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine());

        class Data implements Comparable<Data> {
            int idx, value;
            Data(int idx, int value) {
                this.idx = idx;
                this.value = value;
            }

            @Override
            public int compareTo(Data o) {
                return value - o.value;
            }
        }

        Data[] arr = new Data[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new Data(i, Integer.parseInt(br.readLine()));
        }

        Arrays.sort(arr);

        // 가장 많이 이동한 요소 찾기
        int max = -1;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, arr[i].idx - i);
        }

        System.out.println(max + 1);
    }
}
