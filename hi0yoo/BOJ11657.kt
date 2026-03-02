// https://www.acmicpc.net/problem/11657
fun main() {
    val (N, M) = readln().split(" ").map { it.toInt() }

    data class Edge(val from: Int, val to: Int, val cost: Int) {
        constructor(arr: List<Int>): this(arr[0], arr[1], arr[2])
    }

    val edges = List(M) { Edge(readln().split(" ").map { it.toInt() }) }

    val dist = LongArray(N + 1) { Long.MAX_VALUE }
    dist[1] = 0

    repeat(N - 1) {
        for (edge in edges) {
            if (dist[edge.from] != Long.MAX_VALUE && dist[edge.from] + edge.cost < dist[edge.to]) {
                dist[edge.to] = dist[edge.from] + edge.cost
            }
        }
    }

    var negativeCycle = false;
    for (edge in edges) {
        if (dist[edge.from] != Long.MAX_VALUE && dist[edge.from] + edge.cost < dist[edge.to]) {
            negativeCycle = true;
            break;
        }
    }

    if (negativeCycle) {
        println(-1)
        return
    }

    for (i in 2 .. N) {
        if (dist[i] == Long.MAX_VALUE) println(-1)
        else println(dist[i])
    }
}