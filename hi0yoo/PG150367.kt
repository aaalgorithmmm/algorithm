/**
 * # 표현 가능한 이진트리
 * - https://school.programmers.co.kr/learn/courses/30/lessons/150367?language=kotlin
 */
class PG150367 {
    fun solution(numbers: LongArray): IntArray {
        val answer = IntArray(numbers.size)

        for (i in numbers.indices) {
            answer[i] = if (canMakeTree(numbers[i])) 1 else 0
        }

        return answer
    }

    private fun canMakeTree(number: Long): Boolean {
        val binary = java.lang.Long.toBinaryString(number)

        // 포화 이진트리 형태가 되도록 길이 보정
        var treeSize = 1
        while (treeSize < binary.length) treeSize = treeSize * 2 + 1

        val tree = "0".repeat(treeSize - binary.length) + binary

        // 트리 체크
        return check(tree, 0, tree.lastIndex, true)
    }

    private fun check(tree: String, left: Int, right: Int, parentIsReal: Boolean): Boolean {
        // 더 볼 구간이 없으면 정상
        if (left > right) return true

        // 현재 구간의 가운데가 루트
        val mid = (left + right) / 2
        val currentIsReal = tree[mid] == '1'

        // 부모가 0, 현재가 1이면 표현 불가
        if (!parentIsReal && currentIsReal) return false

        // 죄우 서브트리 검사
        return check(tree, left, mid - 1, currentIsReal) &&
                check(tree, mid + 1, right, currentIsReal)
    }
}