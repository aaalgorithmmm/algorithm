// package subo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

/* BOJ 11003 슬라이딩 윈도우 최솟값
 *
 * 단조 증가 큐 (Monotonic Queue) 방식으로 O(N)에 해결한다.
 *
 * 1) 앞쪽 while
 *    - 윈도우 범위(L)를 벗어난 인덱스는 여러 개 있을 수 있으므로
 *      peekFirst()를 while로 반복 제거해야 한다.
 *
 * 2) 뒤쪽 while
 *    - 현재 값보다 큰 값들은 최솟값 후보가 될 수 없으므로
 *      뒤에서부터 반복적으로 제거해야 한다.
 *    - 반복해야 각 원소가 최대 한 번만 제거되어 전체 O(N)이 된다.
 *
 * 3) 큐의 맨 앞은 항상 현재 윈도우의 최솟값이 된다.
 */
public class BOJ_11003_최솟값찾기 {
  static FastReader scan = new FastReader();
  static StringBuilder sb = new StringBuilder();
  public static void main(String[] args) {
    int n = scan.nextInt();
    int l = scan.nextInt();
    Deque<Node> dq = new LinkedList<>();

    for (int i = 0 ; i < n ; i++) {
      Node currentNode = new Node(i, scan.nextInt());
      //가장최소 idx가
      //0 1 2 3
      while (!dq.isEmpty() && dq.peekFirst().idx <= i - l) {
        dq.pollFirst();
    }
      // 현재숫자가 집합에 들어있는 마지막 숫자보다 크다면 최솟값이 아니라 넣을 필요가 없음.
      // 같을땐 넣어야 항상 최적의 최솟값유지가능
      while (!dq.isEmpty() && dq.peekLast().value > currentNode.value) {
        dq.pollLast();
      }

      // 집합에 넣을수만 있다면
      dq.add(currentNode);

 
      sb.append(dq.peekFirst().value).append(" ");
    }
    
    System.out.println(sb.toString());

  }

  static class Node {
    int idx;
    int value;

    Node(int idx, int value) {
      this.idx = idx;
      this.value = value;
    }
  }
  
  static class FastReader{
    BufferedReader br;
    StringTokenizer st;

    FastReader() {
      br = new BufferedReader(new InputStreamReader(System.in));
    }
    String next() {
      while (st == null || !st.hasMoreTokens()) {
        try {
          st = new StringTokenizer(br. readLine());
        } catch (IOException e){ 
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