import java.util.PriorityQueue

// https://www.acmicpc.net/problem/1854
fun main() {
    data class Edge(val to: Int, val cost: Int)
    data class Node(val num: Int, val cost: Int)

    val (n, m, k) = readln().split(" ").map { it.toInt() }

    val graph = List(n + 1) { mutableListOf<Edge>() }

    repeat(m) {
        val (from, to, cost) = readln().split(" ").map { it.toInt() }
        graph[from].add(Edge(to, cost))
    }

    // 최단거리 배열
    // 우선순위 큐 역순 정렬
    // **우선순위가 가장 높은 값보다 작은 값을 가질 경우, 교체 대상**
    val dist = Array(n + 1) { PriorityQueue<Int>(k) { a, b -> -(a - b) } }
    val start = 1
    dist[start].add(0) // i번 도시에서 i번 도시로 가는 최단경로는 0

    val pq = PriorityQueue<Node> { a, b -> a.cost - b.cost }
    pq.add(Node(start, 0))

    while (pq.isNotEmpty()) {
        val (from, cost) = pq.poll()

        for (edge in graph[from]) {
            val nextCost = cost + edge.cost

            // 최단거리 배열이 가득차지 않았으면 추가
            if (dist[edge.to].size < k) {
                dist[edge.to].add(nextCost)
                pq.add(Node(edge.to, nextCost))
            }
            // 가득 찼으면 가장 큰 값과 비교하여 더 작을 경우만 교체
            else if (nextCost < dist[edge.to].peek()) {
                dist[edge.to].poll()
                dist[edge.to].add(nextCost)
                pq.add(Node(edge.to, nextCost))
            }
        }
    }

    for (to in 1..n) {
        if (dist[to].size == k) println(dist[to].peek())
        else println(-1)
    }
}
