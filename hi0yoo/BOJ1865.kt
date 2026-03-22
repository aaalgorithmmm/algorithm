// https://www.acmicpc.net/problem/1865
fun main() {
    // testcase
    val TC = readln().toInt()

    val result = mutableListOf<String>()
    repeat(TC) {
        val (n, m, w) = readln().split(" ").map { it.toInt() }

        val edges = mutableListOf<Triple<Int, Int, Int>>()
        repeat(m) {
            val (s, e, t) = readln().split(" ").map { it.toInt() }
            // 도로는 방향이 없다
            edges.add(Triple(s, e, t))
            edges.add(Triple(e, s, t))
        }

        repeat(w) {
            val (s, e, t) = readln().split(" ").map { it.toInt() }
            // 웜홀은 방향이 있다.
            edges.add(Triple(s, e, -t))
        }

        result.add(solution(n, m, w, edges))
    }

    println(result.joinToString("\n"))
}

/**
 * 아무 지점에서나 시작해서 다시 그 지점으로 돌아올때, 음수 사이클이 존재하는지 여부를 묻는 문제
 *
 * @param n 지점의 수
 * @param m 도로의 개수
 * @param w 웜홀의 개수
 * @param edges edge 리스트
 * @return YES or NO
 */
fun solution(n: Int, m: Int, w: Int, edges: List<Triple<Int, Int, Int>>): String {
    // 모든 노드를 시작점으로 취급한다.
    val dist = IntArray(n + 1) { 0 }

    // 노드 개수 - 1번 만큼 반복한다.
    repeat(n - 1) {
        // 모든 에지에 대해서 탐색하여 최단거리가 있으면 업데이트 한다.
        for ((from, to, cost) in edges) {
            val nextCost = dist[from] + cost

            if (nextCost < dist[to]) {
                dist[to] = nextCost
            }
        }
    }

    for ((from, to, cost) in edges) {
        // 여기서도 갱신이 되면 사이클이 존재한다는 의미
        if (dist[from] + cost < dist[to]) {
            return "YES"
        }
    }

    return "NO"
}