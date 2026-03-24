// https://www.acmicpc.net/problem/1613
fun main() {
    val INF = 200_000_000
    val (n, k) = readln().split(" ").map { it.toInt() }

    val dist = Array(n + 1) { IntArray(n + 1) { INF } }

    repeat(k) {
        val (a, b) = readln().split(" ").map { it.toInt() }
        // a -> b 순 사건 발생
        dist[b][a] = 1
    }

    // 1 -> 2 -> 3 -> 4
    for (z in 1..n) {
        for (i in 1..n) {
            for (j in 1..n) {
                dist[i][j] = minOf(dist[i][j], dist[i][z] + dist[z][j])
            }
        }
    }

    repeat(readln().toInt()) {
        val (a, b) = readln().split(" ").map { it.toInt() }

        // a -> b 로 갈수 있고 b -> a 못가면 a 가 더 앞에 발생한 사건이다.
        println(dist[b][a].compareTo(dist[a][b]))
    }
}