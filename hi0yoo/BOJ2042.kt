// https://www.acmicpc.net/problem/2042
fun main() {
    val (n, m, k) = readln().split(" ").map { it.toInt() }

    val numbers = LongArray(n + 1)
    for (i in 1..n) numbers[i] = readln().toLong()

    // 트리 사이즈 : (n 보다 큰 2의 제곱수 중 가장 작은 것 * 2) - 1
    fun getTreeSize(n: Int): Int {
        var size = 1
        while (size < n) size *= 2
        return size * 2
    }

    val tree = LongArray(getTreeSize(n))

    // 트리 초기화
    fun init(node: Int, start: Int, end: Int): Long {
        // 리프노드 세팅
        if (start == end) {
            tree[node] = numbers[start]
            return tree[node]
        }

        // 자식 노드를 통해 구간합
        val mid = (start + end) / 2
        val leftSum = init(node * 2, start, mid)
        val rightSum = init(node * 2 + 1, mid + 1, end)

        tree[node] = leftSum + rightSum

        return tree[node]
    }

    init(1, 1, n)

    /**
     * 트리 변경
     *
     * @param current 현재 노드 인덱스
     * @param start 현재 노드 구간합의 시작 인덱스
     * @param end 현재 노드 구간합의 종료 인덱스
     * @param target 교체할 인덱스
     * @param value 교체할 값
     */
    fun update(current: Int, start: Int, end: Int, target: Int, value: Long) {
        if (target !in start..end) return

        // 리프노드 변경
        if (start == end) {
            tree[current] = value
            return
        }

        val mid = (start + end) / 2
        val leftNode = current * 2
        val rightNode = current * 2 + 1

        // 왼쪽 트리 업데이트
        update(leftNode, start, mid, target, value)

        // 오른쪽 트리 업데이트
        update(rightNode, mid + 1, end, target, value)

        // 현재 노드 업데이트
        tree[current] = tree[leftNode] + tree[rightNode]

    }

    /**
     * 질의:리프 노드부터 구간합을 구한다. 범위에 포함되는 것만 합산한다.
     *
     * @param current 현재 노드 인덱스
     * @param start 현재 노드 구간합의 시작 인덱스
     * @param end 현재 노드 구간합의 종료 인덱스
     * @param queryLeft 구간합 질의 시작 인덱스
     * @param queryRight 구간합 질의 종료 인덱스
     */
    fun query(current: Int, start: Int, end: Int, queryLeft: Int, queryRight: Int): Long {
        // 범위에 포함되지 않으면 구간합을 구할 수 없다.
        if (queryRight < start || end < queryLeft) return 0L

        // 범위에 포함되면 합산한다.
        if (queryLeft <= start && end <= queryRight) return tree[current]

        val mid = (start + end) / 2
        val leftNode = current * 2
        val rightNode = current * 2 + 1

        val leftSum = query(leftNode, start, mid, queryLeft, queryRight)
        val rightSum = query(rightNode, mid + 1, end, queryLeft, queryRight)

        return leftSum + rightSum
    }

    val answer = StringBuilder()

    repeat(m + k) {
        val (a, b, c) = readln().split(" ").map { it.toLong() }

        // b번째 수를 c로 변경
        if (a == 1L) update(1, 1, n, b.toInt(), c)
        // b~c 구간합
        else answer.append(query(1, 1, n, b.toInt(), c.toInt())).append("\n")
    }

    print(answer)
}