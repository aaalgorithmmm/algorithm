package graph

import java.util.PriorityQueue

class Dijkstra {

    fun solution(graph: List<List<Edge>>, start: Int): IntArray {
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

    // 엣지
    data class Edge(val to: Int, val cost: Int)

    // DIST 노드
    data class Node(val num: Int, val cost: Int)
}
