import java.util.PriorityQueue

// https://www.acmicpc.net/problem/1504
fun main() {
    // 1_000 * 200_000
    val INF = 200_000_000

    // 1번 정점에서 N번 정점으로 최단거리로 이동
    // 임의의 두 정점은 반드시 통과

    // N:정점 개수, E:간선 개수
    val (N, E) = readln().split(" ").map { it.toInt() }

    // val (b, c) = graph[a] : a -> b 경로의 비용이 c
    val graph = List(N + 1) { mutableListOf<Pair<Int, Int>>() }

    repeat(E) {
        val (a, b, c) = readln().split(" ").map { it.toInt() }

        // 양방향이다
        graph[a].add(Pair(b, c))
        graph[b].add(Pair(a, c))
    }

    val (v1, v2) = readln().split(" ").map { it.toInt() }

    fun dijkstra(graph: List<List<Pair<Int, Int>>>, start: Int, end: Int): Int {
        val dist = IntArray(graph.size) { INF }
        dist[start] = 0

        // val (node, costSum) = pq.poll() : start -> node 최단거리 비용이 costSum
        val pq = PriorityQueue<Pair<Int, Int>> { a, b -> a.second - b.second }
        pq.add(Pair(start, 0))

        while (pq.isNotEmpty()) {
            val (node, costSum) = pq.poll()
            for (to in graph[node]) {
                val nextCost = costSum + to.second
                if (nextCost < dist[to.first]) {
                    dist[to.first] = nextCost
                    pq.add(Pair(to.first, nextCost))
                }
            }
        }

        return dist[end]
    }

    val r1 = dijkstra(graph, 1, v1) + dijkstra(graph, v1, v2) + dijkstra(graph, v2, N)
    val r2 = dijkstra(graph, 1, v2) + dijkstra(graph, v2, v1) + dijkstra(graph, v1, N)

    if (r1 >= INF && r2 >= INF) println(-1)
    else println(minOf(r1, r2))
}