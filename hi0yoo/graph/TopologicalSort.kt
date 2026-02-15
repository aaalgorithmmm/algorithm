package graph

import java.util.*

/**
 * ## 위상정렬
 *
 *    https://www.acmicpc.net/problem/1516
 */
class TopologicalSort(
    val graph: List<List<Int>>
) {
    val indegrees: MutableList<Int> = MutableList(graph.size) { 0 }

    init {
        for (from in graph.indices) {
            for (to in graph[from]) {
                indegrees[to]++
            }
        }
    }

    /**
     * @return 위상정렬된 그래프에서 노드의 순서
     */
    fun sort(): List<Int> {
        // 차수를 복사하여 사용한다.
        val copiedIndegrees = indegrees.toMutableList()

        // 차수가 0인 노드부터 방문한다.
        val queue = ArrayDeque<Int>()
        for (node in graph.indices) {
            if (copiedIndegrees[node] == 0) queue.add(node)
        }

        val result: MutableList<Int> = mutableListOf()
        while (queue.isNotEmpty()) {
            val from = queue.poll()
            result.add(from)

            // 현재 노드에서 갈 수 있는 다음 노드를 방문한다. (해당 노드의 차수를 깎는다)
            for (to in graph[from]) {
                copiedIndegrees[to]-- // 방문

                // 차수가 0이 되었으면 다음 방문으로 넣는다.
                if (copiedIndegrees[to] == 0) queue.add(to)
            }
        }

        return result
    }
}