import java.util.ArrayDeque

data class Edge(val from: Int, val to: Int, val cost: Int)

class UnionFind(size: Int) {
    private val map = IntArray(size + 1) { it }

    fun find(x: Int): Int {
        if (map[x] == x) return x
        map[x] = find(map[x])
        return map[x]
    }

    fun union(a: Int, b: Int): Boolean {
        val pa = find(a)
        val pb = find(b)

        if (pa == pb) return false
        map[pb] = pa
        return true
    }
}

val dr = intArrayOf(-1, 1, 0, 0)
val dc = intArrayOf(0, 0, -1, 1)

// https://www.acmicpc.net/problem/17472
fun main() {
    val (n, m) = readln().split(" ").map { it.toInt() }

    val map = Array(n) {
        readln().split(" ").map { it.toInt() }.toIntArray()
    }

    // 섬 번호 라벨링 : 1은 아직 방문하지 않음
    var id = 2
    for (row in 0 until n) {
        for (col in 0 until m) {
            if (map[row][col] == 1) {
                val queue = ArrayDeque<Pair<Int, Int>>()
                queue.add(Pair(row, col))
                map[row][col] = id

                while (queue.isNotEmpty()) {
                    val (r, c) = queue.removeFirst()

                    for (dir in 0 until 4) {
                        val nr = r + dr[dir]
                        val nc = c + dc[dir]

                        if (!inRange(nr, nc, n, m)) continue
                        if (map[nr][nc] != 1) continue

                        map[nr][nc] = id
                        queue.add(Pair(nr, nc))
                    }
                }
                id++
            }
        }
    }

    val islandCount = id - 2

    if (islandCount <= 1) {
        println(0)
        return
    }

    // 간선 만들기
    val edges = mutableListOf<Edge>()

    for (row in 0 until n) {
        for (col in 0 until m) {
            // 섬일 경우만 탐색
            if (map[row][col] >= 2) {
                val from = map[row][col]

                for (dir in 0 until 4) {
                    var nextRow = row + dr[dir]
                    var nextCol = col + dc[dir]
                    var length = 0

                    // 탐색 지점이 바다가 아니면 pass
                    if (!inRange(nextRow, nextCol, n, m) || map[nextRow][nextCol] != 0) continue

                    while (inRange(nextRow, nextCol, n, m)) {
                        // 시작지점으로 돌아오면 탈락
                        if (map[nextRow][nextCol] == from) break

                        if (map[nextRow][nextCol] >= 2) {
                            val to = map[nextRow][nextCol]
                            if (length >= 2) {
                                // map 섬 번호는 2부터 -> 노드 번호는 -1
                                edges.add(Edge(from - 1, to - 1, length))
                            }
                            break
                        }

                        nextRow += dr[dir]
                        nextCol += dc[dir]
                        length++
                    }
                }
            }
        }
    }

    // MST
    edges.sortBy { it.cost }

    val uf = UnionFind(islandCount)
    var total = 0
    var connected = 0

    for (edge in edges) {
        if (uf.union(edge.from, edge.to)) {
            total += edge.cost
            connected++

            if (connected == islandCount - 1) break
        }
    }

    println(if (connected == islandCount - 1) total else -1)
}

fun inRange(row: Int, col: Int, n: Int, m: Int): Boolean {
    return row in 0 until n && col in 0 until m
}