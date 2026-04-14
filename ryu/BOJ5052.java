// BOJ5052 전화번호목록 (트라이 트리)

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine()); // 테스트 케이스 수

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine()); // 번호 수
            String[] phoneNumbers = new String[n];

            for (int i = 0; i < n; i++) {
                phoneNumbers[i] = br.readLine();
            }

            // 번호 정렬
            Arrays.sort(phoneNumbers);

            boolean isStartWith = true;

            // 두 번호 비교
            for (int i = 0; i < n - 1; i++) {
                // 앞의 번호가 바로 뒤의 번호의 접두어인지 확인
                if (phoneNumbers[i + 1].startsWith(phoneNumbers[i])) {
                    isStartWith = false;
                    break; // 일관성이 깨졌으므로 즉시 종료
                }
            }

            // 3. 결과 출력
            if (isStartWith) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }
}