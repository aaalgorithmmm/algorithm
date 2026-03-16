package graph

/**
 * ### 벨만 포드 알고리즘
 *
 * 음수 사이클 체크
 * 모든 간선을 저장하고, 간선을 V 만큼 탐색해서 V번째 탐색에 음수 사이클을 판단하는 알고리즘
 *
 * 시간 복잡도 : O(VE) : V:노드 수, E:에지 수
 */
class BellmanFord {

    fun solution(nodeCount: Int, edges: List<Edge>, start: Int): IntArray {
        val dist = IntArray(nodeCount + 1) { Int.MAX_VALUE }
        dist[start] = 0

        // 노드 개수 - 1번 만큼 반복한다.
        repeat(nodeCount - 1) {
            // 모든 에지에 대해서 탐색하여 최단거리가 있으면 업데이트 한다.
            for (edge in edges) {
                if (dist[edge.from] != Int.MAX_VALUE &&
                    dist[edge.from] + edge.cost < dist[edge.to]
                ) {
                    dist[edge.to] = dist[edge.from] + edge.cost
                }
            }
        }

        // 음수 사이클 확인
        var negativeCycle = false
        for (edge in edges) {
            if (dist[edge.from] != Int.MAX_VALUE &&
                dist[edge.from] + edge.cost < dist[edge.to]
            ) {
                negativeCycle = true
                break
            }
        }

        if (negativeCycle) return IntArray(0)

        return dist
    }

    data class Edge(val from: Int, val to: Int, val cost: Int)
}

fun main() {
    val bf = BellmanFord()
    val dist = bf.solution(
        5, listOf(
            BellmanFord.Edge(1, 2, 8),
            BellmanFord.Edge(1, 3, 3),
            BellmanFord.Edge(2, 5, 5),
            BellmanFord.Edge(3, 4, 7),
            BellmanFord.Edge(4, 2, -4),
            BellmanFord.Edge(5, 4, -2),
        ), 1
    )

    println(dist.joinToString(" "))
}