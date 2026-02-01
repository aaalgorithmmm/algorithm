// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.util.LinkedList;
// import java.util.Queue;
// import java.util.StringTokenizer;

// /* DFS와 BFS (백준1260) */
// public class Main {
// static FastReader scan = new FastReader();
// static StringBuilder sb = new StringBuilder();
// static int N, M, V;
// static int[][] map;
// static boolean[] visit;

// public static void main(String[] args) {
// N = scan.nextInt();
// M = scan.nextInt();
// V = scan.nextInt();
// visit = new boolean[N + 1];
// map = new int[N + 1][N + 1];

// for (int i = 1; i <= M; i++) {
// int x = scan.nextInt();
// int y = scan.nextInt();
// map[x][y] = 1;
// map[y][x] = 1;
// }
// // process
// dfs(V);
// visit = new boolean[N + 1]; // bfs들어가기전 초기화
// sb.append("\n");
// bfs(V);
// System.out.println(sb.toString());
// }

// static void dfs(int x) {
// sb.append(x).append(" ");
// visit[x] = true;
// for (int y = 1; y <= N; y++) {
// if (map[x][y] == 0 || visit[y])
// continue;
// dfs(y);
// }
// }

// static void bfs(int x) {
// Queue<Integer> queue = new LinkedList<>();
// queue.add(x);
// visit[x] = true;
// while (!queue.isEmpty()) {
// x = queue.poll();
// sb.append(x).append(" ");
// for (int y = 1; y <= N; y++) {
// if (map[x][y] == 0 || visit[y])
// continue;
// queue.add(y);
// visit[y] = true;
// }
// }
// }

// static class FastReader {
// BufferedReader br;
// StringTokenizer st;

// FastReader() {
// br = new BufferedReader(new InputStreamReader(System.in));
// }

// String next() {
// while (st == null || !st.hasMoreTokens()) {
// try {
// st = new StringTokenizer(br.readLine());
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
// return st.nextToken();
// }

// int nextInt() {
// return Integer.parseInt(next());
// }
// }
// }