import java.util.PriorityQueue

fun main() {
    data class Edge(val to: Int, val cost: Int)

    val (v, e) = readln().split(" ").map {it.toInt()}

    val graph = List(v + 1) { mutableListOf<Edge>() }

    val start = readln().toInt()

    repeat(e) {
        val (from, to, cost) = readln().split(" ").map {it.toInt()}
        graph[from].add(Edge(to, cost))
    }

    val dist = IntArray(v + 1) { Int.MAX_VALUE }
    dist[start] = 0

    val pq = PriorityQueue<Edge> { a, b -> a.cost - b.cost }
    pq.add(Edge(start, 0))

    while (pq.isNotEmpty()) {
        val (from, cost) = pq.poll()

        for (edge in graph[from]) {
            val nextCost = cost + edge.cost
            if (nextCost < dist[edge.to]) {
                dist[edge.to] = nextCost
                pq.add(Edge(edge.to, nextCost))
            }
        }
    }

    for (i in 1 .. v) {
        if (dist[i] == Int.MAX_VALUE) println("INF")
        else println(dist[i])
    }
}