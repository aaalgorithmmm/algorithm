package subo.이진트리;

import java.io.*;
import java.util.*;

/*
 * [백준 5639 - 이진 검색 트리]
 * 전위 순회 결과가 주어질 때, 후위 순회 결과를 출력하는 문제
 *
 * 핵심:
 * 1. 전위 순회의 첫 값 = 루트
 * 2. 루트보다 작은 값들 = 왼쪽 서브트리
 * 3. 루트보다 큰 값들 = 오른쪽 서브트리
 * 4. 왼쪽 -> 오른쪽 -> 루트 순서로 출력하면 후위 순회
 */

public class Main {
  static StringBuilder sb = new StringBuilder();
  static ArrayList<Integer> list = new ArrayList<>();

  /*
   * list[left ~ right]를 하나의 서브트리라고 보고
   * 후위 순회 결과를 sb에 저장
   */
  static void traverse(int left, int right) {
    if (left > right) {
      return;
    }

    int root = list.get(left); // 전위 순회 첫 값은 루트
    int mid = right + 1; // 오른쪽 서브트리 시작 위치

    // root보다 큰 값이 처음 나오는 위치 찾기
    for (int i = left + 1; i <= right; i++) {
      if (list.get(i) > root) {
        mid = i;
        break;
      }
    }

    traverse(left + 1, mid - 1); // 왼쪽 서브트리
    traverse(mid, right); // 오른쪽 서브트리
    sb.append(root).append('\n'); // 마지막에 루트 출력 = 후위 순회
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    while (true) {
      String line = br.readLine();
      if (line == null) {
        break;
      }
      list.add(Integer.parseInt(line));
    }

    traverse(0, list.size() - 1);
    System.out.print(sb);
  }
}
