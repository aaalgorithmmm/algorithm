package subo.트라이.접두사찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* BOJ 14426 접두사 찾기 */
/**
 * [문제 요약]
 * 집합 S에 들어 있는 문자열들 중, 적어도 하나의 "접두사"가 되는 쿼리 문자열이 몇 개인지 센다.
 * (쿼리 q에 대해, S 안에 q로 시작하는 문자열 s가 존재하면 성립)
 *
 * [풀이 흐름]
 * Step 1. 집합 S에 들어 있는 모든 문자열을 트라이(Trie)에 삽입한다.
 * Step 2. 각 문자는 'a' ~ 'z'이므로 자식을 길이 26 배열로 둔다.
 * Step 3. 쿼리 문자열을 트라이 루트에서 한 글자씩 따라간다.
 * Step 4. 끝까지 따라갈 수 있으면, 그 접두사 경로가 존재한다는 뜻이므로 S의 어떤 문자열의 접두사다.
 * Step 5. 중간에 자식이 없으면, S의 어떤 문자열도 그 접두사로 시작하지 않는다.
 */
public class Main {
  static FastReader scan = new FastReader();

  public static void main(String[] args) {
    int n = scan.nextInt();
    int m = scan.nextInt();

    Trie trie = new Trie();

    // Step 1. 집합 S 문자열을 트라이에 넣는다.
    for (int i = 0; i < n; i++) {
      trie.insert(scan.next());
    }

    int answer = 0;

    // Step 3 ~ 5. 쿼리마다 접두사 여부 판별
    for (int i = 0; i < m; i++) {
      if (trie.isPrefixOfSomeWord(scan.next())) {
        answer++;
      }
    }

    System.out.println(answer);
  }

  /** 소문자 알파벳만 다루는 기본 트라이 */
  static class Trie {
    private final TrieNode root = new TrieNode();

    void insert(String word) {
      TrieNode current = root;
      for (int i = 0; i < word.length(); i++) {
        int index = word.charAt(i) - 'a';
        if (current.children[index] == null) {
          current.children[index] = new TrieNode();
        }
        current = current.children[index];
      }
    }

    /**
     * word가 집합 S의 어떤 문자열의 접두사인지 여부.
     * 트라이에서 해당 글자 경로가 모두 있으면 true.
     */
    boolean isPrefixOfSomeWord(String word) {
      TrieNode current = root;
      for (int i = 0; i < word.length(); i++) {
        int index = word.charAt(i) - 'a';
        if (current.children[index] == null) {
          return false;
        }
        current = current.children[index];
      }
      return true;
    }
  }

  static class TrieNode {
    // 다음 글자 'a'..'z'에 대응하는 자식
    TrieNode[] children = new TrieNode[26];
  }

  static class FastReader {
    BufferedReader br;
    StringTokenizer st;

    FastReader() {
      br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
      while (st == null || !st.hasMoreTokens()) {
        try {
          st = new StringTokenizer(br.readLine());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      return st.nextToken();
    }

    int nextInt() {
      return Integer.parseInt(next());
    }
  }
}
