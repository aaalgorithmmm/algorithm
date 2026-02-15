import java.util.PriorityQueue

// https://www.acmicpc.net/problem/1916
fun main() {
    data class Edge(val to: Int, val cost: Int)
    data class Node(val num: Int, val cost: Int)

    fun dijkstra(graph: List<List<Edge>>, start: Int): IntArray {
        val dist = IntArray(graph.size) { Int.MAX_VALUE }
        dist[start] = 0

        val pq = PriorityQueue<Node> { a, b -> a.cost - b.cost }
        pq.add(Node(start, 0))

        while (pq.isNotEmpty()) {
            val (from, cost) = pq.poll()

            // 더 짧은 경로가 있다면 pass
            if (cost > dist[from]) continue

            for (edge in graph[from]) {
                val nextCost = cost + edge.cost

                // 더 짧은 경로이면 결과값을 갱신하고 탐색한다.
                if (nextCost < dist[edge.to]) {
                    dist[edge.to] = nextCost
                    pq.add(Node(edge.to, nextCost))
                }
            }
        }

        return dist
    }

    val n = readln().toInt()
    val graph = List(n + 1) { mutableListOf<Pair<Int, Int>>() }

    val m = readln().toInt()
    for (i in 1..m) {
        val (a, b, c) = readln().split(" ").map { it.toInt() }
        graph[a].add(b to c)
    }

    val (start, end) = readln().split(" ").map { it.toInt() }

    println(dijkstra(graph.map { it.map { (to, cost) -> Edge(to, cost) } }, start)[end])
}