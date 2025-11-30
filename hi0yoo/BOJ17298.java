import java.io.*;
import java.util.*;
import java.util.stream.*;

public class BOJ17298 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        // 입력값 배열
        int[] inputs = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        // 오큰수 배열 : -1로 초기화
        int[] results = IntStream.generate(() -> -1)
                    .limit(N)
                    .toArray();

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            // 현재 값을 스택에 넣기 전 확인
            // 현재 값이 스택의 가장 위 보다 작을 때 까지 pop
            while (!stack.isEmpty() && inputs[i] > inputs[stack.peek()]) {
                results[stack.pop()] = inputs[i];
            }

            stack.push(i);
        }

        // 결과 출력
        System.out.println(
                Arrays.stream(results)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(" "))
        );
    }
}
