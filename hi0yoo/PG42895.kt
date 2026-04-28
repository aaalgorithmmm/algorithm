/**
 * ## N으로 표현
 *
 * > DP 문제
 *
 * https://school.programmers.co.kr/learn/courses/30/lessons/42895
 */
class PG42895 {
    // N을 8번 이내로 사용해서 number 를 만드는 최소 횟수
    fun solution(N: Int, number: Int): Int {
        if (N == number) return 1

        val dp = Array(9) { mutableSetOf<Int>() }

        for (count in 1..8) {
            // N, NN, NNN ...
            val repeated = N.toString().repeat(count).toInt()
            dp[count].add(repeated)

            // left, right 로 나누어 숫자 사용 횟수 체크
            for (leftCount in 1 until count) {
                val rightCount = count - leftCount

                // N을 leftCount번 사용해서 만들 수 있는 모든 값
                for (left in dp[leftCount]) {
                    // N을 rightCount번 사용해서 만들 수 있는 모든 값
                    for (right in dp[rightCount]) {
                        dp[count].add(left + right)
                        dp[count].add(left - right)
                        dp[count].add(left * right)

                        if (right != 0) dp[count].add(left / right)
                    }
                }
            }

            if (number in dp[count]) return count
        }

        return -1
    }
}