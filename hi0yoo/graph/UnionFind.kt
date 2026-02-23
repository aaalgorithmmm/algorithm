package graph

/**
 * ## 유니온파인드
 *
 *    https://www.acmicpc.net/problem/1717
 */
class UnionFind(graphSize: Int) {
    val map: IntArray

    init {
        map = IntArray(graphSize + 1) { it }
    }

    /**
     * ### union
     *
     * a 노드가 속한 그룹과 b 노드가 속한 그룹을 합친다.
     * b 노드의 대표 노드가 a 노드의 대표 노드를 가리키도록 한다.
     *
     * @param a 노드 번호
     * @param b 노드 번호
     */
    fun union(a: Int, b: Int) {
        map[find(b)] = map[find(a)]
    }

    /**
     * ### find
     *
     * 특정 노드가 속한 그룹의 대표 노드 번호를 찾는다.
     *
     * @param k 대표노드를 찾을 노드 번호
     * @return k 노드가 속한 그룹의 대표 노드 번호
     */
    fun find(k: Int): Int {
        var cur = k
        while (map[cur] != cur) {
            cur = map[cur]
        }

        return cur
    }

    fun isSameGroup(a: Int, b: Int): Boolean {
        return find(a) == find(b)
    }
}